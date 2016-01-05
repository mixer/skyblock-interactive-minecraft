package pro.beam.minecraft.action.impl;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class ChangeTimeAction extends AbstractBukkitAction {
	public ChangeTimeAction(Server server) {
		super(server);
	}

	@Override
	public void take(Protocol.Report report) {
		Player p = getPlayer();

		if (p == null) {
			return;
		}

		World w = p.getWorld();

		w.setTime(w.getTime() + 12000);

		StringBuffer buffer = new StringBuffer();
		buffer.append(ChatColor.GRAY);
		buffer.append(ChatColor.ITALIC);
		buffer.append("Changing Time Of Day!");

		getPlayer().sendMessage(buffer.toString());
		
	}
}
