package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityValkyrie;
import net.minecraft.src.World;

public class EntityValkyrieMp extends EntityValkyrie {

	public EntityValkyrieMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(15, 0); //boss
		dataWatcher.addObject(16, 0); //hp
		dataWatcher.addObject(17, ""); //name
	}
	
	@Override
	public int getBossHP() {
		health = dataWatcher.getWatchableObjectInt(17);
		return super.getBossHP();
	}
	
	@Override
	public String getBossTitle() {
		bossName = dataWatcher.getWatchableObjectString(18);
		return super.getBossTitle();
	}
	
	public boolean isBoss() {
		 return (dataWatcher.getWatchableObjectInt(15) & 1) != 0;
	}
	
}
