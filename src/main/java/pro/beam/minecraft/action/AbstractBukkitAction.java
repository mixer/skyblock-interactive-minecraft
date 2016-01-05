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
		Player ret = null;

		if (Game.minecraftUsername.isEmpty() == false) {
			for (Player p : this.server.getOnlinePlayers()) {
				if (p.getName().equals(Game.minecraftUsername)) {
					ret = p;
				}
			}
		}

		if (ret == null) {
			System.out.println("ERROR! Player Not Found: " + Game.minecraftUsername);
		}

		return ret;
	}
}
