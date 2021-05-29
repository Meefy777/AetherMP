package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.PoisonNeedle;
import net.mine_diver.aethermp.entities.EntityPoisonNeedle;
import net.minecraft.server.EntityLiving;

public class CraftPoisonNeedle extends AbstractProjectileBase implements PoisonNeedle {

	public CraftPoisonNeedle(CraftServer server, EntityPoisonNeedle entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftPoisonNeedle";
	}

	@Override
	public void setVictim(EntityLiving living) {
		((EntityPoisonNeedle)getHandle()).victim = living;
	}

	@Override
	public EntityLiving getVictim() {
		return ((EntityPoisonNeedle)getHandle()).victim;
	}

	@Override
	public void setPoisonTime(int i) {
		((EntityPoisonNeedle)getHandle()).poisonTime = i;
	}

	@Override
	public int getPoisonTime() {
		return ((EntityPoisonNeedle)getHandle()).poisonTime;
	}
}
