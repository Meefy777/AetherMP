package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.blocks.BlockManager;
import net.minecraft.server.Block;
import net.minecraft.server.EnumToolMaterial;
import net.minecraft.server.ItemTool;
import net.minecraft.server.ToolBase;

public class ItemSkyrootSpade extends ItemTool {

    public ItemSkyrootSpade(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, blocksEffectiveAgainst);
    }
    
    @Override
    public ToolBase getToolBase() {
        return ToolBase.Shovel;
    }
    
    @Override
    public boolean a(Block block) {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
            if(blocksEffectiveAgainst[i].id == block.id)
                return true;
        return false;
    }

    private static Block[] blocksEffectiveAgainst = new Block[] {
            BlockManager.Quicksoil,
            BlockManager.Dirt,
            BlockManager.Grass,
            BlockManager.Aercloud
            };
}
