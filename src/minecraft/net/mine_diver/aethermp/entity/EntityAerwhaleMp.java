package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityAerwhale;
import net.minecraft.src.World;

public class EntityAerwhaleMp extends EntityAerwhale {

	public EntityAerwhaleMp(World world) {
		super(world);
	}
	
	public EntityAerwhaleMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
	}
	
	@Override
    public void onUpdate() {
		//super.onUpdate();
    	if (isMultiplayerEntity && newPosRotationIncrements > 0) {
            double diffX = (newPosX - posX) / (double)newPosRotationIncrements;
            double diffZ = (newPosZ - posZ) / (double)newPosRotationIncrements;
            double d = posX + diffX;
            double d1 = posY + (newPosY - posY) / (double)newPosRotationIncrements;
            double d2 = posZ + diffZ;
            double d3;
            for(d3 = newRotationYaw - (double)rotationYaw; d3 < -180D; d3 += 360D) { }
            for(; d3 >= 180D; d3 -= 360D) { }
            rotationYaw += d3 / (double)newPosRotationIncrements;
            rotationPitch += (newRotationPitch - (double)rotationPitch) / (double)newPosRotationIncrements;
            newPosRotationIncrements--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
        }
    }
}