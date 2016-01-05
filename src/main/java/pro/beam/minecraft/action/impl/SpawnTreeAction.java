package pro.beam.minecraft.action.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.PositionUtil;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class SpawnTreeAction extends AbstractBukkitAction {
	public SpawnTreeAction(Server server) {
		super(server);
	}

	@Override
	public void take(Protocol.Report report) {
		final Player p = getPlayer();

		if (p != null) {
			if (InteractivePlugin.INSTANCE != null) {
				Bukkit.getScheduler().runTaskLater(InteractivePlugin.INSTANCE, new Runnable() {
					@Override
					public void run() {
						Location position = PositionUtil.getSafeLocationWithin(p, 2, 5);
						if (position == null) {
							position = PositionUtil.getLocationWithin(p, 4, 6);
						}
						Block block = p.getWorld().getBlockAt(position);
						if ((block.getType() != Material.DIRT) || (block.getType() != Material.GRASS)) {
							block.setType(Material.DIRT);
							position.add(0, 1, 0);
						}

						p.getWorld().generateTree(position, TreeType.TREE);

						StringBuffer buffer = new StringBuffer();
						buffer.append(ChatColor.GRAY);
						buffer.append(ChatColor.ITALIC);
						buffer.append("A Wild Tree Appears!");

						getPlayer().sendMessage(buffer.toString());
					}
				}, 1);
			}
		}
	}
}
