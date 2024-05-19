// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityMoa.java

package net.mine_diver.aethermp.entity;

import java.util.List;
import net.minecraft.src.AetherItems;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMoa;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MoaColour;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, MoaColour, World, ItemStack, 
//            AetherItems, Entity, EntityLiving, NBTTagCompound, 
//            EntityPlayer, InventoryPlayer, Item, mod_Aether, 
//            ModLoader

public class EntityMoaMp extends EntityMoa
{

    public EntityMoaMp(World world)
    {
        this(world, false, false, false);
    }

    public EntityMoaMp(World world, boolean babyBool, boolean grownBool, boolean saddledBool)
    {
        this(world, babyBool, grownBool, saddledBool, MoaColour.pickRandomMoa());
    }

    public EntityMoaMp(World world, boolean babyBool, boolean grownBool, boolean saddledBool, MoaColour moaColour)
    {
        super(world);
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
        dataWatcher.addObject(17, Byte.valueOf((byte)0));
        dataWatcher.addObject(18, Byte.valueOf((byte)0));
        dataWatcher.addObject(19, Byte.valueOf((byte)0));
        dataWatcher.addObject(20, Byte.valueOf((byte)0));
        dataWatcher.addObject(21, Byte.valueOf((byte)0));
        
        setPetalsEaten(0);
        setWellFed(false);
        setPrivateBool(EntityMoa.class, "followPlayer", false);
        baby = false;
        grown = false;
        saddled = false;
        destPos = 0.0F;
        field_755_h = 1.0F;
        stepHeight = 1.0F;
        jrem = 0;
        setBaby(babyBool);
        setGrown(grownBool);
        setSaddled(saddledBool);
        setColor(moaColour.ID);
        
        if(getBaby())
        {
            setSize(0.4F, 0.5F);
        } else {
        	setSize(1.0F, 2.0F);
        }
        colour = moaColour;
        texture = colour.getTexture(saddled);
        health = 40;
        timeUntilNextEgg = rand.nextInt(6000) + 6000;
        texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
    }
    
    /*protected void entityInit()
    {
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
        dataWatcher.addObject(17, Byte.valueOf((byte)0));
        dataWatcher.addObject(18, Byte.valueOf((byte)0));
        dataWatcher.addObject(19, Byte.valueOf((byte)0));
        dataWatcher.addObject(20, Byte.valueOf((byte)0));
        dataWatcher.addObject(21, Byte.valueOf((byte)0));
    }*/

    public void onLivingUpdate()
    {
    	onLivingUpdateMP();
        if(getBaby())
        {
            setSize(0.4F, 0.5F);
        } else
        {
            setSize(1.0F, 2.0F);
        }
        texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
        field_756_e = field_752_b;
        field_757_d = destPos;
        destPos += (double)(onGround ? -1 : 4) * 0.050000000000000003D;
        if(destPos < 0.01F)
        {
            destPos = 0.01F;
        }
        if(destPos > 1.0F)
        {
            destPos = 1.0F;
        }
        if(onGround)
        {
            destPos = 0.0F;
            jpress = false;
            if(riddenByEntity != null && riddenByEntity instanceof EntityPlayer) {
	            jrem = MoaColour.getColour(getColor()).jumps;
	            Packet230ModLoader packet = new Packet230ModLoader();
	            packet.packetType = 61;
	            packet.dataInt = new int [] {jrem};
	            ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
            }
        }
        if(!onGround && field_755_h < 1.0F)
        {
        	field_755_h = 1.0F;
        }
        field_755_h *= 0.90000000000000002D;
        if(!onGround && motionY < 0.0D)
        {
            if(riddenByEntity == null)
            {
                motionY *= 0.59999999999999998D;
            } else
            {
                motionY *= 0.63749999999999996D;
            }
        }
        field_752_b += field_755_h * 2.0F;
        if(!worldObj.multiplayerWorld && !getBaby() && --timeUntilNextEgg <= 0)
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            entityDropItem(new ItemStack(AetherItems.MoaEgg, 1, getColor()), 0.0F);
            timeUntilNextEgg = rand.nextInt(6000) + 6000;
        }
        if(getWellFed() && rand.nextInt(2000) == 0)
        {
            setWellFed(false);
        }
        if(getSaddled() && riddenByEntity == null)
        {
            moveSpeed = 0.0F;
        } else
        {
            moveSpeed = 0.7F;
        }
        updatePlayerActionState();
    }
    
    @SuppressWarnings("rawtypes")
	public void onLivingUpdateMP()
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
        if(riddenByEntity != null && (riddenByEntity instanceof EntityLiving))
        {
            moveForward = 0.0F;
            moveStrafing = 0.0F;
            isJumping = false;
            mod_AetherMp.PackageAccess.EntityLiving.setFallDistance((EntityLiving) riddenByEntity, 0.0F);
            prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
            prevRotationPitch = rotationPitch = riddenByEntity.rotationPitch;
            EntityLiving entityliving = (EntityLiving)riddenByEntity;
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
                motionY = 0.875D;
                jpress = true;
                jrem--;
                packet.dataInt = new int [] {jrem};
                ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
            } else
            if(handleWaterMovement() && jump)
            {
                motionY = 0.5D;
                jpress = true;
                jrem--;
                packet.dataInt = new int [] {jrem};
                ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
            } else
            if(jrem > 0 && !jpress && jump)
            {
                motionY = 0.75D;
                jpress = true;
                jrem--;
                packet.dataInt = new int [] {jrem};
                ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
            }
            if(jpress && !jump)
            {
                jpress = false;
            }
            double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));
            if(d > 0.375D)
            {
                double d1 = 0.375D / d;
                motionX = motionX * d1;
                motionZ = motionZ * d1;
            }
            
      	   Packet230ModLoader packetMove = new Packet230ModLoader();
      	   packetMove.packetType = 69;
      	   packetMove.dataFloat = new float[] {(float) this.motionX, (float) this.motionY, (float) this.motionZ, (float) this.posX, (float) this.posY, (float) this.posZ, this.rotationYaw, this.rotationPitch};
      	   ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packetMove);
            
            return;
        } else
        {
            super.updatePlayerActionState();
            return;
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Baby", getBaby());
        nbttagcompound.setBoolean("Grown", getGrown());
        nbttagcompound.setBoolean("Saddled", getSaddled());
        nbttagcompound.setInteger("ColorNumber", getColor());
        nbttagcompound.setBoolean("WellFed", getWellFed());
        nbttagcompound.setInteger("PetalsEaten", getPetalsEaten());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setBaby(nbttagcompound.getBoolean("Baby"));
        setGrown(nbttagcompound.getBoolean("Grown"));
        setSaddled(nbttagcompound.getBoolean("Saddled"));
        setColor(nbttagcompound.getInteger("ColorNumber"));
        setWellFed(nbttagcompound.getBoolean("WellFed"));
        setPetalsEaten(nbttagcompound.getInteger("PetalsEaten"));
        if(getBaby())
        {
            setGrown(false);
            setSaddled(false);
        }
        if(getGrown())
        {
            setBaby(false);
            setSaddled(false);
        }
        if(getSaddled())
        {
            setBaby(false);
            setGrown(false);
        }
        texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        if(!getSaddled() && getGrown() && !getBaby() && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().itemID == Item.saddle.shiftedIndex)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            setSaddled(true);
            setGrown(false);
            texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
            return true;
        }
        if(getSaddled() && !worldObj.multiplayerWorld && (riddenByEntity == null || riddenByEntity == entityplayer) && !this.isRiding())
        {
            entityplayer.mountEntity(this);
            entityplayer.prevRotationYaw = entityplayer.rotationYaw = rotationYaw;
            return true;
        }
        if(getSaddled() && (entityplayer instanceof EntityOtherPlayerMP) && (riddenByEntity == null || riddenByEntity == entityplayer) && !this.isRiding())
        {
            entityplayer.mountEntity(this);
            return true;
        }
        if(!getWellFed() && !getSaddled() && getBaby() && !getGrown())
        {
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if(itemstack != null && itemstack.itemID == AetherItems.AechorPetal.shiftedIndex)
            {
            	setPetalsEaten(getPetalsEaten() + 1);
                entityplayer.inventory.decrStackSize(entityplayer.inventory.currentItem, 1);
                if(getPetalsEaten() > MoaColour.getColour(getColor()).jumps)
                {
                    setGrown(true);
                    setBaby(false);
                }
                setWellFed(true);
            }
            return true;
        }
        if(!getSaddled() && (getBaby() || getGrown()))
        {
            if(!getPrivateBool(EntityMoa.class, "followPlayer")) 
            {
            	setPrivateBool(EntityMoa.class, "followPlayer", true);
                playerToAttack = entityplayer;
            } else
            {
            	setPrivateBool(EntityMoa.class, "followPlayer", false);
                playerToAttack = null;
            }

        }
        return true;
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

    public boolean canDespawn()
    {
        return !getBaby() && !getGrown() && !getSaddled();
    }
    
    public boolean getBaby()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setBaby(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)1));
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)0));
        }
    }

    public boolean getSaddled()
    {
        return (dataWatcher.getWatchableObjectByte(17) & 1) != 0;
    }

    public void setSaddled(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(17, Byte.valueOf((byte)1));
        } else
        {
            dataWatcher.updateObject(17, Byte.valueOf((byte)0));
        }
    }

    public boolean getGrown()
    {
        return (dataWatcher.getWatchableObjectByte(18) & 1) != 0;
    }

    public void setGrown(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(18, Byte.valueOf((byte)1));
        } else
        {
            dataWatcher.updateObject(18, Byte.valueOf((byte)0));
        }
    }

    public int getColor()
    {
        return dataWatcher.getWatchableObjectByte(19);
    }

    public void setColor(int i)
    {
        dataWatcher.updateObject(19, Byte.valueOf((byte)i));
    }

    public int getPetalsEaten()
    {
        return dataWatcher.getWatchableObjectByte(20);
    }

    public void setPetalsEaten(int i)
    {
        dataWatcher.updateObject(20, Byte.valueOf((byte)i));
    }

    public boolean getWellFed()
    {
        return (dataWatcher.getWatchableObjectByte(21) & 1) != 0;
    }

    public void setWellFed(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(21, Byte.valueOf((byte)1));
        } else
        {
            dataWatcher.updateObject(21, Byte.valueOf((byte)0));
        }
    }

}
