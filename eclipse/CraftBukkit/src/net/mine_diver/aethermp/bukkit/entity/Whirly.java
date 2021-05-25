package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

public interface Whirly extends Animals {
	
	int getLife();
	
	void setLife(int life);
	
	int getEntCount();
	
	void setEntCount(int count);
	
	float getSpeed();
	
	void setSpeed(float speed);
	
	float getAngle();
	
	void setAngle(float f);
	
	float getCurve();
	
	void setCurve(float f);
	
	boolean getEvil();
	
	void setEvil(boolean bool);

}
