package net.mine_diver.aethermp.item;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemVampireBlade;

public class ItemVampireBladeMp extends ItemVampireBlade {

	public ItemVampireBladeMp(int i) {
		super(i);
	}
	
	@Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
		if (entityliving.worldObj.multiplayerWorld)
			return true;
        return super.hitEntity(itemstack, entityliving, entityliving1);
    }

}
