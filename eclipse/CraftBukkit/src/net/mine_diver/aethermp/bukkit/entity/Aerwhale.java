package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Flying;

public interface Aerwhale extends Flying {

	double[] getWaypoint();
	
	double[] getMotion();
	
	int getCourseChangeCooldown();
	
	void setWaypoint(double[] waypoint);
	
	void setMotion(double[] motion);
	
	void setCourseChangeCooldown(int cooldown);
	
}
