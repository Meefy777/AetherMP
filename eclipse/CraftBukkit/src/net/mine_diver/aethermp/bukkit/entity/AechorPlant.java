package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

public interface AechorPlant extends Animals {
	
	int getSize();
	
	void setSize(int i);
	
	int getPoisonTime();
	
	void setPoisonTime(int i);
	
	int getAttackTime();
	
	void setAttackTime(int i);
	
}
