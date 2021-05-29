package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;

import net.minecraft.server.EntityLiving;

public interface HomeShot extends Flying {
	
	int getTicksAlive();
	
	void setTicksAlive(int life);

	int getLife();
	
	void setLife(int i);
	
	boolean getFirstRun();
	
	void setFirstRun(boolean flag);
	
	EntityLiving getTarget();
	
	void setTargeting(EntityLiving living);
}
