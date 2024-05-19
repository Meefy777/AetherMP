package net.mine_diver.aethermp.entities;

import java.util.List;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.mine_diver.aethermp.bukkit.craftbukkit.event.AbstractAetherPoisonEvent.PoisonSource;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityPoisonDamageEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.entity.EntityPoisonEvent;
import net.mine_diver.aethermp.bukkit.craftbukkit.event.CraftAetherEventFactory;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.AetherPoison;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class EntityDartPoison extends EntityDartGolden {

    public EntityDartPoison(World world) {
        super(world);
    }

    public EntityDartPoison(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartPoison(World world, EntityLiving ent) {
        super(world, ent);
    }
    
    @Override
    public void b() {
        super.b();
        item = new ItemStack(ItemManager.Dart, 1, 1);
        dmg = 2;
    }
    
    @Override
    public boolean onHitTarget(Entity entity) {
        if(!(entity instanceof EntityLiving) || !AetherPoison.canPoison(entity))
            return super.onHitTarget(entity);
        EntityLiving ent = (EntityLiving)entity;
        
        //Custom event here
        if(ent instanceof EntityPlayer && !CraftAetherEventFactory.callPoisonEvent(500, this.shooter, ent, this, PoisonSource.POISON_DART).isCancelled()) {
        	PlayerManager.afflictPoison((EntityPlayer) ent);
            return super.onHitTarget(entity);
        }
        @SuppressWarnings("unchecked")
		List<Entity> list = world.b(this, ent.boundingBox.b(2D, 2D, 2D));
        for(int i = 0; i < list.size(); i++) {
            Entity lr2 = list.get(i);
            if(!(lr2 instanceof EntityDartPoison))
                continue;
            EntityDartPoison arr = (EntityDartPoison)lr2;
            if(arr.victim == ent) {
            	EntityPoisonEvent event = CraftAetherEventFactory.callPoisonEvent(500, this.shooter, arr.victim, arr, PoisonSource.POISON_DART);
            	if (!event.isCancelled()) {
            		arr.poisonTime = 50;
                	arr.dead = false;
            	}
            	EntityDamageByEntityEvent event2 = new EntityDamageByEntityEvent(this.getBukkitEntity(), ent.getBukkitEntity(), DamageCause.PROJECTILE, dmg);
            	this.getBukkitEntity().getServer().getPluginManager().callEvent(event2);
            	if (!event2.isCancelled())
            		ent.damageEntity(shooter, event2.getDamage());
                die();
                return false;
            }
        }

        victim = ent;
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(this.getBukkitEntity(), this.victim.getBukkitEntity(), DamageCause.PROJECTILE, dmg);
        this.getBukkitEntity().getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled())
        	ent.damageEntity(shooter, event.getDamage());
        
        EntityPoisonEvent event2 = CraftAetherEventFactory.callPoisonEvent(500, this.shooter, this.victim, this, PoisonSource.POISON_DART);
        if (!event2.isCancelled())
        	poisonTime = 500;
        return false;
    }
    
    @Override
    public void m_() {
        super.m_();
        if(dead)
            return;
        if(victim != null) {
            if(victim.dead || poisonTime == 0) {
                die();
                return;
            }
            dead = false;
            inGround = false;
            locX = victim.locX;
            locY = victim.boundingBox.b + (double)victim.height * 0.80000000000000004D;
            locZ = victim.locZ;
            AetherPoison.distractEntity(victim, CraftAetherEventFactory.callPoisonDistractEvent(poisonTime, this.shooter, this.victim, this, PoisonSource.POISON_DART));
            poisonTime--;
            EntityPoisonDamageEvent event2 = CraftAetherEventFactory.callPoisonDamageEvent(1, poisonTime, this.shooter, victim, this, PoisonSource.POISON_DART);
            if(poisonTime % 50 == 0 && !event2.isCancelled()) {
            	EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(this.getBukkitEntity(), this.victim.getBukkitEntity(), DamageCause.PROJECTILE, 1);
                this.getBukkitEntity().getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled())
                	victim.damageEntity(shooter, event2.getDamage());
            }
        }
    }

    public EntityLiving victim;
    public int poisonTime;
}
