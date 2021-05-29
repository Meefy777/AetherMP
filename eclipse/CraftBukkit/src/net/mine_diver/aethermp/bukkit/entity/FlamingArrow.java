package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Projectile;

public interface FlamingArrow extends Projectile {
	
	boolean getDoesArrowBelongToPlayer();
	
	void setDoesArrowBelongToPlayer(boolean flag);
	
	int getArrowShake();
	
	void setArrowShake(int i);

}
