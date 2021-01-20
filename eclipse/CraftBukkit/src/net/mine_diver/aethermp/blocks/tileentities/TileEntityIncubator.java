// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   TileEntityIncubator.java

package net.mine_diver.aethermp.blocks.tileentities;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.entities.EntityMoa;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.util.Achievements;
import net.mine_diver.aethermp.util.MoaColour;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.TileEntity;

// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, NBTTagCompound, 
//            NBTTagList, AetherItems, Item, EntityMoa, 
//            MoaColour, World, AetherAchievements, mod_Aether, 
//            AetherBlocks, Block, EntityPlayer

public class TileEntityIncubator extends TileEntity
    implements IInventory
{

    public TileEntityIncubator()
    {
        IncubatorItemStacks = new ItemStack[2];
        progress = 0;
    }

    @Override
    public int getSize()
    {
        return IncubatorItemStacks.length;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return IncubatorItemStacks[i];
    }

    @Override
    public ItemStack splitStack(int i, int j)
    {
        if(IncubatorItemStacks[i] != null)
        {
            if(IncubatorItemStacks[i].count <= j)
            {
                ItemStack itemstack = IncubatorItemStacks[i];
                IncubatorItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = IncubatorItemStacks[i].a(j);
            if(IncubatorItemStacks[i].count == 0)
            {
                IncubatorItemStacks[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }
    
    

    @Override
    public void setItem(int i, ItemStack itemstack)
    {
        IncubatorItemStacks[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxStackSize())
        {
            itemstack.count = getMaxStackSize();
        }
    }

    @Override
    public String getName()
    {
        return "Incubator";
    }
    
    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.l("Items");
        IncubatorItemStacks = new ItemStack[getSize()];
        for(int i = 0; i < nbttaglist.c(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.a(i);
            byte byte0 = nbttagcompound1.c("Slot");
            if(byte0 >= 0 && byte0 < IncubatorItemStacks.length)
            {
                IncubatorItemStacks[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        progress = nbttagcompound.d("BurnTime");
        torchPower = nbttagcompound.d("TorchPower");
    }

    @Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("BurnTime", (short)progress);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < IncubatorItemStacks.length; i++)
        {
            if(IncubatorItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.a("Slot", (byte)i);
                IncubatorItemStacks[i].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", nbttaglist);
        nbttagcompound.a("BurnTime", (short)progress);
        nbttagcompound.a("TorchPower", (short)torchPower);
    }

    @Override
    public int getMaxStackSize()
    {
        return 64;
    }

    public int getCookProgressScaled(int i)
    {
        return (progress * i) / 6000;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        return (torchPower * i) / 500;
    }

    
    public boolean isBurning()
    {
        return torchPower > 0;
    }

    @Override
    public void g_()
    {
        if(torchPower > 0)
        {
            torchPower--;
            if(getItem(1) != null)
            {
                progress++;
            }
        }
        if(IncubatorItemStacks[1] == null || IncubatorItemStacks[1].id != ItemManager.MoaEgg.id)
        {
            progress = 0;
        }
        if(progress >= 6000)
        {
            if(IncubatorItemStacks[1] != null)
            {
                EntityMoa moa = new EntityMoa(world, true, false, false, MoaColour.getColour(IncubatorItemStacks[1].getData()));
                moa.setPosition((double)x + 0.5D, (double)y + 1.5D, (double)z + 0.5D);
                world.addEntity(moa);
                EntityPlayer player = (EntityPlayer) world.findNearbyPlayer(moa, 100D);
                if (player != null) 
                	Achievements.giveAchievement(Achievements.incubator, player);
            }
            splitStack(1, 1);
            progress = 0;
        }
        if(torchPower <= 0 && IncubatorItemStacks[1] != null && IncubatorItemStacks[1].id == ItemManager.MoaEgg.id && getItem(0) != null && getItem(0).id == BlockManager.AmbrosiumTorch.id)
        {
            torchPower += 1000;
            splitStack(0, 1);
        }
        
        update();
    }

    @Override
    public boolean a_(EntityHuman entityplayer)
    {
        if(world.getTileEntity(x, y, z) != this)
        {
            return false;
        } else
        {
            return entityplayer.e((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D) <= 64D;
        }
    }
    
	@Override
	public ItemStack[] getContents() {
		return this.IncubatorItemStacks;
	}

    private ItemStack IncubatorItemStacks[];
    public int torchPower;
    public int progress;
}
