 // Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityAerbunny.java

package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PathEntity;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;
import net.minecraft.server.ModLoader;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, World, EntityPlayer, AxisAlignedBB, 
//            Entity, NBTTagCompound, EntityLiving, MathHelper, 
//            EntityMob, Block, mod_Aether, Item

public class EntityAerbunny extends EntityAetherAnimal
{

    public EntityAerbunny(World world)
    {
        super(world);
        aE = 2.5F;
        texture = "/aether/mobs/aerbunny.png";
        height = -0.16F;
        b(0.4F, 0.4F);
        health = 6;
        if(aH < 5D)
        {
        	aH = 5D;
        }
        age = random.nextInt(64);
        mate = 0;
    }
    
    @Override
    public void b()
    {
    	datawatcher.a(16, String.valueOf((byte)0));
    	datawatcher.a(17, Byte.valueOf((byte)0));
    }


    @Override
    public void m_()
    {
       /* if(gotrider)
        {
            gotrider = false;
            if(vehicle == null)
            {
                EntityPlayer entityplayer = (EntityPlayer)findPlayerToRunFrom();
                if(entityplayer != null && f(entityplayer) < 2.0F && entityplayer.passenger == null)
                {
                    mount(entityplayer);
                }
            }
        }*/
    	if(onGround != getRenderOnGround())
    	{
    		setRenderOnGround(onGround);
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
            List list = world.b(this, boundingBox.b(16D, 16D, 16D));
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
            List list1 = world.b(this, boundingBox.b(1.0D, 1.0D, 1.0D));
            boolean flag = false;
            for(int k = 0; k < list.size(); k++)
            {
                Entity entity1 = (Entity)list1.get(k);
                if(!(entity1 instanceof EntityAerbunny) || entity1 == this)
                {
                    continue;
                }
                EntityAerbunny entitybunny = (EntityAerbunny)entity1;
                if(entitybunny.vehicle != null || entitybunny.age < 1023)
                {
                    continue;
                }
                EntityAerbunny entitybunny1 = new EntityAerbunny(world);
                entitybunny1.setPosition(locX, locY, locZ);
                world.addEntity(entitybunny1);
                PacketManager.makeSound(this, "mob.chickenplop", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                proceed();
                entitybunny.proceed();
                flag = true;
                break;
            }

            if(!flag)
            {
                mate = random.nextInt(16);
            }
        }
        if(getPuffiness() > 0.0F)
        {
            setPuffiness(getPuffiness()-0.1F);
        } else
        {
            setPuffiness(0.0F);
        }
        super.m_();
    }

    @Override
    protected void a(float f1)
    {
    }

    @Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("Fear", fear);
        if(passenger != null)
        {
            gotrider = true;
        }
        nbttagcompound.a("GotRider", gotrider);
        nbttagcompound.a("RepAge", (short)age);
        nbttagcompound.a("RepMate", (short)mate);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        fear = nbttagcompound.m("Fear");
        gotrider = nbttagcompound.m("GotRider");
        age = nbttagcompound.d("RepAge");
        mate = nbttagcompound.d("RepMate");
    }

    @Override
    protected void c_()
    {
        if(onGround)
        {
            if(aA != 0.0F)
            {
                a();
            }
        } else
        if(vehicle != null)
        {
            if(vehicle.dead)
            {
                mount(vehicle);
            } else
            if(!vehicle.onGround && !vehicle.f_())
            {
            	vehicle.fallDistance = 0.0F;
            	vehicle.motY += 0.05000000074505806D;
            	boolean aC = mod_AetherMp.PackageAccess.EntityLiving.getJumping((EntityLiving) vehicle);
                if(vehicle.motY < -0.22499999403953552D && (vehicle instanceof EntityLiving) && aC)
                {
                	vehicle.motY = 0.125D;
                    cloudPoop();
                    setPuffiness(1.15F);
                }
            }
        } else
        if(!grab)
        {
            if(aA != 0.0F)
            {
                int j = MathHelper.floor(locX);
                int i1 = MathHelper.floor(boundingBox.b);
                int k1 = MathHelper.floor(boundingBox.b - 0.5D);
                int l1 = MathHelper.floor(locZ);
                if((world.getTypeId(j, i1 - 1, l1) != 0 || world.getTypeId(j, k1 - 1, l1) != 0) && world.getTypeId(j, i1 + 2, l1) == 0 && world.getTypeId(j, i1 + 1, l1) == 0)
                {
                    if(motY < 0.0D)
                    {
                        cloudPoop();
                        setPuffiness(0.9F);
                    }
                    motY = 0.20000000000000001D;
                }
            }
            if(motY < -0.10000000000000001D)
            {
                motY = -0.10000000000000001D;
            }
        }
        if(!grab)
        {
            super.c_();
            if(fear && random.nextInt(4) == 0)
            {
                if(runFrom != null)
                {
                    runLikeHell();
                   // world.spawnParticle("splash", locX, locY, locZ, 0.0D, 0.0D, 0.0D);
                    if(!C())
                    {
                        a(runFrom, 30F, 30F);
                    }
                    if(runFrom.dead || f(runFrom) > 16F)
                    {
                        runFrom = null;
                    }
                } else
                {
                    runFrom = findPlayerToRunFrom();
                }
            }
        } else
        if(onGround)
        {
            grab = false;
            PacketManager.makeSound(this, "aether.sound.mobs.aerbunny.aerbunnyland", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            List list = world.b(this, boundingBox.b(12D, 12D, 12D));
            for(int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);
                if(entity instanceof EntityMonster)
                {
                    EntityMonster entitymobs = (EntityMonster)entity;
                    entitymobs.setTarget(this);
                }
            }

        }
        if(f_())
        {
            a();
        }
    }

    public void cloudPoop()
    {
        double a = random.nextFloat() - 0.5F;
        double c = random.nextFloat() - 0.5F;
        double d = locX + a * 0.40000000596046448D;
        double e = boundingBox.b;
        double f = locZ + a * 0.40000000596046448D;
        //worldObj.spawnParticle("explode", d, e, f, 0.0D, -0.075000002980232239D, 0.0D);
    }

    @Override
    public boolean damageEntity(Entity e, int i)
    {
        boolean flag = super.damageEntity(e, i);
        if(flag && (e instanceof EntityPlayer))
        {
            fear = true;
        }
        return flag;
    }

    public boolean isOnLadder()
    {
        return aA != 0.0F;
    }

    protected Entity findPlayerToRunFrom()
    {
        EntityPlayer entityplayer = (EntityPlayer) world.findNearbyPlayer(this, 12D);
        if(entityplayer != null && e(entityplayer))
        {
            return entityplayer;
        } else
        {
            return null;
        }
    }

    public void runLikeHell()
    {
        double a = locX - runFrom.locX;
        double b = locZ - runFrom.locZ;
        double crazy = Math.atan2(a, b);
        crazy += (double)(random.nextFloat() - random.nextFloat()) * 0.75D;
        double c = locX + Math.sin(crazy) * 8D;
        double d = locZ + Math.cos(crazy) * 8D;
        int x = MathHelper.floor(c);
        int y = MathHelper.floor(boundingBox.b);
        int z = MathHelper.floor(d);
        int q = 0;
        do
        {
            if(q >= 16)
            {
                break;
            }
            int i = (x + random.nextInt(4)) - random.nextInt(4);
            int j = (y + random.nextInt(4)) - random.nextInt(4) - 1;
            int k = (z + random.nextInt(4)) - random.nextInt(4);
            if(j > 4 && (world.getTypeId(i, j, k) == 0 || world.getTypeId(i, j, k) == Block.SNOW.id) && world.getTypeId(i, j - 1, k) != 0)
            {
                PathEntity dogs = world.a(this, i, j, k, 16F);
                setPathEntity(dogs);
                break;
            }
            q++;
        } while(true);
    }

    @Override
    public boolean a(EntityHuman entityplayer)
    {
        yaw = entityplayer.yaw;
        if(vehicle != null)
        {
            K = vehicle.yaw;
            yaw = vehicle.yaw;
        }
        mount(entityplayer);
        if(vehicle == null)
        {
            grab = true;
        } else
        {
        	PacketManager.makeSound(this, "aether.sound.mobs.aerbunny.aerbunnyLift", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
        aC = false;
        aA = 0.0F;
        az = 0.0F;
        setPathEntity(null);
        motX = entityplayer.motX * 5D;
        motY = entityplayer.motY / 2D + 0.5D;
        motZ = entityplayer.motZ * 5D;
        return true;
    }

    @Override
    public double I()
    {
        if(vehicle instanceof EntityPlayer)
        {
            return (double)(height - 1.15F);
        } else
        {
            return (double)height;
        }
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
        int count = 1 * (human != null && ItemManager.equippedSkyrootSword(human) ? 2 : 1);
        loot.add(new org.bukkit.inventory.ItemStack(Item.STRING.id, count));
        CraftEntity entity = (CraftEntity)this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        for (org.bukkit.inventory.ItemStack stack : event.getDrops())
            b(stack.getTypeId(), stack.getAmount());
    }


    public void proceed()
    {
        mate = 0;
        age = random.nextInt(64);
    }

    @Override
    protected boolean n()
    {
        return onGround;
    }

    @Override
    public boolean d()
    {
        return super.d();
    }
    
    public float getPuffiness()
    {
    	return Float.valueOf(datawatcher.c(16));
    }
    
    public void setPuffiness(float value)
    {
    	datawatcher.watch(16, String.valueOf(value));
    }
    
    public boolean getRenderOnGround()
    {
        return (datawatcher.a(17) & 1) != 0;
    }

    public void setRenderOnGround(boolean flag)
    {
        if(flag)
        {
            datawatcher.watch(17, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(17, Byte.valueOf((byte)0));
        }
    }

    public int age;
    public int mate;
    public boolean grab;
    public boolean fear;
    public boolean gotrider;
    public Entity runFrom;
}
