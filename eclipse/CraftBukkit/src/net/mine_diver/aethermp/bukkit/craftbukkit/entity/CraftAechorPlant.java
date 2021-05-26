package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.AechorPlant;
import net.mine_diver.aethermp.entities.EntityAechorPlant;

public class CraftAechorPlant extends AbstractAetherAnimal implements AechorPlant {

	public CraftAechorPlant(CraftServer server, EntityAechorPlant entity) {
		super(server, entity);
	}

	@Override
	public int getSize() {
		return ((EntityAechorPlant)getHandle()).size;
	}

	@Override
	public void setSize(int i) {
		((EntityAechorPlant)getHandle()).size = i;
	}

	@Override
	public int getPoisonTime() {
		return ((EntityAechorPlant)getHandle()).poisonLeft;
	}

	@Override
	public void setPoisonTime(int i) {
		((EntityAechorPlant)getHandle()).poisonLeft = i;
	}

	@Override
	public int getAttackTime() {
		return ((EntityAechorPlant)getHandle()).attTime;
	}

	@Override
	public void setAttackTime(int i) {
		((EntityAechorPlant)getHandle()).attTime = i;
	}
	
	@Override
	public String toString() {
		return "CraftAechorPlant";
	}
}
