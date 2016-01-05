package pro.beam.minecraft.action.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.action.AbstractBukkitAction;

import java.util.Random;

public class ReplaceCurrentItemAction extends AbstractBukkitAction {
	protected final Random random;
	protected int countDown;
	protected int taskId;
	protected boolean active;

	public ReplaceCurrentItemAction(Server server) {
		super(server);

		this.random = new Random();
		this.countDown = 0;
		this.taskId = 0;
		this.active = false;
	}

	@Override
	public void take(Protocol.Report report) {
		Player player = getPlayer();

		if ((player == null) || this.active) {
			return;
		}

		this.active = true;
		this.countDown = 3;

		if (InteractivePlugin.INSTANCE != null) {
			this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(InteractivePlugin.INSTANCE, new Runnable() {
				@Override
				public void run() {

					if (ReplaceCurrentItemAction.this.countDown <= 0) {
						Bukkit.getScheduler().cancelTask(ReplaceCurrentItemAction.this.taskId);
						swapItem(player);
						ReplaceCurrentItemAction.this.active = false;
					} else {
						StringBuffer buffer = new StringBuffer();
						buffer.append(ChatColor.GRAY);
						buffer.append(ChatColor.ITALIC);
						buffer.append("Changing Item in ");
						buffer.append(ReplaceCurrentItemAction.this.countDown);

						player.sendMessage(buffer.toString());

						ReplaceCurrentItemAction.this.countDown--;
					}
				}
			}, 20, 20);
		}

	}

	private void swapItem(Player player) {
		int count = Math.max(1, this.random.nextInt(3));

		Material material = getType();
		ItemStack item = new ItemStack(material, count);
		player.getInventory().setItemInHand(item);

		while ((player.getInventory().getItemInHand() == null) || (player.getInventory().getItemInHand().getAmount() <= 0)) {
			material = getType();
			item = new ItemStack(material, count);
			player.getInventory().setItemInHand(item);

		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(ChatColor.GRAY);
		buffer.append(ChatColor.ITALIC);
		buffer.append("You were given a random item!");

		player.sendMessage(buffer.toString());
	}

	private Material getType() {
		Material[] materials = Material.values();
		Material material = materials[this.random.nextInt(materials.length)];

		if ((this.random.nextInt(4) == 1) || isBanned(material)) {
			return this.random.nextBoolean() ? Material.SAPLING : Material.BONE;
		}

		return material;
	}

	private boolean isBanned(Material material) {
		String name = material.name();

		return name.contains("DIAMOND") || name.contains("POTION");
	}
}
