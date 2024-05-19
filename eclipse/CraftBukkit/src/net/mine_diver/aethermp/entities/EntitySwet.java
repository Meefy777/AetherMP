// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntitySwet.java

package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.api.entities.IMountable;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, World, Entity, AxisAlignedBB, 
//            MathHelper, EntityLiving, EntityPlayer, NBTTagCompound, 
//            EntityMob, ItemStack, AetherBlocks, Block, 
//            mod_Aether

public class EntitySwet extends EntityAetherAnimal implements IMountable
{

    public EntitySwet(World world)
    {
        super(world);
        health = 25;
        if(!textureSet)
        {
            if(random.nextInt(2) == 0)
            {
                setTexture(2);
                textureSet = true;
            } else
            {
                setTexture(1);
                textureSet = true;
            }
        }
        if(getTexture() == 1)
        {
            texture = "/aether/mobs/swets.png";
            aE = 1.5F;
        } else
        {
            texture = "/aether/mobs/goldswets.png";
            aE = 3F;
        }
        b(0.8F, 0.8F);
        setPosition(locX, locY, locZ);
        hops = 0;
        gotrider = false;
        flutter = 0;
        ticker = 0;
    }

    @Override
    public void E()
    {
        super.E();
        if(passenger != null && kickoff)
        {
            passenger.mount(this);
            setWidth(0.8F);
            setHeight(0.8F);
            setRidden(false);
            setPosition(locX, locY + 1, locZ);
            kickoff = false;
        }
    }
    
    @Override
    protected void b() {
    	datawatcher.a(14, (byte) 0); //texture
    	datawatcher.a(15, String.valueOf(0.1f)); //width 
    	datawatcher.a(16, String.valueOf(0.1f)); //height
    	datawatcher.a(17, String.valueOf(false));//isRidden
    	datawatcher.a(18, String.valueOf(0.0D));//motY
    	datawatcher.a(19, (byte) 0); //isTamed
    }
    
    public void setMotY() {
    	datawatcher.watch(18, String.valueOf(motY));
    }
    
    public void setRidden(boolean flag) {
    	datawatcher.watch(17, String.valueOf(flag));
    }
    
    public boolean getTamed() {
    	return (datawatcher.a(19) & 1) != 0;
    }
    
    public void setTamed(boolean flag) {
    	datawatcher.watch(19, Byte.valueOf((byte) (flag ? 1 : 0)));
    }
    
    public void setTexture(int i) {
    	datawatcher.watch(14, Byte.valueOf((byte)i));
    }
    
    public int getTexture() {
    	return datawatcher.a(14);
    }
    
    public void setWidth(float f) {
    	this.length = f;
    	datawatcher.watch(15, String.valueOf(f));
    }
    
    public void setHeight(float f) {
    	this.width = f;
    	datawatcher.watch(16, String.valueOf(f));
    }

    @Override
    public void f() {
        passenger.setPosition(locX, (boundingBox.b - 0.30000001192092896D) + (double)passenger.height, locZ);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void m_() {
    	setMotY();
        if(target != null)
        {
            for(int i = 0; i < 3; i++)
            {
                double d = (float)locX + (random.nextFloat() - random.nextFloat()) * 0.3F;
                double d1 = (float)locY + width;
                double d2 = (float)locZ + (random.nextFloat() - random.nextFloat()) * 0.3F;
                PacketManager.spawnParticle("splash", (float) d, (float) (d1 - 0.25D), (float) d2, (float) 0.0D, (float) 0.0D, (float) 0.0D, ((WorldServer)world).dimension, locX, locY, locZ);
            }

        }
        super.m_();
        if(gotrider)
        {
            if(passenger != null)
                return;
            List list = world.b(this, boundingBox.b(0.5D, 0.75D, 0.5D));
            int j = 0;
            if(j < list.size())
            {
                Entity entity = (Entity)list.get(j);
                capturePrey(entity);
            }
            gotrider = false;
        }
        if(f_())
        	dissolve();
    }

    @Override
    protected boolean h_() {
        return true;
    }

    @Override
    public void a(float f) {
        if(getTamed())
            return;
        super.a(f);
        if(hops >= 3 && health > 0)
            dissolve();
    }

    @Override
    public void a(Entity entity, int i, double d, double d1)
    {
    	if (passenger == null || passenger != entity)
    		super.a(entity, i, d, d1);
    }
    
    public void dissolve()
    {
        for(int i = 0; i < 50; i++)
        {
            float f = random.nextFloat() * 3.141593F * 2.0F;
            float f1 = random.nextFloat() * 0.5F + 0.25F;
            float f2 = MathHelper.sin(f) * f1;
            float f3 = MathHelper.cos(f) * f1;
            PacketManager.spawnParticle("splash", (float) (locX + (double)f2), (float) (boundingBox.b + 1.25D), (float) (locZ + (double)f3), (float) ((double)f2 * 1.5D + motX), (float) 4D, (float) ((double)f3 * 1.5D + motZ), ((WorldServer)world).dimension, locX, locY, locZ);
        }
        if(passenger != null)
        {
        	passenger.locY += passenger.height - 0.3F;
            passenger.mount(this);
            setRidden(false);
            setWidth(0.8F);
            setHeight(0.8F);
        }
        die();
    }

    public void capturePrey(Entity entity)
    {
        splorch();
        lastX = locX = entity.locX;
        lastY = locY = entity.locY + 0.0099999997764825821D;
        lastZ = locZ = entity.locZ;
        lastYaw = yaw = entity.yaw;
        lastPitch = pitch = entity.pitch;
        motX = entity.motX;
        motY = entity.motY;
        motZ = entity.motZ;
        b(entity.length, entity.width);
        setWidth(entity.length);
        setHeight(entity.width);
        entity.mount(this);
        setPosition(locX, locY + 1, locZ);
        boolean flag = passenger == null;
        setRidden(!flag);
        if (flag) {
            setWidth(0.8F);
            setHeight(0.8F);
        }
        yaw = random.nextFloat() * 360F;
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        if(hops == 3 && entity == null && health > 1)
        {
            health = 1;
        }
        boolean flag = super.damageEntity(entity, i);
        if(flag && passenger != null && (passenger instanceof EntityLiving))
        {
            if(entity != null && passenger == entity)
            {
                if(random.nextInt(3) == 0)
                {
                    kickoff = true;
                }
            } else
            {
            	EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(this.getBukkitEntity(), this.passenger.getBukkitEntity(), DamageCause.CUSTOM, i);
            	this.getBukkitEntity().getServer().getPluginManager().callEvent(event);
            	if (!event.isCancelled())
            		passenger.damageEntity((Entity)null, event.getDamage());
                if(health <= 0)
                {
                    kickoff = true;
                }
            }
        }
        if(flag && health <= 0)
        {
            dissolve();
        } else
        if(flag && (entity instanceof EntityLiving))
        {
            EntityLiving entityliving = (EntityLiving)entity;
            if(entityliving.health > 0 && (passenger == null || entityliving != passenger))
            {
                target = entity;
                a(entity, 180F, 180F);
                kickoff = true;
            }
        }
        if(getTamed() && (target instanceof EntityPlayer))
        {
            target = null;
        }
        return flag;
    }

    @Override
    public void c_()
    {
        ay++;
        U();
        if(getTamed() && passenger != null)
            return;
        if(!onGround && aC)
        {
        	aC = false;
        } else
        if(onGround)
        {
            if(aA > 0.05F)
            {
            	aA *= 0.75F;
            } else
            {
            	aA = 0.0F;
            }
        }
        if(target != null && passenger == null && health > 0)
        {
            a(target, 10F, 10F);
        }
        if(target != null && target.dead)
        {
        	target = null;
        }
        if(!onGround && motY < 0.05000000074505806D && flutter > 0)
        {
            motY += 0.070000000298023224D;
            flutter--;
        }
        if(ticker < 4)
        {
            ticker++;
        } else
        {
            if(onGround && passenger == null && hops != 0 && hops != 3)
            {
                hops = 0;
            }
            if(target == null && passenger == null)
            {
                Entity entity = getPrey();
                if(entity != null)
                {
                    target = entity;
                }
            } else
            if(target != null && passenger == null)
            {
                if(f(target) <= 9F)
                {
                    if(onGround && e(target))
                    {
                        splotch();
                        flutter = 10;
                        aC = true;
                        aA = 1.0F;
                        yaw += 5F * (random.nextFloat() - random.nextFloat());
                    }
                } else
                {
                    target = null;
                    aC = false;
                    aA = 0.0F;
                }
            } else
            if(passenger != null && onGround)
            {
                if(hops == 0)
                {
                    splotch();
                    onGround = false;
                    motY = 0.34999999403953552D;
                    aA = 0.8F;
                    hops = 1;
                    flutter = 5;
                    yaw += 20F * (random.nextFloat() - random.nextFloat());
                } else
                if(hops == 1)
                {
                    splotch();
                    onGround = false;
                    motY = 0.44999998807907104D;
                    aA = 0.9F;
                    hops = 2;
                    flutter = 5;
                    yaw += 20F * (random.nextFloat() - random.nextFloat());
                } else
                if(hops == 2)
                {
                    splotch();
                    onGround = false;
                    motY = 1.25D;
                    aA = 1.25F;
                    hops = 3;
                    flutter = 5;
                    yaw += 20F * (random.nextFloat() - random.nextFloat());
                }
            }
            ticker = 0;
        }
        if(onGround && hops >= 3)
        {
            dissolve();
        }
    }

    @Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("Hops", (short)hops);
        nbttagcompound.a("Flutter", (short)flutter);
        if(passenger != null)
        {
            gotrider = true;
        }
        nbttagcompound.a("GotRider", gotrider);
        nbttagcompound.a("Friendly", getTamed());
        nbttagcompound.a("textureSet", textureSet);
        nbttagcompound.a("textureNum", (short)getTexture());
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        hops = nbttagcompound.d("Hops");
        flutter = nbttagcompound.d("Flutter");
        gotrider = nbttagcompound.m("GotRider");
        setTamed(nbttagcompound.m("Friendly"));
        textureSet = nbttagcompound.m("textureSet");
        setTexture(nbttagcompound.d("textureNum"));
        if(getTexture() == 1)
        {
            texture = "/aether/mobs/swets.png";
            aE = 1.5F;
        } else
        {
            texture = "/aether/mobs/goldswets.png";
            aE = 3F;
        }
    }

    public void splorch()
    {
    	PacketManager.makeSound(this, "mob.slimeattack", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    public void splotch()
    {
    	PacketManager.makeSound(this, "mob.slime", 0.5F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public void collide(Entity entity)
    {
        if(hops == 0 && passenger == null && target != null && entity != null && entity == target && (entity.vehicle == null || !(entity.vehicle instanceof EntitySwet)))
        {
            if(entity.passenger != null)
            {
                entity.passenger.mount(entity);
                setWidth(entity.length);
                setHeight(entity.width);
                setRidden(true);
            }
            capturePrey(entity);
        }
        super.collide(entity);
    }

    @Override
    public boolean a(EntityHuman entityplayer)
    {
        if(!getTamed())
        {
            setTamed(true);
            target = null;
            return true;
        }
        if(getTamed() && passenger == null || passenger == entityplayer)
        {
            capturePrey(entityplayer);
        }
        return true;
    }

    @SuppressWarnings("rawtypes")
	protected Entity getPrey()
    {
        List list = world.b(this, boundingBox.b(6D, 6D, 6D));
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);
            if((entity instanceof EntityLiving) && !(entity instanceof EntitySwet) && (getTamed() ? !(entity instanceof EntityHuman) : !(entity instanceof EntityMonster)))
            {
                return entity;
            }
        }

        return null;
    }

    protected void dropFewItems(EntityHuman human)
    {
        ItemStack stack = new ItemStack(getTexture() != 1 ? Block.GLOWSTONE.id : BlockManager.Aercloud.id, 3, getTexture() != 1 ? 0 : 1);
        List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        int count = stack.count * (human != null && ItemManager.equippedSkyrootSword(human) ? 2 : 1);
        loot.add(new org.bukkit.inventory.ItemStack(stack.id, count, (short) stack.getData()));
        CraftEntity entity = (CraftEntity)this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        /*for (org.bukkit.inventory.ItemStack itemstack : event.getDrops()) {
            b(itemstack.getTypeId(), itemstack.getAmount());
        }*/
        for (org.bukkit.inventory.ItemStack itemstack : event.getDrops())
            this.world.getWorld().dropItemNaturally(entity.getLocation(), itemstack);
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

    public int ticker;
    public int flutter;
    public boolean textureSet;
    public boolean kickoff;
    public int hops;
    public boolean gotrider;
}
