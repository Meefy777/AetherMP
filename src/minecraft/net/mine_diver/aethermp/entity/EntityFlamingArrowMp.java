package net.mine_diver.aethermp.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFlamingArrow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ISpawnable;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;

public class EntityFlamingArrowMp extends EntityFlamingArrow implements ISpawnable {

	public EntityFlamingArrowMp(World world) {
		super(world);
	}

	@Override
	public void spawn(Packet230ModLoader packet) {
		entityId = packet.dataInt[0];
		Entity entity = EntityManager.getEntityByID(packet.dataInt[1]);
		EntityLiving entityliving = null;
		if (entity instanceof EntityLiving)
			entityliving = (EntityLiving) entity;
		try {
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "xTile", -1);
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "yTile", -1);
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "zTile", -1);
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "inTile", 0);
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "inGround", false);
	        doesArrowBelongToPlayer = false;
	        arrowShake = 0;
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "ticksInAir", 0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		try {
			ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "field_28019_h", 0);
		} catch (Exception e) {
			try {
				ModLoader.setPrivateValue(EntityFlamingArrow.class, this, "inData", 0);
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
        owner = entityliving;
        doesArrowBelongToPlayer = entityliving instanceof EntityPlayer;
        setSize(0.5F, 0.5F);
        if (entityliving == null)
        	setLocationAndAngles(packet.dataFloat[0], packet.dataFloat[1] + 0.12, packet.dataFloat[2], packet.dataFloat[3], packet.dataFloat[4]);
        else
        	setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
        serverPosX = (int) (posX * 32);
        serverPosY = (int) (posY * 32);
        serverPosZ = (int) (posZ * 32);
        yOffset = 0.0F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
        setArrowHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
	}

}
