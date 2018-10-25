package pro.beam.minecraft.action;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import pro.beam.minecraft.game.Game;

public abstract class AbstractBukkitAction implements Action {
    protected final Server server;

    public AbstractBukkitAction(Server server) {
        this.server = server;
    }

    protected Player getPlayer() {
        if (!Game.minecraftUsername.isEmpty()) {
            for (Player p : this.server.getOnlinePlayers()) {
                if (p.getName().equals(Game.minecraftUsername)) {
                    return p;
                }
            }
        }

        System.out.println("ERROR! Player Not Found: " + Game.minecraftUsername);
        return null;
    }
}
