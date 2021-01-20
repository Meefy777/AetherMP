package net.mine_diver.aethermp.render;

import net.mine_diver.aethermp.entity.EntityMoaMp;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderMoa;

public class RenderMoaMp extends RenderMoa {

	public RenderMoaMp(ModelBase modelbase, float f) {
		super(modelbase, f);
	}
	
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        if((entityliving instanceof EntityMoaMp) && ((EntityMoaMp)entityliving).getBaby())
        {
            return;
        } else
        {
            scalemoa();
            return;
        }
    }

}
