package net.mine_diver.aethermp.bukkit.craftbukkit;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.bukkit.craftbukkit.listener.EntityListener;
import net.mine_diver.aethermp.bukkit.craftbukkit.listener.PlayerListener;
import net.mine_diver.aethermp.entities.EntityFlyingCow;
import net.mine_diver.aethermp.entities.EntityMoa;
import net.mine_diver.aethermp.entities.EntityPhyg;
import net.mine_diver.aethermp.entities.EntitySwet;
import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.MoaColour;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.World;
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
		EntityListener eListener = new EntityListener();
		pm.registerEvent(Type.PLAYER_QUIT, pListener, Priority.Monitor, this);
		pm.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_TELEPORT, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_PORTAL, pListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_INTERACT_ENTITY, pListener, Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DEATH, eListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_JOIN, pListener, Priority.Monitor, this);
		pm.registerEvent(Type.CREATURE_SPAWN, eListener, Priority.Normal, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp() || !(sender instanceof Player)) {
			sender.sendMessage(TITLE + "You do not have permission to do this.");
			return true;
		} 
		if (args.length == 0) {
			listOptions(sender);
			return true;
		}
		else if (args[0].equalsIgnoreCase("summon")) {
			if (args.length == 1) {
				sender.sendMessage(TITLE + "Usage: /AetherMP Summon EntityType (Metadata)");
				listEntities(sender);
				return true;
			} else {
				Player player = (Player) sender;
				for (SummonableAetherMobs mob : mobs) {
					if (mob.name.equalsIgnoreCase(args[1]) || String.valueOf(mob.ordinal()).equals(args[1])) {
						String[] meta = Arrays.copyOfRange(args, 2, args.length);
						if (mob.validCommand(meta)) {
							Optional<Location> loc = getSummonSpot(player);
							if (!loc.isPresent()) {
								sender.sendMessage(TITLE + "Could not find a valid spawn location.");
								return true;
							}
							Location spawnLoc = loc.get(); 
							World world = ((CraftWorld)player.getWorld()).getHandle();
							mob.summonEntity(world, spawnLoc, meta);
							sender.sendMessage(TITLE + "Entity summoned.");
							return true;
						}
					}
				}
				sender.sendMessage(TITLE + "Invalid format.");
				return true;
			}
		} 
		else 
			sender.sendMessage(TITLE + "Unknown command.");
		
		
		return true;
	}
	
	private void listOptions(CommandSender sender) {
		sender.sendMessage(TITLE + "1. Summon - For More info type /AetherMP Summon");
	}
	
	private void listEntities(CommandSender sender) {
		sender.sendMessage(TITLE + "Entity List: ");
		Arrays.stream(mobs).forEach(e -> sender.sendMessage(TITLE + ChatColor.AQUA + e.name + ": " + ChatColor.BLUE + e.usage));
	}
	
	public void stopBossFights() {
		Player[] player = getServer().getOnlinePlayers();
		for (int i = 0; i < player.length; i++) {
			EntityPlayer p = ((CraftPlayer)player[i]).getHandle();
			IAetherBoss boss = PlayerManager.getCurrentBoss(p);
			if (boss == null)
				continue;
			PlayerManager.setCurrentBoss(p, null);
			boss.stopFight();
		}
	}
	
	private static Optional<Location> getSummonSpot(Player player) {
		Location tp = new Location(player.getWorld(), player.getTargetBlock(null, 150).getX(), player.getTargetBlock(null, 150).getY() + 1, player.getTargetBlock(null, 150).getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
		double i = tp.distance(player.getLocation());
		int j = player.getWorld().getBlockTypeIdAt(tp.getBlockX(), tp.getBlockY(), tp.getBlockZ());
		int z = player.getWorld().getBlockTypeIdAt(tp.getBlockX(), tp.getBlockY() + 1, tp.getBlockZ());
		int a = player.getWorld().getBlockTypeIdAt(tp.getBlockX(), tp.getBlockY() - 1, tp.getBlockZ());
		if (i >= 1 && j == 0 && z == 0 && a != 0)
			return Optional.of(tp);
		return Optional.empty();
	}
	
	public enum SummonableAetherMobs {
		FLYING_COW("/AetherMP summon [FlyingCow/0]", "FlyingCow") {
			@Override
			boolean validCommand(String[] meta) {
				return meta.length == 0;
			}

			@Override
			void summonEntity(World world, Location loc, String[] meta) {
				EntityFlyingCow cow = new EntityFlyingCow(world);
				cow.setSaddled(true);
				cow.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				world.addEntity(cow);
			}
		},
		PHYG("/AetherMP summon [Phyg/1]", "Phyg") {
			@Override
			boolean validCommand(String[] meta) {
				return meta.length == 0;
			}

			@Override
			void summonEntity(World world, Location loc, String[] meta) {
				EntityPhyg phyg = new EntityPhyg(world);
				phyg.setSaddled(true);
				phyg.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				world.addEntity(phyg);
			}
		},
		MOA("/AetherMP summon [Moa/2] [Blue/White/Black]", "Moa") {
			@Override
			boolean validCommand(String[] meta) {
				return meta.length == 1 && Arrays.stream(colours).filter(p -> p.name().toLowerCase().equalsIgnoreCase(meta[0])).findFirst().isPresent();
			}

			@Override
			void summonEntity(World world, Location loc, String[] meta) {
				EntityMoa moa = new EntityMoa(world, false, false, true, MoaColour.getColour(Arrays.stream(colours).filter(p -> p.name().toLowerCase().equalsIgnoreCase(meta[0])).findFirst().get().ordinal()));
				moa.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				world.addEntity(moa);	
			}
		},
		SWET("/AetherMP summon [Swet/3]", "Swet") {

			@Override
			boolean validCommand(String[] meta) {
				return meta.length == 0;
			}

			@Override
			void summonEntity(World world, Location loc, String[] meta) {
				EntitySwet swet = new EntitySwet(world);
				swet.setTamed(true);
				swet.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				world.addEntity(swet);
			}
		};
		
		SummonableAetherMobs(String usage, String name) {
			this.usage = usage;
			this.name = name;
		}
		
		public final String usage;
		public final String name;
		
		abstract boolean validCommand(String[] meta);
		abstract void summonEntity(World world, Location loc, String[] meta);
	}
	
	private enum MoaColours {
		Blue,
		White,
		Black;
	}
	
	private static final String TITLE = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "AetherMP" + ChatColor.DARK_AQUA + "] ";
	private static final SummonableAetherMobs[] mobs = SummonableAetherMobs.values();
	private static final MoaColours[] colours = MoaColours.values();

}
