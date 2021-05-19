package net.mine_diver.aethermp.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityHomeShot;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;

public class EntityHomeShotMp extends EntityHomeShot implements ISpawnable 
	{

	public EntityHomeShotMp(World world) {
		super(world);
	}

	@Override
	public void spawn(Packet230ModLoader packet) {
		entityId = packet.dataInt[0];
		setPositionAndRotation(packet.dataFloat[0], packet.dataFloat[1], packet.dataFloat[2], rotationYaw, rotationPitch);
        serverPosX = (int) (posX * 32);
        serverPosY = (int) (posY * 32);
        serverPosZ = (int) (posZ * 32);
        //motionX = packet.dataFloat[3];
        //motionY = packet.dataFloat[4];
        //motionZ = packet.dataFloat[5];
	}
	
	@Override
    public void moveIt(Entity e1, double sped)
    {
    }
	
	@Override
	public void faceIt() {	
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		life = 2;
	}
}
