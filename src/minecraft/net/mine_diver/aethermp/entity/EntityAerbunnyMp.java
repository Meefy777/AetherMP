package net.mine_diver.aethermp.entity;

import net.minecraft.src.EntityAerbunny;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

public class EntityAerbunnyMp extends EntityAerbunny {

	public EntityAerbunnyMp(World world) {
		super(world);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.worldObj.multiplayerWorld && ridingEntity != null && ridingEntity instanceof EntityPlayer)
			updatePlayerActionState();
	}
	
	@Override
	public void updatePlayerActionState() {
	super.updatePlayerActionState();
   	   Packet230ModLoader packetMove = new Packet230ModLoader();
   	   packetMove.packetType = 69;
   	   packetMove.dataFloat = new float[] {(float) this.motionX, (float) this.motionY, (float) this.motionZ, (float) this.posX, (float) this.posY, (float) this.posZ, this.rotationYaw, this.rotationPitch};
   	   ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packetMove);
	}
	
	@Override
    public void entityInit()
    {
    	super.entityInit();
    	dataWatcher.addObject(16, String.valueOf((byte)0));
    	dataWatcher.addObject(17, Byte.valueOf((byte)0)); 
    }
	
	@Override
	public void runLikeHell() {
		if (ridingEntity != null && ridingEntity instanceof EntityPlayer)
			super.runLikeHell();
	}
	
    public float getPuffiness()
    {
    	return Float.valueOf(dataWatcher.getWatchableObjectString(16));
    }
    
    public void setPuffiness(float value)
    {
    	dataWatcher.updateObject(16, String.valueOf(value));
    }
    
    public boolean getRenderOnGround()
    {
        return (dataWatcher.getWatchableObjectByte(17) & 1) != 0;
    }

    public void setRenderOnGround(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(17, Byte.valueOf((byte)1));
        } else
        {
            dataWatcher.updateObject(17, Byte.valueOf((byte)0));
        }
    }

}
