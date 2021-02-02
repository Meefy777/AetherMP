package net.mine_diver.aethermp.entity;

import java.util.List;

import net.minecraft.src.Entity;
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
    public void onUpdate()
    {
		if (worldObj.multiplayerWorld && this.ridingEntity != null && this.ridingEntity instanceof EntityPlayer) {
	        if(gotrider)
	        {
	            gotrider = false;
	            if(ridingEntity == null)
	            {
	                EntityPlayer entityplayer = (EntityPlayer)findPlayerToRunFrom();
	                if(entityplayer != null && getDistanceToEntity(entityplayer) < 2.0F && entityplayer.riddenByEntity == null)
	                {
	                    mountEntity(entityplayer);
	                }
	            }
	        }
	        if(age < 1023)
	        {
	            age++;
	        } else
	        if(mate < 127)
	        {
	            mate++;
	        } else
	        {
	            int i = 0;
	            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 16D, 16D));
	            for(int j = 0; j < list.size(); j++)
	            {
	                Entity entity = (Entity)list.get(j);
	                if(entity instanceof EntityAerbunny)
	                {
	                    i++;
	                }
	            }
	
	            if(i > 12)
	            {
	                proceed();
	                return;
	            }
	            List list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 1.0D, 1.0D));
	            boolean flag = false;
	            for(int k = 0; k < list.size(); k++)
	            {
	                Entity entity1 = (Entity)list1.get(k);
	                if(!(entity1 instanceof EntityAerbunny) || entity1 == this)
	                {
	                    continue;
	                }
	                EntityAerbunny entitybunny = (EntityAerbunny)entity1;
	                if(entitybunny.ridingEntity != null || entitybunny.age < 1023)
	                {
	                    continue;
	                }
	                EntityAerbunny entitybunny1 = new EntityAerbunny(worldObj);
	                entitybunny1.setPosition(posX, posY, posZ);
	                worldObj.entityJoinedWorld(entitybunny1);
	                worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
	                proceed();
	                entitybunny.proceed();
	                flag = true;
	                break;
	            }
	
	            if(!flag)
	            {
	                mate = rand.nextInt(16);
	            }
	        }
	        if(puffiness > 0.0F)
	        {
	            puffiness -= 0.1F;
	        } else
	        {
	            puffiness = 0.0F;
	        }
    	}
		super.onUpdate();
    }
	
	@Override
	public void runLikeHell() {
		if (ridingEntity != null && ridingEntity instanceof EntityPlayer)
			super.runLikeHell();
	}

}
