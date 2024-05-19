package net.mine_diver.aethermp.util;

import java.util.Random;

import net.minecraft.src.AetherBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.BlockAetherPortal;
import net.minecraft.src.IInterceptBlockSet;
import net.minecraft.src.Loc;
import net.minecraft.src.World;
import net.minecraft.src.mod_Aether;

public class BlockPlacementHandlerMP implements IInterceptBlockSet {

	@Override
    public boolean canIntercept(World world, Loc loc, int blockID)
    {
        if(mod_Aether.getCurrentDimension() == 3)
        {
            if(blockID == Block.torchWood.blockID)
            {
                return true;
            }
            if(blockID == Block.fire.blockID)
            {
                return true;
            }
            if(blockID == Block.netherrack.blockID)
            {
                return true;
            }
            if(blockID == Block.slowSand.blockID)
            {
                return true;
            }
            if(blockID == Block.lavaMoving.blockID || blockID == Block.lavaStill.blockID)
            {
                return true;
            }
            if(blockID == Block.portal.blockID)
            {
                return true;
            }
            if(blockID == Block.blockBed.blockID)
            {
                return true;
            }
        } else
        if(mod_Aether.getCurrentDimension() < 0)
        {
            if(blockID == AetherBlocks.Portal.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Dirt.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Grass.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Aercloud.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.AmbrosiumOre.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.AmbrosiumTorch.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.DungeonStone.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.EnchantedGravitite.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.GoldenOakLeaves.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.GoldenOakSapling.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.GravititeOre.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Holystone.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Icestone.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.LightDungeonStone.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.LockedDungeonStone.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.LockedLightDungeonStone.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.ChestMimic.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Pillar.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Quicksoil.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.SkyrootLeaves.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Plank.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.SkyrootSapling.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Log.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.Trap.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.TreasureChest.blockID)
            {
                return true;
            }
            if(blockID == AetherBlocks.ZaniteOre.blockID)
            {
                return true;
            }
        }
        return blockID == Block.waterMoving.blockID || blockID == Block.waterStill.blockID;
    }

	@Override
    public int intercept(World world, Loc loc, int blockID)
    {
        if(blockID == Block.blockBed.blockID)
        {
            return AetherBlocks.Bed.blockID;
        }
        if(blockID == Block.waterMoving.blockID || blockID == Block.waterStill.blockID)
        {
            if(world.getBlockId(loc.x(), loc.y() - 1, loc.z()) == Block.glowStone.blockID && ((BlockAetherPortal)AetherBlocks.Portal).tryToCreatePortal(world, loc.x(), loc.y(), loc.z()))
            {
                return AetherBlocks.Portal.blockID;
            }
            if(mod_Aether.getCurrentDimension() != -1)
            {
                return blockID;
            }
        }
        if (!world.multiplayerWorld) {
	        Random rand = new Random();
	        for(int n = 0; n < 10; n++)
	        {
	            world.spawnParticle("smoke", loc.x + 0.5D + rand.nextGaussian() * 0.10000000000000001D, loc.y + 0.5D + rand.nextGaussian() * 0.10000000000000001D, loc.z + 0.5D + rand.nextGaussian() * 0.01D, rand.nextGaussian() * 0.01D, rand.nextGaussian() * 0.01D, rand.nextGaussian() * 0.10000000000000001D);
	        }
        }

        if(blockID == Block.lavaMoving.blockID || blockID == Block.lavaStill.blockID)
        {
            return AetherBlocks.Aerogel.blockID;
        }
        if(mod_Aether.getCurrentDimension() < 0 && (blockID == Block.waterMoving.blockID || blockID == Block.waterStill.blockID))
        {
            return Block.cobblestone.blockID;
        } else
        {
            return 0;
        }
    }
	
}
