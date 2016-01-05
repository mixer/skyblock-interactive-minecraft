package pro.beam.minecraft.action.impl;

import com.google.common.collect.ImmutableList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.PositionUtil;
import pro.beam.minecraft.action.AbstractBukkitAction;

import java.util.List;
import java.util.Random;

public abstract class SpawnMobAction extends AbstractBukkitAction {
	protected final ImmutableList<EntityType> spawnableTypes;

	public SpawnMobAction(Server server, List<EntityType> spawnableTypes) {
		super(server);

		this.spawnableTypes = ImmutableList.copyOf(spawnableTypes);
	}

	@Override
	public void take(Protocol.Report report) {
		Player p = getPlayer();

		if (p != null) {

			String faction = isHostile() ? "A Very Mean" : "A Really Nice";

			StringBuffer buffer = new StringBuffer();
			buffer.append(ChatColor.GRAY);
			buffer.append(ChatColor.ITALIC);
			buffer.append("Spawning " + faction + " Mob!");

			p.sendMessage(buffer.toString());

			if (InteractivePlugin.INSTANCE != null) {
				Bukkit.getScheduler().runTaskLater(InteractivePlugin.INSTANCE, new Runnable() {
					@Override
					public void run() {
						p.getWorld().spawnEntity(SpawnMobAction.this.getLocationWithin(p, 5), SpawnMobAction.this.getRandomEntity());
					}
				}, 1);
			}
		}
	}

	private EntityType getRandomEntity() {
		int i = new Random().nextInt(this.spawnableTypes.size());
		return this.spawnableTypes.get(i);
	}

	private Location getLocationWithin(Player player, int i) {
		Location position = PositionUtil.getSafeLocationWithin(player, 2, i);
		if (position != null) {
			return position;
		} else {
			return player.getLocation();
		}
	}

	protected abstract boolean isHostile();
}
