package net.mine_diver.aethermp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.entity.EntityDeathEvent;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityValkyrie.java

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.Achievements;
import net.mine_diver.aethermp.util.NameGen;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagDouble;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            EntityDungeonMob, IAetherBoss, NameGen, World, 
//            Entity, Block, EntityPlayer, ItemStack, 
//            AetherItems, Item, ModLoader, GuiIngame, 
//            EntityHomeShot, EntityLiving, mod_Aether, MathHelper, 
//            AxisAlignedBB, NBTTagCompound, NBTTagList, NBTTagDouble, 
//            AetherBlocks, AetherAchievements


public class EntityValkyrie extends EntityDungeonMob
    implements IAetherBoss
{

    public EntityValkyrie(World world)
    {
        this(world, 0, 0, 0, false);
    }

    public EntityValkyrie(World world, double x, double y, double z, 
            boolean flag)
    {
        super(world);
        b(0.8F, 1.6F);
        setName(NameGen.gen());
        texture = "/aether/mobs/valkyrie.png";
        if(flag)
        {
            texture = "/aether/mobs/valkyrie2.png";
            health = 500;
            setBoss(true);
        } else
        {
            health = 50;
        }
        teleTimer = random.nextInt(250);
        aE = 0.5F;
        timeLeft = 1200;
        attackStrength = 7;
        safeX = locX = x;
        safeY = locY = y;
        safeZ = locZ = z;
        hasDungeon = false;
    }
    
    @Override
    protected void b() {
    	datawatcher.a(19, Byte.valueOf((byte) 0)); //is boss?
    	datawatcher.a(16, Integer.valueOf((int) 0)); //hp
    	datawatcher.a(17, String.valueOf("")); //name
    }
    
    public int getHealth() {
    	return Integer.valueOf(datawatcher.b(16));
    }
    
    public void setHealth(int i) {
    	datawatcher.watch(16, i);
    }
    
    public String getName() {
    	return datawatcher.c(17);
    }
    
    public void setName(String s) {
    	datawatcher.watch(17, String.valueOf(s));
    }

    public boolean isBoss() {
    	return (datawatcher.a(19) & 1) != 0;
    }
    
    public void setBoss(boolean flag) {
        if(flag)
        {
            datawatcher.watch(19, Byte.valueOf((byte)1));
        } else
        {
            datawatcher.watch(19, Byte.valueOf((byte)0));
        }
    }
    
    @Override
    public void a(float f1)
    {
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void m_()
    {	
    	setHealth(health);
        lastMotionY = motY;
        super.m_();
        if (target == null && !isBoss() && lastTarget != null) {
        	EntityHuman entityplayer = world.a(lastTarget);
        	if (entityplayer != null) {
        		boolean isMad = false;
	    		List<Entity> list = world.b(this, this.boundingBox.b(32D, 32D, 32D));
	            for(int j = 0; j < list.size(); j++) {
	                Entity entity1 = list.get(j);
	                if(entity1 instanceof EntityPlayer && ((EntityPlayer)entity1).name.equals(lastTarget)) {
	                	becomeAngryAt(entity1);
	                	isMad = true;
	                }
	            }
	            if(!isMad)
	            	lastTarget = null;
        	}
        }
        
        if(!onGround && target != null && lastMotionY >= 0.0D && motY < 0.0D && f(target) <= 16F && e(target))
        {
            double a = target.locX - locX;
            double b = target.locZ - locZ;
            double angle = Math.atan2(a, b);
            motX = Math.sin(angle) * 0.25D;
            motZ = Math.cos(angle) * 0.25D;
        }
        if(!onGround && !p() && Math.abs(motY - lastMotionY) > 0.070000000000000007D && Math.abs(motY - lastMotionY) < 0.089999999999999997D)
        {
            motY += 0.054999999701976776D;
            if(motY < -0.27500000596046448D)
            {
                motY = -0.27500000596046448D;
            }
        }
        aE = target != null ? 1.0F : 0.5F;
        if(world.spawnMonsters <= 0 && (target != null || angerLevel > 0))
        {
            angerLevel = 0;
            target = null;
            lastTarget = null;
        }
        if(isSwinging)
        {
            Z += 0.15F;
            aa += 0.15F;
            if(Z > 1.0F || aa > 1.0F)
            {
                isSwinging = false;
                Z = 0.0F;
                aa = 0.0F;
            }
        }
        if(!onGround)
        {
            sinage += 0.75F;
        } else
        {
            sinage += 0.15F;
        }
        if(sinage > 6.283186F)
        {
            sinage -= 6.283186F;
        }
        if(target != null && (target instanceof EntityLiving)) {
            EntityLiving e1 = (EntityLiving)target;
            if(e1.health <= 0) {
            	stopFight();
                return;
            }
        } else if((target == null || target.dead) && (targetList.size() == 0 || !mod_AetherMp.betterMPBossMechanics)) {
         	stopFight();
            return;
        }
        
     	if (target != null && target.dead)
     		lastTarget = null;
    }
    
    public void teleport(double x, double y, double z, int rad)
    {
        int a = random.nextInt(rad + 1);
        int b = random.nextInt(rad / 2);
        int c = rad - a;
        a *= random.nextInt(2) * 2 - 1;
        b *= random.nextInt(2) * 2 - 1;
        c *= random.nextInt(2) * 2 - 1;
        x += a;
        y += b;
        z += c;
        int newX = (int)Math.floor(x - 0.5D);
        int newY = (int)Math.floor(y - 0.5D);
        int newZ = (int)Math.floor(z - 0.5D);
        boolean flag = false;
        for(int q = 0; q < 32 && !flag; q++)
        {
            int i = newX + (random.nextInt(rad / 2) - random.nextInt(rad / 2));
            int j = newY + (random.nextInt(rad / 2) - random.nextInt(rad / 2));
            int k = newZ + (random.nextInt(rad / 2) - random.nextInt(rad / 2));
            if(j <= 124 && j >= 5 && isAirySpace(i, j, k) && isAirySpace(i, j + 1, k) && !isAirySpace(i, j - 1, k) && (!hasDungeon || i > dungeonX && i < dungeonX + 20 && j > dungeonY && j < dungeonY + 12 && k > dungeonZ && k < dungeonZ + 20))
            {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if(!flag)
        {
            teleFail();
        } else
        {
            S();
            setPosition((double)newX + 0.5D, (double)newY + 0.5D, (double)newZ + 0.5D);
            motX = 0.0D;
            motY = 0.0D;
            motZ = 0.0D;
            aA = 0.0F;
            az = 0.0F;
            aC = false;
            pitch = 0.0F;
            yaw = 0.0F;
            setPathEntity(null);
            K = random.nextFloat() * 360F;
            S();
            teleTimer = random.nextInt(40);
        }
    }
    
    @Override
    public void S() {
        for (int i = 0; i < 20; ++i) {
            final double d0 = this.random.nextGaussian() * 0.02;
            final double d2 = this.random.nextGaussian() * 0.02;
            final double d3 = this.random.nextGaussian() * 0.02;
            final double d4 = 10.0;
            PacketManager.spawnParticle("explode", (float) (this.locX + this.random.nextFloat() * this.length * 2.0f - this.length - d0 * d4), (float) (this.locY + this.random.nextFloat() * this.width - d2 * d4), (float) (this.locZ + this.random.nextFloat() * this.length * 2.0f - this.length - d3 * d4), (float) d0, (float) d2, (float) d3, ((WorldServer) world).dimension, locX, locY, locZ);
        }
    }

    public boolean isAirySpace(int x, int y, int z)
    {
        int p = world.getTypeId(x, y, z);
        return p == 0 || Block.byId[p].e(world, x, y, z) == null;
    }

    @Override
    public boolean h_()
    {
        return !isBoss();
    }

    @Override
    public boolean a(EntityHuman entityplayer)
    {
        a(entityplayer, 180F, 180F);
        if(!isBoss())
        {
            if(timeLeft >= 1200)
            {
                ItemStack itemstack = entityplayer.G();
                if(itemstack != null && itemstack.id == ItemManager.VictoryMedal.id && itemstack.count >= 0)
                {
                    if(itemstack.count >= 10)
                    {
                        chatItUp("Umm... that's a nice pile of medallions you have there...", entityplayer);
                    } else
                    if(itemstack.count >= 5)
                    {
                        chatItUp("That's pretty impressive, but you won't defeat me.", entityplayer);
                    } else
                    {
                        chatItUp("You think you're a tough guy, eh? Well, bring it on!", entityplayer);
                    }
                } else
                {
                    int pokey = random.nextInt(3);
                    if(pokey == 2)
                    {
                        chatItUp("What's that? You want to fight? Aww, what a cute little human.", entityplayer);
                    } else
                    if(pokey == 1)
                    {
                        chatItUp("You're not thinking of fighting a big, strong Valkyrie are you?", entityplayer);
                    } else
                    {
                        chatItUp("I don't think you should bother me, you could get really hurt.", entityplayer);
                    }
                }
            }
        } else
        if(duel)
        {
            chatItToAll("If you wish to challenge me, strike at any time.", entityplayer);
        } else
        if(!duel)
        {
            ItemStack itemstack = entityplayer.G();
            if(itemstack != null && itemstack.id == ItemManager.VictoryMedal.id && itemstack.count >= 10)
            {
                itemstack.count -= 10;
                if(itemstack.count <= 0)
                {
                    itemstack.a(entityplayer);
                    entityplayer.G();
                    chatTime = 0;
                    chatItToAll("Very well, attack me when you wish to begin.", entityplayer);
                    duel = true;
                }
            } else
            {
                chatItUp("Show me 10 victory medals, and I will fight you.", entityplayer);
            }
        }
        return true;
    }

    private void chatItUp(String s, EntityHuman player)
    {
        if(chatTime <= 0)
        {
            player.a(s);
            chatTime = 60;
        }
    }

    public void makeHomeShot(int shots, EntityLiving ep)
    {
        for(int i = 0; i < shots; i++)
        {
            EntityHomeShot e1 = new EntityHomeShot(world, locX - motX / 2D, locY, locZ - motZ / 2D, ep);
            world.addEntity(e1);
        }

    }

    @Override
    protected void q()
    {
    	List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();
        if(isBoss())
        {
        	loot.add(new org.bukkit.inventory.ItemStack(ItemManager.Key.id, 1, (short) 1));
        	loot.add(new org.bukkit.inventory.ItemStack(Item.GOLD_SWORD.id, 1));
            a(new ItemStack(ItemManager.Key, 1, 1), 0.0F);
            b(Item.GOLD_SWORD.id, 1);
        } else
        {
        	loot.add(new org.bukkit.inventory.ItemStack(ItemManager.VictoryMedal.id, 1));
            b(ItemManager.VictoryMedal.id, 1);
        }
        EntityDeathEvent event = new EntityDeathEvent(this.getBukkitEntity(), loot);
        world.getServer().getPluginManager().callEvent(event);
    }
    @Override
    public void c_()
    {	    	
    	super.c_();
		teleTimer++;
        if(teleTimer >= 450)
        {
            if(target != null)
            {
                if(isBoss() && onGround && random.nextInt(2) == 0 && target != null && (target instanceof EntityLiving))
                {
                    makeHomeShot(1, (EntityLiving)target);
                    teleTimer = -100;
                } else
                {
                    teleport(target.locX, target.locY, target.locZ, 7);
                }
            } else
            if(!onGround || isBoss())
            {
                teleport(safeX, safeY, safeZ, 6);
            } else
            {
                teleport(locX, locY, locZ, 12 + random.nextInt(12));
            }
        } else
        if(teleTimer < 446 && (locY <= 0.0D || locY <= safeY - 16D))
        {
            teleTimer = 446;
        } else
        if(teleTimer % 5 == 0 && target != null && !e(target))
        {
            teleTimer += 100;
        }
        if(onGround && teleTimer % 10 == 0 && !isBoss())
        {
            safeX = locX;
            safeY = locY;
            safeZ = locZ;
        }
        if(target != null && target.dead)
        {
        	target = null;
            if(isBoss())
            {
            	if(targetList.size() == 0 || !mod_AetherMp.betterMPBossMechanics)
            		unlockDoor();
                PlayerManager.setCurrentBoss((EntityPlayer) target, null);
            }
            angerLevel = 0;
        }
        if(chatTime > 0)
        {
            chatTime--;
        }
    }

    public void swingArm()
    {
    	Packet230ModLoader packet = new Packet230ModLoader();
    	packet.packetType = 32;
    	packet.dataInt = new int[] {id};
    	PacketManager.sendToViewDistance(packet, ((WorldServer) world).dimension, locX, locY, locZ);
        if(!isSwinging)
        {
            isSwinging = true;
            Z = 0.0F;
            aa = 0.0F;
        }
    }

    public void teleFail()
    {
        teleTimer -= random.nextInt(40) + 40;
        if(locY <= 0.0D)
        {
            teleTimer = 446;
        }
    }

    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor(locX);
        int j = MathHelper.floor(boundingBox.b);
        int k = MathHelper.floor(locZ);
        return world.k(i, j, k) > 8 && world.containsEntity(boundingBox) && world.getEntities(this, boundingBox).size() == 0 && !world.b(boundingBox);
    }

    @Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("Anger", (short)angerLevel);
        nbttagcompound.a("TeleTimer", (short)teleTimer);
        nbttagcompound.a("TimeLeft", (short)timeLeft);
        nbttagcompound.a("Boss", isBoss());
        nbttagcompound.a("Duel", duel);
        nbttagcompound.a("DungeonX", dungeonX);
        nbttagcompound.a("DungeonY", dungeonY);
        nbttagcompound.a("DungeonZ", dungeonZ);
        nbttagcompound.a("DungeonEntranceZ", dungeonEntranceZ);
        nbttagcompound.a("SafePos", a(new double[] {
            safeX, safeY, safeZ
        }));
        boolean current = target instanceof EntityPlayer ? isCurrentBoss((EntityPlayer) target) : false;
        nbttagcompound.a("IsCurrentBoss", current);
        if (current)
        	nbttagcompound.setString("TargetNickname", ((EntityPlayer)target).name);
        if(isBoss())
        	nbttagcompound.setString("BossName", getName());
        if (lastTarget != null)
        	nbttagcompound.setString("LastTarget", lastTarget);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        angerLevel = nbttagcompound.d("Anger");
        teleTimer = nbttagcompound.d("TeleTimer");
        timeLeft = nbttagcompound.d("TimeLeft");
        duel = nbttagcompound.m("Duel");
        setBoss(nbttagcompound.m("Boss"));
        dungeonX = nbttagcompound.e("DungeonX");
        dungeonY = nbttagcompound.e("DungeonY");
        dungeonZ = nbttagcompound.e("DungeonZ");
        dungeonEntranceZ = nbttagcompound.e("DungeonEntranceZ");
        if(isBoss())
        {
            texture = "/aether/mobs/valkyrie2.png";
        }
        NBTTagList nbttaglist = nbttagcompound.l("SafePos");
        safeX = ((NBTTagDouble)nbttaglist.a(0)).a;
        safeY = ((NBTTagDouble)nbttaglist.a(1)).a;
        safeZ = ((NBTTagDouble)nbttaglist.a(2)).a;
        if(nbttagcompound.m("IsCurrentBoss")) {
        	EntityPlayer player = ((WorldServer)world).server.serverConfigurationManager.i(nbttagcompound.getString("TargetNickname"));
        	if (player != null) {
	            target = player;
	            PlayerManager.setCurrentBoss(player, this);
        	}
        }
        if(isBoss())
        	setName(nbttagcompound.getString("BossName"));
        lastTarget = nbttagcompound.getString("LastTarget");
    }

    @Override
    protected Entity findTarget()
    {
        if(world.spawnMonsters <= 0 || isBoss() && !duel || angerLevel <= 0)
        {
            return null;
        } else
        {
            return super.findTarget();
        }
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        if((entity instanceof EntityPlayer) && world.spawnMonsters > 0)
        {
            if(isBoss() && (!duel || world.spawnMonsters <= 0))
            {
                S();
                int pokey = random.nextInt(2);
                if(pokey == 2)
                {
                    chatItUp("Sorry, I don't fight with weaklings.", (EntityHuman) entity);
                } else
                {
                    chatItUp("Try defeating some weaker valkyries first.", (EntityHuman) entity);
                }
                return false;
            }
            if(isBoss())
            {
                if(target == null)
                {
                	if(!mod_AetherMp.betterMPBossMechanics)
                		PlayerManager.setCurrentBoss((EntityPlayer) entity, this);
                	else
                		setBossForPlayers();
                    chatTime = 0;
                    chatItToAll("This will be your final battle!", (EntityHuman) entity);
                } else
                {
                    teleTimer += 60;
                }
            } else
            if(target == null)
            {
                chatTime = 0;
                int pokey = random.nextInt(3);
                if(pokey == 2)
                {
                    chatItUp("I'm not going easy on you!", (EntityHuman) entity);
                } else
                if(pokey == 1)
                {
                    chatItUp("You're gonna regret that!", (EntityHuman) entity);
                } else
                {
                    chatItUp("Now you're in for it!", (EntityHuman) entity);
                }
            } else
            {
                teleTimer -= 10;
            }
            becomeAngryAt(entity);
        } else
        {
            teleport(locX, locY, locZ, 8);
            fireTicks = 0;
            return false;
        }
        
       boolean flag;
       if(target == null)
        	flag = super.damageEntity(entity, i);
        else
        	flag = super.damageEntity(target, i);
        
        if(flag && health <= 0)
        {
            int pokey = random.nextInt(3);
            dead = true;
            if(isBoss())
            {
            	dead = false;
                unlockDoor();
                unlockTreasure((EntityPlayer) entity);
                chatItToAll("You are truly... a mighty warrior...", (EntityHuman) entity);
                if(!mod_AetherMp.betterMPBossMechanics)
                	PlayerManager.setCurrentBoss((EntityPlayer) target, null);
                else
                	clearTargets();
            } else
            if(pokey == 2)
            {
                chatItUp("Alright, alright! You win!", (EntityHuman) entity);
            } else
            if(pokey == 1)
            {
                chatItUp("Okay, I give up! Geez!", (EntityHuman) entity);
            } else
            {
                chatItUp("Oww! Fine, here's your medal...", (EntityHuman) entity);
            }
            S();
        }
        return flag;
    }

    @Override
    protected void a(Entity entity, float f)
    {
        if(attackTicks <= 0 && f < 2.75F && entity.boundingBox.e > boundingBox.b && entity.boundingBox.b < boundingBox.e)
        {
            attackTicks = 20;
            swingArm();
            entity.damageEntity(this, attackStrength);
            if(entity != null && target != null && entity == target && (entity instanceof EntityLiving))
            {
                EntityLiving e1 = (EntityLiving)entity;
                if(e1.health <= 0)
                {
                	target = null;
                	lastTarget = null;
                    angerLevel = 0;
                    int pokey = random.nextInt(3);
                    chatTime = 0;
                    if(isBoss())
                    {
                        chatItUp("As expected of a human.", (EntityHuman) entity);
                        if(!mod_AetherMp.betterMPBossMechanics || targetList.size() == 0)
                        	unlockDoor();
                        PlayerManager.setCurrentBoss((EntityPlayer) entity, null);
                        if(mod_AetherMp.betterMPBossMechanics) {
                        	targetList.remove((EntityPlayer)entity);
                        	findNewTarget();
                        }
                    } else
                    if(pokey == 2)
                    {
                        chatItUp("You want a medallion? Try being less pathetic.", (EntityHuman) entity);
                    } else
                    if(pokey == 1 && (e1 instanceof EntityPlayer))
                    {
                        EntityPlayer ep = (EntityPlayer)e1;
                        String s = ep.name;
                        chatItUp((new StringBuilder()).append("Maybe some day, ").append(s).append("... maybe some day.").toString(), (EntityHuman) entity);
                    } else
                    {
                        chatItUp("Humans aren't nearly as cute when they're dead.", (EntityHuman) entity);
                    }
                }
            }
        }
    }

    @Override
    public void findNewTarget() {
    	if (targetList.size() == 0) {
    		stopFight();
    		return;
    	}
		target = targetList.get(0);
		teleport(target.locX, target.locY, target.locZ, 7);
	}

	private void becomeAngryAt(Entity entity)
    {
    	if (target == null)
    		target = entity;
    	if (target instanceof EntityPlayer)
    		lastTarget = ((EntityPlayer) target).name;
        angerLevel = 200 + random.nextInt(200);
        if(isBoss())
        {
            for(int k = dungeonZ + 2; k < dungeonZ + 23; k += 7)
            {
                if(world.getTypeId(dungeonX - 1, dungeonY, k) == 0 && health > 0)
                {
                    dungeonEntranceZ = k;
                    world.setRawTypeIdAndData(dungeonX - 1, dungeonY, k, BlockManager.LockedDungeonStone.id, 1);
                    world.setRawTypeIdAndData(dungeonX - 1, dungeonY, k + 1, BlockManager.LockedDungeonStone.id, 1);
                    world.setRawTypeIdAndData(dungeonX - 1, dungeonY + 1, k + 1, BlockManager.LockedDungeonStone.id, 1);
                    world.setRawTypeIdAndData(dungeonX - 1, dungeonY + 1, k, BlockManager.LockedDungeonStone.id, 1);
                    return;
                }
            }

        }
    }

    private void unlockDoor()
    {
    	world.setRawTypeId(dungeonX - 1, dungeonY, dungeonEntranceZ, 0);
        world.setRawTypeId(dungeonX - 1, dungeonY, dungeonEntranceZ + 1, 0);
        world.setRawTypeId(dungeonX - 1, dungeonY + 1, dungeonEntranceZ + 1, 0);
        world.setRawTypeId(dungeonX - 1, dungeonY + 1, dungeonEntranceZ, 0);
    }

    private void unlockTreasure(EntityPlayer entityplayer)
    {
    	world.setRawTypeIdAndData(dungeonX + 16, dungeonY + 1, dungeonZ + 9, Block.TRAP_DOOR.id, 3);
        world.setRawTypeIdAndData(dungeonX + 17, dungeonY + 1, dungeonZ + 9, Block.TRAP_DOOR.id, 2);
        world.setRawTypeIdAndData(dungeonX + 16, dungeonY + 1, dungeonZ + 10, Block.TRAP_DOOR.id, 3);
        world.setRawTypeIdAndData(dungeonX + 17, dungeonY + 1, dungeonZ + 10, Block.TRAP_DOOR.id, 2);
        Achievements.giveAchievement(Achievements.defeatSilver, entityplayer);
        for(int x = dungeonX - 27; x < dungeonX + 30; x++)
        {
            for(int y = dungeonY - 3; y < dungeonY + 22; y++)
            {
                for(int z = dungeonZ - 7; z < dungeonZ + 27; z++)
                {
                    int id = world.getTypeId(x, y, z);
                    if(id == BlockManager.LockedDungeonStone.id)
                    {
                        world.setRawTypeIdAndData(x, y, z, BlockManager.DungeonStone.id, world.getData(x, y, z));
                    }
                    if(id == BlockManager.Trap.id)
                    {
                        world.setRawTypeIdAndData(x, y, z, BlockManager.DungeonStone.id, world.getData(x, y, z));
                    }
                    if(id == BlockManager.LockedLightDungeonStone.id)
                    {
                        world.setRawTypeIdAndData(x, y, z, BlockManager.LightDungeonStone.id, world.getData(x, y, z));
                    }
                }

            }

        }

    }

    public void setDungeon(int i, int j, int k)
    {
        hasDungeon = true;
        dungeonX = i;
        dungeonY = j;
        dungeonZ = k;
    }

    @Override
    public int getBossHP()
    {
        return health;
    }

    @Override
    public int getBossMaxHP()
    {
        return 500;
    }

    @Override
    public boolean isCurrentBoss(EntityPlayer entityplayer)
    {
    	IAetherBoss boss = PlayerManager.getCurrentBoss(entityplayer);
        if(boss == null)
            return false;
        else
            return equals(boss);
    }
    
	@Override
	public void stopFight() {
        if (target instanceof EntityPlayer)
        	PlayerManager.setCurrentBoss((EntityPlayer) target, null);
        target = null; 
        angerLevel = 0;
        unlockDoor();
        clearTargets();
	}
	
	public void clearTargets() {
        if(mod_AetherMp.betterMPBossMechanics) {
	        for (int i = 0; i < targetList.size(); i++) {
	        	PlayerManager.setCurrentBoss(targetList.get(i), null);
	        	targetList.remove(i);
	        }
        }
	}

	@Override
    public int getBossEntityID()
    {
        return id;
    }

    @Override
    public String getBossTitle()
    {
        return (new StringBuilder()).append(getName()).append(", the Valkyrie Queen").toString();
    }
    
	@Override
	public BossType getBossType() {
		return BossType.SILVER;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getEntities() {
		return world.b(this, AxisAlignedBB.a(dungeonX, dungeonY, dungeonZ, dungeonX + 24, dungeonY + 14, dungeonZ + 20));
	}
	
	public void chatItToAll(String s, EntityHuman p) {
		if(!mod_AetherMp.betterMPBossMechanics) {
			this.chatItUp(s, p);
			return;
		}
		List<Entity> list = getEntities();
		List<EntityPlayer> plist = new ArrayList<EntityPlayer>();
		for (int i = 0; i < list.size(); i++) {
			if (!(list.get(i) instanceof EntityPlayer))
				continue;
			plist.add((EntityPlayer) list.get(i));
		}
		for (int j = 0; j < plist.size(); j++)
			plist.get(j).a(s);
	}
	
	public void setBossForPlayers() {
		List<Entity> list = getEntities();
		for (int i = 0; i < list.size(); i++) {
			Entity ent = list.get(i);
			if (!(ent instanceof EntityPlayer))
				continue;
			EntityPlayer p = (EntityPlayer) ent;
			PlayerManager.setCurrentBoss(p, this);
			targetList.add(p);
		}
	}
	
	@Override
	public List<EntityPlayer> getTargetList() {
		return targetList;
	}

	@Override
	public void setTargetList(List<EntityPlayer> list) {
		targetList = list;
	}
	
	@Override
	public EntityPlayer getCurrentTarget() {
		return (EntityPlayer) target;
	}

    public boolean isSwinging;
    public boolean duel;
    public boolean hasDungeon;
    public int teleTimer;
    public int angerLevel;
    public int timeLeft;
    public int chatTime;
    public int dungeonX;
    public int dungeonY;
    public int dungeonZ;
    public int dungeonEntranceZ;
    public double safeX;
    public double safeY;
    public double safeZ;
    public float sinage;
    public double lastMotionY;
    private String lastTarget;
    public List<EntityPlayer> targetList = new ArrayList<EntityPlayer>();
}