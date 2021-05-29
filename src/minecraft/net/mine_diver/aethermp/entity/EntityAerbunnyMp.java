package net.mine_diver.aethermp.entity;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAerbunny;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
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
		if(ridingEntity != null && ridingEntity instanceof EntityPlayer)
			updatePlayerActionState();
		EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());
		EntityPlayer client = ModLoader.getMinecraftInstance().thePlayer;
		
		if (player != null && client != null && !player.username.equals(client.username) && ridingEntity == null && !getPlayer().equals("") || ridingEntity != null && ridingEntity instanceof EntityPlayer && ((EntityPlayer) ridingEntity).username != client.username && getPlayer().equals("")) {
			if (player != null)
				mountEntity(player);
			else if (ridingEntity != null)
				mountEntity(ridingEntity);
		}
		if (ridingEntity == null)
			puffiness = getPuffiness();
		getYOffset();
		
	}
	
	@Override
	public double getYOffset() {
        if(ridingEntity instanceof EntityPlayer) {
        	EntityPlayer player = (EntityPlayer) ridingEntity;
        	EntityPlayer entityplayer = ModLoader.getMinecraftInstance().thePlayer;
        	if (player.username.equals(entityplayer.username))
        		return super.getYOffset();
            return (double)(yOffset + 0.50F);
        }
        else
            return (double)yOffset;
	}
	
	@Override
	public void updatePlayerActionState() {
		super.updatePlayerActionState();
   	    Packet230ModLoader packetMove = new Packet230ModLoader();
   	    packetMove.packetType = 69;
   	    packetMove.dataFloat = new float[] {(float) this.motionX, (float) this.motionY, (float) this.motionZ, (float) this.posX, (float) this.posY, (float) this.posZ, this.rotationYaw, this.rotationPitch, this.puffiness};
   	    ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packetMove);
	}
	
	@Override
    public void entityInit()
    {
    	super.entityInit();
    	dataWatcher.addObject(16, String.valueOf((byte)0));
    	dataWatcher.addObject(17, Byte.valueOf((byte)0)); 
    	dataWatcher.addObject(18, String.valueOf((String) ""));
    }
	
	@Override
	public void runLikeHell() {
	}
	
	@Override
    protected Entity findPlayerToRunFrom()
    {
		return null;
    }
	
	@Override
	public void cloudPoop() {
        double a = rand.nextFloat() - 0.5F;
        double d = posX + a * 0.40000000596046448D;
        double e = boundingBox.minY;
        double f = posZ + a * 0.40000000596046448D;
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.packetType = 71;
		packet.dataFloat = new float [] {(float) d, (float) e, (float) f, 0.0F, -0.075000002980232239F, 0.0F, (float) posX, (float) posY, (float) posZ};
		packet.dataString = new String [] {"explode"};
		ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
	}
	
	@Override
	public void jump() {
	}
	
	@Override
    public boolean interact(EntityPlayer entityplayer)
    {
		if(ridingEntity != null && !getPlayer().equals(entityplayer.username))
			return true;
		if(!getPlayer().equals(""))
			setPlayer("");
		return super.interact(entityplayer);
    }
	
    public float getPuffiness()
    {
    	String s = dataWatcher.getWatchableObjectString(16);
    	if (s == null || s.equals(""))
    		s = "0";
    	return Float.valueOf(s);
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
    
    public String getPlayer()
    {
    	return dataWatcher.getWatchableObjectString(18);
    }
    
    public void setPlayer(String value)
    {
    	dataWatcher.updateObject(16, String.valueOf(value));
    }

}
