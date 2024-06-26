package net.mine_diver.aethermp;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Filter;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPluginLoader;

import com.earth2me.essentials.Mob;
import com.earth2me.essentials.Mob.Enemies;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.CraftAetherEventFactory;
import net.mine_diver.aethermp.crafting.WorkbenchManager;
import net.mine_diver.aethermp.dimension.DimensionManager;
import net.mine_diver.aethermp.entities.EntityManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.player.PlayerBaseAether;
import net.mine_diver.aethermp.proxy.MinecraftLoggerProxy;
import net.mine_diver.aethermp.util.Achievements;
import net.mine_diver.aethermp.util.AetherMPDataHandler;
import net.mine_diver.aethermp.util.BlockPlacementHandler;
import net.minecraft.server.BaseMod;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ModLoader;
import net.minecraft.server.PlayerAPI;
import net.minecraft.server.SAPI;
import net.minecraft.server.mod_AetherMp;

public class Core {
	
	public void preInit(BaseMod mod) {
		Filter target = LOGGER.getFilter();
		LOGGER.setFilter((record) -> {
			record.setMessage("[" + LOGGER.getName() + "] - " + record.getMessage());
	        return target == null || target.isLoggable(record);
		});
		LOGGER.info("Pre initialization...");
		MinecraftServer.log.addHandler(new MinecraftLoggerProxy());
		ModLoader.SetInGameHook(mod, true, false);
		
		if (mod_AetherMp.sliderConfigurableWeapons) {
			String[] weapons = mod_AetherMp.sliderWeaponList.contains(",") ? mod_AetherMp.sliderWeaponList.split(",") : new String[]{};
			for (int i = 0; i < weapons.length; i++) 
				addSliderWeapon(Integer.valueOf(weapons[i]));
			addSliderWeapon(Item.WOOD_PICKAXE.id);
			addSliderWeapon(Item.STONE_PICKAXE.id);
			addSliderWeapon(Item.IRON_PICKAXE.id);
			addSliderWeapon(Item.GOLD_PICKAXE.id);
			addSliderWeapon(Item.DIAMOND_PICKAXE.id);
			addSliderWeapon(ItemManager.PickSkyroot.id);
			addSliderWeapon(ItemManager.PickHolystone.id);
			addSliderWeapon(ItemManager.PickZanite.id);
			addSliderWeapon(ItemManager.PickGravitite.id);
			addSliderWeapon(ItemManager.PickValkyrie.id);
		}	
	}
	
	private void addSliderWeapon(Integer ID) {
		mod_AetherMp.sliderWeapons.add(ID);
	}
	
	public void init() {
		LOGGER.info("Initialization...");
		dungeonAllowedCommands = mod_AetherMp.dungeonAllowedCommands.split(";");
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		WorkbenchManager.registerRecipes();
		EntityManager.registerEntities();
		SAPI.interceptAdd(new BlockPlacementHandler());
		PlayerAPI.RegisterPlayerBase(PlayerBaseAether.class);
		DimensionManager.registerDimensions();
		dataHandler = new AetherMPDataHandler();
		dataHandler.Initialize();
	}
	
	public void onBukkit() {
		LOGGER.info("On Bukkit initialization...");
		try {
			PluginManager pm = Bukkit.getServer().getPluginManager();
			pm.registerInterface(JavaPluginLoader.class);
			pm.loadPlugin(new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()));
		} catch (InvalidPluginException | InvalidDescriptionException | UnknownDependencyException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void postInit(BaseMod mod, MinecraftServer game) {
		LOGGER.info("Post initialization...");
		if (game.server.getPluginManager().isPluginEnabled("Essentials")) {
			LOGGER.info("Essentials detected. Registering entities...");
			JavaPluginLoader jpl = (JavaPluginLoader)game.server.getPluginManager().getPlugin("Essentials").getPluginLoader();
			EntityManager.registerEssentialsEntities((Class<Mob>) jpl.getClassByName("com.earth2me.essentials.Mob"), (Class<Enemies>) jpl.getClassByName("com.earth2me.essentials.Mob$Enemies"));
		}
	}
	
	public void handleSendKey(EntityPlayer entityplayer, int key) {
		PlayerBaseAether playerBase = (PlayerBaseAether)PlayerAPI.getPlayerBase(entityplayer, PlayerBaseAether.class);
		if (key == 0) {
			Environment env = entityplayer.getBukkitEntity().getWorld().getEnvironment();
			if (((mod_AetherMp.bookOfLoreCoolDown && playerBase.canReceiveLore) || (!mod_AetherMp.bookOfLoreCoolDown)) && !CraftAetherEventFactory.callPlayerGetLoreEvent((Player)entityplayer.getBukkitEntity()).isCancelled()) {
				if (env.equals(Environment.valueOf(mod_AetherMp.nameDimensionAether.toUpperCase())))
	                entityplayer.inventory.pickup(new ItemStack(ItemManager.LoreBook, 1, 2));
	            else if(env.equals(Environment.NORMAL))
	                entityplayer.inventory.pickup(new ItemStack(ItemManager.LoreBook, 1, 0));
	            else if(env.equals(Environment.NETHER))
	                entityplayer.inventory.pickup(new ItemStack(ItemManager.LoreBook, 1, 1));
				playerBase.canReceiveLore = false;
			}
		}
	}
	
	public void takenFromCrafting(EntityHuman entityhuman, ItemStack itemstack) {
		if (entityhuman instanceof EntityPlayer) {
			if(itemstack.id == BlockManager.Enchanter.id)
				Achievements.giveAchievement(Achievements.enchanter, (EntityPlayer) entityhuman);
	        if(itemstack.id == ItemManager.PickGravitite.id || itemstack.id == ItemManager.ShovelGravitite.id || itemstack.id == ItemManager.AxeGravitite.id || itemstack.id == ItemManager.SwordGravitite.id)
	        	Achievements.giveAchievement(Achievements.gravTools, (EntityPlayer) entityhuman);
		}
	}
	
	public final Logger LOGGER = Logger.getLogger("AetherMP");
	public AetherMPDataHandler dataHandler;
	
	public String[] dungeonAllowedCommands;
}
