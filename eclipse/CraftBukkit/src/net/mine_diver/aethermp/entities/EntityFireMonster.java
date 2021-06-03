// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityFireMonster.java

package net.mine_diver.aethermp.entities;

import java.util.List;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.bukkit.craftbukkit.entity.CraftEntityAether;
import net.mine_diver.aethermp.items.ItemManager;
import net.mine_diver.aethermp.network.PacketManager;
import net.mine_diver.aethermp.player.PlayerManager;
import net.mine_diver.aethermp.util.Achievements;
import net.mine_diver.aethermp.util.NameGen;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityFlying;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet230ModLoader;
import net.minecraft.server.World;
import net.minecraft.server.WorldServer;
import net.minecraft.server.mod_AetherMp;

// Referenced classes of package net.minecraft.src:
//            EntityFlying, IAetherBoss, MathHelper, AxisAlignedBB, 
//            NameGen, World, EntityPlayer, EntityLiving, 
//            Entity, mod_Aether, Material, EntityFiroBall, 
//            AetherBlocks, Block, BlockFire, NBTTagCompound, 
//            ModLoader, GuiIngame, EntityFireMinion, ItemStack, 
//            AetherItems, AetherAchievements

public class EntityFireMonster extends EntityFlying implements IAetherBoss {

    public EntityFireMonster(World world)
    {
        super(world);
        setTexture(1);
        b(2.25F, 2.5F);
        bt = true;
        orgX = MathHelper.floor(locX);
        orgY = MathHelper.floor(boundingBox.b) + 1;
        orgZ = MathHelper.floor(locZ);
        wideness = 10;
        health = getBossMaxHP();
        speedness = 0.5D - ((double)health / 70D) * 0.20000000000000001D;
        direction = 0;
        entCount = random.nextInt(6);
        bossName = NameGen.gen();
        setOrg();
    }

	public EntityFireMonster(World world, int x, int y, int z, int rad, int dir)
    {
        super(world);
        setTexture(1);
        b(2.25F, 2.5F);
        setPosition((double)x + 0.5D, y, (double)z + 0.5D);
        wideness = rad - 2;
        orgX = x;
        orgY = y;
        orgZ = z;
        bt = true;
        rotary = (double)random.nextFloat() * 360D;
        health = getBossMaxHP();
        speedness = 0.5D - ((double)health / 70D) * 0.20000000000000001D;
        direction = dir;
        entCount = random.nextInt(6);
        bossName = NameGen.gen();
        setOrg();
    }
    
    @Override
    public void b() {
		datawatcher.a(12, (int)1); //orgX
		datawatcher.a(13, (int)1); //orgY
		datawatcher.a(14, (int)1); //orgZ
		datawatcher.a(15, (byte)1); //texture
		datawatcher.a(16, (int)health); //health
    }
    
    public void setOrg() {
    	datawatcher.watch(12, orgX);
    	datawatcher.watch(13, orgY);
    	datawatcher.watch(14, orgZ);
    }
    
    public int getHealth() {
    	return datawatcher.b(16);
    }
    
    public void setHealth() {
    	datawatcher.watch(16, (int)health);
    }
    
    public void setTexture(int i) {
    	datawatcher.watch(15, (byte)i);
    }
    
    @Override
    public boolean h_()
    {
        return false;
    }
    
    @Override
    public void m_()
    {
    	if (dir == 0)
    		coordinate();
    	
        super.m_();
        setHealth();
        if(health > 0)
        {
            double a = random.nextFloat() - 0.5F;
            double b = random.nextFloat();
            double c = random.nextFloat() - 0.5F;
            double d = locX + a * b;
            double e = (boundingBox.b + b) - 0.5D;
            double f = locZ + c * b;
            Packet230ModLoader packet = new Packet230ModLoader();
            packet.packetType = 31;
            packet.dataString = new String [] {"flame"};
            packet.dataFloat = new float [] {(float) d, (float) e, (float) f, 0.0F, -0.075000002980232239F, 0.0F};
            PacketManager.sendToViewDistance(packet, ((WorldServer) world).dimension, locX, locY, locZ);
            entCount++;
            if(entCount >= 3)
            {
                burnEntities();
                evapWater();
                entCount = 0;
            }
            if(hurtness > 0)
            {
                hurtness--;
                if(hurtness == 0)
                {
                	setTexture(1);
                }
            }
        }
        if(chatCount > 0)
        {
            chatCount--;
        }
    }

    protected Entity findPlayerToAttack()
    {
        EntityHuman entityplayer = world.findNearbyPlayer(this, 32D);
        if(entityplayer != null && e(entityplayer))
            return entityplayer;
        return null;
    }

    @Override
    public void c_()
    {
        super.c_();
        if(gotTarget && targetfire == null)
        {
            targetfire = findPlayerToAttack();
            gotTarget = false;
        }
        if(targetfire == null)
        {
        	if (orgX != 0 && orgZ != 0)
        		setPosition((double)orgX + 0.5D, orgY, (double)orgZ + 0.5D);
            setDoor(0, entranceDoor);
            return;
        }
        K = yaw;
        setPosition(locX, orgY, locZ);
        motY = 0.0D;
        boolean pool = false;
        if(motX > 0.0D && (int)Math.floor(locX) > orgX + wideness)
        {
            rotary = 360D - rotary;
            pool = true;
        } else
        if(motX < 0.0D && (int)Math.floor(locX) < orgX - wideness)
        {
            rotary = 360D - rotary;
            pool = true;
        }
        if(motZ > 0.0D && (int)Math.floor(locZ) > orgZ + wideness)
        {
            rotary = 180D - rotary;
            pool = true;
        } else
        if(motZ < 0.0D && (int)Math.floor(locZ) < orgZ - wideness)
        {
            rotary = 180D - rotary;
            pool = true;
        }
        if(rotary > 360D)
        {
            rotary -= 360D;
        } else
        if(rotary < 0.0D)
        {
            rotary += 360D;
        }
        if(targetfire != null)
        {
            a(targetfire, 20F, 20F);
        }
        double crazy = rotary / 57.295772552490234D;
        motX = Math.sin(crazy) * speedness;
        motZ = Math.cos(crazy) * speedness;
        motionTimer++;
        if(motionTimer >= 20 || pool)
        {
            motionTimer = 0;
            if(random.nextInt(3) == 0)
            {
                rotary += (double)(random.nextFloat() - random.nextFloat()) * 60D;
            }
        }
        flameCount++;
        if(flameCount == 40 && random.nextInt(2) == 0)
        {
            poopFire();
        } else
        if(flameCount >= 55 + health / 2 && targetfire != null && (targetfire instanceof EntityLiving))
        {
            makeFireBall(1);
            flameCount = 0;
        }
        if(targetfire != null && targetfire.dead)
        {
            setPosition((double)orgX + 0.5D, orgY, (double)orgZ + 0.5D);
            motX = 0.0D;
            motY = 0.0D;
            motZ = 0.0D;
            setDoor(0, entranceDoor);
            if (targetfire instanceof EntityPlayer) {
            	PlayerManager.setCurrentBoss((EntityPlayer) targetfire, null);
                chatLine("\247cSuch is the fate of a being who opposes the might of the sun.", (EntityPlayer) targetfire);
                clearFiroBalls();
            }
            targetfire = null;
            gotTarget = false;
        }
    }

    @SuppressWarnings("rawtypes")
	public void burnEntities()
    {
        List list = world.b(this, boundingBox.b(0.0D, 4D, 0.0D));
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            boolean fireProof = mod_AetherMp.PackageAccess.Entity.isImmuneToFire(entity1);
            if((entity1 instanceof EntityLiving) && !fireProof)
            {
                entity1.damageEntity(this, 10);
                entity1.fireTicks = 300;
            }
        }

    }

    public void evapWater()
    {
        int x = MathHelper.floor(locX);
        int z = MathHelper.floor(locZ);
        for(int i = 0; i < 8; i++)
        {
            int b = (orgY - 2) + i;
            if(world.getMaterial(x, b, z) != Material.WATER)
            {
                continue;
            }
            world.setTypeId(x, b, z, 0);
            Packet230ModLoader packet = new Packet230ModLoader();
            packet.packetType = 35;
            packet.dataFloat = new float[] {(float)x + 0.5F, (float)b + 0.5F, (float)z + 0.5F, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F};
            packet.dataString = new String [] {"random.fizz"};
            PacketManager.sendToViewDistance(packet, ((WorldServer)world).dimension, locX, locY, locZ);
            for(int l = 0; l < 8; l++)
            {
            	Packet230ModLoader smoke = new Packet230ModLoader();
            	smoke.packetType = 31;
            	smoke.dataString = new String [] {"largesmoke"};
            	smoke.dataFloat = new float [] {(float) ((float)x + Math.random()), (float) ((float)b + 0.75D), (float) ((float)z + Math.random()), 0.0F, 0.0F, 0.0F};
            	PacketManager.sendToViewDistance(smoke, ((WorldServer) world).dimension, locX, locY, locZ);
            }

        }

    }

    public void makeFireBall(int shots)
    {
    	PacketManager.makeSound(this, "mob.ghast.fireball", 5F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        boolean flag = false;
        ballCount++;
        if(ballCount >= 3 + random.nextInt(3))
        {
            flag = true;
            ballCount = 0;
        }
        for(int i = 0; i < shots; i++)
        {
            EntityFiroBall e1 = new EntityFiroBall(world, locX - motX / 2D, locY, locZ - motZ / 2D, flag);
            world.addEntity(e1);
        }

    }

    public void poopFire()
    {
        int x = MathHelper.floor(locX);
        int z = MathHelper.floor(locZ);
        int b = orgY - 2;
        if(BlockManager.isGood(world.getTypeId(x, b, z), world.getData(x, b, z)))
        {
            world.setTypeId(x, b, z, Block.FIRE.id);
        }
    }

    @Override
    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.a("OriginX", orgX);
        nbttagcompound.a("OriginY", orgY);
        nbttagcompound.a("OriginZ", orgZ);
        nbttagcompound.a("Wideness", (short)wideness);
        nbttagcompound.a("FlameCount", (short)flameCount);
        nbttagcompound.a("BallCount", (short)ballCount);
        nbttagcompound.a("ChatLog", (short)chatLog);
        nbttagcompound.a("Rotary", (float)rotary);
        gotTarget = targetfire != null;
        nbttagcompound.a("GotTarget", gotTarget);
        boolean current = targetfire instanceof EntityPlayer ? isCurrentBoss((EntityPlayer) targetfire) : false;
        nbttagcompound.a("IsCurrentBoss", current);
        if (current)
        	nbttagcompound.setString("TargetNickname", ((EntityPlayer)targetfire).name);
        nbttagcompound.setString("BossName", bossName);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        orgX = nbttagcompound.e("OriginX");
        orgY = nbttagcompound.e("OriginY");
        orgZ = nbttagcompound.e("OriginZ");
        wideness = nbttagcompound.d("Wideness");
        flameCount = nbttagcompound.d("FlameCount");
        ballCount = nbttagcompound.d("BallCount");
        chatLog = nbttagcompound.d("ChatLog");
        rotary = nbttagcompound.g("Rotary");
        gotTarget = nbttagcompound.m("GotTarget");
        speedness = 0.5D - ((double)health / 70D) * 0.20000000000000001D;
        if(nbttagcompound.m("IsCurrentBoss"))
        {
        	EntityPlayer player = ((WorldServer) world).server.serverConfigurationManager.i(nbttagcompound.getString("TargetNickname"));
        	if (player != null) {
        		targetfire = player;
        		PlayerManager.setCurrentBoss(player, this);
        	}
        }
        bossName = nbttagcompound.getString("BossName");
    }

    public void chatLine(String s, EntityHuman human)
    {
        human.a(s);
    }

    public boolean chatWithMe(EntityHuman human)
    {
        if(chatCount <= 0)
        {
            if(chatLog == 0)
            {
                chatLine("\247cYou are certainly a brave soul to have entered this chamber.", human);
                chatLog = 1;
                chatCount = 100;
            } else
            if(chatLog == 1)
            {
                chatLine("\247cBegone human, you serve no purpose here.", human);
                chatLog = 2;
                chatCount = 100;
            } else
            if(chatLog == 2)
            {
                chatLine("\247cYour presence annoys me. Do you not fear my burning aura?", human);
                chatLog = 3;
                chatCount = 100;
            } else
            if(chatLog == 3)
            {
                chatLine("\247cI have nothing to offer you, fool. Leave me at peace.", human);
                chatLog = 4;
                chatCount = 100;
            } else
            if(chatLog == 4)
            {
                chatLine("\247cPerhaps you are ignorant. Do you wish to know who I am?", human);
                chatLog = 5;
                chatCount = 100;
            } else
            if(chatLog == 5)
            {
                chatLine("\247cI am a sun spirit, embodiment of Aether's eternal daylight.", human);
                chatLine("\247cAs long as I am alive, the sun will never set on this world.", human);
                chatLog = 6;
                chatCount = 100;
            } else
            if(chatLog == 6)
            {
                chatLine("\247cMy body burns with the anger of a thousand beasts.", human);
                chatLine("\247cNo man, hero, or villain can harm me. You are no exception.", human);
                chatLog = 7;
                chatCount = 100;
            } else
            if(chatLog == 7)
            {
                chatLine("\247cYou wish to challenge the might of the sun? You are mad.", human);
                chatLine("\247cDo not further insult me or you will feel my wrath.", human);
                chatLog = 8;
                chatCount = 100;
            } else
            if(chatLog == 8)
            {
                chatLine("\247cThis is your final warning. Leave now, or prepare to burn.", human);
                chatLog = 9;
                chatCount = 100;
            } else
            {
                if(chatLog == 9)
                {
                    chatLine("\2476As you wish, your death will be slow and agonizing.", human);
                    chatLog = 10;
                    PlayerManager.setCurrentBoss((EntityPlayer) human, this);
                    return true;
                }
                if(chatLog == 10 && targetfire == null)
                {
                    chatLine("\247cDid your previous death not satisfy your curiosity, human?", human);
                    chatLog = 9;
                    chatCount = 100;
                }
            }
        }
        return false;
    }

    @Override
    public boolean a(EntityHuman ep)
    {
        if(chatWithMe(ep))
        {
            rotary = 57.295772552490234D * Math.atan2(locX - ep.locX, locZ - ep.locZ);
            targetfire = ep;
            setDoor(BlockManager.LockedDungeonStone.id, entranceDoor);
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void b(double d3, double d4, double d5)
    {
    }

    @Override
    public void a(Entity entity1, int j, double d2, double d3)
    {
    }

    @Override
    public boolean damageEntity(Entity e, int i)
    {
        if(e == null || !(e instanceof EntityFiroBall))
        {
            return false;
        }
        speedness = 0.5D - ((double)health / 70D) * 0.20000000000000001D;
        boolean flag = super.damageEntity(e, i);
        if(flag)
        {
            hurtness = 15;
            setTexture(2);
            EntityFireMinion minion = new EntityFireMinion(world);
            minion.setPositionRotation(locX, locY, locZ, yaw, 0.0F);
            world.addEntity(minion);
            world.addEntity(minion);
            world.addEntity(minion);
            if(health <= 0)
            {
                if (targetfire instanceof EntityPlayer) {
                	PlayerManager.setCurrentBoss((EntityPlayer) targetfire, null);
                	chatLine("\247bSuch bitter cold... is this the feeling... of pain?", (EntityPlayer) targetfire);
                }
            	mod_AetherMp.CORE.dataHandler.setFireMonsterKilled(true);
                setDoor(0, entranceDoor);
                unlockTreasure(e);
            }
        }
        return flag;
    }

    @Override
    protected void q()
    {
        a(new ItemStack(ItemManager.Key, 1, 2), 0.0F);
    }

    private void unlockTreasure(Entity ent)
    {
    	
    	setDoor(0, treasureDoor);
        
        
        EntityPlayer player;
        
        if (ent instanceof EntityPlayer)
        	player = (EntityPlayer) ent;
        else
        	player = (EntityPlayer) world.findNearbyPlayer(this, 20D);
        	
        if (player != null)	
        	Achievements.giveAchievement(Achievements.defeatGold, (EntityPlayer) ent);
        
        for(int x = orgX - 20; x < orgX + 20; x++)
        {
            for(int y = orgY - 3; y < orgY + 6; y++)
            {
                for(int z = orgZ - 20; z < orgZ + 20; z++)
                {
                    int id = world.getTypeId(x, y, z);
                    if(id == BlockManager.LockedDungeonStone.id)
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
    
    private void findDirection() {
    	int tresid = BlockManager.TreasureChest.id;
    	
    	int i = world.getTypeId(orgX + 15, orgY - 1, orgZ);
    	if (i == tresid) {
    		dir = 1;
    	}
    	
    	int j = world.getTypeId(orgX - 15, orgY - 1, orgZ);
    	if (j == tresid) {
    		dir = 2;
    	}
    	
    	int k = world.getTypeId(orgX, orgY - 1, orgZ + 15);
    	if (k == tresid) {
    		dir = 3;
    	}
    	
    	int l = world.getTypeId(orgX, orgY - 1, orgZ - 15);
    	if (l == tresid) {
    		dir = 4;
    	}
    }
    
    private void findDoors() {
    	switch(dir) {
    		case 1: {
    			treasureDoor = (new int[] {orgX + 11, orgY - 1, orgZ});
    			entranceDoor = (new int[] {orgX - 11, orgY - 1, orgZ});
    			break;
    		}
    		case 2: {
    			treasureDoor = (new int[] {orgX - 11, orgY - 1, orgZ});
    			entranceDoor = (new int[] {orgX + 11, orgY - 1, orgZ});
    			break;
    		}
    		case 3: {
    			treasureDoor = (new int[] {orgX, orgY - 1, orgZ + 11});
    			entranceDoor = (new int[] {orgX, orgY - 1, orgZ - 11});
    			break;
    		}
    		case 4: {
    			treasureDoor = (new int[] {orgX, orgY - 1, orgZ - 11});
    			entranceDoor = (new int[] {orgX, orgY - 1, orgZ + 11});
    			break;
    		}
		}
    }
    
    private void setDoor(int ID, int[] doorLoc) {
    	int meta;
    	if (ID == BlockManager.LockedDungeonStone.id)
    		meta = 2;
    	else
    		meta = 0;
    	if (dir == 1 || dir == 2) {
    		for (int y = 0; y <= 2; y++) {
	    		world.setRawTypeIdAndData(doorLoc[0], doorLoc[1] + y, doorLoc[2], ID, meta);
				world.setRawTypeIdAndData(doorLoc[0], doorLoc[1] + y, doorLoc[2] + 1, ID, meta);
				world.setRawTypeIdAndData(doorLoc[0], doorLoc[1] + y, doorLoc[2] - 1, ID, meta);
    		}
    	}
    	else if (dir == 3 || dir == 4) {
    		for (int y = 0; y <= 2; y++) {
				world.setRawTypeIdAndData(doorLoc[0], doorLoc[1] + y, doorLoc[2], ID, meta);
				world.setRawTypeIdAndData(doorLoc[0] + 1, doorLoc[1] + y, doorLoc[2], ID, meta);
				world.setRawTypeIdAndData(doorLoc[0] - 1, doorLoc[1] + y, doorLoc[2], ID, meta);
    		}
    	}
    }
  
    private void coordinate() {
   	 	findDirection();
   	 	findDoors();
	}

    @Override
    public int getBossHP()
    {
        return health;
    }

    @Override
    public int getBossMaxHP()
    {
        return 50;
    }

    @Override
    public boolean isCurrentBoss(EntityPlayer player)
    {
    	IAetherBoss boss = PlayerManager.getCurrentBoss(player);
    	if (boss == null)
    		return false;
    	else
    		return equals(boss);
    }

    @Override
    public int getBossEntityID()
    {
        return id;
    }

    @Override
    public String getBossTitle()
    {
        return (new StringBuilder()).append(bossName).append(", the Sun Spirit").toString();
    }

	@Override
	public void stopFight() {
        if (targetfire instanceof EntityPlayer)
        	PlayerManager.setCurrentBoss((EntityPlayer) targetfire, null);
        targetfire = null;
        gotTarget = false;
        setDoor(0, entranceDoor);
        clearFiroBalls();
	}
    
    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = CraftEntityAether.getEntity(this.world.getServer(), this);
        return this.bukkitEntity;
    }
    
    @SuppressWarnings("unchecked")
	public void clearFiroBalls() {
    	if (!mod_AetherMp.clearFiroBallsAfterDeath)
    		return;
        List<Entity> list = world.b(this, AxisAlignedBB.a(orgX-10, orgY-2, orgZ-10, orgX+11, orgY+5, orgZ+10));
        for (int i = 0; i < list.size(); i++) {
        	Entity ent = list.get(i);
        	if (ent instanceof EntityFiroBall)
        		((EntityFiroBall)ent).die();
        }
    }

    public int wideness;
    public int orgX;
    public int orgY;
    public int orgZ;
    public int motionTimer;
    public int entCount;
    public int flameCount;
    public int ballCount;
    public int chatLog;
    public int chatCount;
    public int hurtness;
    public int direction;
    public double rotary;
    public double speedness;
    public Entity targetfire;
    public boolean gotTarget;
    public String bossName;
    public static final float jimz = 57.29577F;
    private int dir;
    private int treasureDoor[] = new int [3];
    private int entranceDoor[] = new int [3];
}
