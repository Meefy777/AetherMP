package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;

public interface Slider extends Flying, AetherBoss {

	void setMoveTimer(int i);
	
	int getMoveTimer();
	
	void setDennis(int i);
	
	int getDennis();
	
	void setRennis(int i);
	
	int getRennis();
	
	void setChatTime(int i);
	
	int getChatTime();
	
	void setAwake(boolean flag);
	
	boolean getAwake();
	
	void setGotMovement(boolean flag);
	
	boolean getGotMovement();
	
	void setCrushed(boolean flag);
	
	boolean getCrushed();
	
	void setSpeedy(float f);
	
	float getSpeedy();
	
	void setHarvey(float f);
	
	float getHarvey();
	
	void setDirection(int i);
	
	int getDirection();
	
	void setDungeonCoords(int[] i);
	
	int[] getDungeonCoords();
}
