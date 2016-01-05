package pro.beam.minecraft.action.impl;

import com.google.common.collect.ImmutableList;

import org.bukkit.Server;
import org.bukkit.entity.EntityType;

public class SpawnFriendlyMobAction extends SpawnMobAction {
	public SpawnFriendlyMobAction(Server server) {
		super(server,
				ImmutableList.of(EntityType.CHICKEN, EntityType.COW, EntityType.PIG, EntityType.OCELOT, EntityType.RABBIT, EntityType.HORSE, EntityType.WOLF));
	}

	@Override
	protected boolean isHostile() {
		return false;
	}
}
