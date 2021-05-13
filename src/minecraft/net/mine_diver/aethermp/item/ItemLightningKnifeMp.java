package net.mine_diver.aethermp.item;

import net.minecraft.src.EntityLightningKnife;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemLightningKnife;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemLightningKnifeMp extends ItemLightningKnife {

	public ItemLightningKnifeMp(int i) {
		super(i);
	}
	
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        if(!world.multiplayerWorld)
        {
        	world.playSoundAtEntity(entityplayer, "mob.aether.dartshoot", 2.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            world.entityJoinedWorld(new EntityLightningKnife(world, entityplayer));
        }
        return itemstack;
    }

}
