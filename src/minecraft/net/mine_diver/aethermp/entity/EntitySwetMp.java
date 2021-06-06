package net.mine_diver.aethermp.entity;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySwet;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

public class EntitySwetMp extends EntitySwet {

	public EntitySwetMp(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		dataWatcher.addObject(14, (byte)0); //texture
		dataWatcher.addObject(15, String.valueOf(0.1f)); //width 
		dataWatcher.addObject(16, String.valueOf(0.1f)); //height
		dataWatcher.addObject(17, (byte) 0); //isRidden
		dataWatcher.addObject(18, String.valueOf(0.0D)); //motY
		dataWatcher.addObject(19, (byte) 0); //isTamed
		dataWatcher.addObject(20, (String) ""); //rideName
		dataWatcher.addObject(21, (byte) 0); //hops
	}
	
    public void setHops(int i) {
    	dataWatcher.updateObject(21, Byte.valueOf((byte)i));
    }
    
    public int getHops() {
    	return dataWatcher.getWatchableObjectByte(21);
    }
	
	public String getRiderName() {
		return dataWatcher.getWatchableObjectString(20);
	}
	
    public byte getTexture() {
    	return dataWatcher.getWatchableObjectByte(14);
    }
    
    public boolean getIsTamed() {
    	return (dataWatcher.getWatchableObjectByte(19) & 1) != 0;
    }
    
    public float getWidth() {
    	return Float.valueOf(dataWatcher.getWatchableObjectString(15));
    }
    
    public float getHeight() {
    	return Float.valueOf(dataWatcher.getWatchableObjectString(16));
    }
    
    public double getMotY() {
    	return Double.valueOf(dataWatcher.getWatchableObjectString(18));
    }
    
    public boolean getIsRidden() {
    	return (dataWatcher.getWatchableObjectByte(17) & 1) != 0;
    }
    
    @Override
    public void capturePrey(Entity entity)
    {
        splorch();
        prevPosX = posX = entity.posX;
        prevPosY = posY = entity.posY + 0.0099999997764825821D;
        prevPosZ = posZ = entity.posZ;
        prevRotationYaw = rotationYaw = entity.rotationYaw;
        prevRotationPitch = rotationPitch = entity.rotationPitch;
        motionX = entity.motionX;
        motionY = entity.motionY;
        motionZ = entity.motionZ;
        setSize(getWidth(), getHeight());
        setPosition(posX, posY, posZ);
        entity.mountEntity(this);
        rotationYaw = rand.nextFloat() * 360F;
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	if(getTexture() == 1)
        {
            texture = "/aether/mobs/swets.png";
            moveSpeed = 1.5F;
        } else
        {
            texture = "/aether/mobs/goldswets.png";
            moveSpeed = 3F;
        }
    }
    
    @SuppressWarnings("rawtypes")
	@Override
    public void onLivingUpdate() {
        if(riddenByEntity != null && getIsTamed())
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
            updatePlayerActionState();
            return;
        } else
        {
            super.onLivingUpdate();
            return;
        }
    }
    
    @Override
    public void updatePlayerActionState()
    {
        entityAge++;
        despawnEntity();
        if(getIsTamed() && riddenByEntity != null)
        {
            d_2();
            return;
        }
        if(!onGround && isJumping)
        {
            isJumping = false;
        } else
        if(onGround)
        {
            if(moveForward > 0.05F)
            {
                moveForward *= 0.75F;
            } else
            {
                moveForward = 0.0F;
            }
        }
        if(playerToAttack != null && riddenByEntity == null && health > 0)
        {
            faceEntity(playerToAttack, 10F, 10F);
        }
        if(playerToAttack != null && playerToAttack.isDead)
        {
            playerToAttack = null;
        }
        if(!onGround && motionY < 0.05000000074505806D && flutter > 0)
        {
            motionY += 0.070000000298023224D;
            flutter--;
        }
        if(ticker < 4)
        {
            ticker++;
        } else
        {
            if(onGround && riddenByEntity == null && hops != 0 && hops != 3)
            {
                hops = 0;
            }
            if(playerToAttack == null && riddenByEntity == null)
            {
                Entity entity = getPrey();
                if(entity != null)
                {
                    playerToAttack = entity;
                }
            } else
            if(playerToAttack != null && riddenByEntity == null)
            {
                if(getDistanceToEntity(playerToAttack) <= 9F)
                {
                    if(onGround && canEntityBeSeen(playerToAttack))
                    {
                        splotch();
                        flutter = 10;
                        isJumping = true;
                        moveForward = 1.0F;
                        rotationYaw += 5F * (rand.nextFloat() - rand.nextFloat());
                    }
                } else
                {
                    playerToAttack = null;
                    isJumping = false;
                    moveForward = 0.0F;
                }
            } else
            if(riddenByEntity != null && onGround)
            {
                if(hops == 0)
                {
                    splotch();
                    onGround = false;
                    motionY = 0.34999999403953552D;
                    moveForward = 0.8F;
                    hops = 1;
                    flutter = 5;
                    rotationYaw += 20F * (rand.nextFloat() - rand.nextFloat());
                } else
                if(hops == 1)
                {
                    splotch();
                    onGround = false;
                    motionY = 0.44999998807907104D;
                    moveForward = 0.9F;
                    hops = 2;
                    flutter = 5;
                    rotationYaw += 20F * (rand.nextFloat() - rand.nextFloat());
                } else
                if(hops == 2)
                {
                    splotch();
                    onGround = false;
                    motionY = 1.25D;
                    moveForward = 1.25F;
                    hops = 3;
                    flutter = 5;
                    rotationYaw += 20F * (rand.nextFloat() - rand.nextFloat());
                }
            }
            ticker = 0;
        }
        if(onGround && hops >= 3)
        {
            dissolve();
        }
    }
    
    @Override
    public void d_2()
    {
        if(riddenByEntity != null && (riddenByEntity instanceof EntityLiving))
        {
            moveForward = 0.0F;
            moveStrafing = 0.0F;
            isJumping = false;
            mod_AetherMp.PackageAccess.EntityLiving.setFallDistance((EntityLiving) riddenByEntity, 0.0f);
            prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
            prevRotationPitch = rotationPitch = 0.0F;
            EntityLiving entityliving = (EntityLiving)riddenByEntity;
            float f = 3.141593F;
            float f1 = f / 180F;
            float f2 = entityliving.rotationYaw * f1;
        	boolean flag = mod_AetherMp.PackageAccess.EntityLiving.getIsJumping(entityliving);
        	float forward = mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving);
        	float strafing = mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving);
            if(onGround)
            {
                if(flag)
                {
                    if(hops == 0)
                    {
                        onGround = false;
                        motionY = 0.85000002384185791D;
                        hops = 1;
                        flutter = 5;
                    } else
                    if(hops == 1)
                    {
                        onGround = false;
                        motionY = 1.0499999523162842D;
                        hops = 2;
                        flutter = 5;
                    } else
                    if(hops == 2)
                    {
                        onGround = false;
                        motionY = 1.25D;
                        flutter = 5;
                    }
                } else
                if(forward > 0.125F || forward < -0.125F || strafing > 0.125F || strafing < -0.125F)
                {
                    onGround = false;
                    motionY = 0.34999999403953552D;
                    hops = 0;
                    flutter = 0;
                } else
                if(hops > 0)
                {
                    hops = 0;
                }
                mod_AetherMp.PackageAccess.EntityLiving.setMoveForward(entityliving, 0.0f);
                mod_AetherMp.PackageAccess.EntityLiving.setMoveStrafing(entityliving, 0.0f);
            } else
            {
                if(forward > 0.1F)
                {
                    if(getTexture() == 1)
                    {
                        motionX += (double)forward * -Math.sin(f2) * 0.125D;
                        motionZ += (double)forward * Math.cos(f2) * 0.125D;
                    } else
                    {
                        motionX += (double)forward * -Math.sin(f2) * 0.32500000000000001D;
                        motionZ += (double)forward * Math.cos(f2) * 0.125D;
                    }
                } else
                if(forward < -0.1F)
                {
                    if(getTexture() == 1)
                    {
                        motionX += (double)forward * -Math.sin(f2) * 0.125D;
                        motionZ += (double)forward * Math.cos(f2) * 0.125D;
                    } else
                    {
                        motionX += (double)forward * -Math.sin(f2) * 0.32500000000000001D;
                        motionZ += (double)forward * Math.cos(f2) * 0.125D;
                    }
                }
                if(strafing > 0.1F)
                {
                    if(getTexture() == 1)
                    {
                        motionX += (double)strafing * Math.cos(f2) * 0.125D;
                        motionZ += (double)strafing * Math.sin(f2) * 0.125D;
                    } else
                    {
                        motionX += (double)strafing * Math.cos(f2) * 0.32500000000000001D;
                        motionZ += (double)strafing * Math.sin(f2) * 0.125D;
                    }
                } else
                if(strafing < -0.1F)
                {
                    if(getTexture() == 1)
                    {
                        motionX += (double)strafing * Math.cos(f2) * 0.125D;
                        motionZ += (double)strafing * Math.sin(f2) * 0.125D;
                    } else
                    {
                        motionX += (double)strafing * Math.cos(f2) * 0.32500000000000001D;
                        motionZ += (double)strafing * Math.sin(f2) * 0.125D;
                    }
                }
                if(motionY < 0.05000000074505806D && flutter > 0 && flag)
                {
                    motionY += 0.070000000298023224D;
                    flutter--;
                }
            }
            double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));
            if(d > 0.27500000596046448D)
            {
                double d1 = 0.27500000000000002D / d;
                motionX = motionX * d1;
                motionZ = motionZ * d1;
            }
        }
    }

}
