package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Entity;

public interface FloatingBlock extends Entity {
	
	int getTypeId();
	
	int getMetadata();
	
	void setMetadata(int i);
	
	int getFlyTime();
	
	void setFlyTime(int i);
}
