package net.mine_diver.aethermp.bukkit.craftbukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import net.mine_diver.aethermp.bukkit.entity.ZephyrSnowball;

public class EntityZephyrSnowballPushEntityEvent extends Event implements Cancellable {

	public EntityZephyrSnowballPushEntityEvent(Entity victim, ZephyrSnowball zephyrSnowball) {
		super("AetherZephyrPushEntity");
		this.victim = victim;
		this.zephyrSnowball = zephyrSnowball;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public Entity getVictim() {
		return this.victim;
	}
	
	public ZephyrSnowball getZephyrSnowball() {
		return this.zephyrSnowball;
	}

	private final Entity victim;
	private final ZephyrSnowball zephyrSnowball;
	private boolean cancelled;
	private static final long serialVersionUID = 1L;
}
