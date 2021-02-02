package net.minecraft.server;

import java.io.IOException;
import java.util.Properties;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.entities.EntityFlyingCow;
import net.mine_diver.aethermp.entities.EntityMoa;
import net.mine_diver.aethermp.entities.EntityPhyg;

public class mod_AetherMp extends BaseModMp {

	public mod_AetherMp() {
		try {
			info.load(getClass().getResourceAsStream("/" + CORE.getClass().getPackage().getName().replace(".", "/") + "/mod.info"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String Name() {
		return info.getProperty("name");
	}
	
	public String Description() {
		return info.getProperty("description");
	}
	
	@Override
	public String Version() {
		return info.getProperty("version");
	}
	
	@Override
	public void ModsLoaded() {
		super.ModsLoaded();
		CORE.preInit(this);
	}
	
	@Override
	public void HandleSendKey(EntityPlayer entityplayer, int key) {
		CORE.handleSendKey(entityplayer, key);
	}
	
	@Override
	public void TakenFromCrafting(EntityHuman entityhuman, ItemStack itemstack) {
		CORE.takenFromCrafting(entityhuman, itemstack);
	}
	
	@Override
	public void OnTickInGame(MinecraftServer game) {
		if (firstTick) {
			CORE.postInit(this, game);
			firstTick = false;
		}
	}

	@Override
	public void HandlePacket(Packet230ModLoader packet, EntityPlayer player) {
		switch(packet.packetType) {
			case 60: {
				if (player.vehicle != null && player.vehicle instanceof EntityMoa)
					((EntityMoa)player.vehicle).jrem = packet.dataInt[0];
				break;
			}
		
			case 61: {
				if (player.vehicle != null) {
					if (player.vehicle instanceof EntityPhyg) {
						EntityPhyg mount = (EntityPhyg)player.vehicle;
						if (packet.dataInt[0] == 1)
							mount.hasJumped = true;
						else
							mount.hasJumped = false;
					}
					if (player.vehicle instanceof EntityFlyingCow) {
						EntityFlyingCow mount = (EntityFlyingCow)player.vehicle;
						if (packet.dataInt[0] == 1)
							mount.hasJumped = true;
						else 
							mount.hasJumped = false;
					}
				}
				break;
			}
		
			case 69: {
				if (player.vehicle instanceof EntityLiving) {
					EntityLiving mount = (EntityLiving) player.vehicle;
					mount.motX = packet.dataFloat[0];
					mount.motY = packet.dataFloat[1];
					mount.motZ = packet.dataFloat[2];
					mount.locX = packet.dataFloat[3];
					mount.locY = packet.dataFloat[4];
					mount.locZ = packet.dataFloat[5];
					mount.yaw = packet.dataFloat[6];
					mount.pitch = packet.dataFloat[7];
					
					player.motX = packet.dataFloat[0];
					player.motY = packet.dataFloat[1];
					player.motZ = packet.dataFloat[2];
					player.locX = packet.dataFloat[3];
					player.locY = packet.dataFloat[4];
					player.locZ = packet.dataFloat[5];
					player.yaw = packet.dataFloat[6];
					player.pitch = packet.dataFloat[7];
				}
				
				
				break;
			}
		}
	}
	
	public static final Core CORE = new Core();
	private final Properties info = new Properties();
	
	private boolean firstTick = true;
	
	@MLProp
	public static boolean
	
	allowLoreBookKeyBind = true,
	punishQuittingDuringFight = true,
	preventTeleportDuringFight = true,
	punishTeleportDuringFight = false,
	bookOfLoreCoolDown = false;
	
	@MLProp
	public static String
	
	nameDimensionAether = "Aether",
	
	dungeonAllowedCommands = "/tell;/msg;/help";
	
	@MLProp
	public static int
	
	idDimensionAether = 3,
	
	idGuiEnchanter = 80,
	idGuiTreasureChest = 81,
	idGuiFreezer = 82,
	idGuiLore = 83,
	idGuiIncubator = 84,
	
	idEntityFloatingBlock = 80,
	idEntityMimic = 81,
	idEntityZephyr = 85,
	idEntitySheepuff = 86,
	idEntityAechorPlant = 88,
	idEntitySentry = 89,
	idEntityZephyrSnowball = 100,
	idEntityCloudParachute = 101,
	idEntityDartEnchanted = 102,
	idEntityDartGolden = 103,
	idEntityDartPoison = 104,
	idEntityAetherLightning = 105,
	idEntityLightningKnife = 106,
	idEntityNotchWave = 107,
	idEntityFlamingArrow = 108,
	idEntityMiniCloud = 109,
	idEntityFiroBall = 110,
	idEntityPoisonNeedle = 111,
	idEntitySlider = 112,
	idEntityPhyg = 113,
	idEntityFlyingCow = 114,
	idEntityMoa = 115,
	idEntityAerBunny = 116,
	
	rarityAechorPlant = 8,
	rarityZephyr = 5,
	raritySheepuff = 10,
	rarityPhyg = 12,
	rarityFlyingCow = 10,
	rarityMoa = 10,
	rarityAerbunny = 11,
	
    idBlockAetherPortal = 165,
    idBlockAetherDirt = 166,
    idBlockAetherGrass = 167,
    idBlockQuicksoil = 168,
    idBlockHolystone = 169,
    idBlockIcestone = 170,
    idBlockAercloud = 171,
    idBlockAerogel = 172,
    idBlockEnchanter = 173,
    idBlockIncubator = 174,
    idBlockLog = 175,
    idBlockPlank = 176,
    idBlockSkyrootLeaves = 177,
    idBlockGoldenOakLeaves = 178,
    idBlockSkyrootSapling = 179,
    idBlockGoldenOakSapling = 180,
    idBlockAmbrosiumOre = 181,
    idBlockAmbrosiumTorch = 182,
    idBlockZaniteOre = 183,
    idBlockGravititeOre = 184,
    idBlockEnchantedGravitite = 185,
    idBlockTrap = 186,
    idBlockChestMimic = 187,
    idBlockTreasureChest = 188,
    idBlockDungeonStone = 189,
    idBlockLightDungeonStone = 190,
    idBlockLockedDungeonStone = 191,
    idBlockLockedLightDungeonStone = 192,
    idBlockPillar = 193,
    idBlockZanite = 194,
    idBlockQuicksoilGlass = 195,
    idBlockFreezer = 196,
    idBlockWhiteFlower = 197,
    idBlockPurpleFlower = 198,
    
    idItemVictoryMedal = 17000,
    idItemKey = 17001,
    idItemLoreBook = 17002,
    idItemMoaEgg = 17003,
    idItemBlueMusicDisk = 17004,
    idItemGoldenAmber = 17005,
    idItemAechorPetal = 17006,
    idItemStick = 17007,
    idItemDart = 17008,
    idItemDartShooter = 17009,
    idItemAmbrosiumShard = 17010,
    idItemZanite = 17011,
    idItemBucket = 17012,
    idItemPickSkyroot = 17013,
    idItemPickHolystone = 17014,
    idItemPickZanite = 17015,
    idItemPickGravitite = 17016,
    idItemShovelSkyroot = 17017,
    idItemShovelHolystone = 17018,
    idItemShovelZanite = 17019,
    idItemShovelGravitite = 17020,
    idItemAxeSkyroot = 17021,
    idItemAxeHolystone = 17022,
    idItemAxeZanite = 17023,
    idItemAxeGravitite = 17024,
    idItemSwordSkyroot = 17025,
    idItemSwordHolystone = 17026,
    idItemSwordZanite = 17027,
    idItemSwordGravitite = 17028,
    idItemIronBubble = 17029,
    idItemPigSlayer = 17030,
    idItemVampireBlade = 17031,
    idItemNatureStaff = 17032,
    idItemSwordFire = 17033,
    idItemSwordHoly = 17034,
    idItemSwordLightning = 17035,
    idItemLightningKnife = 17036,
    idItemGummieSwet = 17037,
    idItemHammerNotch = 17038,
    idItemPhoenixBow = 17039,
    idItemCloudParachute = 17040,
    idItemCloudParachuteGold = 17041,
    idItemCloudStaff = 17042,
    idItemLifeShard = 17043,
    idItemGoldenFeather = 17044,
    idItemLance = 17045,
    idItemIronRing = 17046,
    idItemGoldRing = 17047,
    idItemZaniteRing = 17048,
    idItemIronPendant = 17049,
    idItemGoldPendant = 17050,
    idItemZanitePendant = 17051,
    idItemRepShield = 17052,
    idItemAetherCape = 17053,
    idItemLeatherGlove = 17054,
    idItemIronGlove = 17055,
    idItemGoldGlove = 17056,
    idItemDiamondGlove = 17057,
    idItemZaniteGlove = 17058,
    idItemZaniteHelmet = 17059,
    idItemZaniteChestplate = 17060,
    idItemZaniteLeggings = 17061,
    idItemZaniteBoots = 17062,
    idItemGravititeGlove = 17063,
    idItemGravititeHelmet = 17064,
    idItemGravititeBodyplate = 17065,
    idItemGravititePlatelegs = 17066,
    idItemGravititeBoots = 17067,
    idItemPhoenixGlove = 17068,
    idItemPhoenixHelm = 17069,
    idItemPhoenixBody = 17070,
    idItemPhoenixLegs = 17071,
    idItemPhoenixBoots = 17072,
    idItemObsidianGlove = 17073,
    idItemObsidianBody = 17074,
    idItemObsidianHelm = 17075,
    idItemObsidianLegs = 17076,
    idItemObsidianBoots = 17077,
    idItemNeptuneGlove = 17078,
    idItemNeptuneHelmet = 17079,
    idItemNeptuneChestplate = 17080,
    idItemNeptuneLeggings = 17081,
    idItemNeptuneBoots = 17082,
    idItemRegenerationStone = 17083,
    idItemInvisibilityCloak = 17084,
    idItemAgilityCape = 17085,
    idItemWhiteCape = 17086,
    idItemRedCape = 17087,
    idItemYellowCape = 17088,
    idItemBlueCape = 17089,
    idItemPickValkyrie = 17090,
    idItemAxeValkyrie = 17091,
    idItemShovelValkyrie = 17092,
    idItemHealingStone = 17093,
    idItemIceRing = 17094,
    idItemIcePendant = 17095,
    
    commandCancellationDistance = 17,
	
	secondsBetweenLoreBooks = 5;
	
	public static class PackageAccess {
		
		public static class Block {
			
			public static net.minecraft.server.Block newBlock(int i, int j, Material material) {
				return new net.minecraft.server.Block(i, j, material);
			}
			
			public static net.minecraft.server.Block newBlock(int i, Material material) {
				return new net.minecraft.server.Block(i, material);
			}
			
			public static net.minecraft.server.Block setHardness(net.minecraft.server.Block block, float hardness) {
				block.c(hardness);
				return block;
			}
			
			public static net.minecraft.server.Block setResistance(net.minecraft.server.Block block, float resistance) {
				block.b(resistance);
				return block;
			}
			
			public static net.minecraft.server.Block setLightValue(net.minecraft.server.Block block, float lightValue) {
				block.a(lightValue);
				return block;
			}
			
			public static net.minecraft.server.Block setLightValue(net.minecraft.server.Block block, int lightOpacity) {
				block.f(lightOpacity);
				return block;
			}
		}
		
		public static class Item {
			
			public static net.minecraft.server.Item newItem(int ID) {
				return new net.minecraft.server.Item(ID);
			}
			
			public static void setMaxDamage(net.minecraft.server.Item item, int maxDamage) {
				item.d(maxDamage);
			}
			
			public static void setHasSubtypes(net.minecraft.server.Item item, boolean hasSubtypes) {
				item.a(hasSubtypes);
			}
		}
		
		public static class SlotArmor {
			
			public static net.minecraft.server.SlotArmor newSlotArmor(ContainerPlayer container, IInventory inventory, int slot, int xDisplay, int yDisplay, int armorType) {
				return new net.minecraft.server.SlotArmor(container, inventory, slot, xDisplay, yDisplay, armorType);
			}
		}
		
		public static class Entity {
			
			public static boolean getInWater(net.minecraft.server.Entity entity) {
				return entity.bA;
			}
			
			public static void setIsImmuneToFire(net.minecraft.server.Entity entity, boolean isImmuneToFire) {
				entity.fireProof = isImmuneToFire;
			}
			
			public static void setEntityFlag(net.minecraft.server.Entity entity, int ID, boolean flag) {
				entity.a(ID, flag);
			}
		}
		
		public static class EntityPlayer extends EntityLiving {
			
			public static MinecraftServer getMCServer(net.minecraft.server.EntityPlayer entityplayer) {
				return entityplayer.b;
			}
		}
		
		public static class EntityLiving extends Entity {
			
			public static void dropFewItems(net.minecraft.server.EntityLiving entityliving) {
				entityliving.q();
			}
		}
	}
}
