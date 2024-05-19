package net.mine_diver.aethermp.bukkit.craftbukkit.event;

import java.lang.reflect.Method;
import java.util.Optional;

import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEvent;

import net.mine_diver.aethermp.bukkit.craftbukkit.event.AbstractAetherPoisonEvent.PoisonSource;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityPoisonDamageEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityPoisonDistractEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityPoisonEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityZephyrSnowballPushEntityEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.player.PlayerGetLoreEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.player.PlayerParachuteDeployEvent;
import net.mine_diver.aethermp.bukkit.entity.ZephyrSnowball;
import net.mine_diver.aethermp.entities.EntityZephyrSnowball;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public class CraftAetherEventFactory extends CraftEventFactory {
	
	public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemInHand) {
        return (PlayerBucketEmptyEvent) getPlayerBucketEvent(Event.Type.PLAYER_BUCKET_EMPTY, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, ItemManager.Bucket);
    }
    
    public static PlayerBucketFillEvent callPlayerBucketFillEvent(final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemInHand, final Item bucket) {
        return (PlayerBucketFillEvent) getPlayerBucketEvent(Event.Type.PLAYER_BUCKET_FILL, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
    }
    
    public static EntityPoisonEvent callPoisonEvent(int ticks, EntityLiving shooter, EntityLiving victim, Entity agent, PoisonSource source) {
    	EntityPoisonEvent event = new EntityPoisonEvent(ticks, Optional.ofNullable(shooter == null ? null : (LivingEntity)shooter.getBukkitEntity()), (LivingEntity)victim.getBukkitEntity(), Optional.ofNullable(agent == null ? null : agent.getBukkitEntity()), source);
    	victim.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    	return event;
    }
    
    public static EntityPoisonDistractEvent callPoisonDistractEvent(int ticks, EntityLiving shooter, EntityLiving victim, Entity agent, PoisonSource source) {
    	EntityPoisonDistractEvent event = new EntityPoisonDistractEvent(ticks, Optional.ofNullable(shooter == null ? null : (LivingEntity)shooter.getBukkitEntity()), (LivingEntity)victim.getBukkitEntity(), Optional.ofNullable(agent == null ? null : agent.getBukkitEntity()), source);
    	victim.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    	return event;
    }
    
    public static EntityPoisonDamageEvent callPoisonDamageEvent(int damage, int ticks, EntityLiving shooter, EntityLiving victim, Entity agent, PoisonSource source) {
    	EntityPoisonDamageEvent event = new EntityPoisonDamageEvent(damage, ticks, Optional.ofNullable(shooter == null ? null : (LivingEntity)shooter.getBukkitEntity()), (LivingEntity)victim.getBukkitEntity(), Optional.ofNullable(agent == null ? null : agent.getBukkitEntity()), source);
    	victim.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    	return event;
    }
    
    public static PlayerGetLoreEvent callPlayerGetLoreEvent(Player player) {
    	PlayerGetLoreEvent event = new PlayerGetLoreEvent(player);
    	player.getServer().getPluginManager().callEvent(event);
    	return event;
    }
    
    public static PlayerParachuteDeployEvent callPlayerParachuteDeployEvent(Player player, boolean fall) {
    	PlayerParachuteDeployEvent event = new PlayerParachuteDeployEvent(player, fall);
    	player.getServer().getPluginManager().callEvent(event);
    	return event;
    }
    
    public static EntityZephyrSnowballPushEntityEvent callEntityZephyrPushEntityEvent(Entity entity, EntityZephyrSnowball projectile) {
    	EntityZephyrSnowballPushEntityEvent event = new EntityZephyrSnowballPushEntityEvent(entity.getBukkitEntity(), (ZephyrSnowball)projectile.getBukkitEntity());
    	entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    	return event;
    }
    
    private static PlayerEvent getPlayerBucketEvent(final Event.Type type, final EntityHuman who, final int clickedX, final int clickedY, final int clickedZ, final int clickedFace, final ItemStack itemstack, final Item item) {
        try {
			return (PlayerEvent) getPlayerBucketEventMethod.invoke(null, type, who, clickedX, clickedY, clickedZ, clickedFace, itemstack, item);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    private static final Method getPlayerBucketEventMethod;
    
    static {
    	Method method = null;
    	try {
			method = new Object(){}.getClass().getEnclosingClass().getSuperclass().getDeclaredMethod("getPlayerBucketEvent", Event.Type.class, EntityHuman.class, int.class, int.class, int.class, int.class, ItemStack.class, Item.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		method.setAccessible(true);
    	getPlayerBucketEventMethod = method;
    }
}
