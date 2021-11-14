// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityHomeShot.java

package net.mine_diver.aethermp.entities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityWeatherStorm;
import net.minecraft.server.ISpawnable;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;

// Referenced classes of package net.minecraft.src:
//            EntityFlying, World, EntityLiving, EntityLightningBolt, 
//            Entity, AxisAlignedBB, NBTTagCompound, EntityPlayer

public class EntityHomeShot extends EntityFlying implements ISpawnable
{

    public EntityHomeShot(World world)
    {
        super(world);
        texture = "/aether/mobs/electroball.png";
        lifeSpan = 200;
        life = lifeSpan;
        b(0.7F, 0.7F);
        firstRun = true;
        fireProof = true;        
    }

    public EntityHomeShot(World world, double x, double y, double z, 
            EntityLiving ep)
    {
        super(world);
        texture = "/aether/mobs/electroball.png";
        lifeSpan = 200;
        life = lifeSpan;
        b(0.7F, 0.7F);
        setPosition(x, y, z);
        target = ep;
        fireProof = true;
    }

    @Override
    public void m_()
    {
        super.m_();
        life--;
        if(firstRun && target == null)
        {
            target = (EntityLiving)findPlayerToAttack();
            firstRun = false;
        }
        if(target == null || target.dead || target.health <= 0)
        {
            dead = true;
        } else
        if(life <= 0)
        {  
        	BlockIgniteEvent event = null;
        	if (target instanceof EntityPlayer) {
        		event = new BlockIgniteEvent(world.getWorld().getBlockAt((int)locX, (int)locY, (int)locZ), BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, (Player) target.getBukkitEntity());
        		world.getServer().getPluginManager().callEvent(event);
        	}
        	EntityWeatherStorm entitylightningbolt = new EntityWeatherStorm(world, locX, locY, locZ);
        	if(event == null || !event.isCancelled())
        		world.strikeLightning(entitylightningbolt);
            dead = true;
        } else
        {
            faceIt();
            moveIt(target, 0.02D);
        }
    }

    public void moveIt(Entity e1, double sped)
    {
        double angle1 = yaw / 57.29577F;
        motX -= Math.sin(angle1) * sped;
        motZ += Math.cos(angle1) * sped;
        double a = e1.locY - 0.75D;
        double minY = boundingBox.b;
        if(a < minY - 0.5D)
        {
            motY -= sped / 2D;
        } else
        if(a > minY + 0.5D)
        {
            motY += sped / 2D;
        } else
        {
            motY += (a - minY) * (sped / 2D);
        }
        if(onGround)
        {
            onGround = false;
            motY = 0.10000000149011612D;
        }
    }

    public void faceIt()
    {
        a(target, 10F, 10F);
    }

    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("LifeLeft", (short)life);
    }

    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        life = nbttagcompound.d("LifeLeft");
    }

    public void checkOverLimit()
    {
        double a = target.locX - locX;
        double b = target.locY - locY;
        double c = target.locZ - locZ;
        double d = Math.sqrt(a * a + b * b + c * c);
        if(d > 0.125D)
        {
            double e = 0.125D / d;
            motX *= e;
            motY *= e;
            motZ *= e;
        }
    }

    public Entity findPlayerToAttack()
    {
        EntityHuman entityplayer = world.findNearbyPlayer(this, 16D);
        if(entityplayer != null && e(entityplayer))
        {
            return entityplayer;
        } else
        {
            return null;
        }
    }

    @Override
    public void collide(Entity entity)
    {
        super.collide(entity);
        if(entity != null && target != null && entity == target)
        {
        	EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), DamageCause.PROJECTILE, 1);
        	world.getServer().getPluginManager().callEvent(event);
        	if (!event.isCancelled()) {
	            boolean flag = entity.damageEntity(this, 1);
	            if(flag)
	                moveIt(entity, -0.10000000000000001D);
        	}
        }
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        if(entity != null)
        {
            moveIt(entity, -0.14999999999999999D - (double)i / 8D);
            return true;
        } else
        {
            return false;
        }
    }
    
	@Override
	public Packet230ModLoader getSpawnPacket() {
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.dataInt = new int [] {id};
		packet.dataFloat = new float [] {(float) locX, (float) locY, (float) locZ, (float) motX, (float) motY, (float) motZ};
		return packet;
	}
	
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    public EntityLiving target;
    public boolean firstRun;
    public int life;
    public int lifeSpan;
}
