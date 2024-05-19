package net.mine_diver.aethermp.render;

import org.lwjgl.opengl.GL11;

import net.mine_diver.aethermp.entity.EntitySwetMp;
import net.minecraft.src.EntitySwet;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderSwet;

public class RenderSwetMp extends RenderSwet {

	public RenderSwetMp(ModelBase modelbase, ModelBase modelbase1, float f) {
		super(modelbase, modelbase1, f);
	}
	
	@Override
    protected void a(EntitySwet entityswets, float f) {
        float f2;
        float f1 = f2 = 1.0F;
        float f3 = 1.5F;
        Double motY = Double.valueOf(entityswets.getDataWatcher().getWatchableObjectString(18));
        if(!entityswets.onGround)
        {
            if(motY > 0.85000002384185791D)
            {
                f1 = 1.425F;
                f2 = 0.575F;
            } else
            if(motY < -0.85000002384185791D)
            {
                f1 = 0.575F;
                f2 = 1.425F;
            } else
            {
                float f4 = (float)(motY * 0.5F);
                f1 += f4;
                f2 -= f4;
            }
        }
        
        if (entityswets.worldObj.multiplayerWorld) {
        	EntitySwetMp swet = (EntitySwetMp)entityswets;
        	float dimensions = swet.getWidth() + swet.getHeight();
        	if (swet.isMounted())
        		f3 = 1.5F + dimensions * 0.75F;
        	else if (entityswets.riddenByEntity != null)
        		f3 = 1.5F + (entityswets.riddenByEntity.width + entityswets.riddenByEntity.height) * 0.75F;
        }
        else if(entityswets.riddenByEntity != null)
        	f3 = 1.5F + (entityswets.riddenByEntity.width + entityswets.riddenByEntity.height) * 0.75F;
        GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
    }

}
