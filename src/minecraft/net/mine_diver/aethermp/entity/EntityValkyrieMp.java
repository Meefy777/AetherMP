package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityValkyrie;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

public class EntityValkyrieMp extends EntityValkyrie {

	public EntityValkyrieMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
	}
	
	@Override
	protected void entityInit() {
		dataWatcher.addObject(19, (byte)0); //boss
		dataWatcher.addObject(16, (int)1); //hp
		dataWatcher.addObject(17, (String) ""); //name
	}
	
	@Override
	public int getBossHP() {
		health = dataWatcher.getWatchableObjectInt(16);
		return super.getBossHP();
	}
	
	@Override
	public String getBossTitle() {
		String dataName = dataWatcher.getWatchableObjectString(17);
		if (dataName.equals("") && name != null)
			bossName = name;
		else 
			bossName = dataName;
		return super.getBossTitle();
	}
	
	public boolean isBoss() {
		return (dataWatcher.getWatchableObjectByte(19) & 1) != 0;
	}
	
	public void setBoss(boolean flag) {
        if(flag)
        	dataWatcher.updateObject(19, Byte.valueOf((byte)1));
        else
            dataWatcher.updateObject(19, Byte.valueOf((byte)0));
	}
	
	@Override
	public void updatePlayerActionState() {
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticks < 5)
			ticks++;
		else if (!constructed) {
			constructed = true;
			Packet230ModLoader packet = new Packet230ModLoader();
			packet.packetType = 70;
			packet.dataInt = new int [] {entityId};
			ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
		}
		
		timeLeft = Integer.MAX_VALUE;
		if (isBoss())
			texture = "/aether/mobs/valkyrie2.png";
		else
			texture = "/aether/mobs/valkyrie.png";
	}
	
	@Override
	public void teleport(double x, double y, double z, int rad) {
	}
	
	@Override
    public boolean interact(EntityPlayer entityplayer) {
		return true;
    }
	
	@Override
	public boolean otherDimension() {
		return false;
	}
	
	private boolean constructed = false;
	private int ticks = 0;
	public String name;
}
