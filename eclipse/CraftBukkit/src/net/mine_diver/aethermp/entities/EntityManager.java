package net.mine_diver.aethermp.entities;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.entity.CreatureType;
import org.bukkit.event.weather.LightningStrikeEvent;

import com.earth2me.essentials.Mob;
import com.earth2me.essentials.Mob.Enemies;

import net.mine_diver.aethermp.entities.EntityInfo.RegType;
import net.minecraft.server.DimensionBase;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.MathHelper;
import net.minecraft.server.ModLoader;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

@SuppressWarnings("unchecked")
public class EntityManager {
	
	public static void registerEntities() {
		for (EntityInfo entity : entities)
			switch(entity.getRegType()){
			case MAIN: {
				ModLoader.RegisterEntityID(entity.getEntityClass(), entity.getName(), entity.getID());
				mod_AetherMp.CORE.LOGGER.info("Adding CreatureType: " + entity.getName().toUpperCase() + ":" + entity.getName() + ":" + entity.getEntityClass().getName());
				mapping.put(entity.getName(), DimensionBase.EnumHelper.addEnum(CreatureType.class, entity.getName().toUpperCase(), new Class[] {String.class}, new Object[] {entity.getName()}));
				break;
			}
			case SECONDARY: {
				ModLoaderMp.RegisterEntityTracker(entity.getEntityClass(), entity.getTrackingDistance(), entity.getUpdateRate());
				ModLoaderMp.RegisterEntityTrackerEntry(entity.getEntityClass(), entity.getHasOwner(), entity.getID());
				break;
			}
		}
	}
	
	public static void registerEssentialsEntities(Class<Mob> mobEnum, Class<Enemies> enemies) {
		Map<String, Object> mapping;
		try {
			Field field = mobEnum.getDeclaredField("hashMap");
			field.setAccessible(true);
			mapping = (Map<String, Object>) field.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		for (EntityInfo entity : entities)
			if (entity.getRegType() == RegType.MAIN) {
				String suffix = entity.getSuffix();
				mod_AetherMp.CORE.LOGGER.info("Adding Mob: " + entity.getName().toUpperCase() + ":" + entity.getName() + ":" + entity.getEntityClass().getName());
				Object mob;
				if (suffix == null)
					mob = DimensionBase.EnumHelper.addEnum(mobEnum, entity.getName().toUpperCase(), new Class[] {String.class, enemies, CreatureType.class}, new Object[] {entity.getName(), Enum.valueOf(enemies, entity.getBehavior()), CreatureType.fromName(entity.getName())});
				else
					mob = DimensionBase.EnumHelper.addEnum(mobEnum, entity.getName().toUpperCase(), new Class[] {String.class, enemies, String.class, CreatureType.class}, new Object[] {entity.getName(), Enum.valueOf(enemies, entity.getBehavior()), suffix, CreatureType.fromName(entity.getName())});
				mapping.put(entity.getName(), mob);
			}
	}
	
	public static void strikeAetherLightning(EntityAetherLightning entity) {
		WorldServer world = (WorldServer)entity.world;
		// CraftBukkit start
		LightningStrikeEvent lightning = new LightningStrikeEvent(world.getWorld(), (org.bukkit.entity.LightningStrike) entity.getBukkitEntity());
		world.getServer().getPluginManager().callEvent(lightning);

        if (lightning.isCancelled()) {
            return;
        }
        // CraftBukkit end
        world.e.add(entity);
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.dataInt = new int[] {entity.id,
				MathHelper.floor(entity.locX * 32.0),
				MathHelper.floor(entity.locY * 32.0),
				MathHelper.floor(entity.locZ * 32.0),
				entity instanceof EntityWeatherStorm ? 1 : 0};
		packet.packetType = 5;
		packet.modId = ModLoaderMp.GetModInstance(mod_AetherMp.class).getId();
        world.server.serverConfigurationManager.sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, world.dimension, packet);
	}
	
	public static final EntityInfo[] entities = new EntityInfo[] {
			new EntityInfo(EntityCloudParachute.class, mod_AetherMp.idEntityCloudParachute, 160, 5),
			new EntityInfo(EntityFloatingBlock.class, mod_AetherMp.idEntityFloatingBlock, 160, 20),
			new EntityInfo(EntityMimic.class, "Mimic", mod_AetherMp.idEntityMimic, "ENEMY"),
			new EntityInfo(EntitySentry.class, "Sentry", mod_AetherMp.idEntitySentry, "ENEMY", "ies"),
			new EntityInfo(EntitySheepuff.class, "Sheepuff", mod_AetherMp.idEntitySheepuff, "FRIENDLY", ""),
			new EntityInfo(EntityZephyr.class, "Zephyr", mod_AetherMp.idEntityZephyr, "ENEMY"),
			new EntityInfo(EntityZephyrSnowball.class, mod_AetherMp.idEntityZephyrSnowball, 64, 2),
			new EntityInfo(EntityDartEnchanted.class, "EnchantedDart", mod_AetherMp.idEntityDartEnchanted, "NEUTRAL"),
			new EntityInfo(EntityDartEnchanted.class, mod_AetherMp.idEntityDartEnchanted, 64, 20),
			new EntityInfo(EntityDartGolden.class, "GoldenDart", mod_AetherMp.idEntityDartGolden, "NEUTRAL"),
			new EntityInfo(EntityDartGolden.class, mod_AetherMp.idEntityDartGolden, 64, 20),
			new EntityInfo(EntityDartPoison.class, "PoisonDart", mod_AetherMp.idEntityDartPoison, "NEUTRAL"),
			new EntityInfo(EntityDartPoison.class, mod_AetherMp.idEntityDartPoison, 64, 20),
			new EntityInfo(EntityLightningKnife.class, mod_AetherMp.idEntityLightningKnife, 64, 10),
			new EntityInfo(EntityNotchWave.class, mod_AetherMp.idEntityNotchWave, 64, 10),
			new EntityInfo(EntityFlamingArrow.class, "FlamingArrow", mod_AetherMp.idEntityFlamingArrow, "NEUTRAL"),
			new EntityInfo(EntityFlamingArrow.class, mod_AetherMp.idEntityFlamingArrow, 64, 20),
			new EntityInfo(EntityMiniCloud.class, "Mini Cloud", mod_AetherMp.idEntityMiniCloud, "NEUTRAL"),
			new EntityInfo(EntityMiniCloud.class, mod_AetherMp.idEntityMiniCloud, 512, 2),
			new EntityInfo(EntityFiroBall.class, "Firo Ball", mod_AetherMp.idEntityFiroBall, "NEUTRAL"),
			new EntityInfo(EntityFiroBall.class, mod_AetherMp.idEntityFiroBall, 64, 1),
			new EntityInfo(EntityAechorPlant.class, "Aechorplant", mod_AetherMp.idEntityAechorPlant, "ENEMY"),
			new EntityInfo(EntityPoisonNeedle.class, "PoisonNeedle", mod_AetherMp.idEntityPoisonNeedle, "NEUTRAL"),
			new EntityInfo(EntityPoisonNeedle.class, mod_AetherMp.idEntityPoisonNeedle, 64, 20),
			new EntityInfo(EntitySlider.class, "Slider", mod_AetherMp.idEntitySentry, "NEUTRAL"),
			new EntityInfo(EntitySlider.class, mod_AetherMp.idEntitySlider, 512, 2),
			new EntityInfo(EntityPhyg.class, "Phyg", mod_AetherMp.idEntityPhyg, "FRIENDLY", ""),
			new EntityInfo(EntityFlyingCow.class, "Flyingcow", mod_AetherMp.idEntityFlyingCow, "FRIENDLY", ""),
			new EntityInfo(EntityMoa.class, "Moa", mod_AetherMp.idEntityMoa, "FRIENDLY", ""),
			new EntityInfo(EntityAerbunny.class, "Aerbunny", mod_AetherMp.idEntityAerBunny, "FRIENDLY", ""),
			new EntityInfo(EntityValkyrie.class, "Valkyrie", mod_AetherMp.idEntityValkyrie, "NEUTRAL"),
			new EntityInfo(EntityValkyrie.class, mod_AetherMp.idEntityValkyrie, 512, 2),
			new EntityInfo(EntityHomeShot.class, "HomeShot", mod_AetherMp.idEntityHomeShot, "NEUTRAL"),
			new EntityInfo(EntityHomeShot.class, mod_AetherMp.idEntityHomeShot, 64, 2),
			new EntityInfo(Whirly.class, mod_AetherMp.idEntityWhirlwind, 128, 2),
			new EntityInfo(Whirly.class, "Whirly", mod_AetherMp.idEntityWhirlwind, "ENEMY"),
			new EntityInfo(EntityCockatrice.class, "Cockatrice", mod_AetherMp.idEntityCockatrice, "ENEMY", ""),
			new EntityInfo(EntitySwet.class, "Swet", mod_AetherMp.idEntitySwet, "ENEMY"),
			new EntityInfo(EntityFireMonster.class, "Firemonster", mod_AetherMp.idEntityFireMonster, "NEUTRAL", ""),
			new EntityInfo(EntityFireMonster.class, mod_AetherMp.idEntityFireMonster, 512, 2),
			new EntityInfo(EntityFireMinion.class, "Fireminion", mod_AetherMp.idEntityFireMinion, "ENEMY"),
			new EntityInfo(EntityFireMinion.class, mod_AetherMp.idEntityFireMinion, 512, 2),
			new EntityInfo(EntityAerwhale.class, "Aerwhale", mod_AetherMp.idEntityAerwhale, "FRIENDLY", ""),
	};
	
	public static final Map<String, CreatureType> mapping;
	
	static {
		Map<String, CreatureType> map = null;
		try {
			Field field = CreatureType.class.getDeclaredField("mapping");
			field.setAccessible(true);
			map = (Map<String, CreatureType>) field.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		mapping = map;
	}
}
