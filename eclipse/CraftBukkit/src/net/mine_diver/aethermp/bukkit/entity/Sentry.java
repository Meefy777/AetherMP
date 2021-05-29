package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Monster;

public interface Sentry extends Monster {
	
	boolean isOn();
	
	public void shutdown();
	
	int getJcount();
	
	void setJcount(int i);
	
	int getSize();
	
	void setSize(int i);
	
	int getCounter();
	
	void setCounter(int i);
}
