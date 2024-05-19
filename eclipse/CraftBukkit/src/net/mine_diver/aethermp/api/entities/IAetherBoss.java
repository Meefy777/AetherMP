package net.mine_diver.aethermp.api.entities;

import java.util.List;

import net.mine_diver.aethermp.api.util.LootType;
import net.mine_diver.aethermp.blocks.tileentities.TileEntityAetherMPTreasureChest;
import net.mine_diver.aethermp.dimension.world.generation.AetherGenDungeon;
import net.mine_diver.aethermp.dimension.world.generation.AetherGenDungeonBronze;
import net.mine_diver.aethermp.dimension.world.generation.AetherGenDungeonSilver;
import net.mine_diver.aethermp.util.IDungeonLootGenerator;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.mod_AetherMp;

public interface IAetherBoss {

    public int getBossHP();

    public int getBossMaxHP();

    public boolean isCurrentBoss(EntityPlayer entityPlayer);

    public int getBossEntityID();

    public String getBossTitle();
    
    public void stopFight();
    
    public BossType getBossType();
    
    public enum BossType {
    	BRONZE(AetherGenDungeonBronze.SINGLETON_BRONZE_LOOT, LootType.BRONZE),
    	SILVER(AetherGenDungeonSilver.SINGLETON_SILVER_LOOT, LootType.SILVER),
    	GOLD(AetherGenDungeon.SINGLETON_GOLD_LOOT, LootType.GOLD),
    	OTHER(null, null);
    	
    	BossType(IDungeonLootGenerator lootGen, LootType lootType) {
    		this.lootGen = lootGen;
    		this.lootType = lootType;
    	}
    	
    	private final IDungeonLootGenerator lootGen;
    	private final LootType lootType;
    }
    
    public List<EntityPlayer> getTargetList();
    
    public void setTargetList(List<EntityPlayer> list);
    
    public EntityPlayer getCurrentTarget();   
    
    public void findNewTarget();
    
    default void populatePerPlayerLootPool(TileEntityChest chestCheck) {
    	if(!mod_AetherMp.perPlayerBossLoot)
    		return;
    	TileEntityAetherMPTreasureChest chest;
    	if (chestCheck instanceof TileEntityAetherMPTreasureChest) { 
    		chest = (TileEntityAetherMPTreasureChest) chestCheck;
    		chest.clearMainInventory();
    	} else {
    		chest = new TileEntityAetherMPTreasureChest();
    		chestCheck.world.o(chestCheck.x, chestCheck.y, chestCheck.z);
    		chestCheck.world.setTileEntity(chestCheck.x, chestCheck.y, chestCheck.z, chest); 
    	}
    	
    	LootType type = getBossType().lootType;
    	for (EntityPlayer player : this.getTargetList()) {
    		ItemStack[] loot = new ItemStack[chest.getSize()];		
            for(int p = 0; p < type.getGuaranteedLootPerChest() + player.world.random.nextInt(type.getMaximumLootPerChest() - type.getGuaranteedLootPerChest()); p++)
            	loot[player.getRandom().nextInt(chest.getSize())] = getBossType().lootGen.getLoot(player.getRandom());
    		chest.playerChestLootItems.put(player.name, loot);    
    	}
    }
    
}
