// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityFireMinion.java

package net.mine_diver.aethermp.entities;

import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;

// Referenced classes of package net.minecraft.src:
//            EntityMob, AxisAlignedBB, World

public class EntityFireMinion extends EntityMonster
{

    public EntityFireMinion(World world)
    {
        super(world);
        texture = "/aether/mobs/firemonster.png";
        aE = 1.5F;
        damage = 5;
        health = 40;
        fireProof = true;
    }

    @Override
    public void m_()
    {
        super.m_();
        if(health > 0)
        {
            for(int j = 0; j < 4; j++)
            {
                double a = random.nextFloat() - 0.5F;
                double b = random.nextFloat();
                double c = random.nextFloat() - 0.5F;
                double d = locX + a * b;
                double e = (boundingBox.b + b) - 0.5D;
                double f = locZ + c * b;
                PacketManager.spawnParticle("flame", (float) d, (float) e, (float) f, 0.0F, -0.075000002980232239F, 0.0F, ((WorldServer)world).dimension, locX, locY, locZ);
            }

        }
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

}
