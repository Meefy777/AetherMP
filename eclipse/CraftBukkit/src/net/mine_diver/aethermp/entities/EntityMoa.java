// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityMoa.java

package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.util.MoaColour;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, MoaColour, World, ItemStack, 
//            AetherItems, Entity, EntityLiving, NBTTagCompound, 
//            EntityPlayer, InventoryPlayer, Item, mod_Aether, 
//            ModLoader

public class EntityMoa extends EntityAetherAnimal
{

    public EntityMoa(World world)
    {
        this(world, false, false, false);
    }

    public EntityMoa(World world, boolean babyBool, boolean grownBool, boolean saddledBool)
    {
        this(world, babyBool, grownBool, saddledBool, MoaColour.pickRandomMoa());
    }

    public EntityMoa(World world, boolean babyBool, boolean grownBool, boolean saddledBool, MoaColour moaColour)
    {
        super(world);
        setPetalsEaten(0);
        setWellFed(false);
        followPlayer = false;
        baby = false;
        grown = false;
        saddled = false;
        destPos = 0.0F;
        wingRotDelta = 1.0F;
        bs = 1.0F;
        jrem = 0;
        setBaby(babyBool);
        setGrown(grownBool);
        setSaddled(saddledBool);
        setColor(moaColour.ID);
      
        if(getBaby())
        {
            b(0.4F, 0.5F);
        } else {
        	b(1.0F, 2.0F);
        }
        colour = moaColour;
        texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
        
        health = 40;
        timeUntilNextEgg = random.nextInt(6000) + 6000;
        
        
    }

    @Override
    public void m_()
    {
        super.m_();
        bK = passenger == world.findNearbyPlayer(this, 100D);
    }
    
    @Override
    protected void b()
    {
        datawatcher.a(16, Byte.valueOf((byte)0));
        datawatcher.a(17, Byte.valueOf((byte)0));
        datawatcher.a(18, Byte.valueOf((byte)0));
        datawatcher.a(19, Byte.valueOf((byte)0));
        datawatcher.a(20, Byte.valueOf((byte)0));
        datawatcher.a(21, Byte.valueOf((byte)0));
    }

    @Override
    public void v()
    {
        super.v();
        
        if(getBaby())
        {
            b(0.4F, 0.5F);
        } else
        {
            b(1.0F, 2.0F);
        }
        texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
        oFlap = wingRotation;
        oFlapSpeed = destPos;
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
            jrem = MoaColour.getColour(getColor()).jumps;
        }
        if(!onGround && wingRotDelta < 1.0F)
        {
        	wingRotDelta = 1.0F;
        }
        wingRotDelta *= 0.90000000000000002D;
        if(!onGround && motY < 0.0D)
        {
            if(passenger == null)
            {
                motY *= 0.59999999999999998D;
            } else
            {
                motY *= 0.63749999999999996D;
            }
        }
        wingRotation += wingRotDelta * 2.0F;
        if(!getBaby() && --timeUntilNextEgg <= 0)
        {
        	PacketManager.makeSound(this, "mob.chickenplop", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            a(new ItemStack(ItemManager.MoaEgg, 1, getColor()), 0.0F);
            timeUntilNextEgg = random.nextInt(6000) + 6000;
        }
        if(getWellFed() && random.nextInt(2000) == 0)
        {
            setWellFed(false);
        }
        if(getSaddled() && passenger == null)
        {
            aE = 0.0F;
        } else
        {
            aE = 0.7F;
        }
    }

    @Override
    protected void a(float f1)
    {
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        boolean flag = super.damageEntity(entity, i);
        if(flag && passenger != null && (health <= 0 || random.nextInt(3) == 0))
        {
        	passenger.mount(this);
        }
        return flag;
    }

    @Override
    public void c_()
    {
        if(passenger != null && (passenger instanceof EntityLiving))
        {
        	aA = 0.0F;
            az = 0.0F;
            aC = false;
            passenger.fallDistance = 0.0F;
            return;
	        } else
	        {
	            super.c_(); 
	            return;
	        }
    	} 

	@Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("Baby", getBaby());
        nbttagcompound.a("Grown", getGrown());
        nbttagcompound.a("Saddled", getSaddled());
        nbttagcompound.a("ColorNumber", getColor());
        nbttagcompound.a("WellFed", getWellFed());
        nbttagcompound.a("PetalsEaten", getPetalsEaten());
        
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        setBaby(nbttagcompound.m("Baby"));
        setGrown(nbttagcompound.m("Grown"));
        setSaddled(nbttagcompound.m("Saddled"));
        setColor(nbttagcompound.e("ColorNumber"));
        setWellFed(nbttagcompound.m("WellFed"));
        setPetalsEaten(nbttagcompound.e("PetalsEaten"));
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

    @Override
    public boolean a(EntityHuman entityplayer)
    {
        if(!getSaddled() && getGrown() && !getBaby() && entityplayer.inventory.getItemInHand() != null && entityplayer.inventory.getItemInHand().id == Item.SADDLE.id)
        {
            entityplayer.inventory.setItem(entityplayer.inventory.itemInHandIndex, null);
            setSaddled(true);
            setGrown(false);
            texture = MoaColour.getColour(getColor()).getTexture(getSaddled());
            return true;
        }
        if(getSaddled() && (passenger == null || passenger == entityplayer))
        {
            entityplayer.mount(this);
            entityplayer.lastYaw = entityplayer.yaw = yaw;
            Packet230ModLoader packet = new Packet230ModLoader();
            packet.packetType = 30;
            packet.dataInt = new int [] {jrem};
            if(passenger != null && passenger instanceof EntityPlayer)
            	ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), (EntityPlayer) passenger, packet);
            return true;
        }
        if(!getWellFed() && !getSaddled() && getBaby() && !getGrown())
        {
            ItemStack itemstack = entityplayer.inventory.getItemInHand();
            if(itemstack != null && itemstack.id == ItemManager.AechorPetal.id)
            {
                setPetalsEaten(getPetalsEaten() + 1);
                entityplayer.inventory.splitStack(entityplayer.inventory.itemInHandIndex, 1);
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
            if(!followPlayer)
            {
                followPlayer = true;
                target = entityplayer;
            } else
            {
                followPlayer = false;
                target = null;
            }
        }
        return true;
    }

    @Override
    public boolean h_()
    {
        return !getBaby() && !getGrown() && !getSaddled();
    }
    
    @Override
    protected boolean n()
    {
        return onGround;
    }

    @Override
    protected void q()
    {
        dropFewItems(null);
    }
    
    @Override
    public void die(Entity entity) {
    	if (W >= 0 && entity != null)
            entity.c(this, W);
        if (entity != null)
            entity.a(this);
        this.ak = true;
        if (entity instanceof EntityHuman)
            dropFewItems((EntityHuman) entity);
        else
        	q();
        world.a(this, (byte)3);
    }
    
    protected void dropFewItems(EntityHuman human) {
        List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        int count = 3 * (human != null && ItemManager.equippedSkyrootSword(human) ? 2 : 1);
        loot.add(new org.bukkit.inventory.ItemStack(Item.FEATHER.id, count));
        CraftEntity entity = (CraftEntity)this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        for (org.bukkit.inventory.ItemStack stack : event.getDrops())
            b(stack.getTypeId(), stack.getAmount());
    }
    
    public boolean getBaby()
    {
        return (datawatcher.a(16) & 1) != 0;
    }

    public void setBaby(boolean flag)
    {
        if(flag)
        {
            datawatcher.watch(16, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(16, Byte.valueOf((byte)0));
        }
    }

    public boolean getSaddled()
    {
        return (datawatcher.a(17) & 1) != 0;
    }

    public void setSaddled(boolean flag)
    {
        if(flag)
        {
            datawatcher.watch(17, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(17, Byte.valueOf((byte)0));
        }
    }

    public boolean getGrown()
    {
        return (datawatcher.a(18) & 1) != 0;
    }

    public void setGrown(boolean flag)
    {
        if(flag)
        {
            datawatcher.watch(18, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(18, Byte.valueOf((byte)0));
        }
    }

    public int getColor()
    {
        return datawatcher.a(19);
    }

    public void setColor(int i)
    {
        datawatcher.watch(19, Byte.valueOf((byte)i));
    }

    public int getPetalsEaten()
    {
        return datawatcher.a(20);
    }

    public void setPetalsEaten(int i)
    {
        datawatcher.watch(20, Byte.valueOf((byte)i));
    }

    public boolean getWellFed()
    {
        return (datawatcher.a(21) & 1) != 0;
    }

    public void setWellFed(boolean flag)
    {
        if(flag)
        {
            datawatcher.watch(21, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(21, Byte.valueOf((byte)0));
        }
    }

    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta;
    public int timeUntilNextEgg; 
    public int jrem;
    int petalsEaten;
    boolean wellFed;
    public boolean followPlayer; 
    public boolean jpress;
    public boolean baby;
    public boolean grown;
    public boolean saddled;
    public MoaColour colour;

}
