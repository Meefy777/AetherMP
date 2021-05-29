package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.mine_diver.aethermp.bukkit.entity.Slider;
import net.mine_diver.aethermp.entities.EntitySlider;

public class CraftSlider extends CraftFlyingAether implements Slider {

	public CraftSlider(CraftServer server, EntitySlider entity) {
		super(server, entity);
	}

	@Override
	public LivingEntity getTarget() {
		return (LivingEntity) ((EntitySlider) getHandle()).target.getBukkitEntity();
	}

	@Override
	public void setTarget(LivingEntity entity) {
		((EntitySlider) getHandle()).target = ((CraftEntity) entity).getHandle();
	}

	@Override
	public int getBossHP() {
		return ((EntitySlider) getHandle()).getBossHP();
	}

	@Override
	public int getBossMaxHP() {
		return ((EntitySlider) getHandle()).getBossMaxHP();
	}

	@Override
	public boolean isCurrentBoss(Player player) {
		return ((EntitySlider) getHandle()).isCurrentBoss(((CraftPlayer) player).getHandle());
	}

	@Override
	public int getBossEntityID() {
		return ((EntitySlider) getHandle()).getBossEntityID();
	}

	@Override
	public String getBossTitle() {
		return ((EntitySlider) getHandle()).getBossTitle();
	}
	
	@Override
	public void stopFight() {
		((EntitySlider) getHandle()).stopFight();
	}

	@Override
	public void setMoveTimer(int i) {
		((EntitySlider) getHandle()).moveTimer = i;
	}

	@Override
	public int getMoveTimer() {
		return ((EntitySlider) getHandle()).moveTimer;
	}

	@Override
	public void setDennis(int i) {
		((EntitySlider) getHandle()).dennis = i;
	}

	@Override
	public int getDennis() {
		return ((EntitySlider) getHandle()).dennis;
	}

	@Override
	public void setRennis(int i) {
		((EntitySlider) getHandle()).rennis = i;
	}

	@Override
	public int getRennis() {
		return ((EntitySlider) getHandle()).rennis;
	}

	@Override
	public void setChatTime(int i) {
		((EntitySlider) getHandle()).chatTime = i;
	}

	@Override
	public int getChatTime() {
		return ((EntitySlider) getHandle()).chatTime;
	}

	@Override
	public void setAwake(boolean flag) {
		((EntitySlider) getHandle()).awake = flag;
	}

	@Override
	public boolean getAwake() {
		return ((EntitySlider) getHandle()).awake;
	}

	@Override
	public void setGotMovement(boolean flag) {
		((EntitySlider) getHandle()).gotMovement = flag;
	}

	@Override
	public boolean getGotMovement() {
		return ((EntitySlider) getHandle()).gotMovement;
	}

	@Override
	public void setCrushed(boolean flag) {
		((EntitySlider) getHandle()).crushed = flag;
	}

	@Override
	public boolean getCrushed() {
		return ((EntitySlider) getHandle()).crushed;
	}

	@Override
	public void setSpeedy(float f) {
		((EntitySlider) getHandle()).speedy = f;
	}

	@Override
	public float getSpeedy() {
		return ((EntitySlider) getHandle()).speedy;
	}

	@Override
	public void setHarvey(float f) {
		((EntitySlider) getHandle()).harvey = f;
	}

	@Override
	public float getHarvey() {
		return ((EntitySlider) getHandle()).harvey;
	}

	@Override
	public void setDirection(int i) {
		((EntitySlider) getHandle()).direction = i;
	}

	@Override
	public int getDirection() {
		return ((EntitySlider) getHandle()).direction;
	}

	@Override
	public void setDungeonCoords(int[] i) {
		((EntitySlider) getHandle()).dungeonX = i[0];
		((EntitySlider) getHandle()).dungeonY = i[1];
		((EntitySlider) getHandle()).dungeonZ = i[2];
	}

	@Override
	public int[] getDungeonCoords() {
		EntitySlider slider = ((EntitySlider) getHandle());
		int i[] = new int [] {slider.dungeonX, slider.dungeonY, slider.dungeonZ};
		return i;
	}
	
	@Override
	public String toString() {
		return "CraftSlider";
	}
}
