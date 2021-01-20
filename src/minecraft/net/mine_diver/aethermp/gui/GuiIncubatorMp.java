package net.mine_diver.aethermp.gui;

import org.lwjgl.opengl.GL11;

import net.mine_diver.aethermp.inventory.ContainerIncubatorMp;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntityIncubator;

public class GuiIncubatorMp extends GuiContainer {

	public GuiIncubatorMp(InventoryPlayer inventoryplayer, TileEntityIncubator tileentityIncubator) {
		super(new ContainerIncubatorMp(inventoryplayer, tileentityIncubator));
		IncubatorInventory = tileentityIncubator;
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Incubator", 60, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("/aether/gui/incubator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        if(IncubatorInventory.isBurning())
        {
            int l = IncubatorInventory.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(j + 74, (k + 47) - l, 176, 12 - l, 14, l + 2);
        }
        int i1 = IncubatorInventory.getCookProgressScaled(54);
        drawTexturedModalRect(j + 103, (k + 70) - i1, 179, 70 - i1, 10, i1);
    }

    private TileEntityIncubator IncubatorInventory;

}
