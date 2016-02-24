package pro.beam.minecraft.action.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class StrikeLightningAction extends AbstractBukkitAction {
    private static final List<Enchantment> EnchantList = new ArrayList<Enchantment>();
    private static Random random;

    public StrikeLightningAction(Server server) {
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
                        p.getWorld().strikeLightning(p.getLocation());
                    }
                }, 1);
            }
        }
    }
}
