package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntitySlider;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

public class EntitySliderMp extends EntitySlider {
	
	public EntitySliderMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, "/aether/mobs/sliderSleep.png");
		dataWatcher.addObject(17, 1);
		dataWatcher.addObject(18, "");
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		chatTime = 1;
		texture = dataWatcher.getWatchableObjectString(16);
		if (tick < 5)
			tick++;
		else if (!constructed) {
			constructed = true;
			updateSlider();
		}
	}
	
	@Override
	public int getBossHP() {
		health = dataWatcher.getWatchableObjectInt(17);
		return super.getBossHP();
	}
	
	@Override
	public String getBossTitle() {
		if(name != null && !name.equals(""))
			bossName = name;
		else
			bossName = dataWatcher.getWatchableObjectString(18);
		return super.getBossTitle();
	}
	
	private void updateSlider() {
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.packetType = 70;
		packet.dataInt = new int [] {entityId};
		ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
	}
	
	private boolean constructed;
	private int tick;
	public String name;
}
