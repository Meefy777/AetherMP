package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.IReach;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ItemTool;
import net.minecraft.server.SAPI;
import net.minecraft.server.ToolBase;

public class ItemValkyrieAxe extends ItemTool implements IReach {

    protected ItemValkyrieAxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, blocksEffectiveAgainst);
        SAPI.reachAdd(this);
    }

    public boolean a(Block block) {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
            if(blocksEffectiveAgainst[i].id == block.id)
                return true;
        return false;
    }
    
    @Override
    public ToolBase getToolBase() {
        return ToolBase.Axe;
    }
    
    @Override
    public boolean reachItemMatches(ItemStack itemstack) {
        if(itemstack == null)
            return false;
        else
            return itemstack.id == ItemManager.AxeValkyrie.id;
    }
    
    @Override
    public float getReach(ItemStack itemstack) {
        return 10F;
    }

    private static Block blocksEffectiveAgainst[];

    static {
        blocksEffectiveAgainst = (new Block[] {
            BlockManager.Plank, BlockManager.Log
        });
    }
}
