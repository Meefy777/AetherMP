// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityZephyrSnowball.java

package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityZephyrSnowball;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

// Referenced classes of package net.minecraft.src:
//            Entity, AxisAlignedBB, EntityLiving, MathHelper, 
//            World, Vec3D, MovingObjectPosition, NBTTagCompound

public class EntityZephyrSnowballMp extends EntityZephyrSnowball
{

    public EntityZephyrSnowballMp(World world, double x, double y, double z) {
        super(world);
        posX = x;
        posY = y;
        posZ = z;
    }
    
    @Override
    public void onUpdate() {
    	if (!worldObj.multiplayerWorld) {
    		super.onUpdate();
    		return;
    	}
        this.onEntityUpdate();
        if(field_9406_a > 0)
        	field_9406_a--;
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.95F;
        if(handleWaterMovement()) {
            for(int k = 0; k < 4; k++) {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }
            f1 = 0.8F;
        }
        motionX += field_9405_b;
        motionY += field_9404_c;
        motionZ += field_9403_d;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
    }
}
