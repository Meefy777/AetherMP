package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftMonster;

import net.mine_diver.aethermp.bukkit.entity.FireMinion;
import net.mine_diver.aethermp.entities.EntityFireMinion;

public class CraftFireMinion extends CraftMonster implements FireMinion {

	public CraftFireMinion(CraftServer server, EntityFireMinion entity) {
		super(server, entity);
	}
	
	@Override
	public String toString() {
		return "CraftFireMinion";
	}

}
