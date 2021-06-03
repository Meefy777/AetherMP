package net.mine_diver.aethermp.entity;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntitySwet;
import net.minecraft.src.World;

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

}
