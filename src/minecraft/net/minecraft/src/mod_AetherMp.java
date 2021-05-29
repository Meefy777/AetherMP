package net.minecraft.src;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.entity.EntityAerbunnyMp;
import net.mine_diver.aethermp.entity.EntityFireMonsterMp;
import net.mine_diver.aethermp.entity.EntityFlyingCowMp;
import net.mine_diver.aethermp.entity.EntityMoaMp;
import net.mine_diver.aethermp.entity.EntityPhygMp;
import net.mine_diver.aethermp.entity.EntityValkyrieMp;
import net.minecraft.client.Minecraft;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void ModsLoaded() {
		for (BaseMod mod : (List<BaseMod>) ModLoader.getLoadedMods())
			if (mod.getClass().equals(mod_Aether.class)) {
				aetherInstance = mod;
				break;
			}
		CORE.postInit(this, aetherInstance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void AddRenderer(@SuppressWarnings("rawtypes") Map map) {
		CORE.addRenderer(map);
	}
	
	@Override
	public boolean OnTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {
		return CORE.onTickInGUI(minecraft, guiscreen);
	}
	
	@Override
	public boolean OnTickInGame(Minecraft minecraft) {
		return CORE.onTickInGame(minecraft, aetherInstance);
	}
	
	@Override
	public void KeyboardEvent(KeyBinding event) {
		CORE.keyboardEvent(event, aetherInstance);
	}
	
	@Override
	public GuiScreen HandleGUI(int ID) {
		return CORE.handleGui(ID);
	}
	
	@Override
	public void HandlePacket(Packet230ModLoader packet) {
		EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
		if(packet.packetType == 30 && player != null) {
			if(!player.isRiding())
				return;
			Entity mount = player.ridingEntity;

			if (mount instanceof EntityMoaMp) {
				EntityMoaMp moa = (EntityMoaMp) mount;
				moa.jrem = packet.dataInt[0];
			} else if (mount instanceof EntityPhygMp) {
				EntityPhygMp phyg = (EntityPhygMp) mount;
				phyg.jrem = packet.dataInt[0];
			} else if (mount instanceof EntityFlyingCowMp) {
				EntityFlyingCowMp cow = (EntityFlyingCowMp) mount;
				cow.jrem = packet.dataInt[0];
			}
		}
		else if (packet.packetType == 31 && player != null) 
			player.worldObj.spawnParticle(packet.dataString[0], packet.dataFloat[0], packet.dataFloat[1], packet.dataFloat[2], packet.dataFloat[3], packet.dataFloat[4], packet.dataFloat[5]);
		else if (packet.packetType == 32 && player != null) {
			Entity ent = ((WorldClient) player.worldObj).getEntityByID(packet.dataInt[0]);
			if(ent == null || !(ent instanceof EntityValkyrieMp))
				return;
			EntityValkyrieMp valk = (EntityValkyrieMp) ent;
			valk.swingArm();
		}
		else if (packet.packetType == 33 && player != null) {
			Entity ent = ((WorldClient) player.worldObj).getEntityByID(packet.dataInt[0]);
			if (ent == null || (!(ent instanceof EntityValkyrieMp) && !(ent instanceof EntityFireMonsterMp)))
				return;
			if (ent instanceof EntityValkyrieMp) {
				EntityValkyrieMp valk = (EntityValkyrieMp) ent;
				valk.setBoss(true);
				valk.bossName = packet.dataString[0];
				valk.name = packet.dataString[0];
			} else if (ent instanceof EntityFireMonsterMp) {
				EntityFireMonsterMp fire = (EntityFireMonsterMp) ent;
				fire.orgX = packet.dataInt[1];
				fire.orgY = packet.dataInt[2];
				fire.orgZ = packet.dataInt[3];
				fire.name = packet.dataString[0];
			}
		}
		else if (packet.packetType == 34 && player != null) {
			Entity ent = ((WorldClient) player.worldObj).getEntityByID(packet.dataInt[0]);
			if (ent == null || !(ent instanceof EntityAerbunnyMp) || (player.riddenByEntity != null && packet.dataInt[0] == player.riddenByEntity.entityId))
				return;
			EntityAerbunnyMp bunny = (EntityAerbunnyMp) ent;
			bunny.puffiness = packet.dataFloat[0];
		}
		else
			CORE.handlePacket(packet);
    }
	
	public static final Core CORE = new Core();
	private final Properties info = new Properties();
	private BaseMod aetherInstance;
	
	@MLProp
	public static int
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
	idEntityHomeShot = 117,
	idEntityValkyrie = 118,
	idEntityWhirlwind = 119,
	idEntityCockatrice = 120,
	idEntitySwet = 121,
	idEntityFireMonster = 122,
	idEntityFireMinion = 123;
	
	
	public static class PackageAccess {
		
		public static class World {
			
			public static ISaveHandler getSaveHandler(net.minecraft.src.World world) {
				return world.saveHandler;
			}
		}
		
		public static class Block {
			
			public static void setHardness(net.minecraft.src.Block block, float hardness) {
				block.setHardness(hardness);
			}
			
			public static float getResistance(net.minecraft.src.Block block) {
				return block.blockResistance;
			}
			
			public static void setResistance(net.minecraft.src.Block block, float resistance) {
				block.blockResistance = resistance;
			}
			
			public static void setStepSound(net.minecraft.src.Block block, StepSound stepsound) {
				block.setStepSound(stepsound);
			}
		}
		
		public static class Entity {

			public static void setFallDistance(net.minecraft.src.Entity entity, float fallDistance) {
				entity.fallDistance = fallDistance;
			}
			
			public static boolean getInWater(net.minecraft.src.Entity entity) {
				return entity.inWater;
			}
			public static Random getRand(net.minecraft.src.Entity entity) {
				return entity.rand;
			}
			
			public static void setEntityFlag(net.minecraft.src.Entity entity, int ID, boolean flag) {
				entity.setEntityFlag(ID, flag);
			}
		}
		
		public static class GuiConnecting {
			
			public static NetClientHandler setNetClientHandler(net.minecraft.src.GuiConnecting guiconnecting, NetClientHandler netclienthandler)
		    {
		        return net.minecraft.src.GuiConnecting.setNetClientHandler(guiconnecting, netclienthandler);
		    }

		    public static NetClientHandler getNetClientHandler(net.minecraft.src.GuiConnecting guiconnecting)
		    {
		        return net.minecraft.src.GuiConnecting.getNetClientHandler(guiconnecting);
		    }
		}
		
		public static class EntityLiving {
			
			public static boolean getIsJumping(net.minecraft.src.EntityLiving entityliving) {
				return entityliving.isJumping;
			}
			
			public static void setFallDistance(net.minecraft.src.EntityLiving entityliving, float f) {
				entityliving.fallDistance = f;
			}
			
			public static float getMoveForward(net.minecraft.src.EntityLiving entityliving) {
				return entityliving.moveForward;
			}
			
			public static float getMoveStrafing(net.minecraft.src.EntityLiving entityliving) {
				return entityliving.moveStrafing;
			}
			
		}
		
		public static class Item {
			
			public static int getIconIndex(net.minecraft.src.Item item) {
				return item.iconIndex;
			}
		}
	}
}
