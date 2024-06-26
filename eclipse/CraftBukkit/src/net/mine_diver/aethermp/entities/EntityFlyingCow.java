// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityFlyingCow.java

package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.api.entities.IMountable;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, DataWatcher, NBTTagCompound, World, 
//            EntityLiving, Entity, EntityPlayer, InventoryPlayer, 
//            ItemStack, Item, mod_Aether

public class EntityFlyingCow extends EntityAetherAnimal implements IMountable
{

    public EntityFlyingCow(World world)
    {
        super(world);
        getSaddled = false;
        texture = "/aether/mobs/Mob_FlyingCowBase.png";
        b(0.9F, 1.3F);
        jrem = 0;
        jumps = 1;
        ticks = 0;
        bs = 1.0F;
        bK = true;
    }

    @Override
    protected boolean h_()
    {
        return !getSaddled();
    }

    @Override
    protected boolean n()
    {
        return onGround;
    }

    protected void b()
    {
        datawatcher.a(16, Byte.valueOf((byte)0));
    }

    @Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("getSaddled", getSaddled());
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        setSaddled(nbttagcompound.m("getSaddled"));
        if(getSaddled)
        {
            texture = "/aether/mobs/Mob_FlyingCowSaddle.png";
        }
    }
    
    public boolean getSaddled()
    {
        return (datawatcher.a(16) & 1) != 0;
    }
    
    public void setSaddled(boolean flag)
    {
        if(flag)
        {
            datawatcher.watch(16, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(16, Byte.valueOf((byte)0));
        }
    }

    @Override
    protected void O()
    {
        motY = 0.59999999999999998D;
    }

    @Override
    public void m_()
    {
        super.m_();
        if(onGround)
        {
            wingAngle *= 0.8F;
            aimingForFold = 0.1F;
            jrem = jumps;
        } else
        {
            aimingForFold = 1.0F;
        }
        ticks++;
        wingAngle = wingFold * (float)Math.sin((float)ticks / 31.83099F);
        wingFold += (aimingForFold - wingFold) / 5F;
        fallDistance = 0.0F;
        if(motY < -0.20000000000000001D)
        {
            motY = -0.20000000000000001D;
        }
        c_();
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
    public boolean a(EntityHuman entityplayer)
    {
        if(!getSaddled() && entityplayer.inventory.getItemInHand() != null && entityplayer.inventory.getItemInHand().id == Item.SADDLE.id)
        {
            entityplayer.inventory.setItem(entityplayer.inventory.itemInHandIndex, null);
            getSaddled = true;
            setSaddled(true);
            texture = "/aether/mobs/Mob_FlyingCowSaddle.png";
            return true;
        }
        if(getSaddled() && (passenger == null || passenger == entityplayer) && this.vehicle == null)
        {
            entityplayer.mount(this);
            Packet230ModLoader packet = new Packet230ModLoader();
            packet.packetType = 30;
            packet.dataInt = new int [] {jrem};
            if (passenger != null && passenger instanceof EntityPlayer)
            	ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), (EntityPlayer) passenger, packet);
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    protected void q()
    {
    	dropFewItems(null);
    }
    
    @Override
    public void die (Entity entity) {
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
        int count = 2 * (human != null && ItemManager.equippedSkyrootSword(human) ? 2 : 1);
        loot.add(new org.bukkit.inventory.ItemStack(Item.LEATHER.id, count));
        CraftEntity entity = (CraftEntity)this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        for (org.bukkit.inventory.ItemStack stack : event.getDrops())
            b(stack.getTypeId(), stack.getAmount());
    }
    
    public boolean getSaddled;
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private int ticks;
    public boolean hasJumped = true;
}
