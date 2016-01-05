package pro.beam.minecraft.action.old;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

import java.util.List;
import java.util.Random;

public abstract class SpawnMobAction extends AbstractBukkitAction {
    protected final ImmutableList<EntityType> spawnableTypes;

    public SpawnMobAction(Server server, List<EntityType> spawnableTypes) {
        super(server);

        this.spawnableTypes = ImmutableList.copyOf(spawnableTypes);
    }

    @Override
    public void take(Protocol.Report report) {
        this.spawnMobAround(this.getPlayer());
    }

    private void spawnMobAround(Player player) {
        Entity _ = player.getWorld().spawnEntity(
            this.getLocationWithin(player, 5),
            this.getRandomEntity()
        );
    }

    private EntityType getRandomEntity() {
        int i = new Random().nextInt(this.spawnableTypes.size());
        return this.spawnableTypes.get(i);
    }

    private Location getLocationWithin(Player player, int i) {
        Location l = player.getLocation();

        double dx = Math.min(2, Math.random() * i);
        double dy = Math.min(2, Math.random() * i);
        double dz = Math.min(2, Math.random() * i);

        return l.getWorld().getHighestBlockAt(l.add(dx, dy, dz)).getLocation();
    }
}
