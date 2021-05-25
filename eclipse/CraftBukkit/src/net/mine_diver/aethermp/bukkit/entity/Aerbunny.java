package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

import net.minecraft.server.Entity;

public interface Aerbunny extends Animals {
	
	float getPuffiness();
	
	void setPuffiness(float puff);
	
	int getAge();
	
	void setAge(int age);
	
	int getMate();
	
	void setMate(int mate);
	
	boolean getGrab();
	
	void setGrab(boolean flag);
	
	boolean getFear();
	
	void setFear(boolean flag);
	
	boolean getGotRider();
	
	void setGotRider(boolean flag);
	
	Entity getRunFrom();
	
	void setRunFrom(Entity ent);

}
