package net.mine_diver.aethermp.bukkit.craftbukkit.event;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class AbstractAetherPoisonEvent extends Event implements Cancellable {

	public AbstractAetherPoisonEvent(String type, int poisonTicksLeft, Optional<LivingEntity> shooter, LivingEntity victim, Optional<Entity> agent, PoisonSource source) {
		super(type);
		this.shooter = shooter;
		this.victim = victim;
		this.agent = agent;
		this.poisonTicksLeft = poisonTicksLeft;
		this.source = source;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public int getPoisonTicksLeft() {
		return this.poisonTicksLeft;
	}
	
	public Optional<LivingEntity> getShooter() {
		return this.shooter;
	}
	
	public LivingEntity getVictim() {
		return this.victim;
	}
	
	public Optional<Entity> getAgent() {
		return this.agent;
	}
	
	public PoisonSource getSource() {
		return this.source;
	}
	
	private final int poisonTicksLeft;
	private final Optional<LivingEntity> shooter;
	private final LivingEntity victim;
	private final Optional<Entity> agent;
	private boolean cancelled = false;
	private final PoisonSource source;
	private static final long serialVersionUID = 1L;
	
	public enum PoisonSource {
		MOB,
		POISON_BUCKET,
		POISON_DART,
		OTHER
	}

}
