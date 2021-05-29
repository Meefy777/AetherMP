package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.AechorPlant;
import net.mine_diver.aethermp.entities.EntityAechorPlant;
import net.minecraft.server.EntityLiving;

public class CraftAechorPlant extends AbstractAetherAnimal implements AechorPlant {

	public CraftAechorPlant(CraftServer server, EntityAechorPlant entity) {
		super(server, entity);
	}

	@Override
	public int getSize() {
		return ((EntityAechorPlant)getHandle()).size;
	}

	@Override
	public void setSize(int i) {
		((EntityAechorPlant)getHandle()).size = i;
	}

	@Override
	public int getPoisonTime() {
		return ((EntityAechorPlant)getHandle()).poisonLeft;
	}

	@Override
	public void setPoisonTime(int i) {
		((EntityAechorPlant)getHandle()).poisonLeft = i;
	}

	@Override
	public int getAttackTime() {
		return ((EntityAechorPlant)getHandle()).attTime;
	}

	@Override
	public void setAttackTime(int i) {
		((EntityAechorPlant)getHandle()).attTime = i;
	}
	
	@Override
	public boolean getNoDespawn() {
		return ((EntityAechorPlant)getHandle()).noDespawn;
	}

	@Override
	public void setNoDespawn(boolean flag) {
		((EntityAechorPlant)getHandle()).noDespawn = flag;
	}

	@Override
	public int getPoisonLeft() {
		return ((EntityAechorPlant)getHandle()).poisonLeft;
	}

	@Override
	public void setPoisonLeft(int i) {
		((EntityAechorPlant)getHandle()).poisonLeft = i;
	}

	@Override
	public EntityLiving getLivingTarget() {
		return ((EntityAechorPlant)getHandle()).target;
	}

	@Override
	public void setLivingTarget(EntityLiving living) {
		((EntityAechorPlant)getHandle()).target = living;
	}

	@Override
	public int getSmokeTime() {
		return ((EntityAechorPlant)getHandle()).smokeTime;
	}

	@Override
	public void setSmokeTime(int i) {
		((EntityAechorPlant)getHandle()).smokeTime = i;
	}

	@Override
	public boolean getSeePrey() {
		return ((EntityAechorPlant)getHandle()).seeprey;
	}

	@Override
	public void setSeePrey(boolean flag) {
		((EntityAechorPlant)getHandle()).seeprey = flag;
	}

	@Override
	public boolean getGrounded() {
		return ((EntityAechorPlant)getHandle()).grounded;
	}

	@Override
	public void setGrounded(boolean flag) {
		((EntityAechorPlant)getHandle()).grounded = flag;
	}
	
	@Override
	public String toString() {
		return "CraftAechorPlant";
	}

}
