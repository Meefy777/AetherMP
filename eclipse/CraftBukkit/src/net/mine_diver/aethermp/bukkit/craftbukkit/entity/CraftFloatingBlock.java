package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.mine_diver.aethermp.bukkit.entity.FloatingBlock;
import net.mine_diver.aethermp.entities.EntityFloatingBlock;

public class CraftFloatingBlock extends CraftEntity implements FloatingBlock {

	public CraftFloatingBlock(CraftServer server, EntityFloatingBlock entity) {
		super(server, entity);
	}
	
	@Override
	public int getTypeId() {
		return ((EntityFloatingBlock)getHandle()).blockID;
	}
	
	@Override
	public String toString() {
		return "CraftFloatingBlock";
	}

	@Override
	public int getMetadata() {
		return ((EntityFloatingBlock)getHandle()).metadata;
	}

	@Override
	public void setMetadata(int i) {
		((EntityFloatingBlock)getHandle()).metadata = i;
	}

	@Override
	public int getFlyTime() {
		return ((EntityFloatingBlock)getHandle()).flytime;
	}

	@Override
	public void setFlyTime(int i) {
		((EntityFloatingBlock)getHandle()).flytime = i;
	}
}
