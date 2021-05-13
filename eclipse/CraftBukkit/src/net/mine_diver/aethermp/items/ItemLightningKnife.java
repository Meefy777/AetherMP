package net.mine_diver.aethermp.items;

import net.mine_diver.aethermp.entities.EntityLightningKnife;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemLightningKnife extends Item {

    public ItemLightningKnife(int i) {
        super(i);
        maxStackSize = 16;
    }
    
    @Override
    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityplayer) {
        itemstack.count--;
        world.addEntity(new EntityLightningKnife(world, entityplayer));
        PacketManager.makeSound(entityplayer, "mob.aether.dartshoot", 2.0F, 1.0F / (world.random.nextFloat() * 0.4F + 0.8F));
        return itemstack;
    }
}
