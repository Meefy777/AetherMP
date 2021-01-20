// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   ContainerIncubator.java

package net.mine_diver.aethermp.inventory;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityIncubator;
import net.mine_diver.aethermp.blocks.tileentities.TileEntityIncubatorSlot;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.InventoryPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;

// Referenced classes of package net.minecraft.src:
//            Container, TileEntityIncubatorSlot, Slot, InventoryPlayer, 
//            TileEntityIncubator, ItemStack, EntityPlayer

public class ContainerIncubator extends Container
{

    public ContainerIncubator(InventoryPlayer inventoryplayer, TileEntityIncubator tileentityIncubator)
    {
        cookTime = 0;
        burnTime = 0;
        itemBurnTime = 0;
        Incubator = tileentityIncubator;
        a(new TileEntityIncubatorSlot(tileentityIncubator, 1, 73, 17));
        a(new Slot(tileentityIncubator, 0, 73, 53));
        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
                a(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
            a(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    @Override
    protected void a(ItemStack itemstack1, int k, int l, boolean flag1)
    {
    }

    @Override
    public boolean b(EntityHuman entityplayer)
    {
        return Incubator.a_(entityplayer);
    }

    @Override
    public ItemStack a(int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)e.get(i);
        if(slot != null && slot.b())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if(i == 2)
            {
                a(itemstack1, 3, 39, true);
            } else
            if(i >= 3 && i < 30)
            {
                a(itemstack1, 30, 39, false);
            } else
            if(i >= 30 && i < 39)
            {
                a(itemstack1, 3, 30, false);
            } else
            {
                a(itemstack1, 3, 39, false);
            }
            if(itemstack1.count == 0)
            {
                slot.c(null);
            } else
            {
                slot.c();
            }
            if(itemstack1.count != itemstack.count)
            {
                slot.a(itemstack1);
            } else
            {
                return null;
            }
        }
        return itemstack;
    }
    
   /* @Override
    public void a() {
        super.a();
        for(int i = 0; i < listeners.size(); i++) {
            ICrafting icrafting = (ICrafting)listeners.get(i);
            if (cookTime != 6000)
                icrafting.a(this, 0, 6000);
            if (burnTime != Incubator.progress)
                icrafting.a(this, 1, Incubator.progress);
            if (itemBurnTime != Incubator.torchPower)
                icrafting.a(this, 2, Incubator.torchPower);
        }

        cookTime = 6000;
        burnTime = Incubator.progress;
        itemBurnTime = Incubator.torchPower;
    }
    
    @Override
    public void a(final ICrafting crafting) {
        super.a(crafting);
        crafting.a(this, 0, 6000);
        crafting.a(this, 1, Incubator.progress);
        crafting.a(this, 2, Incubator.torchPower);
    }*/

    @Override
    public void a(ICrafting icrafting)
    {
        super.a(icrafting);
        //icrafting.a(this, 0, Incubator.frozenTimeForItem);
        icrafting.a(this, 0, Incubator.progress);
        icrafting.a(this, 1, Incubator.torchPower);
    }

    @Override
    public void a()
    {
        super.a();
        for(int i = 0; i < listeners.size(); i++) {
            ICrafting icrafting = (ICrafting)listeners.get(i);
           /* if(cookTime != Incubator.frozenTimeForItem)
                icrafting.a(this, 0, Incubator.frozenTimeForItem);*/
            if(burnTime != Incubator.progress)
                icrafting.a(this, 0, Incubator.progress);
            if(itemBurnTime != Incubator.torchPower)
                icrafting.a(this, 1, Incubator.torchPower);
        }

        //cookTime = Incubator.frozenTimeForItem;
        burnTime = Incubator.progress;
        itemBurnTime = Incubator.torchPower;
    }
    
    private TileEntityIncubator Incubator;
    @SuppressWarnings("unused")
	private int cookTime;
    @SuppressWarnings("unused")
	private int burnTime;
    private int itemBurnTime;
}
