package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.HomeShot;
import net.mine_diver.aethermp.entities.EntityHomeShot;

public class CraftHomeShot extends CraftFlyingAether implements HomeShot {

	public CraftHomeShot(CraftServer server, EntityHomeShot entity) {
		super(server, entity);
	}

	@Override
	public int getTicksAlive() {
		return ((EntityHomeShot)getHandle()).life;
	}

	@Override
	public void setTicksAlive(int life) {
		((EntityHomeShot)getHandle()).life = life;
	}

}
