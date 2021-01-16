package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.FlyingCow;
import net.mine_diver.aethermp.entities.EntityAetherAnimal;

public class CraftFlyingCow extends AbstractAetherAnimal implements FlyingCow {

	public CraftFlyingCow(CraftServer server, EntityAetherAnimal entity) {
		super(server, entity);
	}
	
	public String toString() {
		return "CraftFlyingCow";
	}

}
