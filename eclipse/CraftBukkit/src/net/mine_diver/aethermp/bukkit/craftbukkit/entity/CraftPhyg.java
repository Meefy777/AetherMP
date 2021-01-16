package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Phyg;
import net.mine_diver.aethermp.entities.EntityPhyg;

public class CraftPhyg extends AbstractAetherAnimal implements Phyg {

	public CraftPhyg(CraftServer server, EntityPhyg entity) {
		super(server, entity);
	}
	
	public String toString() {
		return "CraftPhyg";
	}
}
