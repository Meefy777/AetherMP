package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Aerbunny;
import net.mine_diver.aethermp.entities.EntityAerbunny;

public class CraftAerbunny extends CraftAnimalsAether implements Aerbunny  {

	public CraftAerbunny(CraftServer server, EntityAerbunny entity) {
		super(server, entity);
	}
	
	public String toString() {
		return "CraftAerbunny";
	}

}
