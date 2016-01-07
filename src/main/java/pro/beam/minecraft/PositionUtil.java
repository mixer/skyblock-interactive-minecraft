package pro.beam.minecraft;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PositionUtil {
    static public double lenghtDirectionX(double lenght, double direction) {
        return Math.cos((direction * Math.PI) / 180) * lenght;
    }

    static public double lenghtDirectionY(double lenght, double direction) {
        return -Math.sin((direction * Math.PI) / 180) * lenght;
    }

    static public Location getSafeLocationWithin(Player player, int min, double max) {
        Location l = player.getLocation();
        Location pos = player.getLocation();

        for (int i = 0; i < 100; i++) {
            double length = (min + (Math.random() * (max - min)));
            double dir = (Math.random() * 360.0);

            double dx = PositionUtil.lenghtDirectionX(length, dir);
            double dz = PositionUtil.lenghtDirectionY(length, dir);
            Block block = l.getWorld().getHighestBlockAt((int) (pos.getX() + dx), (int) (pos.getZ() + dz));
            if (block.getLocation().getBlockY() > 1) {
                return block.getLocation();
            }
        }
        return null;
    }

    static public Location getLocationWithin(Player player, int min, double max) {
        Location l = player.getLocation();
        Location pos = player.getLocation();

        double length = (min + (Math.random() * (max - min)));
        double dir = (Math.random() * 360.0);

        double dx = PositionUtil.lenghtDirectionX(length, dir);
        double dz = PositionUtil.lenghtDirectionY(length, dir);
        Block block = l.getWorld().getHighestBlockAt((int) (pos.getX() + dx), (int) (pos.getZ() + dz));
        if (block.isEmpty()) {
            Location location = block.getLocation();
            location.setY(player.getLocation().getBlockY());
            return location;
        } else {
            return block.getLocation();
        }
    }

}
