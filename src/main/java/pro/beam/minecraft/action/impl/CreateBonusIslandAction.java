package pro.beam.minecraft.action.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.util.Vector;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

import java.util.Random;

public class CreateBonusIslandAction extends AbstractBukkitAction {
    protected final Random random;

    public CreateBonusIslandAction(Server server) {
        super(server);

        this.random = new Random();
    }

    @Override
    public void take(Protocol.Report report) {
        Location l = this.getPlayer().getLocation().clone().add(this.getDelta());

        this.fill(l, 0, 3, Material.DIRT);
        this.fill(l, 3, 4, Material.GRASS);

        Location chestLocation = l.clone().add(0, 4, 0);
        // TODO: fill with items
    }

    private void fill(Location l, int a, int b, Material type) {
        for (int dx = 0; dx < 4; dx++) {
            for (int dy = a; dy < b; dy++) {
                for (int dz = 0; dz < 4; dz++) {
                    l.getWorld().getBlockAt(l.clone().add(dx, dy, dz)).setType(type);
                }
            }
        }
    }

    public Vector getDelta() {
        return new Vector(
            this.random.nextInt(64) - 32,
            this.random.nextInt(4) - 2,
            this.random.nextInt(64) - 32
        );
    }
}
