// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   TileEntityIncubatorSlot.java

package net.mine_diver.aethermp.blocks.tileentities;

import net.minecraft.server.IInventory;
import net.minecraft.server.Slot;

// Referenced classes of package net.minecraft.src:
//            Slot, IInventory

public class TileEntityIncubatorSlot extends Slot
{

    public TileEntityIncubatorSlot(IInventory inv, int slot, int x, int y)
    {
        super(inv, slot, x, y);
    }

    @Override
    public int d()
    {
        return 1;
    }
}
