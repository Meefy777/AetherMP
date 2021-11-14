package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.entities.EntityPhyg;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemSword;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp.PackageAccess;

public class ItemPigSlayer extends ItemSword {

    public ItemPigSlayer(int i) {
        super(i, EnumToolMaterial.IRON);
        d(0);
    }
    
    @Override
    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        if(entityliving == null || entityliving1 == null)
            return false;
        String s = EntityTypes.b(entityliving);
        if((!s.equals("") && s.toLowerCase().contains("pig")) || entityliving instanceof EntityPhyg) {
            if(entityliving.health > 0) {
                entityliving.health = 1;
                entityliving.hurtTicks = 0;
                entityliving.damageEntity(entityliving1, 9999);
            }
            
            for(int j = 0; j < 20; j++)
            {
                double d = PackageAccess.EntityLiving.getRand(entityliving1).nextGaussian() * 0.02D;
                double d1 = PackageAccess.EntityLiving.getRand(entityliving1).nextGaussian() * 0.02D;
                double d2 = PackageAccess.EntityLiving.getRand(entityliving1).nextGaussian() * 0.02D;
                double d3 = 5D;
                
                float a = (float) ((entityliving.locX + (double)(PackageAccess.EntityLiving.getRand(entityliving).nextFloat() * entityliving.length * 2.0F)) - (double)entityliving.length - d * d3);
                float b = (float) ((entityliving.locY + (double)(PackageAccess.EntityLiving.getRand(entityliving).nextFloat() * entityliving.width)) - d1 * d3);
                float c = (float) ((entityliving.locZ + (double)(PackageAccess.EntityLiving.getRand(entityliving).nextFloat() * entityliving.length * 2.0F)) - (double)entityliving.length - d2 * d3);
                PacketManager.spawnParticle("flame", a, b, c, (float)d, (float)d1, (float)d2, ((WorldServer)entityliving.world).dimension, entityliving.locX, entityliving.locY, entityliving.locZ);
            }
            

            PackageAccess.EntityLiving.dropFewItems(entityliving);
            entityliving.dead = true;
        }
        return true;
    }
    
    @Override
    public boolean a(ItemStack itemstack, int i, int j, int i1, int j1, EntityLiving entityliving1) {
        return true;
    }
}
