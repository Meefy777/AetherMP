package net.mine_diver.aethermp.entities;

public class EntityValkyrie {}


// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   EntityValkyrie.java
/*
package net.mine_diver.aethermp.entities;

import java.util.List;
import java.util.Random;

import net.mine_diver.aethermp.api.entities.IAetherBoss;
import net.mine_diver.aethermp.blocks.BlockManager;
import net.mine_diver.aethermp.items.ItemManager;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagDouble;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.World;

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
        super(world);
        b(0.8F, 1.6F);
        texture = "/aether/mobs/valkyrie.png";
        teleTimer = random.nextInt(250);
        health = 50;
        aE = 0.5F;
        timeLeft = 1200;
        attackStrength = 7;
        safeX = locX;
        safeY = locY;
        safeZ = locZ;
    }

    public EntityValkyrie(World world, double x, double y, double z, 
            boolean flag)
    {
        super(world);
        b(0.8F, 1.6F);
        bossName = NameGen.gen();
        texture = "/aether/mobs/valkyrie.png";
        if(flag)
        {
            texture = "/aether/mobs/valkyrie2.png";
            health = 500;
            boss = true;
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

    public void fall(float f1)
    {
    }

    @Override
    public void m_()
    {
        lastMotionY = motY;
        super.m_();
        if(!onGround && target != null && lastMotionY >= 0.0D && motY < 0.0D && getDistanceToEntity(target) <= 16F && canEntityBeSeen(target))
        {
            double a = target.locX - locX;
            double b = target.locZ - locZ;
            double angle = Math.atan2(a, b);
            motX = Math.sin(angle) * 0.25D;
            motZ = Math.cos(angle) * 0.25D;
        }
        if(!onGround && !isOnLadder() && Math.abs(motY - lastMotionY) > 0.070000000000000007D && Math.abs(motY - lastMotionY) < 0.089999999999999997D)
        {
            motY += 0.054999999701976776D;
            if(motY < -0.27500000596046448D)
            {
                motY = -0.27500000596046448D;
            }
        }
        aE = target != null ? 1.0F : 0.5F;
        if(world.difficultySetting <= 0 && (target != null || angerLevel > 0))
        {
            angerLevel = 0;
            target = null;
        }
        if(isSwinging)
        {
            prevSwingProgress += 0.15F;
            swingProgress += 0.15F;
            if(prevSwingProgress > 1.0F || swingProgress > 1.0F)
            {
                isSwinging = false;
                prevSwingProgress = 0.0F;
                swingProgress = 0.0F;
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
        if(!otherDimension())
        {
            timeLeft--;
            if(timeLeft <= 0)
            {
                isDead = true;
                spawnExplosionParticle();
            }
        }
    }

    public boolean otherDimension()
    {
        return true;
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
            spawnExplosionParticle();
            setPosition((double)newX + 0.5D, (double)newY + 0.5D, (double)newZ + 0.5D);
            motX = 0.0D;
            motY = 0.0D;
            motZ = 0.0D;
            moveForward = 0.0F;
            moveStrafing = 0.0F;
            isJumping = false;
            rotationPitch = 0.0F;
            rotationYaw = 0.0F;
            setPathToEntity(null);
            renderYawOffset = rand.nextFloat() * 360F;
            spawnExplosionParticle();
            teleTimer = random.nextInt(40);
        }
    }

    public boolean isAirySpace(int x, int y, int z)
    {
        int p = world.getTypeId(x, y, z);
        return p == 0 || Block.byId[p].getCollisionBoundingBoxFromPool(world, x, y, z) == null;
    }

    public boolean canDespawn()
    {
        return !boss;
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        faceEntity(entityplayer, 180F, 180F);
        if(!boss)
        {
            if(timeLeft >= 1200)
            {
                ItemStack itemstack = entityplayer.G();
                if(itemstack != null && itemstack.id == ItemManager.VictoryMedal.id && itemstack.count >= 0)
                {
                    if(itemstack.count >= 10)
                    {
                        chatItUp("Umm... that's a nice pile of medallions you have there...");
                    } else
                    if(itemstack.count >= 5)
                    {
                        chatItUp("That's pretty impressive, but you won't defeat me.");
                    } else
                    {
                        chatItUp("You think you're a tough guy, eh? Well, bring it on!");
                    }
                } else
                {
                    int pokey = random.nextInt(3);
                    if(pokey == 2)
                    {
                        chatItUp("What's that? You want to fight? Aww, what a cute little human.");
                    } else
                    if(pokey == 1)
                    {
                        chatItUp("You're not thinking of fighting a big, strong Valkyrie are you?");
                    } else
                    {
                        chatItUp("I don't think you should bother me, you could get really hurt.");
                    }
                }
            }
        } else
        if(duel)
        {
            chatItUp("If you wish to challenge me, strike at any time.");
        } else
        if(!duel)
        {
            ItemStack itemstack = entityplayer.G();
            if(itemstack != null && itemstack.id == ItemManager.VictoryMedal.id && itemstack.count >= 10)
            {
                itemstack.count -= 10;
                if(itemstack.count <= 0)
                {
                    itemstack.onItemDestroyedByUse(entityplayer);
                    entityplayer.G();
                    chatTime = 0;
                    chatItUp("Very well, attack me when you wish to begin.");
                    duel = true;
                }
            } else
            {
                chatItUp("Show me 10 victory medals, and I will fight you.");
            }
        }
        return true;
    }

    private void chatItUp(String s)
    {
        if(chatTime <= 0 && otherDimension())
        {
            ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(s);
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

    protected void dropFewItems()
    {
        if(boss)
        {
            a(new ItemStack(ItemManager.Key, 1, 1), 0.0F);
            b(Item.GOLD_SWORD.id, 1);
        } else
        {
            b(ItemManager.VictoryMedal.id, 1);
        }
    }

    public void updatePlayerActionState()
    {
        super.updatePlayerActionState();
        teleTimer++;
        if(teleTimer >= 450)
        {
            if(target != null)
            {
                if(boss && onGround && random.nextInt(2) == 0 && target != null && (target instanceof EntityLiving))
                {
                    makeHomeShot(1, (EntityLiving)target);
                    teleTimer = -100;
                } else
                {
                    teleport(target.locX, target.locY, target.locZ, 7);
                }
            } else
            if(!onGround || boss)
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
        if(teleTimer % 5 == 0 && target != null && !canEntityBeSeen(target))
        {
            teleTimer += 100;
        }
        if(onGround && teleTimer % 10 == 0 && !boss)
        {
            safeX = locX;
            safeY = locY;
            safeZ = locZ;
        }
        if(target != null && target.isDead)
        {
        	target = null;
            if(boss)
            {
                unlockDoor();
                mod_Aether.currentBoss = null;
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
        if(!isSwinging)
        {
            isSwinging = true;
            prevSwingProgress = 0.0F;
            swingProgress = 0.0F;
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
        int i = MathHelper.floor_double(locX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(locZ);
        return world.getFullBlockLightValue(i, j, k) > 8 && world.checkIfAABBIsClear(boundingBox) && world.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !world.getIsAnyLiquid(boundingBox);
    }

    public void b(NBTTagCompound nbttagcompound)
    {
        super.b(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)angerLevel);
        nbttagcompound.setShort("TeleTimer", (short)teleTimer);
        nbttagcompound.setShort("TimeLeft", (short)timeLeft);
        nbttagcompound.setBoolean("Boss", boss);
        nbttagcompound.setBoolean("Duel", duel);
        nbttagcompound.setInteger("DungeonX", dungeonX);
        nbttagcompound.setInteger("DungeonY", dungeonY);
        nbttagcompound.setInteger("DungeonZ", dungeonZ);
        nbttagcompound.setInteger("DungeonEntranceZ", dungeonEntranceZ);
        nbttagcompound.setTag("SafePos", newDoubleNBTList(new double[] {
            safeX, safeY, safeZ
        }));
        nbttagcompound.setBoolean("IsCurrentBoss", isCurrentBoss());
        nbttagcompound.setString("BossName", bossName);
    }

    @Override
    public void a(NBTTagCompound nbttagcompound)
    {
        super.a(nbttagcompound);
        angerLevel = nbttagcompound.getShort("Anger");
        teleTimer = nbttagcompound.getShort("TeleTimer");
        timeLeft = nbttagcompound.getShort("TimeLeft");
        duel = nbttagcompound.getBoolean("Duel");
        boss = nbttagcompound.getBoolean("Boss");
        dungeonX = nbttagcompound.getInteger("DungeonX");
        dungeonY = nbttagcompound.getInteger("DungeonY");
        dungeonZ = nbttagcompound.getInteger("DungeonZ");
        dungeonEntranceZ = nbttagcompound.getInteger("DungeonEntranceZ");
        if(boss)
        {
            texture = "/aether/mobs/valkyrie2.png";
        }
        NBTTagList nbttaglist = nbttagcompound.getTagList("SafePos");
        safeX = ((NBTTagDouble)nbttaglist.tagAt(0)).doubleValue;
        safeY = ((NBTTagDouble)nbttaglist.tagAt(1)).doubleValue;
        safeZ = ((NBTTagDouble)nbttaglist.tagAt(2)).doubleValue;
        if(nbttagcompound.getBoolean("IsCurrentBoss"))
        {
            mod_Aether.currentBoss = this;
        }
        bossName = nbttagcompound.getString("BossName");
    }

    protected Entity findPlayerToAttack()
    {
        if(otherDimension() && (world.difficultySetting <= 0 || boss && !duel || angerLevel <= 0))
        {
            return null;
        } else
        {
            return super.findPlayerToAttack();
        }
    }

    @Override
    public boolean damageEntity(Entity entity, int i)
    {
        if((entity instanceof EntityPlayer) && world.difficultySetting > 0)
        {
            if(boss && (!duel || world.difficultySetting <= 0))
            {
                spawnExplosionParticle();
                int pokey = random.nextInt(2);
                if(pokey == 2)
                {
                    chatItUp("Sorry, I don't fight with weaklings.");
                } else
                {
                    chatItUp("Try defeating some weaker valkyries first.");
                }
                return false;
            }
            if(boss)
            {
                if(target == null)
                {
                    mod_Aether.currentBoss = this;
                    chatTime = 0;
                    chatItUp("This will be your final battle!");
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
                    chatItUp("I'm not going easy on you!");
                } else
                if(pokey == 1)
                {
                    chatItUp("You're gonna regret that!");
                } else
                {
                    chatItUp("Now you're in for it!");
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
        boolean flag = super.damageEntity(entity, i);
        if(flag && health <= 0)
        {
            int pokey = random.nextInt(3);
            isDead = true;
            if(boss)
            {
                isDead = false;
                unlockDoor();
                unlockTreasure();
                chatItUp("You are truly... a mighty warrior...");
                mod_Aether.currentBoss = null;
            } else
            if(pokey == 2)
            {
                chatItUp("Alright, alright! You win!");
            } else
            if(pokey == 1)
            {
                chatItUp("Okay, I give up! Geez!");
            } else
            {
                chatItUp("Oww! Fine, here's your medal...");
            }
            spawnExplosionParticle();
        }
        return flag;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if(attackTicks <= 0 && f < 2.75F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
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
                    angerLevel = 0;
                    int pokey = random.nextInt(3);
                    chatTime = 0;
                    if(boss)
                    {
                        chatItUp("As expected of a human.");
                        unlockDoor();
                        mod_Aether.currentBoss = null;
                    } else
                    if(pokey == 2)
                    {
                        chatItUp("You want a medallion? Try being less pathetic.");
                    } else
                    if(pokey == 1 && (e1 instanceof EntityPlayer))
                    {
                        EntityPlayer ep = (EntityPlayer)e1;
                        String s = ep.username;
                        chatItUp((new StringBuilder()).append("Maybe some day, ").append(s).append("... maybe some day.").toString());
                    } else
                    {
                        chatItUp("Humans aren't nearly as cute when they're dead.");
                    }
                }
            }
        }
    }

    private void becomeAngryAt(Entity entity)
    {
    	target = entity;
        angerLevel = 200 + random.nextInt(200);
        if(boss)
        {
            for(int k = dungeonZ + 2; k < dungeonZ + 23; k += 7)
            {
                if(world.getTypeId(dungeonX - 1, dungeonY, k) == 0)
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

    private void unlockTreasure()
    {
    	world.setRawTypeIdAndData(dungeonX + 16, dungeonY + 1, dungeonZ + 9, Block.TRAP_DOOR.id, 3);
        world.setRawTypeIdAndData(dungeonX + 17, dungeonY + 1, dungeonZ + 9, Block.TRAP_DOOR.id, 2);
        world.setRawTypeIdAndData(dungeonX + 16, dungeonY + 1, dungeonZ + 10, Block.TRAP_DOOR.id, 3);
        world.setRawTypeIdAndData(dungeonX + 17, dungeonY + 1, dungeonZ + 10, Block.TRAP_DOOR.id, 2);
        mod_Aether.giveAchievement(AetherAchievements.defeatSilver);
        for(int x = dungeonX - 26; x < dungeonX + 29; x++)
        {
            for(int y = dungeonY - 1; y < dungeonY + 22; y++)
            {
                for(int z = dungeonZ - 5; z < dungeonZ + 25; z++)
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

    public int getBossHP()
    {
        return health;
    }

    public int getBossMaxHP()
    {
        return 500;
    }

    public boolean isCurrentBoss()
    {
        if(mod_Aether.currentBoss == null)
        {
            return false;
        } else
        {
            return equals(mod_Aether.currentBoss);
        }
    }

    public int getBossEntityID()
    {
        return id;
    }

    public String getBossTitle()
    {
        return (new StringBuilder()).append(bossName).append(", the Valkyrie Queen").toString();
    }

    public boolean isSwinging;
    public boolean boss;
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
    public String bossName;
}
*/