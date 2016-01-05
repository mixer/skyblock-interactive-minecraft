package pro.beam.minecraft.logger;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractBukkitLogger implements ILogger {
    protected final JavaPlugin plugin;

    protected AbstractBukkitLogger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void log(Level l, String msg) {
        this.plugin.getServer().broadcastMessage(this.format(l, msg));
    }

    protected abstract String format(Level l, String msg);
}
