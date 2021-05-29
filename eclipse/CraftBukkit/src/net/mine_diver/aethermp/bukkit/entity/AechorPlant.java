package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

import net.minecraft.server.EntityLiving;

public interface AechorPlant extends Animals {
	
	int getSize();
	
	void setSize(int i);
	
	int getPoisonTime();
	
	void setPoisonTime(int i);
	
	int getAttackTime();
	
	void setAttackTime(int i);
	
	boolean getNoDespawn();
	
	void setNoDespawn(boolean flag);
	
	int getPoisonLeft();
	
	void setPoisonLeft(int i);
	
	EntityLiving getLivingTarget();
	
	void setLivingTarget(EntityLiving living);
	
	int getSmokeTime();
	
	void setSmokeTime(int i);
	
	boolean getSeePrey();
	
	void setSeePrey(boolean flag);
	
	boolean getGrounded();
	
	void setGrounded(boolean flag);
	
}
