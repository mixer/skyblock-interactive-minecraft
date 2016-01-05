package pro.beam.minecraft.action.old;

import com.google.common.collect.ImmutableList;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;

public class SpawnHostileMobAction extends SpawnMobAction {
    public SpawnHostileMobAction(Server server) {
        super(server, ImmutableList.of(
            EntityType.SPIDER, EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SKELETON, EntityType.PRIMED_TNT
        ));
    }
}
