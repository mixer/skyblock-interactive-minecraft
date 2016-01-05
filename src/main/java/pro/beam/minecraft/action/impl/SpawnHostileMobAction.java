package pro.beam.minecraft.action.impl;

import com.google.common.collect.ImmutableList;

import org.bukkit.Server;
import org.bukkit.entity.EntityType;

public class SpawnHostileMobAction extends SpawnMobAction {
	public SpawnHostileMobAction(Server server) {
		super(server, ImmutableList.of(EntityType.SPIDER, EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SKELETON, EntityType.PRIMED_TNT,
				EntityType.LIGHTNING, EntityType.ENDERMAN, EntityType.SLIME, EntityType.WITCH, EntityType.ENDERMITE));

	}

	@Override
	protected boolean isHostile() {
		return true;
	}
}
