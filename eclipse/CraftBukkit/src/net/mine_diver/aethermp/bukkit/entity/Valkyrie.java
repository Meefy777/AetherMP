package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Monster;

public interface Valkyrie extends AetherBoss, Monster {
	
	boolean isBoss();
	
	void setBoss(boolean flag);
	
	boolean isDuel();
	
	void setDuel(boolean flag);
	
	int getChatTime();
	
	void setChatTime(int time);
	
	int getTeleTime();
	
	void setTeleTime(int time);
	
	boolean getHasDungeon();
	
	void setHasDungeon(boolean flag);
	
	int getAngerLevel();
	
	void setAngerLevel(int i);
	
	int getTimeLeft();
	
	void setTimeLeft(int i);
	
	double getLastMotionY();
	
	void setLastMotionY(Double d);
	
	int[] getDungeonCoords();
	
	void setDungeonCoords(int[] i);
	
	double[] getSafeCoords();
	
	void setSafeCoords(double[] i);
	
	int getDungeonEntranceZ();
	
	void setDungeonEntranceZ(int i);

}
