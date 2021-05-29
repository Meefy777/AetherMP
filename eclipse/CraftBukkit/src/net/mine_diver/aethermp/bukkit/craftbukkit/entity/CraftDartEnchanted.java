package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import net.mine_diver.aethermp.bukkit.entity.DartEnchanted;
import net.mine_diver.aethermp.entities.EntityDartEnchanted;

public class CraftDartEnchanted extends CraftDartGolden implements DartEnchanted {

	public CraftDartEnchanted(CraftServer server, EntityDartEnchanted entity) {
		super(server, entity);
	}
	
	@Override
    public String toString() {
        return "CraftDartEnchanted";
    }
	
	@Override
	public LivingEntity getShooter() {
		return (LivingEntity) ((EntityDartEnchanted)getHandle()).shooter.getBukkitEntity();
	}

	@Override
	public void setShooter(LivingEntity entity) {
		((EntityDartEnchanted)getHandle()).shooter = ((CraftLivingEntity)entity).getHandle();
	}
	
	
}
