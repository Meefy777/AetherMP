package net.mine_diver.aetherapi.api.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.IAetherBoss;

public class BossManager {

	public static void registerBossOverride(BossOverride override) {
		boss.add(override);
	}
	
	public static IAetherBoss getBoss(IAetherBoss entity) {
		IAetherBoss newBoss = entity;
		for (BossOverride override : boss)
			newBoss = override.overrideEntity(newBoss);
		return newBoss;
	}
	
	private static final List<BossOverride> boss = new ArrayList<>();
	
	public interface BossOverride {
		
		IAetherBoss overrideEntity(IAetherBoss boss);
	
	}
}