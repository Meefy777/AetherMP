package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

public interface FlyingCow extends Animals {

	boolean getSaddled();
	
	void setSaddled(boolean flag);
	
	boolean hasJumped();
	
}
