package net.mine_diver.aethermp.bukkit.craftbukkit.event.player;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class PlayerCurePoisonEvent extends Event implements Cancellable {

	public PlayerCurePoisonEvent(LivingEntity victim) {
		super("AetherPoisonCure");
		this.victim = victim;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public LivingEntity getVictim() {
		return this.victim;
	}
	
	private final LivingEntity victim;
	private boolean cancelled = false;
	private static final long serialVersionUID = 1L;
}
