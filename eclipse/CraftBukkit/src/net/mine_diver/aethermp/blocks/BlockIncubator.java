// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   BlockIncubator.java

package net.mine_diver.aethermp.blocks;

import java.util.Random;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityIncubator;
import net.mine_diver.aethermp.inventory.ContainerIncubator;
import net.minecraft.server.Block;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.ModLoader;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import net.minecraft.server.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, ModLoader, World, 
//            Block, TileEntityIncubator, GuiIncubator, EntityPlayer, 
//            EntityLiving, MathHelper, ItemStack, EntityItem, 
//            TileEntity

public class BlockIncubator extends BlockContainer
{

    protected BlockIncubator(int blockID)
    {
        super(blockID, Material.STONE);
        IncubatorRand = new Random();
    }

    @Override
    public void c(World world, int i, int j, int k)
    {
        super.c(world, i, j, k);
        setDefaultDirection(world, i, j, k);
    }

    private void setDefaultDirection(World world, int i, int j, int k)
    {
        int l = world.getTypeId(i, j, k - 1);
        int i1 = world.getTypeId(i, j, k + 1);
        int j1 = world.getTypeId(i - 1, j, k);
        int k1 = world.getTypeId(i + 1, j, k);
        byte byte0 = 3;
        if(Block.o[l] && !Block.o[i1])
        {
            byte0 = 3;
        }
        if(Block.o[i1] && !Block.o[l])
        {
            byte0 = 2;
        }
        if(Block.o[j1] && !Block.o[k1])
        {
            byte0 = 5;
        }
        if(Block.o[k1] && !Block.o[j1])
        {
            byte0 = 4;
        }
        world.setData(i, j, k, byte0);
    }

    @Override
    public boolean interact(World world, int i, int j, int k, EntityHuman entityplayer)
    {
        TileEntityIncubator tileentityIncubator = (TileEntityIncubator)world.getTileEntity(i, j, k);
        ModLoader.OpenGUI(entityplayer, mod_AetherMp.idGuiIncubator, tileentityIncubator, new ContainerIncubator(entityplayer.inventory, tileentityIncubator));
        return true;
    }
    
    

    public static void updateIncubatorBlockState(boolean flag, World world, int i, int j, int k)
    {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        keepInv = true;
        if(flag)
            world.setRawData(i, j, k, 1);
        else
            world.setRawData(i, j, k, 0);
        keepInv = false;
        if (tileentity != null) {
        	tileentity.j();
            world.setTileEntity(i, j, k, tileentity);
        }
    }

    @Override
    protected TileEntity a_()
    {
        return new TileEntityIncubator();
    }

    @Override
    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving)
    {
        int l = MathHelper.floor((double)((entityliving.yaw * 4F) / 360F) + 0.5D) & 3;
        if(l == 0)
        {
            world.setData(i, j, k, 2);
        }
        if(l == 1)
        {
            world.setData(i, j, k, 5);
        }
        if(l == 2)
        {
            world.setData(i, j, k, 3);
        }
        if(l == 3)
        {
            world.setData(i, j, k, 4);
        }
    }

    @Override
    public void remove(World world, int i, int j, int k)
    {
    	if (!keepInv) {
    		TileEntityIncubator tileentityIncubator = (TileEntityIncubator)world.getTileEntity(i, j, k);
label0:
	        for(int l = 0; l < tileentityIncubator.getSize(); l++)
	        {
	            ItemStack itemstack = tileentityIncubator.getItem(l);
	            if(itemstack == null)
	            {
	                continue;
	            }
	            float f = IncubatorRand.nextFloat() * 0.8F + 0.1F;
	            float f1 = IncubatorRand.nextFloat() * 0.8F + 0.1F;
	            float f2 = IncubatorRand.nextFloat() * 0.8F + 0.1F;
	            do
	            {
	                if(itemstack.count <= 0)
	                {
	                    continue label0;
	                }
	                int i1 = IncubatorRand.nextInt(21) + 10;
	                if(i1 > itemstack.count)
	                {
	                    i1 = itemstack.count;
	                }
	                itemstack.count -= i1;
	                EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.id, i1, itemstack.getData()));
	                float f3 = 0.05F;
	                entityitem.motX = (float)IncubatorRand.nextGaussian() * f3;
	                entityitem.motY = (float)IncubatorRand.nextGaussian() * f3 + 0.2F;
	                entityitem.motZ = (float)IncubatorRand.nextGaussian() * f3;
	                world.addEntity(entityitem);
	            } while(true);
	        }
    	}
        super.remove(world, i, j, k);
    }

    private Random IncubatorRand;
    private static boolean keepInv = false;
}
