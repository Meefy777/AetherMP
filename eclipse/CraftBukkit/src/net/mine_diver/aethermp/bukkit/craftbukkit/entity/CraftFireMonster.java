package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.entity.CraftEntity;

import net.mine_diver.aethermp.bukkit.entity.FireMonster;
import net.mine_diver.aethermp.entities.EntityFireMonster;

public class CraftFireMonster extends CraftFlyingAether implements FireMonster {

	public CraftFireMonster(CraftServer server, EntityFireMonster entity) {
		super(server, entity);
	}

	@Override
	public int getBossHP() {
		return ((EntityFireMonster) getHandle()).getBossHP();
	}

	@Override
	public int getBossMaxHP() {
		return ((EntityFireMonster) getHandle()).getBossMaxHP();
	}

	@Override
	public boolean isCurrentBoss(Player player) {
		return ((EntityFireMonster) getHandle()).isCurrentBoss(((CraftPlayer)player).getHandle());
	}

	@Override
	public int getBossEntityID() {
		return ((EntityFireMonster) getHandle()).getBossEntityID();
	}

	@Override
	public String getBossTitle() {
		return ((EntityFireMonster) getHandle()).getBossTitle();
	}

	@Override
	public void stopFight() {
		((EntityFireMonster) getHandle()).stopFight();
	}

	@Override
	public LivingEntity getTarget() {
		return (LivingEntity) ((EntityFireMonster) getHandle()).targetfire.getBukkitEntity();
	}

	@Override
	public void setTarget(LivingEntity ent) {
		((EntityFireMonster) getHandle()).targetfire = ((CraftEntity)ent).getHandle();
	}

	@Override
	public boolean getGotTarget() {
		return ((EntityFireMonster) getHandle()).gotTarget;
	}

	@Override
	public void setGotTarget(boolean flag) {
		((EntityFireMonster) getHandle()).gotTarget = flag;
	}
	
	@Override
	public double getSpeedness() {
		return ((EntityFireMonster) getHandle()).speedness;
	}

	@Override
	public void setSpeedness(double d) {
		((EntityFireMonster) getHandle()).speedness = d;
	}

	@Override
	public double getRotary() {
		return ((EntityFireMonster) getHandle()).rotary;
	}

	@Override
	public void setRotary(double d) {
		((EntityFireMonster) getHandle()).rotary = d;
	}

	@Override
	public int getDirection() {
		return ((EntityFireMonster) getHandle()).direction;
	}

	@Override
	public void setDirection(int i) {
		((EntityFireMonster) getHandle()).direction = i;
	}

	@Override
	public int getHurtness() {
		return ((EntityFireMonster) getHandle()).hurtness;
	}

	@Override
	public void setHurtness(int i) {
		((EntityFireMonster) getHandle()).hurtness = i;
	}

	@Override
	public int getChatCount() {
		return ((EntityFireMonster) getHandle()).chatCount;
	}

	@Override
	public void setChatCount(int i) {
		((EntityFireMonster) getHandle()).chatCount = i;
	}

	@Override
	public int getChatLog() {
		return ((EntityFireMonster) getHandle()).chatLog;
	}

	@Override
	public void setChatLog(int i) {
		((EntityFireMonster) getHandle()).chatLog = i;
	}

	@Override
	public int getBallCount() {
		return ((EntityFireMonster) getHandle()).ballCount;
	}

	@Override
	public void setBallCount(int i) {
		((EntityFireMonster) getHandle()).ballCount = i;
	}

	@Override
	public int getFlameCount() {
		return ((EntityFireMonster) getHandle()).flameCount;
	}

	@Override
	public void setFlameCount(int i) {
		((EntityFireMonster) getHandle()).flameCount = i;
	}

	@Override
	public int getEntCount() {
		return ((EntityFireMonster) getHandle()).entCount;
	}

	@Override
	public void setEntCount(int i) {
		((EntityFireMonster) getHandle()).entCount = i;
	}

	@Override
	public int getMotionTimer() {
		return ((EntityFireMonster) getHandle()).motionTimer;
	}

	@Override
	public void setMotionTimer(int i) {
		((EntityFireMonster) getHandle()).motionTimer = i;
	}

	@Override
	public int getWideness() {
		return ((EntityFireMonster) getHandle()).wideness;
	}

	@Override
	public void setWideness(int i) {
		((EntityFireMonster) getHandle()).wideness = i;
	}

	@Override
	public int[] getOrg() {
		EntityFireMonster fire = ((EntityFireMonster) getHandle());
		int[] i = new int [] {fire.orgX, fire.orgY, fire.orgZ};
		return i;
	}

	@Override
	public void setOrg(int[] i) {
		EntityFireMonster fire = ((EntityFireMonster) getHandle());
		fire.orgX = i[0];
		fire.orgY = i[1];
		fire.orgZ = i[2];
	}
	
	@Override
	public void clearFiroBalls() {
		((EntityFireMonster) getHandle()).clearFiroBalls();
	}
	
	@Override
	public String toString() {
		return "CraftFireMonster";
	}

}
