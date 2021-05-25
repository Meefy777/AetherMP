package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.bukkit.entity.Aerbunny;
import net.mine_diver.aethermp.entities.EntityAerbunny;
import net.minecraft.server.Entity;

public class CraftAerbunny extends CraftAnimalsAether implements Aerbunny  {

	public CraftAerbunny(CraftServer server, EntityAerbunny entity) {
		super(server, entity);
	}

	@Override
	public float getPuffiness() {
		return ((EntityAerbunny)getHandle()).getPuffiness();
	}

	@Override
	public void setPuffiness(float puff) {
		((EntityAerbunny)getHandle()).setPuffiness(puff);
	}

	@Override
	public int getAge() {
		return ((EntityAerbunny)getHandle()).age;
	}

	@Override
	public void setAge(int i) {
		((EntityAerbunny)getHandle()).age = i;
	}

	@Override
	public int getMate() {
		return ((EntityAerbunny)getHandle()).mate;
	}

	@Override
	public void setMate(int i) {
		((EntityAerbunny)getHandle()).mate = i;
	}

	@Override
	public boolean getGrab() {
		return ((EntityAerbunny)getHandle()).grab;
	}

	@Override
	public void setGrab(boolean flag) {
		((EntityAerbunny)getHandle()).grab = flag;
	}

	@Override
	public boolean getFear() {
		return ((EntityAerbunny)getHandle()).fear;
	}

	@Override
	public void setFear(boolean flag) {
		((EntityAerbunny)getHandle()).fear = flag;
	}

	@Override
	public boolean getGotRider() {
		return ((EntityAerbunny)getHandle()).gotrider;
	}

	@Override
	public void setGotRider(boolean flag) {
		((EntityAerbunny)getHandle()).gotrider = flag;
	}

	@Override
	public Entity getRunFrom() {
		return ((EntityAerbunny)getHandle()).runFrom;
	}

	@Override
	public void setRunFrom(Entity ent) {
		((EntityAerbunny)getHandle()).runFrom = ent;
	}	
	
	public String toString() {
		return "CraftAerbunny";
	}

}
