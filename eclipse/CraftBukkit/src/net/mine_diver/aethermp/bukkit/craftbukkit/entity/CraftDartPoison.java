package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.DartPoison;
import net.mine_diver.aethermp.entities.EntityDartPoison;

public class CraftDartPoison extends CraftDartGolden implements DartPoison {

	public CraftDartPoison(CraftServer server, EntityDartPoison entity) {
		super(server, entity);
	}
	
	@Override
	public int getPoisonTime() {
		return ((EntityDartPoison) getHandle()).poisonTime;
	}

	@Override
	public void setPoisonTime(int i) {
		((EntityDartPoison)getHandle()).poisonTime = i;
	}
	
	@Override
    public String toString() {
        return "CraftDartPoison";
	}
	
	@Override
	public LivingEntity getShooter() {
		return (LivingEntity) ((EntityDartPoison)getHandle()).shooter.getBukkitEntity();
	}

	@Override
	public void setShooter(LivingEntity entity) {
		((EntityDartPoison)getHandle()).shooter = ((CraftLivingEntity)entity).getHandle();
	}
}
