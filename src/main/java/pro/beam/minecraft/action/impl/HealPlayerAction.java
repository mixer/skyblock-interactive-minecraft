package pro.beam.minecraft.action.impl;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class HealPlayerAction extends AbstractBukkitAction {
    // HEARTS represents the number of Minecraft "HP" per unit heart.
    private static final int HEARTS = 2;

    public HealPlayerAction(Server server) {
        super(server);
    }

    @Override public void take(Protocol.Report report) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }

        player.setHealth(player.getHealth()+(1 * HEARTS));
    }
}
