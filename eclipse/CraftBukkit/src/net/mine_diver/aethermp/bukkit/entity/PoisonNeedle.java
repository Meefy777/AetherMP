package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Projectile;

import net.minecraft.server.EntityLiving;

public interface PoisonNeedle extends Projectile {
	
	void setVictim(EntityLiving living);
	
	EntityLiving getVictim();
	
	void setPoisonTime(int i);
	
	int getPoisonTime();

}
