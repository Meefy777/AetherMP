package net.mine_diver.aethermp.api.entities;

import java.util.List;

import net.minecraft.server.EntityPlayer;

public interface IAetherBoss {

    public int getBossHP();

    public int getBossMaxHP();

    public boolean isCurrentBoss(EntityPlayer entityPlayer);

    public int getBossEntityID();

    public String getBossTitle();
    
    public void stopFight();
    
    public BossType getBossType();
    
    public enum BossType {
    	BRONZE,
    	SILVER,
    	GOLD,
    	OTHER
    }
    
    public List<EntityPlayer> getTargetList();
    
    public void setTargetList(List<EntityPlayer> list);
    
    public EntityPlayer getCurrentTarget();   
    
    public void findNewTarget();
}
