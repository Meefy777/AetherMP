package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;

public interface FireMonster extends AetherBoss, Flying {

	boolean getGotTarget();
	
	void setGotTarget(boolean flag);
	
	double getSpeedness();
	
	void setSpeedness(double d);
	
	double getRotary();
	
	void setRotary(double d);
	
	int getDirection();
	
	void setDirection(int i);
	
	int getHurtness();
	
	void setHurtness(int i);
	
	int getChatCount();
	
	void setChatCount(int i);
	
	int getChatLog();
	
	void setChatLog(int i);
	
	int getBallCount();
	
	void setBallCount(int i);
	
	int getFlameCount();
	
	void setFlameCount(int i);
	
	int getEntCount();
	
	void setEntCount(int i);
	
	int getMotionTimer();
	
	void setMotionTimer(int i);
	
	int getWideness();
	
	void setWideness(int i);
	
	int[] getOrg();
	
	void setOrg(int[] i);
	
}
