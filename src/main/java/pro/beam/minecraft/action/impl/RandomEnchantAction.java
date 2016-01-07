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

public class RandomEnchantAction extends AbstractBukkitAction {
    private static final List<Enchantment> EnchantList = new ArrayList<Enchantment>();
    private static Random random;

    public RandomEnchantAction(Server server) {
        super(server);

        RandomEnchantAction.random = new Random();

        RandomEnchantAction.EnchantList.add(Enchantment.ARROW_DAMAGE);
        RandomEnchantAction.EnchantList.add(Enchantment.ARROW_FIRE);
        RandomEnchantAction.EnchantList.add(Enchantment.ARROW_INFINITE);
        RandomEnchantAction.EnchantList.add(Enchantment.ARROW_KNOCKBACK);
        RandomEnchantAction.EnchantList.add(Enchantment.DAMAGE_ALL);
        RandomEnchantAction.EnchantList.add(Enchantment.DAMAGE_ARTHROPODS);
        RandomEnchantAction.EnchantList.add(Enchantment.DAMAGE_UNDEAD);
        RandomEnchantAction.EnchantList.add(Enchantment.DEPTH_STRIDER);
        RandomEnchantAction.EnchantList.add(Enchantment.DIG_SPEED);
        RandomEnchantAction.EnchantList.add(Enchantment.DURABILITY);
        RandomEnchantAction.EnchantList.add(Enchantment.FIRE_ASPECT);
        RandomEnchantAction.EnchantList.add(Enchantment.KNOCKBACK);
        RandomEnchantAction.EnchantList.add(Enchantment.LOOT_BONUS_BLOCKS);
        RandomEnchantAction.EnchantList.add(Enchantment.LOOT_BONUS_MOBS);
        RandomEnchantAction.EnchantList.add(Enchantment.LUCK);
        RandomEnchantAction.EnchantList.add(Enchantment.LURE);
        RandomEnchantAction.EnchantList.add(Enchantment.OXYGEN);
        RandomEnchantAction.EnchantList.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        RandomEnchantAction.EnchantList.add(Enchantment.PROTECTION_EXPLOSIONS);
        RandomEnchantAction.EnchantList.add(Enchantment.PROTECTION_FALL);
        RandomEnchantAction.EnchantList.add(Enchantment.PROTECTION_FIRE);
        RandomEnchantAction.EnchantList.add(Enchantment.PROTECTION_PROJECTILE);
        RandomEnchantAction.EnchantList.add(Enchantment.SILK_TOUCH);
        RandomEnchantAction.EnchantList.add(Enchantment.THORNS);
        RandomEnchantAction.EnchantList.add(Enchantment.WATER_WORKER);

    }

    @Override
    public void take(Protocol.Report report) {
        final Player p = getPlayer();

        if (p != null) {
            if (InteractivePlugin.INSTANCE != null) {
                Bukkit.getScheduler().runTaskLater(InteractivePlugin.INSTANCE, new Runnable() {
                    @Override
                    public void run() {
                        ItemStack hand = p.getItemInHand();

                        if ((hand != null) && (hand.getAmount() > 0)) {
                            RandomEnchantAction.PerformRandomEnchant(hand);

                            StringBuffer buffer = new StringBuffer();
                            buffer.append(ChatColor.GRAY);
                            buffer.append(ChatColor.ITALIC);
                            buffer.append("Enchanting Your Hand Item!");

                            getPlayer().sendMessage(buffer.toString());

                        } else {
                            for (int it = 0; it < p.getInventory().getSize(); it++) {
                                ItemStack i = p.getInventory().getItem(it);
                                if ((i != null) && (i.getAmount() > 0)) {
                                    RandomEnchantAction.PerformRandomEnchant(i);

                                    StringBuffer buffer = new StringBuffer();
                                    buffer.append(ChatColor.GRAY);
                                    buffer.append(ChatColor.ITALIC);
                                    buffer.append("Enchanting A Item In Your Inventory!");

                                    getPlayer().sendMessage(buffer.toString());

                                    break;
                                }
                            }
                        }

                    }
                }, 1);
            }
        }
    }

    private static int GetRandomInRange(int min, int max) {
        return RandomEnchantAction.random.nextInt((max - min) + 1) + min;
    }

    private static void PerformRandomEnchant(ItemStack item) {
        Enchantment e = RandomEnchantAction.EnchantList.get(RandomEnchantAction.GetRandomInRange(0, RandomEnchantAction.EnchantList.size() - 1));
        item.addUnsafeEnchantment(e, RandomEnchantAction.GetRandomInRange(1, 10));
    }
}
