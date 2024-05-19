package net.mine_diver.aethermp.bukkit.craftbukkit.event.entity;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.craftbukkit.event.AbstractAetherPoisonEvent;

public class EntityPoisonDistractEvent extends AbstractAetherPoisonEvent {

	public EntityPoisonDistractEvent(int poisonTicksLeft, Optional<LivingEntity> shooter, LivingEntity victim, Optional<Entity> agent, PoisonSource source) {
		super("AetherPoisonDistract", poisonTicksLeft, shooter, victim, agent, source);
	}
	
	private static final long serialVersionUID = 1L;
	
}