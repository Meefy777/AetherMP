package net.mine_diver.aethermp.entity;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFlyingCow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

public class EntityFlyingCowMp extends EntityFlyingCow {

	public EntityFlyingCowMp(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
        if(getSaddled() && texture != "/aether/mobs/Mob_FlyingCowSaddle.png")
        {
        	texture = "/aether/mobs/Mob_FlyingCowSaddle.png";
        }
			 
		if (worldObj.multiplayerWorld) {
			updatePlayerActionState();
		}
	}
	
    public void onLivingUpdate()
    {
        if(riddenByEntity != null)
        {
            if(isMovementBlocked())
            {
                isJumping = false;
                moveStrafing = 0.0F;
                moveForward = 0.0F;
                randomYawVelocity = 0.0F;
            }
            boolean flag = isInWater();
            boolean flag1 = handleLavaMovement();
            if(isJumping)
            {
                if(flag)
                {
                    motionY += 0.039999999105930328D;
                } else
                if(flag1)
                {
                    motionY += 0.039999999105930328D;
                } else
                if(onGround)
                {
                    jump();
                }
            }
            moveStrafing *= 0.98F;
            moveForward *= 0.98F;
            randomYawVelocity *= 0.9F;
            moveEntityWithHeading(moveStrafing, moveForward);
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            if(list != null && list.size() > 0)
            {
                for(int i = 0; i < list.size(); i++)
                {
                    Entity entity = (Entity)list.get(i);
                    if(entity.canBePushed())
                    {
                        entity.applyEntityCollision(this);
                    }
                }

            }
            return;
        } else
        {
            super.onLivingUpdate();
            return;
        }
    }
    
    public void updatePlayerActionState()
    {
        if(worldObj.multiplayerWorld && riddenByEntity != null && (riddenByEntity instanceof EntityLiving))
        {
            EntityLiving entityliving = (EntityLiving)riddenByEntity;
            moveForward = 0.0F;
            moveStrafing = 0.0F;
            isJumping = false;
            mod_AetherMp.PackageAccess.EntityLiving.setFallDistance(entityliving, 0.0F);
            prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
            prevRotationPitch = rotationPitch = riddenByEntity.rotationPitch;
            float f = 3.141593F;
            float f1 = f / 180F;
            if(mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving) > 0.1F)
            {
                float f2 = entityliving.rotationYaw * f1;
                motionX += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving) * -Math.sin(f2) * 0.17499999701976776D;
                motionZ += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving) * Math.cos(f2) * 0.17499999701976776D;
            } else
            if(mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving) < -0.1F)
            {
                float f3 = entityliving.rotationYaw * f1;
                motionX += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving) * -Math.sin(f3) * 0.17499999701976776D;
                motionZ += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving) * Math.cos(f3) * 0.17499999701976776D;
            }
            if(mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving) > 0.1F)
            {
                float f4 = entityliving.rotationYaw * f1;
                motionX += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving) * Math.cos(f4) * 0.17499999701976776D;
                motionZ += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving) * Math.sin(f4) * 0.17499999701976776D;
            } else
            if(mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving) < -0.1F)
            {
                float f5 = entityliving.rotationYaw * f1;
                motionX += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving) * Math.cos(f5) * 0.17499999701976776D;
                motionZ += (double)mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving) * Math.sin(f5) * 0.17499999701976776D;
            }
            if(onGround && mod_AetherMp.PackageAccess.EntityLiving.getIsJumping(entityliving))
            {
                onGround = false;
                motionY = 1.3999999999999999D;
                try {
					ModLoader.setPrivateValue(EntityFlyingCow.class, this, "jpress", true);
				} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
					e.printStackTrace();
				}
                jrem--;
            } else
            if(handleWaterMovement() && mod_AetherMp.PackageAccess.EntityLiving.getIsJumping(entityliving))
            {
                motionY = 0.5D;
                try {
					ModLoader.setPrivateValue(EntityFlyingCow.class, this, "jpress", true);
				} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
					e.printStackTrace();
				}
                jrem--;
            } else
				try {
					if(jrem > 0 && !(boolean)ModLoader.getPrivateValue(EntityFlyingCow.class, this, "jpress") && mod_AetherMp.PackageAccess.EntityLiving.getIsJumping(entityliving))
					{
					    motionY = 1.2D;
					    ModLoader.setPrivateValue(EntityFlyingCow.class, this, "jpress", true);
					    jrem--;
					}
				} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
					e.printStackTrace();
				}
            try {
				if((boolean)ModLoader.getPrivateValue(EntityFlyingCow.class, this, "jpress") && !mod_AetherMp.PackageAccess.EntityLiving.getIsJumping(entityliving))
				{
					ModLoader.setPrivateValue(EntityFlyingCow.class, this, "jpress", false);
				}
			} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
            double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));
            if(d > 0.375D)
            {
                double d1 = 0.375D / d;
                motionX = motionX * d1;
                motionZ = motionZ * d1;
            }
            
           rotationYaw = entityliving.rotationYaw;
     	   Packet230ModLoader packet = new Packet230ModLoader();
     	   packet.packetType = 69;
     	   packet.dataFloat = new float[] {(float) this.motionX, (float) this.motionY, (float) this.motionZ, (float) this.posX, (float) this.posY, (float) this.posZ, this.rotationYaw, this.rotationPitch};
     	   ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
            
            return;
        } else
        {
             return;
        }
    }
    
    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if(!getSaddled() && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().itemID == Item.saddle.shiftedIndex)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            setSaddled(true);
            //texture = "/aether/mobs/Mob_FlyingCowSaddle.png";
            return true;
        }
        if(getSaddled() && !worldObj.multiplayerWorld && (riddenByEntity == null || riddenByEntity == entityplayer))
        {
            entityplayer.mountEntity(this);
            return true;
        } else
        {
            return false;
        }
    }
    
    public boolean getSaddled()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setSaddled(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)1));
            texture = "/aether/mobs/Mob_FlyingCowSaddle.png";
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)0));
            texture = "/aether/mobs/Mob_FlyingCowBase.png";
        }
    }

	

}
