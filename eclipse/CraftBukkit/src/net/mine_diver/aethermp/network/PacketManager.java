package net.mine_diver.aethermp.network;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ModLoader;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

public class PacketManager {
	
	public static void makeSound(Entity entity, String sound, float volume, float pitch){
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.packetType = 0;
        packet.dataInt = new int[]{entity.id};
        packet.dataString = new String[]{sound};
        packet.dataFloat = new float[]{volume, pitch};
        packet.modId = ModLoaderMp.GetModInstance(mod_AetherMp.class).getId();
        ModLoader.getMinecraftServerInstance().getTracker(((WorldServer) entity.world).dimension).a(entity, packet);
    }
	
    public static void makeSound(float x, float y, float z, String sound, float volume, float pitch, int dimension) {
        Packet230ModLoader packet = new Packet230ModLoader();
        packet.packetType = 1;
        packet.dataString = new String[]{sound};
        packet.dataFloat = new float[]{x, y, z, volume, pitch};
        sendToViewDistance(packet, dimension, x, y, z);
    }
    
    public static void addBlockDestroyEffects(int x, int y, int z, int blockID, int metadata, int dimension) {
    	Packet230ModLoader packet = new Packet230ModLoader();
    	packet.dataInt = new int[] {x, y, z, blockID, metadata};
    	packet.packetType = 12;
    	sendToViewDistance(packet, dimension, x, y, z);
    }
    
    public static void sendToViewDistance(Packet230ModLoader packet, int dimension, double x, double y, double z) {
    	packet.modId = ModLoaderMp.GetModInstance(mod_AetherMp.class).getId();
    	ModLoader.getMinecraftServerInstance().serverConfigurationManager.sendPacketNearby(x, y, z, ModLoader.getMinecraftServerInstance().propertyManager.getInt("view-distance", 10) * 16, dimension, packet);
    }
    
    public static void sendFireBoss(EntityPlayer player) {
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.packetType = 36;
		packet.dataInt = new int [] {boolToInt(mod_AetherMp.CORE.dataHandler.getFireMonsterKilled())};
		if (player != null)
			ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), player, packet);
		else
			ModLoaderMp.SendPacketToAll(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
    }
    
    public static void spawnParticle(String s, float a, float b, float c, float d, float e, float f, int dim, double x, double y, double z) {
    	Packet230ModLoader packet = new Packet230ModLoader();
    	packet.packetType = 31;
    	packet.dataString = new String[] {s};
    	packet.dataFloat = new float [] {a, b, c, d, e, f};
    	sendToViewDistance(packet, dim, x, y, z);
    }
    
	public static int boolToInt(boolean flag) {
		if (flag)
			return 1;
		else
			return 0;
	}
}
