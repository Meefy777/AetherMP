package net.mine_diver.aethermp.proxy;

import java.util.HashMap;

import net.minecraft.src.Achievement;
import net.minecraft.src.AetherAchievements;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.WorldProviderAether;
import net.minecraft.src.mod_Aether;
import net.minecraft.src.mod_AetherMp;

public class AchievementsMapProxy<K, V> extends HashMap<K, V> {
	
	private static final long serialVersionUID = 1204728830661192969L;
	
	@Override
	public boolean containsKey(Object key) {
		EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
		
		if (player != null && player.worldObj != null && !player.worldObj.multiplayerWorld)
			return super.containsKey(key);
		StackTraceElement element = new Exception().getStackTrace()[2];
		if (element.getClassName().equals(mod_Aether.class.getName()) && element.getMethodName().equals("giveAchievement") && key instanceof Achievement)
			return true;
		else if (player != null && player.worldObj != null && player.worldObj.multiplayerWorld && key instanceof Achievement && ((Achievement) key) == AetherAchievements.defeatGold && element.getClassName().equals(WorldProviderAether.class.getName()))
			return mod_AetherMp.isFireDefeated;
		else
			return super.containsKey(key);
	}
}
