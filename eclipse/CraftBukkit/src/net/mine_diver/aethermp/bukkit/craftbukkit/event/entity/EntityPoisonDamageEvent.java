package net.mine_diver.aethermp.bukkit.craftbukkit.event.entity;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.craftbukkit.event.AbstractAetherPoisonEvent;

public class EntityPoisonDamageEvent extends AbstractAetherPoisonEvent {

	public EntityPoisonDamageEvent(int damage, int poisonTicksLeft, Optional<LivingEntity> shooter, LivingEntity victim, Optional<Entity> agent, PoisonSource source) {
		super("AetherPoisonDamage", poisonTicksLeft, shooter, victim, agent, source);
		this.damage = damage;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	private int damage;
	private static final long serialVersionUID = 1L;
	
}
