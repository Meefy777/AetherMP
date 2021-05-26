package net.mine_diver.aethermp.entity;

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
	}
	
    public byte getTexture() {
    	return dataWatcher.getWatchableObjectByte(14);
    }
    
    public float getWidth() {
    	return Float.valueOf(dataWatcher.getWatchableObjectString(15));
    }
    
    public float getHeight() {
    	return Float.valueOf(dataWatcher.getWatchableObjectString(16));
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
    	System.out.println(motionY);
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

}
