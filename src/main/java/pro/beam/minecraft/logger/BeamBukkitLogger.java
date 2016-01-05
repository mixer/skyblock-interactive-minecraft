package pro.beam.minecraft.logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class BeamBukkitLogger extends AbstractBukkitLogger {
	public BeamBukkitLogger(JavaPlugin plugin) {
		super(plugin);
	}

	@Override
	protected String format(Level l, String msg) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(ChatColor.DARK_GRAY + "[");
		if (l == Level.NORMAL) {
			buffer.append(ChatColor.GREEN + "+");
		} else if (l == Level.URGENT) {
			buffer.append(ChatColor.RED + "-");
		}
		buffer.append(ChatColor.DARK_GRAY + "]");

		buffer.append(ChatColor.RESET + " ");

		buffer.append(ChatColor.GRAY + msg);

		return buffer.toString();
	}
}
