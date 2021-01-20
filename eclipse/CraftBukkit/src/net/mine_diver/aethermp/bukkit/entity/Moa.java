package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

public interface Moa extends Animals {

	void setWellFed(boolean flag);
	
	boolean getWellFed();
	
	void setPetalsEaten(int i);
	
	int getPetalsEaten();
	
	void setColor(int i);
	
	int getColor();
	
	void setGrown(boolean flag);
	
	boolean getGrown();
	
	void setSaddled(boolean flag);
	
	boolean getSaddled();
	
	void setBaby(boolean flag);
	
	boolean getBaby();
	
	int getJumpsRemaining();
	
}
