package net.mine_diver.aethermp.bukkit.craftbukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class PlayerParachuteDeployEvent extends Event implements Cancellable {

	public PlayerParachuteDeployEvent(Player player, boolean fallFromAether) {
		super("AetherParachuteDeploy");
		this.player = player;
		this.fallFromAether = fallFromAether;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean getFallFromAether() {
		return this.fallFromAether;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	private final boolean fallFromAether;
	private final Player player;
	private boolean cancelled = false;
	private static final long serialVersionUID = 1L;
}
