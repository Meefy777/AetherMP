package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.DartGolden;
import net.mine_diver.aethermp.entities.EntityDartGolden;

public class CraftDartGolden extends AbstractProjectileBase implements DartGolden {

	public CraftDartGolden(CraftServer server, EntityDartGolden entity) {
		super(server, entity);
	}
	
	@Override
    public String toString() {
        return "CraftDartGolden";
    }
	
	@Override
	public LivingEntity getShooter() {
		return (LivingEntity) ((EntityDartGolden)getHandle()).shooter.getBukkitEntity();
	}

	@Override
	public void setShooter(LivingEntity entity) {
		((EntityDartGolden)getHandle()).shooter = ((CraftLivingEntity)entity).getHandle();
	}
}
