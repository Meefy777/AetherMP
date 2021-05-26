package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMonster;

import net.mine_diver.aethermp.bukkit.entity.Cockatrice;
import net.mine_diver.aethermp.entities.EntityCockatrice;

public class CraftCockatrice extends CraftMonster implements Cockatrice {

	public CraftCockatrice(CraftServer server, EntityCockatrice entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftCockatrice";
	}

}
