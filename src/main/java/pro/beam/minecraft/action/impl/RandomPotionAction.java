package pro.beam.minecraft.action.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class RandomPotionAction extends AbstractBukkitAction {
	private static final List<PotionEffectType> PotionList = new ArrayList<PotionEffectType>();
	private static Random random;

	public RandomPotionAction(Server server) {
		super(server);

		RandomPotionAction.random = new Random();

		RandomPotionAction.PotionList.add(PotionEffectType.ABSORPTION);
		RandomPotionAction.PotionList.add(PotionEffectType.BLINDNESS);
		RandomPotionAction.PotionList.add(PotionEffectType.CONFUSION);
		RandomPotionAction.PotionList.add(PotionEffectType.DAMAGE_RESISTANCE);
		RandomPotionAction.PotionList.add(PotionEffectType.FAST_DIGGING);
		RandomPotionAction.PotionList.add(PotionEffectType.FIRE_RESISTANCE);
		RandomPotionAction.PotionList.add(PotionEffectType.HARM);
		RandomPotionAction.PotionList.add(PotionEffectType.HEAL);
		RandomPotionAction.PotionList.add(PotionEffectType.HEALTH_BOOST);
		RandomPotionAction.PotionList.add(PotionEffectType.HUNGER);
		RandomPotionAction.PotionList.add(PotionEffectType.INCREASE_DAMAGE);
		RandomPotionAction.PotionList.add(PotionEffectType.INVISIBILITY);
		RandomPotionAction.PotionList.add(PotionEffectType.JUMP);
		RandomPotionAction.PotionList.add(PotionEffectType.NIGHT_VISION);
		RandomPotionAction.PotionList.add(PotionEffectType.POISON);
		RandomPotionAction.PotionList.add(PotionEffectType.REGENERATION);
		RandomPotionAction.PotionList.add(PotionEffectType.SATURATION);
		RandomPotionAction.PotionList.add(PotionEffectType.SLOW);
		RandomPotionAction.PotionList.add(PotionEffectType.SLOW_DIGGING);
		RandomPotionAction.PotionList.add(PotionEffectType.SPEED);
		RandomPotionAction.PotionList.add(PotionEffectType.WATER_BREATHING);
		RandomPotionAction.PotionList.add(PotionEffectType.WEAKNESS);
		RandomPotionAction.PotionList.add(PotionEffectType.WITHER);
	}

	@Override
	public void take(Protocol.Report report) {
		Player p = getPlayer();

		if (p != null) {
			
			
			
			if (InteractivePlugin.INSTANCE != null) {
				Bukkit.getScheduler().runTaskLater(InteractivePlugin.INSTANCE, new Runnable() {
					@Override
					public void run() {
						int potionId = RandomPotionAction.GetRandomInRange(0, RandomPotionAction.PotionList.size() - 1);
						p.addPotionEffect(new PotionEffect(RandomPotionAction.PotionList.get(potionId), RandomPotionAction.GetRandomInRange(20, 2400),
								RandomPotionAction.GetRandomInRange(0, 2)));
						
						StringBuffer buffer = new StringBuffer();
						buffer.append(ChatColor.GRAY);
						buffer.append(ChatColor.ITALIC);
						buffer.append("Potion Attack!");

						getPlayer().sendMessage(buffer.toString());
					}
				}, 1);
			}
		}
	}

	private static int GetRandomInRange(int min, int max) {
		return RandomPotionAction.random.nextInt((max - min) + 1) + min;
	}
}
