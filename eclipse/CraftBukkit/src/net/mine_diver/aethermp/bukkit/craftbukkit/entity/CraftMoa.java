package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Moa;
import net.mine_diver.aethermp.entities.EntityMoa;

public class CraftMoa extends AbstractAetherAnimal implements Moa {

	public CraftMoa(CraftServer server, EntityMoa entity) {
		super(server, entity);
	}
	
	public String toString() {
		return "CraftMoa";
	}

	@Override
	public void setWellFed(boolean flag) {
		((EntityMoa)getHandle()).setWellFed(flag);
	}

	@Override
	public boolean getWellFed() {
		return ((EntityMoa)getHandle()).getWellFed();
	}

	@Override
	public void setPetalsEaten(int i) {
		((EntityMoa)getHandle()).setPetalsEaten(i);
	}

	@Override
	public int getPetalsEaten() {
		return ((EntityMoa)getHandle()).getPetalsEaten();
	}

	@Override
	public void setColor(int i) {
		((EntityMoa)getHandle()).setColor(i);
	}

	@Override
	public int getColor() {
		return ((EntityMoa)getHandle()).getColor();
	}

	@Override
	public void setGrown(boolean flag) {
		((EntityMoa)getHandle()).setGrown(flag);
	}

	@Override
	public boolean getGrown() {
		return ((EntityMoa)getHandle()).getGrown();
	}

	@Override
	public void setSaddled(boolean flag) {
		((EntityMoa)getHandle()).setSaddled(flag);
	}

	@Override
	public boolean getSaddled() {
		return ((EntityMoa)getHandle()).getSaddled();
	}

	@Override
	public void setBaby(boolean flag) {
		((EntityMoa)getHandle()).setBaby(flag);
	}

	@Override
	public boolean getBaby() {
		return ((EntityMoa)getHandle()).getBaby();
	}

	@Override
	public int getJumpsRemaining() {
		return ((EntityMoa)getHandle()).jrem;
	}

	@Override
	public void setJumpsRemaining(int i) {
		((EntityMoa)getHandle()).jrem = i;
	}
	
}
