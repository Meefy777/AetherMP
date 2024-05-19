// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityAerwhale.java

package net.mine_diver.aethermp.entities;

import java.util.List;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.IMonster;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.StepSound;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

// Referenced classes of package net.minecraft.src:
//            EntityFlying, IMob, DataWatcher, MathHelper, 
//            AxisAlignedBB, World, Vec3D, MovingObjectPosition, 
//            EnumMovingObjectType, AetherBlocks, Block, Entity

public class EntityAerwhale extends EntityFlying implements IMonster {

    public EntityAerwhale(World world)
    {
        super(world);
        checkTime = 0L;
        checkX = 0.0D;
        checkY = 0.0D;
        checkZ = 0.0D;
        isStuckWarning = false;
        courseChangeCooldown = 0;
        fireProof = true;
        prevAttackCounter = 0;
        attackCounter = 0;
        texture = "/aether/mobs/Mob_Aerwhale.png";
        b(4F, 4F);
        aE = 0.5F;
        health = 20;
        yaw = 360F * random.nextFloat();
        pitch = 90F * random.nextFloat() - 45F;
        bK = true;
    }

    @Override
    public void v() {}

    @Override
    public void m_() {
        double distances[] = new double[5];
        distances[0] = openSpace(0.0F, 0.0F);
        distances[1] = openSpace(45F, 0.0F);
        distances[2] = openSpace(0.0F, 45F);
        distances[3] = openSpace(-45F, 0.0F);
        distances[4] = openSpace(0.0F, -45F);
        int longest = 0;
        int i;
        for(i = 1; i < 5; i++)
        {
            if(distances[i] > distances[longest])
            {
                longest = i;
            }
        }

        switch(longest)
        {
        default:
            break;

        case 0: // '\0'
            if(distances[0] == 50D)
            {
                motionYaw *= 0.89999997615814209D;
                motionPitch *= 0.89999997615814209D;
                if(locY > 100D)
                    motionPitch -= 2D;
                if(locY < 20D)
                    motionPitch += 2D;
            } else
            {
                pitch = -pitch;
                yaw = -yaw;
            }
            break;

        case 1: // '\001'
            motionYaw += 5D;
            break;

        case 2: // '\002'
            motionPitch -= 5D;
            break;

        case 3: // '\003'
            motionYaw -= 5D;
            break;

        case 4: // '\004'
            motionPitch += 5D;
            break;
        }
        motionYaw += 2.0F * random.nextFloat() - 1.0F;
        motionPitch += 2.0F * random.nextFloat() - 1.0F;
        pitch += 0.10000000000000001D * motionPitch;
        yaw += 0.10000000000000001D * motionYaw;
        if(pitch < -60F)
            pitch = -60F;
        if(pitch > 60F)
            pitch = 60F;
        pitch *= 0.98999999999999999D;
        motX += 0.0050000000000000001D * Math.cos(((double)yaw / 180D) * 3.1415926535897931D) * Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
        motY += 0.0050000000000000001D * Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
        motZ += 0.0050000000000000001D * Math.sin(((double)yaw / 180D) * 3.1415926535897931D) * Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
        motX *= 0.97999999999999998D;
        motY *= 0.97999999999999998D;
        motZ *= 0.97999999999999998D;
        i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        if(motX > 0.0D && world.getTypeId(i + 1, j, k) != 0)
        {
            motX = -motX;
            motionYaw -= 10D;
        } else
        if(motX < 0.0D && world.getTypeId(i - 1, j, k) != 0)
        {
            motX = -motX;
            motionYaw += 10D;
        }
        if(motY > 0.0D && world.getTypeId(i, j + 1, k) != 0)
        {
            motY = -motY;
            motionPitch -= 10D;
        } else
        if(motY < 0.0D && world.getTypeId(i, j - 1, k) != 0)
        {
            motY = -motY;
            motionPitch += 10D;
        }
        if(motZ > 0.0D && world.getTypeId(i, j, k + 1) != 0)
        {
            motZ = -motZ;
            motionYaw -= 10D;
        } else
        if(motZ < 0.0D && world.getTypeId(i, j, k - 1) != 0)
        {
            motZ = -motZ;
            motionYaw += 10D;
        }
        fireTicks = 0;
        move(motX, motY, motZ);
        checkForBeingStuck();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void move(double d0, double d1, double d2) {
        if (this.bt) {
            this.boundingBox.d(d0, d1, d2);
            this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0;
            this.locY = this.boundingBox.b + this.height - this.br;
            this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0;
        }
        else {
            this.br *= 0.4f;
            final double d3 = this.locX;
            final double d4 = this.locZ;
            if (this.bf) {
                this.bf = false;
                d0 *= 0.25;
                d1 *= 0.05000000074505806;
                d2 *= 0.25;
                this.motX = 0.0;
                this.motY = 0.0;
                this.motZ = 0.0;
            }
            double d5 = d0;
            final double d6 = d1;
            double d7 = d2;
            final AxisAlignedBB axisalignedbb = this.boundingBox.clone();
            final boolean flag = this.onGround && this.isSneaking();
            if (flag) {
                final double d8 = 0.05;
                while (d0 != 0.0 && this.world.getEntities(this, this.boundingBox.c(d0, -1.0, 0.0)).size() == 0) {
                    if (d0 < d8 && d0 >= -d8) {
                        d0 = 0.0;
                    }
                    else if (d0 > 0.0) {
                        d0 -= d8;
                    }
                    else {
                        d0 += d8;
                    }
                    d5 = d0;
                }
                while (d2 != 0.0 && this.world.getEntities(this, this.boundingBox.c(0.0, -1.0, d2)).size() == 0) {
                    if (d2 < d8 && d2 >= -d8) {
                        d2 = 0.0;
                    }
                    else if (d2 > 0.0) {
                        d2 -= d8;
                    }
                    else {
                        d2 += d8;
                    }
                    d7 = d2;
                }
            }
            List<AxisAlignedBB> list = (List<AxisAlignedBB>)this.world.getEntities(this, this.boundingBox.a(d0, d1, d2));
            for (int i = 0; i < list.size(); ++i) {
                d1 = list.get(i).b(this.boundingBox, d1);
            }
            this.boundingBox.d(0.0, d1, 0.0);
            if (!this.bg && d6 != d1) {
                d2 = 0.0;
                d1 = 0.0;
                d0 = 0.0;
            }
            final boolean flag2 = this.onGround || (d6 != d1 && d6 < 0.0);
            for (int j = 0; j < list.size(); ++j) {
                d0 = list.get(j).a(this.boundingBox, d0);
            }
            this.boundingBox.d(d0, 0.0, 0.0);
            if (!this.bg && d5 != d0) {
                d2 = 0.0;
                d1 = 0.0;
                d0 = 0.0;
            }
            for (int j = 0; j < list.size(); ++j) {
                d2 = list.get(j).c(this.boundingBox, d2);
            }
            this.boundingBox.d(0.0, 0.0, d2);
            if (!this.bg && d7 != d2) {
                d2 = 0.0;
                d1 = 0.0;
                d0 = 0.0;
            }
            if (this.bs > 0.0f && flag2 && (flag || this.br < 0.05f) && (d5 != d0 || d7 != d2)) {
                final double d9 = d0;
                final double d10 = d1;
                final double d11 = d2;
                d0 = d5;
                d1 = this.bs;
                d2 = d7;
                final AxisAlignedBB axisalignedbb2 = this.boundingBox.clone();
                this.boundingBox.b(axisalignedbb);
                list = this.world.getEntities(this, this.boundingBox.a(d5, d1, d7));
                for (int k = 0; k < list.size(); ++k) {
                    d1 = list.get(k).b(this.boundingBox, d1);
                }
                this.boundingBox.d(0.0, d1, 0.0);
                if (!this.bg && d6 != d1) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                for (int k = 0; k < list.size(); ++k) {
                    d0 = list.get(k).a(this.boundingBox, d0);
                }
                this.boundingBox.d(d0, 0.0, 0.0);
                if (!this.bg && d5 != d0) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                for (int k = 0; k < list.size(); ++k) {
                    d2 = list.get(k).c(this.boundingBox, d2);
                }
                this.boundingBox.d(0.0, 0.0, d2);
                if (!this.bg && d7 != d2) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                if (!this.bg && d6 != d1) {
                    d2 = 0.0;
                    d1 = 0.0;
                    d0 = 0.0;
                }
                else {
                    d1 = -this.bs;
                    for (int k = 0; k < list.size(); ++k) {
                        d1 = list.get(k).b(this.boundingBox, d1);
                    }
                    this.boundingBox.d(0.0, d1, 0.0);
                }
                if (d9 * d9 + d11 * d11 >= d0 * d0 + d2 * d2) {
                    d0 = d9;
                    d1 = d10;
                    d2 = d11;
                    this.boundingBox.b(axisalignedbb2);
                }
                else {
                    final double d12 = this.boundingBox.b - (int)this.boundingBox.b;
                    if (d12 > 0.0) {
                        this.br = (float)(this.br + d12 + 0.01);
                    }
                }
            }
            double xCheck = (this.boundingBox.a + this.boundingBox.d) / 2.0;
            double zCheck = (this.boundingBox.c + this.boundingBox.f) / 2.0;
            int a = MathHelper.floor(xCheck);
            int b = MathHelper.floor(zCheck);
            //This took way to long to do
            if (!this.world.a(a - 32, 0, b - 32, a + 32, 128, b + 32)) {
            	this.die();
            	return;
            }
            	
            this.locX = xCheck;
            this.locY = this.boundingBox.b + this.height - this.br;
            this.locZ = zCheck;
            
            
            
            this.positionChanged = (d5 != d0 || d7 != d2);
            this.bc = (d6 != d1);
            this.onGround = (d6 != d1 && d6 < 0.0);
            this.bd = (this.positionChanged || this.bc);
            this.a(d1, this.onGround);
            if (d5 != d0) {
                this.motX = 0.0;
            }
            if (d6 != d1) {
                this.motY = 0.0;
            }
            if (d7 != d2) {
                this.motZ = 0.0;
            }
            final double d9 = this.locX - d3;
            final double d10 = this.locZ - d4;
            if (this.positionChanged && this.getBukkitEntity() instanceof Vehicle) {
                final Vehicle vehicle = (Vehicle)this.getBukkitEntity();
                org.bukkit.block.Block block = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.20000000298023224 - this.height), MathHelper.floor(this.locZ));
                if (d5 > d0)
                    block = block.getRelative(BlockFace.SOUTH);
                else if (d5 < d0)
                    block = block.getRelative(BlockFace.NORTH);
                else if (d7 > d2)
                    block = block.getRelative(BlockFace.WEST);
                else if (d7 < d2)
                    block = block.getRelative(BlockFace.EAST);
                final VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, block);
                this.world.getServer().getPluginManager().callEvent(event);
            }
            if (this.n() && !flag && this.vehicle == null) {
                this.bm += (float)(MathHelper.a(d9 * d9 + d10 * d10) * 0.6);
                final int l = MathHelper.floor(this.locX);
                final int i2 = MathHelper.floor(this.locY - 0.20000000298023224 - this.height);
                final int j2 = MathHelper.floor(this.locZ);
                int k = this.world.getTypeId(l, i2, j2);
                if (this.world.getTypeId(l, i2 - 1, j2) == net.minecraft.server.Block.FENCE.id) {
                    k = this.world.getTypeId(l, i2 - 1, j2);
                }
                if (this.bm > this.b && k > 0) {
                    ++this.b;
                    StepSound stepsound = net.minecraft.server.Block.byId[k].stepSound;
                    if (this.world.getTypeId(l, i2 + 1, j2) == net.minecraft.server.Block.SNOW.id) {
                        stepsound = net.minecraft.server.Block.SNOW.stepSound;
                        this.world.makeSound(this, stepsound.getName(), stepsound.getVolume1() * 0.15f, stepsound.getVolume2());
                    }
                    else if (!net.minecraft.server.Block.byId[k].material.isLiquid()) {
                        this.world.makeSound(this, stepsound.getName(), stepsound.getVolume1() * 0.15f, stepsound.getVolume2());
                    }
                    net.minecraft.server.Block.byId[k].b(this.world, l, i2, j2, this);
                }
            }
            final int l = MathHelper.floor(this.boundingBox.a + 0.001);
            final int i2 = MathHelper.floor(this.boundingBox.b + 0.001);
            final int j2 = MathHelper.floor(this.boundingBox.c + 0.001);
            int k = MathHelper.floor(this.boundingBox.d - 0.001);
            final int k2 = MathHelper.floor(this.boundingBox.e - 0.001);
            final int l2 = MathHelper.floor(this.boundingBox.f - 0.001);
            if (this.world.a(l, i2, j2, k, k2, l2)) {
                for (int i3 = l; i3 <= k; ++i3) {
                    for (int j3 = i2; j3 <= k2; ++j3) {
                        for (int k3 = j2; k3 <= l2; ++k3) {
                            final int l3 = this.world.getTypeId(i3, j3, k3);
                            if (l3 > 0) {
                                net.minecraft.server.Block.byId[l3].a(this.world, i3, j3, k3, this);
                            }
                        }
                    }
                }
            }
            final boolean flag3 = this.ac();
            if (this.world.d(this.boundingBox.shrink(0.001, 0.001, 0.001))) {
                this.burn(1);
                if (!flag3) {
                    ++this.fireTicks;
                    if (this.fireTicks <= 0) {
                        final EntityCombustEvent event2 = new EntityCombustEvent(this.getBukkitEntity());
                        this.world.getServer().getPluginManager().callEvent(event2);
                        if (!event2.isCancelled()) {
                            this.fireTicks = 300;
                        }
                    }
                    else {
                        this.fireTicks = 300;
                    }
                }
            }
            else if (this.fireTicks <= 0) {
                this.fireTicks = -this.maxFireTicks;
            }
            if (flag3 && this.fireTicks > 0) {
                this.world.makeSound(this, "random.fizz", 0.7f, 1.6f + (this.random.nextFloat() - this.random.nextFloat()) * 0.4f);
                this.fireTicks = -this.maxFireTicks;
            }
        }
    }
    
    private int b = 1;

    public double getSpeed()
    {
        return Math.sqrt(motX * motX + motY * motY + motZ * motZ);
    }

    private double openSpace(float rotationYawOffset, float rotationPitchOffset)
    {
        float yaw = this.yaw + rotationYawOffset;
        float pitch = this.yaw + rotationYawOffset;
        Vec3D vec3d = Vec3D.create(locX, locY, locZ);
        float f3 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 50D;
        Vec3D vec3d1 = vec3d.add((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, true);
        if(movingobjectposition == null)
            return 50D;
        if(movingobjectposition.type == EnumMovingObjectType.TILE)
        {
            double i = (double)movingobjectposition.b - locX;
            double j = (double)movingobjectposition.c - locY;
            double k = (double)movingobjectposition.d - locZ;
            return Math.sqrt(i * i + j * j + k * k);
        } else
            return 50D;
    }

    @Override
    protected void c_() {}

    private void checkForBeingStuck()
    {
        long curtime = System.currentTimeMillis();
        if(curtime > checkTime + 3000L)
        {
            double diffx = locX - checkX;
            double diffy = locY - checkY;
            double diffz = locZ - checkZ;
            double distanceTravelled = Math.sqrt(diffx * diffx + diffy * diffy + diffz * diffz);
            if(distanceTravelled < 3D)
            {
                if(!isStuckWarning)
                    isStuckWarning = true;
                else
                    die();
            }
            checkX = locX;
            checkY = locY;
            checkZ = locZ;
            checkTime = curtime;
        }
    }

    /*private boolean isCourseTraversable(double d, double d1, double d2, double d3)
    {
        double d4 = (waypointX - locX) / d3;
        double d5 = (waypointY - locY) / d3;
        double d6 = (waypointZ - locZ) / d3;
        AxisAlignedBB axisalignedbb = boundingBox.clone();
        for(int i = 1; (double)i < d3; i++)
        {
            axisalignedbb.d(d4, d5, d6);
            if(world.getEntities(this, axisalignedbb).size() > 0)
                return false;
        }

        return true;
    }*/

    @Override
    public int l()
    {
        return 1;
    }

    @Override
    public boolean d()
    {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return random.nextInt(65) == 0 && world.containsEntity(boundingBox) && world.getEntities(this, boundingBox).size() == 0 && !world.b(boundingBox) && world.getTypeId(i, j - 1, k) != BlockManager.DungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LightDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LockedDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.LockedLightDungeonStone.id && world.getTypeId(i, j - 1, k) != BlockManager.Holystone.id;
    }
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    private long checkTime;
    private double checkX;
    private double checkY;
    private double checkZ;
    private boolean isStuckWarning;
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    public int prevAttackCounter;
    public int attackCounter;
    public double motionYaw;
    public double motionPitch;
}
