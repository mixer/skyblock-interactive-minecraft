package pro.beam.minecraft.action;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public abstract class AbstractBukkitAction implements Action {
    protected final Server server;

    public AbstractBukkitAction(Server server) {
        this.server = server;
    }

    protected Player getPlayer() {
        return this.server.getOnlinePlayers().iterator().next();
    }
}
