package pro.beam.minecraft.action.impl;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

import java.util.Random;

public class ReplaceCurrentItemAction extends AbstractBukkitAction {
    protected final Random random;

    public ReplaceCurrentItemAction(Server server) {
        super(server);

        this.random = new Random();
    }

    @Override public void take(Protocol.Report report) {
        Player player = this.getPlayer();

        Material material = this.getType();
        int count = Math.max(1, this.random.nextInt(3));

        ItemStack item = new ItemStack(material, count);
        player.getInventory().setItemInHand(item);

        StringBuffer buffer = new StringBuffer();
        buffer.append(ChatColor.GRAY);
        buffer.append(ChatColor.ITALIC);
        buffer.append("You were given ");
        buffer.append(count);
        buffer.append(" ");
        buffer.append(material.name());

        player.sendMessage(buffer.toString());
    }

    private Material getType() {
        Material[] materials = Material.values();
        Material material = materials[this.random.nextInt(materials.length)];

        if (random.nextInt(4) == 1 || this.isBanned(material)) {
            return random.nextBoolean() ? Material.SAPLING : Material.BONE;
        }

        return material;
    }

    private boolean isBanned(Material material) {
        String name = material.name();

        return name.contains("DIAMOND") ||
                name.contains("IRON") ||
                name.contains("GOLD");
    }
}
