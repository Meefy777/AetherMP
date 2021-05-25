package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

import net.mine_diver.aethermp.entities.Whirly;

public class CraftWhirly extends CraftAnimalsAether implements net.mine_diver.aethermp.bukkit.entity.Whirly {

	public CraftWhirly(CraftServer server, Whirly entity) {
		super(server, entity);
	}

	@Override
	public int getLife() {
		return ((Whirly)getHandle()).Life;
	}

	@Override
	public void setLife(int life) {
		((Whirly)getHandle()).Life = life;
	}

	@Override
	public int getEntCount() {
		return ((Whirly)getHandle()).entcount;
	}

	@Override
	public void setEntCount(int count) {
		((Whirly)getHandle()).entcount = count;
	}

	@Override
	public float getSpeed() {
		return ((Whirly)getHandle()).Speed;
	}

	@Override
	public void setSpeed(float speed) {
		((Whirly)getHandle()).Speed = speed;
	}

	@Override
	public float getAngle() {
		return ((Whirly)getHandle()).Angle;
	}

	@Override
	public void setAngle(float f) {
		((Whirly)getHandle()).Angle = f;
	}

	@Override
	public float getCurve() {
		return ((Whirly)getHandle()).Curve;
	}

	@Override
	public void setCurve(float f) {
		((Whirly)getHandle()).Curve = f;
	}

	@Override
	public boolean getEvil() {
		return ((Whirly)getHandle()).evil;
	}

	@Override
	public void setEvil(boolean bool) {
		((Whirly)getHandle()).evil = bool;
	}
	
	@Override
	public String toString() {
		return "CraftWhirly";
	}
}