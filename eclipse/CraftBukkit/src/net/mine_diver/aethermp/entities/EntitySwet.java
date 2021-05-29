// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntitySwet.java

package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDeathEvent;

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
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            EntityAetherAnimal, World, Entity, AxisAlignedBB, 
//            MathHelper, EntityLiving, EntityPlayer, NBTTagCompound, 
//            EntityMob, ItemStack, AetherBlocks, Block, 
//            mod_Aether

public class EntitySwet extends EntityAetherAnimal
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
        if(textureNum == 1)
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
            setIsRidden(passenger != null);
            if (getIsRidden()) {
            	this.setWidth(passenger.width);
            	this.setHeight(passenger.height);
            }
            kickoff = false;
        }
    }
    
    @Override
    protected void b() {
    	datawatcher.a(14, (byte) 0); //texture
    	datawatcher.a(15, String.valueOf(0.1f)); //width 
    	datawatcher.a(16, String.valueOf(0.1f)); //height
    	datawatcher.a(17, (byte) 0); //is ridden
    	datawatcher.a(18, String.valueOf(0.0D)); //motY
    }
    
    public void setTexture(int i) {
    	datawatcher.watch(14, Byte.valueOf((byte)i));
    }
    
    public void setWidth(float f) {
    	datawatcher.watch(15, String.valueOf(f));
    }
    
    public void setHeight(float f) {
    	datawatcher.watch(16, String.valueOf(f));
    }
    
    public void setMotY() {
    	datawatcher.watch(18, String.valueOf(motY));
    }
    
    public boolean getIsRidden()
    {
        return (datawatcher.a(17) & 1) != 0;
    }

    public void setIsRidden(boolean flag)
    {
        if(flag)
            datawatcher.watch(17, Byte.valueOf((byte)1));
        else
            datawatcher.watch(17, Byte.valueOf((byte)0));
    }

    @Override
    public void f()
    {
        passenger.setPosition(locX, (boundingBox.b - 0.30000001192092896D) + (double)passenger.height, locZ);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void m_()
    {
    	setMotY();
        if(target != null)
        {
            for(int i = 0; i < 3; i++)
            {
                double d = (float)locX + (random.nextFloat() - random.nextFloat()) * 0.3F;
                double d1 = (float)locY + height;
                double d2 = (float)locZ + (random.nextFloat() - random.nextFloat()) * 0.3F;
                Packet230ModLoader packet = new Packet230ModLoader();
                packet.packetType = 31;
                packet.dataString = new String [] {"splash"};
                packet.dataFloat = new float [] {(float) d, (float) (d1 - 0.25D), (float) d2, (float) 0.0D, (float) 0.0D, (float) 0.0D};
                PacketManager.sendToViewDistance(packet, ((WorldServer) world).dimension, locX, locY, locZ);
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
    protected boolean h_()
    {
        return true;
    }

    @Override
    public void a(float f)
    {
        if(friendly)
        {
            return;
        }
        super.a(f);
        if(hops >= 3 && health > 0)
        {
            dissolve();
        }
    }

    @Override
    public void a(Entity entity, int i, double d, double d1)
    {
        if(passenger != null && entity == passenger)
        {
            return;
        } else
        {
            super.a(entity, i, d, d1);
            return;
        }
    }

    public void dissolve()
    {
        for(int i = 0; i < 50; i++)
        {
            float f = random.nextFloat() * 3.141593F * 2.0F;
            float f1 = random.nextFloat() * 0.5F + 0.25F;
            float f2 = MathHelper.sin(f) * f1;
            float f3 = MathHelper.cos(f) * f1;
            Packet230ModLoader packet = new Packet230ModLoader();
            packet.packetType = 31;
            packet.dataString = new String [] {"splash"};
            packet.dataFloat = new float [] {(float) (locX + (double)f2), (float) (boundingBox.b + 1.25D), (float) (locZ + (double)f3), (float) ((double)f2 * 1.5D + motX), (float) 4D, (float) ((double)f3 * 1.5D + motZ)};
            PacketManager.sendToViewDistance(packet, ((WorldServer) world).dimension, locX, locY, locZ);
        }

        if(passenger != null)
        {
        	passenger.locY += passenger.height - 0.3F;
            passenger.mount(this);
            setIsRidden(passenger != null);
            if (getIsRidden()) {
            	this.setWidth(passenger.width);
            	this.setHeight(passenger.height);
            }
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
        b(entity.width, entity.height);
        setPosition(locX, locY, locZ);
        entity.mount(this);
        setIsRidden(passenger != null);
        if (getIsRidden()) {
        	this.setWidth(passenger.width);
        	this.setHeight(passenger.height);
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
            	passenger.damageEntity((Entity)null, i);
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
        if(friendly && (target instanceof EntityPlayer))
        {
            target = null;
        }
        return flag;
    }

    public void d_2()
    {
        if(passenger != null && (passenger instanceof EntityLiving))
        {
        	aA = 0.0F;
            az = 0.0F;
            aC = false;
            passenger.fallDistance = 0.0F;
            lastYaw = yaw = passenger.yaw;
            lastPitch = pitch = 0.0F;
            EntityLiving entityliving = (EntityLiving)passenger;
            float f = 3.141593F;
            float f1 = f / 180F;
            float f2 = entityliving.yaw * f1;
            if(onGround)
            {
            	boolean jump = mod_AetherMp.PackageAccess.EntityLiving.getJumping(entityliving);
            	float forward = mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving);
            	float strafing = mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving);
                if(jump)
                {
                    if(hops == 0)
                    {
                        onGround = false;
                        motY = 0.85000002384185791D;
                        hops = 1;
                        flutter = 5;
                    } else
                    if(hops == 1)
                    {
                        onGround = false;
                        motY = 1.0499999523162842D;
                        hops = 2;
                        flutter = 5;
                    } else
                    if(hops == 2)
                    {
                        onGround = false;
                        motY = 1.25D;
                        flutter = 5;
                    }
                } else
                if(forward > 0.125F || forward < -0.125F || strafing > 0.125F || strafing < -0.125F)
                {
                	
                    onGround = false;
                    motY = 0.34999999403953552D;
                    hops = 0;
                    flutter = 0;
                } else
                if(hops > 0)
                {
                    hops = 0;
                }
                mod_AetherMp.PackageAccess.EntityLiving.setMoveForward(entityliving, 0.0f);
                mod_AetherMp.PackageAccess.EntityLiving.setMoveStrafing(entityliving, 0.0f);
            } else
            {
            	float forward = mod_AetherMp.PackageAccess.EntityLiving.getMoveForward(entityliving);
                if(forward > 0.1F)
                {
                    if(textureNum == 1)
                    {
                        motX += (double)forward * -Math.sin(f2) * 0.125D;
                        motZ += (double)forward * Math.cos(f2) * 0.125D;
                    } else
                    {
                        motX += (double)forward * -Math.sin(f2) * 0.32500000000000001D;
                        motZ += (double)forward * Math.cos(f2) * 0.125D;
                    }
                } else
                if(forward < -0.1F)
                {
                    if(textureNum == 1)
                    {
                        motX += (double)forward * -Math.sin(f2) * 0.125D;
                        motZ += (double)forward * Math.cos(f2) * 0.125D;
                    } else
                    {
                        motX += (double)forward * -Math.sin(f2) * 0.32500000000000001D;
                        motZ += (double)forward * Math.cos(f2) * 0.125D;
                    }
                }
                float strafing = mod_AetherMp.PackageAccess.EntityLiving.getMoveStrafing(entityliving);
                if(strafing > 0.1F)
                {
                    if(textureNum == 1)
                    {
                        motX += (double)strafing * Math.cos(f2) * 0.125D;
                        motZ += (double)strafing * Math.sin(f2) * 0.125D;
                    } else
                    {
                        motX += (double)strafing * Math.cos(f2) * 0.32500000000000001D;
                        motZ += (double)strafing * Math.sin(f2) * 0.125D;
                    }
                } else
                if(strafing < -0.1F)
                {
                    if(textureNum == 1)
                    {
                        motX += (double)strafing * Math.cos(f2) * 0.125D;
                        motZ += (double)strafing * Math.sin(f2) * 0.125D;
                    } else
                    {
                        motX += (double)strafing * Math.cos(f2) * 0.32500000000000001D;
                        motZ += (double)strafing * Math.sin(f2) * 0.125D;
                    }
                }
                if(motY < 0.05000000074505806D && flutter > 0 && mod_AetherMp.PackageAccess.EntityLiving.getJumping(entityliving))
                {
                    motY += 0.070000000298023224D;
                    flutter--;
                }
            }
            double d = Math.abs(Math.sqrt(motX * motX + motZ * motZ));
            if(d > 0.27500000596046448D)
            {
                double d1 = 0.27500000000000002D / d;
                motX = motX * d1;
                motZ = motZ * d1;
            }
        }
    }

    @Override
    public void c_()
    {
        ay++;
        U();
        if(friendly && passenger != null)
        {
            d_2();
            return;
        }
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
        nbttagcompound.a("Friendly", friendly);
        nbttagcompound.a("textureSet", textureSet);
        nbttagcompound.a("textureNum", (short)textureNum);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        hops = nbttagcompound.d("Hops");
        flutter = nbttagcompound.d("Flutter");
        gotrider = nbttagcompound.m("GotRider");
        friendly = nbttagcompound.m("Friendly");
        textureSet = nbttagcompound.m("textureSet");
        textureNum = nbttagcompound.d("textureNum");
        if(textureNum == 1)
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
                setIsRidden(passenger != null);
                if (getIsRidden()) {
                	this.setWidth(passenger.width);
                	this.setHeight(passenger.height);
                }
            }
            capturePrey(entity);
        }
        super.collide(entity);
    }

    @Override
    public boolean a(EntityHuman entityplayer)
    {
        if(!friendly)
        {
            friendly = true;
            target = null;
            return true;
        }
        if(friendly && passenger == null || passenger == entityplayer)
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
            if((entity instanceof EntityLiving) && !(entity instanceof EntitySwet) && (friendly ? !(entity instanceof EntityPlayer) : !(entity instanceof EntityMonster)))
            {
                return entity;
            }
        }

        return null;
    }

    protected void dropFewItems(EntityHuman human)
    {
        ItemStack stack = new ItemStack(textureNum != 1 ? Block.GLOWSTONE.id : BlockManager.Aercloud.id, 3, textureNum != 1 ? 0 : 1);
        List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        int count = stack.count * (human != null && ItemManager.equippedSkyrootSword(human) ? 2 : 1);
        loot.add(new org.bukkit.inventory.ItemStack(stack.id, count));
        CraftEntity entity = (CraftEntity)this.getBukkitEntity();
        EntityDeathEvent event = new EntityDeathEvent(entity, loot);
        this.world.getServer().getPluginManager().callEvent(event);
        for (org.bukkit.inventory.ItemStack itemstack : event.getDrops())
            b(itemstack.getTypeId(), itemstack.getAmount());
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
    public int hops;
    public int textureNum;
    public boolean textureSet;
    public boolean gotrider;
    public boolean kickoff;
    public boolean friendly;
}
