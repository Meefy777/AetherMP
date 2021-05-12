package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;

public interface HomeShot extends Flying {
	
	int getTicksAlive();
	
	void setTicksAlive(int life);

}
