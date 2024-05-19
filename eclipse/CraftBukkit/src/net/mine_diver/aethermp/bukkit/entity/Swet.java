package net.mine_diver.aethermp.bukkit.entity;

import org.bukkit.entity.Animals;

public interface Swet extends Animals {

	int getTicker();
	
	int getFlutter();
	
	boolean getTextureSet();
	
	boolean getKickoff();
	
	int getHops();
	
	boolean getGotRider();
	
	void setTicker(int ticker);
	
	void setFlutter(int flutter);
	
	void setTextureSet(boolean textureSet);
	
	void setKickOff(boolean kickoff);
	
	void setHops(int hops);
	
	void setGotRider(boolean gotrider);
	
}
