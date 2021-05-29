package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.HomeShot;
import net.mine_diver.aethermp.entities.EntityHomeShot;
import net.minecraft.server.EntityLiving;

public class CraftHomeShot extends CraftFlyingAether implements HomeShot {

	public CraftHomeShot(CraftServer server, EntityHomeShot entity) {
		super(server, entity);
	}

	@Override
	public int getTicksAlive() {
		return ((EntityHomeShot)getHandle()).lifeSpan;
	}

	@Override
	public void setTicksAlive(int life) {
		((EntityHomeShot)getHandle()).lifeSpan = life;
	}

	@Override
	public int getLife() {
		return ((EntityHomeShot)getHandle()).life;
	}

	@Override
	public void setLife(int i) {
		((EntityHomeShot)getHandle()).life = i;
	}

	@Override
	public boolean getFirstRun() {
		return ((EntityHomeShot)getHandle()).firstRun;
	}

	@Override
	public void setFirstRun(boolean flag) {
		((EntityHomeShot)getHandle()).firstRun = flag;
	}

	@Override
	public EntityLiving getTarget() {
		return ((EntityHomeShot)getHandle()).target;
	}

	@Override
	public void setTargeting(EntityLiving living) {
		((EntityHomeShot)getHandle()).target = living;
	}
	
	@Override
	public String toString() {
		return "CraftHomeShot";
	}

}
