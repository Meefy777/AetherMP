// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   Whirly.java

package net.mine_diver.aethermp.entities;

import java.util.*;

import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.ItemSpawnEvent;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.PathEntity;
import net.minecraft.server.World;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, World, ModLoader, SaveHandler, 
//            EntityPlayer, AxisAlignedBB, EntityCreeper, EntityExplodeFX, 
//            EffectRenderer, EntitySmokeFX, Entity, PathEntity, 
//            MathHelper, Block, BlockLeaves, EntityFX, 
//            Item, NBTTagCompound

public class Whirly extends EntityAetherAnimal
{

	public Whirly(World world)
    {
        super(world);
        entcount = 0;
        b(0.6F, 0.8F);
        setPosition(locX, locY, locZ);
        aE = 0.6F;
        Angle = random.nextFloat() * 360F;
        Speed = random.nextFloat() * 0.025F + 0.025F;
        Curve = (random.nextFloat() - random.nextFloat()) * 0.1F;
        Life = random.nextInt(512) + 512;
        if(random.nextInt(10) == 0 && !shouldStopEvil())
        {
            evil = true;
            Life /= 2;
        }
    }

    @Override
    public boolean n()
    {
        return false;
    }

    public boolean shouldStopEvil()
    {
    	return false;
    }

   
    @SuppressWarnings("rawtypes")
	@Override
    public void c_()
    {
        if(evil)
        {
            EntityPlayer entityplayer = (EntityPlayer)getPlayer();
            if(entityplayer != null && entityplayer.onGround)
            {
                target = entityplayer;
            }
        }
        if(target == null)
        {
            motX = Math.cos(0.01745329F * Angle) * (double)Speed;
            motZ = -Math.sin(0.01745329F * Angle) * (double)Speed;
            Angle += Curve;
        } else
        {
            super.c_();
        }
        List list = world.b(this, boundingBox.b(2.5D, 2.5D, 2.5D));
        if(Life-- <= 0 || f_())
        {
            die();
        }
        if(getPlayer() != null)
        {
            entcount++;
        }
        if(entcount >= 128)
        {
            if(evil && target != null)
            {
                EntityCreeper entitycreeper = new EntityCreeper(world);
                entitycreeper.setPositionRotation(locX, locY - 0.75D, locZ, random.nextFloat() * 360F, 0.0F);
                entitycreeper.motX = (double)(random.nextFloat() - random.nextFloat()) * 0.125D;
                entitycreeper.motZ = (double)(random.nextFloat() - random.nextFloat()) * 0.125D;
                org.bukkit.entity.Entity ent = entitycreeper.getBukkitEntity();
            	CreatureSpawnEvent event = new CreatureSpawnEvent(ent, CreatureType.CREEPER, ent.getLocation(), SpawnReason.CUSTOM);
            	world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
	                world.addEntity(entitycreeper);
	                entcount = 0;
                }
            } else
            {
                int i = loot();
                if(i != 0)
                {
                	EntityItem entityitem = new EntityItem(world, locX, locY, locZ, new ItemStack(Block.byId[1], 1));
                	org.bukkit.entity.Entity entity = entityitem.getBukkitEntity();
                	ItemSpawnEvent event = new ItemSpawnEvent(entity, entity.getLocation());
                	world.getServer().getPluginManager().callEvent(event);
                	if(!event.isCancelled()) {
                		b(i, 1);
                    	entcount = 0;
                	}
                }
            }
        }
        double d = (float)locX;
        double d3 = (float)locY;
        double d6 = (float)locZ;
        for(int l = 0; l < list.size(); l++)
        {
            Entity entity = (Entity)list.get(l);
            double d9 = (float)entity.locX;
            double d11 = (float)entity.locY - entity.height * 0.6F;
            double d13 = (float)entity.locZ;
            double d15 = f(entity);
            double d17 = d11 - d3;
            if(d15 <= 1.5D + d17)
            {
                entity.motY = 0.15000000596046448D;
                entity.fallDistance = 0.0F;
                if(d17 > 1.5D)
                {
                    entity.motY = -0.44999998807907104D + d17 * 0.34999999403953552D;
                    d15 += d17 * 1.5D;
                    if(entity == target)
                    {
                        target = null;
                        setPathEntity((PathEntity)null);
                    }
                } else
                {
                    entity.motY = 0.125D;
                }
                double d19 = Math.atan2(d - d9, d6 - d13) / 0.01745329424738884D;
                d19 += 160D;
                entity.motX = -Math.cos(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D;
                entity.motZ = Math.sin(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D;
                if(entity instanceof Whirly)
                {
                    entity.die();
                    if(!shouldStopEvil() && !evil)
                    {
                        evil = true;
                        Life /= 2;
                    }
                }
            } else
            {
                double d20 = Math.atan2(d - d9, d6 - d13) / 0.01745329424738884D;
                entity.motX += Math.sin(0.01745329424738884D * d20) * 0.0099999997764825821D;
                entity.motZ += Math.cos(0.01745329424738884D * d20) * 0.0099999997764825821D;
            }
            int j1 = MathHelper.floor(locX);
            int k1 = MathHelper.floor(locY);
            int l1 = MathHelper.floor(locZ);
            if(world.getTypeId(j1, k1 + 1, l1) != 0)
            {
                Life -= 50;
            }
            int i2 = (j1 - 1) + random.nextInt(3);
            int j2 = k1 + random.nextInt(5);
            int k2 = (l1 - 1) + random.nextInt(3);
            if(world.getTypeId(i2, j2, k2) == Block.LEAVES.id)
            {
                world.setTypeId(i2, j2, k2, 0);
            }
        }
    }

    public int loot()
    {
        int i = random.nextInt(100) + 1;
        if(i == 100)
        {
            return Item.DIAMOND.id;
        }
        if(i >= 96)
        {
            return Item.IRON_INGOT.id;
        }
        if(i >= 91)
        {
            return Item.GOLD_INGOT.id;
        }
        if(i >= 82)
        {
            return Item.COAL.id;
        }
        if(i >= 75)
        {
            return Block.GRAVEL.id;
        }
        if(i >= 64)
        {
            return Block.CLAY.id;
        }
        if(i >= 52)
        {
            return Item.STICK.id;
        }
        if(i >= 38)
        {
            return Item.FLINT.id;
        }
        if(i > 20)
        {
            return Block.LOG.id;
        } else
        {
            return Block.SAND.id;
        }
    }

    @Override
    public boolean d()
    {
        if(locY < 64D)
        {
            locY += 64D;
        }
        if(!world.containsEntity(boundingBox) || world.getEntities(this, boundingBox).size() != 0 || world.b(boundingBox))
        {
            return false;
        }
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        boolean flag = true;
        int l = 1;
        do
        {
            if(l >= 20 || l + j >= 125)
            {
                break;
            }
            if(world.getLightLevel(i, j + l, k) <= 12 || world.getTypeId(i, j + l, k) != 0)
            {
                flag = false;
                break;
            }
            l++;
        } while(true);
        return flag;
    }

    public Entity getPlayer()
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
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("Angle", Angle);
        nbttagcompound.a("Speed", Speed);
        nbttagcompound.a("Curve", Curve);
        nbttagcompound.a("Life", (short)Life);
        nbttagcompound.a("Counter", (short)entcount);
        nbttagcompound.a("Evil", evil);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        Angle = nbttagcompound.g("Angle");
        Speed = nbttagcompound.g("Speed");
        Curve = nbttagcompound.g("Curve");
        Life = nbttagcompound.d("Life");
        entcount = nbttagcompound.d("Counter");
        evil = nbttagcompound.m("Evil");
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        return false;
    }

    @Override
    public void collide(Entity entity1)
    {
    }

    @Override
    public int l()
    {
        return 1;
    }

    @Override
    public boolean p()
    {
        return positionChanged;
    }

    public int entcount;
    public int Life;
    public static final float pie = 3.141593F;
    public static final float pia = 0.01745329F;
    public float Angle;
    public float Speed;
    public float Curve;
    public boolean evil;
}