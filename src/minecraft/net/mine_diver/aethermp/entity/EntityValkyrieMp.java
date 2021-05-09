package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityValkyrie;
import net.minecraft.src.World;

public class EntityValkyrieMp extends EntityValkyrie {

	public EntityValkyrieMp(World world, double x, double y, double z) {
		super(world);
		dataWatcher.addObject(15, Byte.valueOf((byte)0)); //boss
		dataWatcher.addObject(16, Integer.valueOf((int)0)); //hp
		dataWatcher.addObject(17, String.valueOf((String) "")); //name
		dataWatcher.addObject(18, Byte.valueOf((byte)0)); //isMad
		setPosition(x, y, z);
	}
	
	@Override
	public int getBossHP() {
		health = dataWatcher.getWatchableObjectInt(16);
		return super.getBossHP();
	}
	
	@Override
	public String getBossTitle() {
		bossName = dataWatcher.getWatchableObjectString(17);
		return super.getBossTitle();
	}
	
	public boolean isBoss() {
		return (dataWatcher.getWatchableObjectByte(15) & 1) != 0;
	}
	
   public boolean isMad() {
	   return (dataWatcher.getWatchableObjectByte(18) & 1) != 0;
   }
	
	@Override
	public void updatePlayerActionState() {
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		chatTime = Integer.MAX_VALUE;
		if (isBoss())
			texture = "/aether/mobs/valkyrie2.png";
		else
			texture = "/aether/mobs/valkyrie.png";
	}
	
	@Override
	public void teleport(double x, double y, double z, int rad) {
	}
	
}
