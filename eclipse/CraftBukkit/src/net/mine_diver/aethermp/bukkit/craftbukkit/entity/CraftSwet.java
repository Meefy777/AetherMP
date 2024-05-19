package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftAnimals;

import net.mine_diver.aethermp.bukkit.entity.Swet;
import net.mine_diver.aethermp.entities.EntitySwet;

public class CraftSwet extends CraftAnimals implements Swet {

	public CraftSwet(CraftServer server, EntitySwet entity) {
		super(server, entity);
	}

	@Override
	public int getTicker() {
		return ((EntitySwet)getHandle()).ticker;
	}

	@Override
	public int getFlutter() {
		return ((EntitySwet)getHandle()).flutter;
	}

	@Override
	public boolean getTextureSet() {
		return ((EntitySwet)getHandle()).textureSet;
	}

	@Override
	public boolean getKickoff() {
		return ((EntitySwet)getHandle()).kickoff;
	}

	@Override
	public int getHops() {
		return ((EntitySwet)getHandle()).hops;
	}

	@Override
	public boolean getGotRider() {
		return ((EntitySwet)getHandle()).gotrider;
	}

	@Override
	public void setTicker(int ticker) {
		((EntitySwet)getHandle()).ticker = ticker;
	}

	@Override
	public void setFlutter(int flutter) {
		((EntitySwet)getHandle()).flutter = flutter;
	}

	@Override
	public void setTextureSet(boolean textureSet) {
		((EntitySwet)getHandle()).textureSet = textureSet;
	}

	@Override
	public void setKickOff(boolean kickoff) {
		((EntitySwet)getHandle()).kickoff = kickoff;
	}

	@Override
	public void setHops(int hops) {
		((EntitySwet)getHandle()).hops = hops;
	}

	@Override
	public void setGotRider(boolean gotrider) {
		((EntitySwet)getHandle()).gotrider = gotrider;
	}
	
	@Override
	public String toString() {
		return "CraftSwet";
	}
	
}
