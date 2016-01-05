package pro.beam.minecraft.action.impl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.PositionUtil;
import pro.beam.minecraft.action.AbstractBukkitAction;

import java.util.Random;

public class CreateBonusIslandAction extends AbstractBukkitAction {
	protected final Random random;

	public CreateBonusIslandAction(Server server) {
		super(server);

		this.random = new Random();
	}

	@Override
	public void take(Protocol.Report report) {

		if (getPlayer() == null) {
			return;
		}

		Location l = getPlayer().getLocation().clone().add(getDelta());
		l.setY(l.getY() - 3);

		fill(l, 0, 3, Material.DIRT);
		fill(l, 3, 4, Material.GRASS);

		Location chestLocation = l.clone().add(2, 4, 2);
		spawnChest(chestLocation);
		

		StringBuffer buffer = new StringBuffer();
		buffer.append(ChatColor.GRAY);
		buffer.append(ChatColor.ITALIC);
		buffer.append("Spawning Bonus Island!");

		getPlayer().sendMessage(buffer.toString());
		InteractivePlugin.INSTANCE.addIsland(l.getBlockX(), l.getBlockZ());
	}

	private void fill(final Location l, final int a, final int b, final Material type) {
		if (InteractivePlugin.INSTANCE != null) {
			Bukkit.getScheduler().runTaskLater(InteractivePlugin.INSTANCE, new Runnable() {
				@Override
				public void run() {
					for (int dx = 0; dx < 4; dx++) {
						for (int dy = a; dy < b; dy++) {
							for (int dz = 0; dz < 4; dz++) {
								l.getWorld().getBlockAt(l.clone().add(dx, dy, dz)).setType(type);
							}
						}
					}
				}
			}, 1);
		}
	}
	private void spawnChest(final Location l) {
		if (InteractivePlugin.INSTANCE != null) {
			Bukkit.getScheduler().runTaskLater(InteractivePlugin.INSTANCE, new Runnable() {
				@Override
				public void run() {
			        Block block = l.getBlock();
			        block.setType(Material.CHEST);
			        Chest chest = (Chest)block.getState();
			        Inventory inv = chest.getInventory();
			        
			        int amount = 3 + CreateBonusIslandAction.this.random.nextInt(6);
			        
			        for (int i = 0; i < amount; i++){
						Material[] materials = Material.values();
						Material material = materials[CreateBonusIslandAction.this.random.nextInt(materials.length)];
						
				        inv.addItem(new ItemStack(material, 1 + CreateBonusIslandAction.this.random.nextInt(3)));
			        }
				}
			}, 1);
		}
	}

	public Vector getDelta() {
		double length = (30 + (Math.random() * (15)));
		double dir = (Math.random() * 360.0);

		double dx = PositionUtil.lenghtDirectionX(length, dir);
		double dz = PositionUtil.lenghtDirectionY(length, dir);

		return new Vector(dx, this.random.nextInt(4) - 2, dz);
	}
}
