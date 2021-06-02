package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityFireMinion;
import net.minecraft.src.World;

public class EntityFireMinionMp extends EntityFireMinion {

	public EntityFireMinionMp(World world) {
		super(world);
		isMultiplayerEntity = true;
	}
	
	public EntityFireMinionMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
		isMultiplayerEntity = true;
	}
	

}
