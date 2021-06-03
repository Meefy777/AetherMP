package net.mine_diver.aethermp.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.mine_diver.aethermp.Core;
import net.mine_diver.aethermp.network.PacketManager;
import net.minecraft.server.mod_AetherMp;

public class AetherMPDataHandler {

	private	File storageFile;
	private JSONObject jsonData;
	private Core core = mod_AetherMp.CORE;
	
	public void Initialize() {
		storageFile = new File ("./AetherMPData.json");
		if(!storageFile.exists()) {
            storageFile.getParentFile().mkdirs();
            try (FileWriter file = new FileWriter(storageFile)) {
                core.LOGGER.info("Generating new json file");
                jsonData = new JSONObject();
                file.write(jsonData.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		
        try {
        	core.LOGGER.info("Reading AetherMPData.json file");
            JSONParser parser = new JSONParser();
            jsonData = (JSONObject) parser.parse(new FileReader(storageFile));
        } catch (ParseException e) {
            core.LOGGER.info("AetherMPData.json file is corrupt: " + e + ": " + e.getMessage());
            jsonData = new JSONObject();
            saveData();
        } catch (Exception e) {
            System.out.println("Error reading AetherMPData.json, changing to memory only cache: " + e + ": " + e.getMessage());
            jsonData = new JSONObject();
        }
        
        if(jsonData.get("isSunSpiritDead") == null)
        	setFireMonsterKilled(false);
        saveData();
	}
	
	public void saveData() {
		try(FileWriter file = new FileWriter(storageFile)) {
			core.LOGGER.info("Saving AetherMPData.json");
			file.write(jsonData.toJSONString());
			file.flush();
		}	catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	public void setFireMonsterKilled(boolean flag) {
		boolean flag1 = getFireMonsterKilled();
		jsonData.put("isSunSpiritDead", flag);
		
		if(flag1 != flag)
			PacketManager.sendFireBoss(null);
	}
	
	public boolean getFireMonsterKilled() {
		Object obj = jsonData.get("isSunSpiritDead");
		if (obj == null)
			return false;
		return (boolean) obj;
	}

}
