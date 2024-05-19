package net.mine_diver.aethermp.bukkit.craftbukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class PlayerGetLoreEvent extends Event implements Cancellable {

	public PlayerGetLoreEvent(Player player) {
		super("AetherLoreGet");
		this.player = player;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	private final Player player;
	private boolean cancelled;
	private static final long serialVersionUID = 1L;
}
