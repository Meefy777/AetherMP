package net.mine_diver.aethermp.bukkit.craftbukkit;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.bukkit.craftbukkit.listener.PlayerListener;
import net.mine_diver.aethermp.player.PlayerManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.mod_AetherMp;

public class Core extends JavaPlugin {

	@Override
	public void onDisable() {
		mod_AetherMp.CORE.LOGGER.info("Disabling plugin...");
		mod_AetherMp.CORE.dataHandler.saveData();
		stopBossFights();
	}

	@Override
	public void onEnable() {
		mod_AetherMp.CORE.LOGGER.info("Enabling plugin...");
		PluginManager pm = getServer().getPluginManager();
		PlayerListener pListener = new PlayerListener();
		pm.registerEvent(Type.PLAYER_QUIT, pListener, Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_TELEPORT, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_PORTAL, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_INTERACT_ENTITY, pListener, Priority.Highest, this);
	}
	
	public void stopBossFights() {
		Player[] player = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < player.length; i++) {
			EntityPlayer p = ((CraftPlayer)player[i]).getHandle();
			IAetherBoss boss = PlayerManager.getCurrentBoss(p);
			if (boss == null)
				continue;
			PlayerManager.setCurrentBoss(p, null);
			boss.stopFight();
		}
	}
}
