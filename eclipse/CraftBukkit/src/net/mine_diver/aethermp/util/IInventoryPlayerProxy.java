package net.mine_diver.aethermp.util;

import net.mine_diver.aethermp.blocks.tileentities.TileEntityAetherMPTreasureChest;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

public class IInventoryPlayerProxy implements IInventory {
	
	public IInventoryPlayerProxy(TileEntityAetherMPTreasureChest chest, ItemStack[] items) {
		this.chest = chest;
		this.items = items;
	}

	@Override
	public ItemStack[] getContents() {
		return this.items;
	}

	@Override
	public ItemStack getItem(int arg0) {
		return this.items[arg0];
	}

	@Override
	public void setItem(int arg0, ItemStack arg1) {
		this.items[arg0] = arg1;
	}

	@Override
    public ItemStack splitStack(final int i, final int j) {
        if (this.items[i] == null) {
            return null;
        }
        if (this.items[i].count <= j) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            this.update();
            return itemstack;
        }
        final ItemStack itemstack = this.items[i].a(j);
        if (this.items[i].count == 0) {
            this.items[i] = null;
        }
        this.update();
        return itemstack;
    }

	@Override
	public void update() {
		chest.update();
	}
	
	@Override
	public boolean a_(EntityHuman arg0) {
		return chest.a_(arg0);
	}
	
	@Override
	public int getMaxStackSize() {
		return chest.getMaxStackSize();
	}

	@Override
	public int getSize() {
		return chest.getSize();
	}
	
	@Override
	public String getName() {
		return chest.getName();
	}
	
	private final TileEntityAetherMPTreasureChest chest;
	private final ItemStack[] items;
	
}
