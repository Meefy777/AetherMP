package net.mine_diver.aethermp.entity;

import net.minecraft.src.Whirly;
import net.minecraft.src.World;

public class WhirlyMp extends Whirly {

	public WhirlyMp(World world) {
		super(world);
	}
	
	public WhirlyMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
	}
	
	@Override
	public int loot() {
		return 0;
	}

}
