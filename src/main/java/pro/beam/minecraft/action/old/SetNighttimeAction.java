package pro.beam.minecraft.action.old;

import org.bukkit.Server;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class SetNighttimeAction extends AbstractBukkitAction {
    public SetNighttimeAction(Server server) {
        super(server);
    }

    @Override public void take(Protocol.Report report) {
        this.getPlayer().getWorld().setTime(2);
    }
}
