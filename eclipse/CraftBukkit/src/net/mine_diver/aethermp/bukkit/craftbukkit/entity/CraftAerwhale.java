package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Aerwhale;
import net.mine_diver.aethermp.entities.EntityAerwhale;

public class CraftAerwhale extends CraftFlyingAether implements Aerwhale {

	public CraftAerwhale(CraftServer server, EntityAerwhale entity) {
		super(server, entity);
	}

	@Override
	public double[] getWaypoint() {
		EntityAerwhale whale = ((EntityAerwhale)getHandle());
		return new double[] {whale.waypointX, whale.waypointY, whale.waypointZ};
	}

	@Override
	public double[] getMotion() {
		EntityAerwhale whale = ((EntityAerwhale)getHandle());
		return new double[] {whale.motionYaw, whale.motionPitch};
	}

	@Override
	public int getCourseChangeCooldown() {
		return ((EntityAerwhale)getHandle()).courseChangeCooldown;
	}

	@Override
	public void setWaypoint(double[] waypoint) {
		EntityAerwhale whale = ((EntityAerwhale)getHandle());
		whale.waypointX = waypoint[0];
		whale.waypointY = waypoint[1];
		whale.waypointZ = waypoint[2];
	}

	@Override
	public void setMotion(double[] motion) {
		EntityAerwhale whale = ((EntityAerwhale)getHandle());
		whale.motionYaw = motion[0];
		whale.motionPitch = motion[1];
	}

	@Override
	public void setCourseChangeCooldown(int cooldown) {
		((EntityAerwhale)getHandle()).courseChangeCooldown = cooldown;
	}
	
	@Override
	public String toString() {
		return "CraftAerwhale";
	}
}
