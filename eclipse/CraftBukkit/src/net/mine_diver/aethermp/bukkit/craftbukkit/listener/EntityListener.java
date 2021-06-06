package net.mine_diver.aethermp.bukkit.craftbukkit.listener;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.api.entities.IAetherBoss.BossType;
import net.mine_diver.aethermp.entities.EntityFireMonster;
import net.mine_diver.aethermp.player.PlayerManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

public class EntityListener extends org.bukkit.event.entity.EntityListener {

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		Entity ent = event.getEntity();
		if (!(ent instanceof Player) || !mod_AetherMp.betterMPBossMechanics)
			return;
		EntityPlayer p = ((CraftPlayer) ent).getHandle();
		IAetherBoss boss = PlayerManager.getCurrentBoss(p);
		if (boss == null)
			return;
		if (boss.getBossType() == BossType.GOLD) {
			EntityFireMonster monster = (EntityFireMonster) ((WorldServer)p.world).getEntity(boss.getBossEntityID());
			if (monster.targetfire instanceof EntityPlayer && !((EntityPlayer) monster.targetfire).name.equals(p.name)) {
				monster.targetList.remove(p);
				PlayerManager.setCurrentBoss(p, null);
				((Player)ent).sendMessage("\247cSuch is the fate of a being who opposes the might of the sun.");
			}
		}
	}

}
