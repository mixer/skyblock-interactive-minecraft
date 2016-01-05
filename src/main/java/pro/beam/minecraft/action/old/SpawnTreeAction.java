package pro.beam.minecraft.action.old;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.AbstractBukkitAction;

public class SpawnTreeAction extends AbstractBukkitAction {
    public SpawnTreeAction(Server server) {
        super(server);
    }

    @Override public void take(Protocol.Report report) {
        Player player = this.getPlayer();
        Block target = player.getTargetBlock(ImmutableSet.of(Material.AIR), 20);

        player.getWorld().generateTree(target.getLocation().add(0, 1, 0), TreeType.TREE);
    }
}
