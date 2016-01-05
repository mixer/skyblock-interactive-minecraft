package pro.beam.minecraft.containers;

import org.bukkit.Location;

public class IslandPosition {

	int x;
	int z;

	public IslandPosition(int x, int z) {
		this.x = x;
		this.z = z;
	}

	@Override
	public String toString() {
		return "IslandPosition [x=" + this.x + ", z=" + this.z + "]";
	}

	public double distance(Location locationTo) {
		return Math.sqrt(((locationTo.getBlockX() - this.x) * (locationTo.getBlockX() - this.x))
				+ ((locationTo.getBlockZ() - this.z) * (locationTo.getBlockZ() - this.z)));
	}
}
