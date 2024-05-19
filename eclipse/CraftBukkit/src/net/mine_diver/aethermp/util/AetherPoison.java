package net.mine_diver.aethermp.util;

import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityPoisonDistractEvent;
import net.mine_diver.aethermp.entities.EntityAechorPlant;
import net.mine_diver.aethermp.entities.EntityCockatrice;
import net.mine_diver.aethermp.entities.EntityFireMonster;
import net.mine_diver.aethermp.entities.EntityFiroBall;
import net.mine_diver.aethermp.entities.EntityHomeShot;
import net.mine_diver.aethermp.entities.EntityMiniCloud;
import net.mine_diver.aethermp.entities.EntitySentry;
import net.mine_diver.aethermp.entities.EntitySlider;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.PlayerBaseAether;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.PlayerAPI;

public class AetherPoison {

    public static boolean canPoison(Entity entity) {
        return !(entity instanceof EntitySlider) && !(entity instanceof EntitySentry) && !(entity instanceof EntityMiniCloud) && !(entity instanceof EntityFireMonster) && !(entity instanceof EntityAechorPlant) && !(entity instanceof EntityFiroBall) && !(entity instanceof EntityCockatrice) && !(entity instanceof EntityHomeShot);
    }

    public static void distractEntity(Entity ent, EntityPoisonDistractEvent event) {
    	//Server distraction was not such a good idea so I am commenting this out and moving to client side
    	//Easier to cheat but still has better overall experience, thanks beta networking.
    	//Will only apply to player
    	if (!(ent instanceof EntityPlayer)) {
    		if (event.isCancelled())
    			return;
	        double gauss = ent.world.random.nextGaussian();
	        double newMotD = motDFac * gauss;
	        motD = motTaper * newMotD + (1.0D - motTaper) * motD;
	        ent.motX += motD;
	        ent.motZ += motD;
	        double newRotD = rotDFac * gauss;
	        rotD = rotTaper * newRotD + (1.0D - rotTaper) * rotD;
	        ent.yaw += rotD;
	        ent.pitch += rotD;
	        ent.velocityChanged = true;
    	} else {
    		PlayerBaseAether playerBase = (PlayerBaseAether)PlayerAPI.getPlayerBase((EntityPlayer)ent, PlayerBaseAether.class);
    		if (playerBase.distracted != !event.isCancelled()) {
    			playerBase.distracted = event.isCancelled();
    			PacketManager.updateDistraction((EntityPlayer) ent);
    		}
    	}
    }

    public static double rotDFac = 0.78539816339744828D;
    public static double rotD;
    public static double rotTaper = 0.125D;
    public static double motTaper = 0.20000000000000001D;
    public static double motD;
    public static double motDFac = 0.10000000000000001D;
}
