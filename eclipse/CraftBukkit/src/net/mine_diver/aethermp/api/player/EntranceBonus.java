package net.mine_diver.aethermp.api.player;

import net.minecraft.server.ItemStack;

public interface EntranceBonus extends PlayerApplicableInterface {
	
	ItemStack[] getBonus();
}
