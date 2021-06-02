package net.mine_diver.aethermp.entity;
 
import net.minecraft.src.EntityFireMonster;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModLoaderMp;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_AetherMp;

public class EntityFireMonsterMp extends EntityFireMonster {
	
	public EntityFireMonsterMp(World world, double x, double y, double z) {
		super(world);
		setPosition(x, y, z);
		isMultiplayerEntity = true;
	}
	
	@Override
	public void entityInit() {
		dataWatcher.addObject(12, (int)1); //orgX
		dataWatcher.addObject(13, (int)1); //orgY
		dataWatcher.addObject(14, (int)1); //orgZ
		dataWatcher.addObject(15, (byte)1); //texture
		dataWatcher.addObject(16, (int)1); //health
	}
	
	public void setTexture() {
		int i = dataWatcher.getWatchableObjectByte(15);
		if (i == 1)
			 texture = "/aether/mobs/firemonster.png";
		else
			 texture = "/aether/mobs/firemonsterHurt.png";
	}
	
	@Override
	public int getBossHP() {
		health = dataWatcher.getWatchableObjectInt(16);
		return super.getBossHP();
	}

	@Override
	public void updatePlayerActionState() {		
	}
	
	@Override
	public String getBossTitle() {
		if (name != null && !name.equals(""))
			bossName = name;
		else
			bossName = "";
		return super.getBossTitle();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		setTexture();
		if (ticks < 5)
			ticks++;
		else if (!constructed) {
			constructed = true;
			Packet230ModLoader packet = new Packet230ModLoader();
			packet.packetType = 70;
			packet.dataInt = new int [] {entityId};
			ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet);
		}
		
		posY = orgY;
	}
	
	@Override
    public boolean interact(EntityPlayer ep)
    {
		return false; 
    }
	
	public boolean constructed = false;
	public int ticks = 0;
	public int yCoord;
	public String name;
}
