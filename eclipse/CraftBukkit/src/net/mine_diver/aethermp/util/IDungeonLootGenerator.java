package net.mine_diver.aethermp.util;

import java.util.Random;

import net.minecraft.server.ItemStack;

public interface IDungeonLootGenerator {

    ItemStack getLoot(Random random);
    
    ItemStack getDefaultLoot(Random random);
	
}
