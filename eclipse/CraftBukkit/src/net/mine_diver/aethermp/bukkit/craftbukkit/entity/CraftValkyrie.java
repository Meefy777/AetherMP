package net.mine_diver.aethermp.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.mine_diver.aethermp.bukkit.entity.Valkyrie;
import net.mine_diver.aethermp.entities.EntityValkyrie;

public class CraftValkyrie extends CraftDungeonMob implements Valkyrie {

	public CraftValkyrie(CraftServer server, EntityValkyrie entity) {
		super(server, entity);
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

}