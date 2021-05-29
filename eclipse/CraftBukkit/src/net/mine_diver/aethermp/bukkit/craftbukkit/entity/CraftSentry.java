package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Sentry;
import net.mine_diver.aethermp.entities.EntitySentry;

public class CraftSentry extends CraftDungeonMob implements Sentry {

	public CraftSentry(CraftServer server, EntitySentry entity) {
		super(server, entity);
	}

	@Override
	public boolean isOn() {
		return ((EntitySentry)getHandle()).aa().b(17) == 1;
	}
	
	@Override
	public void shutdown() {
		((EntitySentry)getHandle()).shutdown();
	}
	
	@Override
    public String toString() {
        return "CraftSentry";
    }

	@Override
	public int getJcount() {
		return ((EntitySentry)getHandle()).jcount;
	}

	@Override
	public void setJcount(int i) {
		((EntitySentry)getHandle()).jcount = i;
	}

	@Override
	public int getSize() {
		return ((EntitySentry)getHandle()).size;
	}

	@Override
	public void setSize(int i) {
		((EntitySentry)getHandle()).size = i;
	}

	@Override
	public int getCounter() {
		return ((EntitySentry)getHandle()).counter;
	}

	@Override
	public void setCounter(int i) {
		((EntitySentry)getHandle()).counter = i;
	}
}
