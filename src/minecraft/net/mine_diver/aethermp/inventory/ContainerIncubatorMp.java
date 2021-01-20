package net.mine_diver.aethermp.inventory;

import net.minecraft.src.ContainerIncubator;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntityIncubator;

public class ContainerIncubatorMp extends ContainerIncubator {

	public ContainerIncubatorMp(InventoryPlayer inventoryplayer, TileEntityIncubator tileentityIncubator) {
		super(inventoryplayer, tileentityIncubator);
		try {
			tileEntity = (TileEntityIncubator) ModLoader.getPrivateValue(ContainerIncubator.class, this, "Incubator");
		} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
        for(int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);
            
            try {
				if(((int)ModLoader.getPrivateValue(ContainerIncubator.class, this, "burnTime")) != tileEntity.progress)
				{
				    icrafting.updateCraftingInventoryInfo(this, 0, tileEntity.progress);
				}
			} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
            try {
				if(((int)ModLoader.getPrivateValue(ContainerIncubator.class, this, "itemBurnTime")) != tileEntity.torchPower)
				{
				    icrafting.updateCraftingInventoryInfo(this, 1, tileEntity.torchPower);
				}
			} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
        }

        //burnTime = tileEntity.progress;
        //itemBurnTime = tileEntity.torchPower;
        
        try {
			ModLoader.setPrivateValue(ContainerIncubator.class, this, "burnTime", tileEntity.progress);
	        ModLoader.setPrivateValue(ContainerIncubator.class, this, "itemBurnTime", tileEntity.torchPower);
		} catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void updateProgressBar(int i, int j)
    {
        if(i == 0)
        {
        	tileEntity.progress = j;
        }
        if(i == 1)
        {
        	tileEntity.torchPower = j;
        }
    }
    
    private TileEntityIncubator tileEntity;
}
