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

}
