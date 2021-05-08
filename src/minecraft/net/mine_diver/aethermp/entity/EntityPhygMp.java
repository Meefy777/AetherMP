package net.mine_diver.aethermp.entity;

import java.util.List;

import net.minecraft.src.AetherAchievements;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPhyg;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_Aether;
import net.minecraft.src.mod_AetherMp;

public class EntityPhygMp extends EntityPhyg {
	
	public EntityPhygMp(World world) {
		super(world);
	}
	
	
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(getSaddled() && texture != "/aether/mobs/Mob_FlyingPigSaddle.png")
        {
            texture = "/aether/mobs/Mob_FlyingPigSaddle.png";
        }
        if (worldObj.multiplayerWorld) {
        	updatePlayerActionState();
        }
    }
	
    @SuppressWarnings("rawtypes")
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
                moveForward = 0.0F;
                moveStrafing = 0.0F;
                isJumping = false;
                EntityLiving entityliving = (EntityLiving)riddenByEntity;
                mod_AetherMp.PackageAccess.EntityLiving.setFallDistance(entityliving, 0.0F);
                prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
                prevRotationPitch = rotationPitch = riddenByEntity.rotationPitch;
                float f = 3.141593F;
                float f1 = f / 180F;
                float forward = mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving);
                float strafing = mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving);
                boolean jump = mod_AetherMp.PackageAccess.EntityLiving.getIsJumping(entityliving);
                Packet230ModLoader packet = new Packet230ModLoader();
                packet.packetType = 61;
                if(forward > 0.1F)
                {
                    float f2 = entityliving.rotationYaw * f1;
                    motionX += forward * -Math.sin(f2) * 0.17499999701976776D;
                    motionZ += forward * Math.cos(f2) * 0.17499999701976776D;
                } else
                if(forward < -0.1F)
                {
                    float f3 = entityliving.rotationYaw * f1;
                    motionX += forward * -Math.sin(f3) * 0.17499999701976776D;
                    motionZ += forward * Math.cos(f3) * 0.17499999701976776D;
                }
                if(strafing > 0.1F)
                {
                    float f4 = entityliving.rotationYaw * f1;
                    motionX += strafing * Math.cos(f4) * 0.17499999701976776D;
                    motionZ += strafing * Math.sin(f4) * 0.17499999701976776D;
                } else
                if(strafing < -0.1F)
                {
                    float f5 = entityliving.rotationYaw * f1;
                    motionX += strafing * Math.cos(f5) * 0.17499999701976776D;
                    motionZ += strafing * Math.sin(f5) * 0.17499999701976776D;
                }
                if(onGround && jump)
                {
                    onGround = false;
                    motionY = 1.3999999999999999D;
                    setPrivateBool(EntityPhyg.class, "jpress", true);
                    jrem--;
                    packet.dataInt = new int [] {1, jrem};
                    ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
                } else
                if(handleWaterMovement() && jump)
                {
                    motionY = 0.5D;
                    setPrivateBool(EntityPhyg.class, "jpress", true);
                    jrem--;
                    packet.dataInt = new int [] {1, jrem};
                    ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
                } else if(jrem > 0 && !getPrivateBool(EntityPhyg.class, "jpress") && jump) {
					    motionY = 1.2D;
					    setPrivateBool(EntityPhyg.class, "jpress", true);
					    jrem--;
	                    packet.dataInt = new int [] {1, jrem};
	                    ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
					}else if (onGround && !touchedGround) {
						touchedGround = true;
						packet.dataInt = new int [] {0, jrem};
						ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
					}
                
                if(touchedGround && !onGround)
                	touchedGround = false;
                
                if(getPrivateBool(EntityPhyg.class, "jpress") && !jump)
					setPrivateBool(EntityPhyg.class, "jpress", false);
						
                double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));
                if(d > 0.375D)
                {
                    double d1 = 0.375D / d;
                    motionX = motionX * d1;
                    motionZ = motionZ * d1;
                }
                
               rotationYaw = entityliving.rotationYaw;
         	   Packet230ModLoader packetMove = new Packet230ModLoader();
         	   packetMove.packetType = 69;
         	   packetMove.dataFloat = new float[] {(float) this.motionX, (float) this.motionY, (float) this.motionZ, (float) this.posX, (float) this.posY, (float) this.posZ, this.rotationYaw, this.rotationPitch};
         	   ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packetMove);
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
            return true;
        }
        if(getSaddled() && !worldObj.multiplayerWorld && (riddenByEntity == null || riddenByEntity == entityplayer))
        {
            entityplayer.mountEntity(this);
            mod_Aether.giveAchievement(AetherAchievements.flyingPig, entityplayer);
            return true;
        } else
        {
            return false;
        }
    }
    
    private void setPrivateBool(@SuppressWarnings("rawtypes") Class c, String bool, boolean value) {
    	try {
			ModLoader.setPrivateValue(c, this, bool, value);
		} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
    }
    
    private boolean getPrivateBool(@SuppressWarnings("rawtypes") Class c, String bool) {
    	try {
			return (boolean) ModLoader.getPrivateValue(c, this, bool);
		} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
    	return false;
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
            texture = "/aether/mobs/Mob_FlyingPigSaddle.png";
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)0));
            texture = "/aether/mobs/Mob_FlyingPigBase.png";
        }
    }

	private boolean touchedGround = true;

}
