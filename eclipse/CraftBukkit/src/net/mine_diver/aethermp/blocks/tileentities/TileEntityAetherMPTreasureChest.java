package net.mine_diver.aethermp.blocks.tileentities;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;
import net.minecraft.server.TileEntityChest;

public class TileEntityAetherMPTreasureChest extends TileEntityChest {
	
	//read
    @SuppressWarnings("unchecked")
	@Override
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        Optional<NBTTagString> namesGet = nbttagcompound.c().stream().filter(e -> e instanceof NBTTagString && ((NBTTagString)e).toString().startsWith("$~")).findFirst();
        if (!namesGet.isPresent())
        	return;
        String[] names = namesGet.get().toString().substring(2).split(",");
        for (String name : names) {
            final NBTTagList nbttaglist = nbttagcompound.l("Items:" + name);
            ItemStack[] items = new ItemStack[this.getSize()];
            for (int i = 0; i < nbttaglist.c(); ++i) {
                final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist.a(i);
                final int j = nbttagcompound2.c("Slot") & 0xFF;
                if (j >= 0 && j < items.length)
                    items[j] = new ItemStack(nbttagcompound2);
            }
            this.playerChestLootItems.put(name, items);
        } 
    }
    
    //write
    @Override
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        //Things are private. To avoid reflection it is time for jank.
        nbttagcompound.setString("PlaceHold", "$~" + this.playerChestLootItems.keySet().stream().collect(Collectors.joining(",")));
        for (Map.Entry<String, ItemStack[]> set : this.playerChestLootItems.entrySet()) {
	        final NBTTagList nbttaglist = new NBTTagList();
	        ItemStack[] items = set.getValue();
	        for (int i = 0; i < items.length; ++i) {
	            if (items[i] != null) {
	                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
	                nbttagcompound2.a("Slot", (byte)i);
	                items[i].a(nbttagcompound2);
	                nbttaglist.a(nbttagcompound2);
	            }
	        }
	        nbttagcompound.a("Items:" + set.getKey(), nbttaglist);
        }
    }
    
    public void clearMainInventory() {
    	try {
			ITEMSTACK_FIELD.set(this, new ItemStack[27]);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
    public final Map<String, ItemStack[]> playerChestLootItems = new HashMap<>();
	private static final Field ITEMSTACK_FIELD;
	
	static {
		try {
			ITEMSTACK_FIELD = TileEntityChest.class.getDeclaredField("items");
			ITEMSTACK_FIELD.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
