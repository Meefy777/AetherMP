package net.mine_diver.aethermp.util;

import net.mine_diver.aethermp.player.PlayerBaseAetherMp;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AetherPoison;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.PlayerAPI;

public class AetherPoisonMp extends AetherPoison {
	
	public static void distractEntity(EntityPlayerSP ent) {
		//Old mine_diver code, just replacing with standard distraction code since server version is not code
		//Client side distraction will only work for players
        /*double gauss = mc.theWorld.rand.nextGaussian();
        double newRotD = rotDFac * gauss;
        rotD = rotTaper * newRotD + (1.0D - rotTaper) * rotD;
        ent.rotationYaw += rotD;
        ent.rotationPitch += rotD;
        */
        if (!((PlayerBaseAetherMp)PlayerAPI.getPlayerBase(ent, PlayerBaseAetherMp.class)).distract)
        	return;
        double gauss = mc.theWorld.rand.nextGaussian();
        double newMotD = motDFac * gauss;
        motD = motTaper * newMotD + (1.0D - motTaper) * motD;
        ent.motionX += motD;
        ent.motionZ += motD;
        double newRotD = rotDFac * gauss;
        rotD = rotTaper * newRotD + (1.0D - rotTaper) * rotD;
        ent.rotationYaw += rotD;
        ent.rotationPitch += rotD;
    }
	
	public static void tickRender(Minecraft game) {
        if((world != game.theWorld && !game.theWorld.multiplayerWorld) || game.thePlayer != null && (game.thePlayer.isDead || game.thePlayer.health <= 0)) {
            world = game.theWorld;
            poisonTime = 0;
            return;
        }
        if(world == null)
            return;
        if(poisonTime < 0) {
            poisonTime++;
            displayCureEffect();
            return;
        }
        if(poisonTime == 0)
            return;
        long time = mc.theWorld.getWorldTime();
        int mod = poisonTime % 50;
        if(clock != time) {
            distractEntity(game.thePlayer);
            poisonTime--;
            clock = time;
        }
        displayPoisonEffect(mod);
    }
	
    public static boolean afflictPoison(int i)
    {
        if(poisonTime < 0)
        {
            return false;
        } else
        {
            poisonTime = i;
            world = mc.theWorld;
            return true;
        }
    }
}
