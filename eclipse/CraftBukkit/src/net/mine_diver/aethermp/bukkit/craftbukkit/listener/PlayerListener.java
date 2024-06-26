package net.mine_diver.aethermp.bukkit.craftbukkit.listener;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.api.entities.IMountable;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftAechorPlant;
import net.mine_diver.aethermp.entities.EntitySwet;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.PlayerBaseAether;
import net.mine_diver.aethermp.player.PlayerManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ModLoaderMp;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.PlayerAPI;
import net.minecraft.server.mod_AetherMp;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
	
	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		EntityPlayer entityplayer = ((CraftPlayer)player).getHandle();
		IAetherBoss boss = PlayerManager.getCurrentBoss(entityplayer);
		if (boss != null) {
			if (mod_AetherMp.punishQuittingDuringFight)
				player.setHealth(0);
			if (mod_AetherMp.betterMPBossMechanics) {
				PlayerManager.setCurrentBoss(entityplayer, null);
				List<EntityPlayer> list = boss.getTargetList();
				list.remove(entityplayer);
				boss.setTargetList(list);
				if(boss.getCurrentTarget().name.equals(entityplayer.name))
					boss.findNewTarget();
				if(list.size() == 0)
					boss.stopFight();
			}
			else
				boss.stopFight();
		}
		if (entityplayer.vehicle instanceof EntitySwet) {
            EntitySwet swet = (EntitySwet)entityplayer.vehicle;
            swet.setWidth(0.8F);
            swet.setHeight(0.8F);
            swet.setRidden(false);
            swet.setPosition(swet.locX, swet.locY + 1, swet.locZ);
		}
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		EntityPlayer player = ((CraftPlayer)p).getHandle();
		Packet230ModLoader packet = new Packet230ModLoader();
		packet.packetType = 37;
		ModLoaderMp.SendPacketTo(ModLoaderMp.GetModInstance(mod_AetherMp.class), player, packet);
	}
	
	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (!mod_AetherMp.disableDungeonCommands)
			return;
		String command = event.getMessage();
		for (String allowedCommand : mod_AetherMp.CORE.dungeonAllowedCommands)
			if (command.equals(allowedCommand) || command.startsWith(allowedCommand + " "))
				return;
		Player player = event.getPlayer();
		World world = player.getWorld();
		Location location = event.getPlayer().getLocation();
		int Ox = location.getBlockX();
		int Oz = location.getBlockZ();
		int radius = mod_AetherMp.commandCancellationDistance;
		for (int x = Ox - radius; x < Ox + radius; x++)
			for (int y = 0; y < 128; y++)
				for (int z = Oz - radius; z < Oz + radius; z++) {
					int blockId = world.getBlockAt(x, y, z).getTypeId();
					if (blockId == BlockManager.LockedDungeonStone.id || blockId == BlockManager.LockedLightDungeonStone.id) {
						event.setCancelled(true);
						player.sendMessage("It seems like my magic doesn't work near a locked dungeon!");
						return;
					}
				}
	}
	
	@Override
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if (player instanceof CraftPlayer) {
			EntityPlayer entityplayer = ((CraftPlayer)player).getHandle();
			IAetherBoss boss = PlayerManager.getCurrentBoss(entityplayer);
			if (boss != null) {
				if (mod_AetherMp.preventTeleportDuringFight)
					event.setCancelled(true);
				if (mod_AetherMp.punishTeleportDuringFight)
					player.setHealth(0);
				if (!event.isCancelled()) {
					if (mod_AetherMp.betterMPBossMechanics) {
						PlayerManager.setCurrentBoss(entityplayer, null);
						List<EntityPlayer> list = boss.getTargetList();
						list.remove(entityplayer);
						boss.setTargetList(list);
						if(boss.getCurrentTarget().name.equals(entityplayer.name))
							boss.findNewTarget();
						if(list.size() == 0)
							boss.stopFight();
					}
					else
						boss.stopFight();
				}
			}
			
			if (!event.isCancelled() && entityplayer.vehicle instanceof IMountable && ((IMountable)entityplayer.vehicle).canTeleport(event)) {
				float deltaX = (float) (entityplayer.locX - entityplayer.vehicle.locX);
				float deltaY = (float) entityplayer.vehicle.height;
				float deltaZ = (float) (entityplayer.locZ - entityplayer.vehicle.locZ);
				
				entityplayer.vehicle.setPosition(event.getTo().getX() - deltaX, event.getTo().getY() - deltaY, event.getTo().getZ() - deltaZ);
				entityplayer.vehicle.positionChanged = true;
				PacketManager.sendMountTeleport(event, deltaX, deltaY, deltaZ);
			}
		}
	}
	
	@Override
	public void onPlayerPortal(PlayerPortalEvent event) {
		onPlayerTeleport(event);
	}
	
	@Override
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof CraftAechorPlant))
			return;
		Player p = event.getPlayer();
		EntityPlayer player = ((CraftPlayer) p).getHandle();
		PlayerBaseAether playerBase = (PlayerBaseAether)PlayerAPI.getPlayerBase(player, PlayerBaseAether.class);
		playerBase.isLookingAtAechor = true;
	}
	
}
