package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.FlyingCow;
import net.mine_diver.aethermp.entities.EntityFlyingCow;

public class CraftFlyingCow extends AbstractAetherAnimal implements FlyingCow {

	public CraftFlyingCow(CraftServer server, EntityFlyingCow entity) {
		super(server, entity);
	}
	
	public String toString() {
		return "CraftFlyingCow";
	}

	@Override
	public boolean getSaddled() {
		return ((EntityFlyingCow)getHandle()).getSaddled;
	}

	@Override
	public void setSaddled(boolean flag) {
		((EntityFlyingCow)getHandle()).setSaddled(flag);
	}

	@Override
	public boolean hasJumped() {
		return ((EntityFlyingCow)getHandle()).hasJumped;
	}

	@Override
	public void setHasJumped(boolean flag) {
		((EntityFlyingCow)getHandle()).hasJumped = flag;
	}

	@Override
	public int getJumps() {
		return ((EntityFlyingCow)getHandle()).jumps;
	}

	@Override
	public void setJumps(int i) {
		((EntityFlyingCow)getHandle()).jumps = i;
	}

	@Override
	public int getJrem() {
		return ((EntityFlyingCow)getHandle()).jrem;
	}

	@Override
	public void setJrem(int i) {
		((EntityFlyingCow)getHandle()).jrem = i;
	}

}
