package net.mine_diver.aethermp.bukkit.craftbukkit.listener;

import java.util.List;

import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.api.entities.IAetherBoss.BossType;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftSlider;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftValkyrie;
import net.mine_diver.aethermp.entities.EntitySwet;
import net.mine_diver.aethermp.player.PlayerManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.mod_AetherMp;

public class EntityListener extends org.bukkit.event.entity.EntityListener {

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		Entity ent = event.getEntity();
		net.minecraft.server.Entity nms = ((CraftEntity)ent).getHandle();
		if (nms.vehicle instanceof EntitySwet) {
            EntitySwet swet = (EntitySwet)nms.vehicle;
            swet.setWidth(0.8F);
            swet.setHeight(0.8F);
            swet.setRidden(false);
            swet.setPosition(swet.locX, swet.locY + 1, swet.locZ);
		}
		if (!(ent instanceof Player) || !mod_AetherMp.betterMPBossMechanics)
			return;
		Player player = (Player) ent;
		EntityPlayer p = ((CraftPlayer) player).getHandle();
		
		
		
		IAetherBoss boss = PlayerManager.getCurrentBoss(p);
		if (boss == null)
			return;
		if(!boss.getCurrentTarget().name.equals(p.name) || (bossClear(event) && boss.getBossType() != BossType.GOLD)) {
			List<EntityPlayer> list = boss.getTargetList();
			list.remove(p);
			boss.setTargetList(list);
			PlayerManager.setCurrentBoss(p, null);
			if (boss.getBossType() == BossType.GOLD) 
				player.sendMessage("\247cSuch is the fate of a being who opposes the might of the sun.");
			else if (boss.getBossType() == BossType.SILVER)
				player.sendMessage("As expected of a human.");
			
			if(boss.getCurrentTarget().name.equals(p.name) && boss.getTargetList().size() > 0)
				boss.findNewTarget();
		}
	}
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (mod_AetherMp.bedFix && event.getSpawnReason() == SpawnReason.BED && ((CraftWorld)event.getEntity().getWorld()).getHandle().dimension == mod_AetherMp.idDimensionAether)
			event.setCancelled(true);
	}
	
	public boolean bossClear(EntityDeathEvent event) {
		if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent))
			return false;
		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event.getEntity().getLastDamageCause();
		Entity ent = e.getDamager();
		if (ent instanceof CraftSlider || ent instanceof CraftValkyrie)
			return false;
		return true;
	}
	
}
