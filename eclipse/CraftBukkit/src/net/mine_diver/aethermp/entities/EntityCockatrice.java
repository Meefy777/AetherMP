// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityCockatrice.java

package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.Item;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

// Referenced classes of package net.minecraft.src:
//            EntityMob, MathHelper, AxisAlignedBB, World, 
//            AetherBlocks, Block, Entity, EntityPoisonNeedle, 
//            EntityLiving, NBTTagCompound, Item, mod_Aether, 
//            ModLoader, EntityPlayer

public class EntityCockatrice extends EntityMonster
{

    public EntityCockatrice(World world)
    {
        super(world);
        destPos = 0.0F;
        wingRotDelta = 1.0F;
        bs = 1.0F;
        texture = "/aether/mobs/Cockatrice.png";
        b(1.0F, 2.0F);
        health = 20;
    }

    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return random.nextInt(25) == 0 && a(i, j, k) >= 0.0F && world.containsEntity(boundingBox) && world.getEntities(this, boundingBox).size() == 0 && !world.b(boundingBox) && world.getTypeId(i, j - 1, k) != BlockManager.DungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LightDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LockedDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LockedLightDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.Holystone.id && world.spawnMonsters > 0;
    }

    @Override
    public void m_()
    {
        super.m_();
        bK = passenger == world.findNearbyPlayer(this, 100D);
        if(world.spawnMonsters == 0)
        {
            die();
        }
    }

    @Override
    protected void a(Entity entity, float f)
    {
        if(f < 10F)
        {
            double d = entity.locX - locX;
            double d1 = entity.locZ - locZ;
            if(attackTicks == 0)
            {
                EntityPoisonNeedle entityarrow = new EntityPoisonNeedle(world, this);
                entityarrow.locY += 1.3999999761581421D;
                double d2 = (entity.locY + (double)entity.t()) - 0.20000000298023224D - entityarrow.locY;
                float f1 = MathHelper.a(d * d + d1 * d1) * 0.2F;
                PacketManager.makeSound(this, "mob.aether.dartshoot", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
                world.addEntity(entityarrow);
                entityarrow.setArrowHeading(d, d2 + (double)f1, d1, 0.6F, 12F);
                attackTicks = 30;
            }
            yaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
            e = true;
        }
    }

    @Override
    public void v()
    {
        super.v();
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
    }

    @Override
    protected void a(float f1)
    {
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        if(entity != null && passenger != null && entity == passenger)
        {
            return false;
        }
        boolean flag = super.damageEntity(entity, i);
        if(flag && passenger != null && (health <= 0 || random.nextInt(3) == 0))
        {
        	passenger.mount(this);
        }
        return flag;
    }

    @Override
    public boolean a(EntityHuman entityplayer)
    {
        return true;
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

    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta;

}