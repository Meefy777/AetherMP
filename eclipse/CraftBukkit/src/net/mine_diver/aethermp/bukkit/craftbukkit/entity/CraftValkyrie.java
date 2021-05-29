package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.mine_diver.aethermp.bukkit.entity.Valkyrie;
import net.mine_diver.aethermp.entities.EntityValkyrie;

public class CraftValkyrie extends CraftDungeonMob implements Valkyrie {

	public CraftValkyrie(CraftServer server, EntityValkyrie entity) {
		super(server, entity);
	}
	
	@Override
	public CraftLivingEntity getTarget() {
		return (CraftLivingEntity) ((EntityValkyrie) getHandle()).target.getBukkitEntity();
	}

	@Override
	public void setTarget(LivingEntity entity) {
		((EntityValkyrie) getHandle()).target = ((CraftEntity) entity).getHandle();
	}

	@Override
	public int getBossHP() {
		return ((EntityValkyrie) getHandle()).getBossHP();
	}

	@Override
	public int getBossMaxHP() {
		return ((EntityValkyrie) getHandle()).getBossMaxHP();
	}

	@Override
	public boolean isCurrentBoss(Player player) {
		return ((EntityValkyrie) getHandle()).isCurrentBoss(((CraftPlayer) player).getHandle());
	}
	
	@Override
	public int getBossEntityID() {
		return ((EntityValkyrie) getHandle()).getBossEntityID();
	}

	@Override
	public String getBossTitle() {
		return ((EntityValkyrie) getHandle()).getBossTitle();
	}

	@Override
	public void stopFight() {
		((EntityValkyrie) getHandle()).stopFight();
	}

	@Override
	public boolean isBoss() {
		return ((EntityValkyrie)getHandle()).isBoss();
	}

	@Override
	public void setBoss(boolean flag) {
		((EntityValkyrie)getHandle()).setBoss(flag);
	}
	
	@Override
	public boolean isDuel() {
		return ((EntityValkyrie)getHandle()).duel;
	}

	@Override
	public void setDuel(boolean flag) {
		((EntityValkyrie)getHandle()).duel = flag;
	}
	
	@Override
	public int getChatTime() {
		return ((EntityValkyrie)getHandle()).chatTime;
	}

	@Override
	public void setChatTime(int time) {
		((EntityValkyrie)getHandle()).chatTime = time;
	}

	@Override
	public int getTeleTime() {
		return ((EntityValkyrie)getHandle()).teleTimer;
	}

	@Override
	public void setTeleTime(int time) {
		((EntityValkyrie)getHandle()).teleTimer = time;
	}
	
	@Override
	public String toString() {
		return "CraftValkyrie";
	}

	@Override
	public boolean getHasDungeon() {
		return ((EntityValkyrie)getHandle()).hasDungeon;
	}

	@Override
	public void setHasDungeon(boolean flag) {
		((EntityValkyrie)getHandle()).hasDungeon = flag;
	}

	@Override
	public int getAngerLevel() {
		return ((EntityValkyrie)getHandle()).angerLevel;
	}

	@Override
	public void setAngerLevel(int i) {
		((EntityValkyrie)getHandle()).angerLevel = i;
	}

	@Override
	public int getTimeLeft() {
		return ((EntityValkyrie)getHandle()).timeLeft;
	}

	@Override
	public void setTimeLeft(int i) {
		((EntityValkyrie)getHandle()).timeLeft = i;
	}

	@Override
	public double getLastMotionY() {
		return ((EntityValkyrie)getHandle()).lastMotionY;
	}

	@Override
	public void setLastMotionY(Double d) {
		((EntityValkyrie)getHandle()).lastMotionY = d;
	}

	@Override
	public int[] getDungeonCoords() {
		EntityValkyrie valk = ((EntityValkyrie)getHandle());
		int[] i = new int [] {valk.dungeonX, valk.dungeonY, valk.dungeonZ};
		return i;
	}

	@Override
	public void setDungeonCoords(int[] i) {
		((EntityValkyrie)getHandle()).dungeonX = i[0];
		((EntityValkyrie)getHandle()).dungeonY = i[1];
		((EntityValkyrie)getHandle()).dungeonZ = i[2];
	}

	@Override
	public double[] getSafeCoords() {
		EntityValkyrie valk = ((EntityValkyrie)getHandle());
		double[] i = new double[] {valk.safeX, valk.safeY, valk.safeZ};
		return i;
	}

	@Override
	public void setSafeCoords(double[] i) {
		((EntityValkyrie)getHandle()).safeX = i[0];
		((EntityValkyrie)getHandle()).safeY = i[1];
		((EntityValkyrie)getHandle()).safeZ = i[2];
	}

	@Override
	public int getDungeonEntranceZ() {
		return ((EntityValkyrie)getHandle()).dungeonEntranceZ;
	}

	@Override
	public void setDungeonEntranceZ(int i) {
		((EntityValkyrie)getHandle()).dungeonEntranceZ = i;
	}

}