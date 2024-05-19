package net.mine_diver.aethermp.api.entities;

import org.bukkit.event.player.PlayerTeleportEvent;

public interface IMountable {
	
	default boolean canTeleport(PlayerTeleportEvent event) {return true;}

}
