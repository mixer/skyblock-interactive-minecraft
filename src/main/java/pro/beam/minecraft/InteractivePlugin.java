package pro.beam.minecraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.interactive.robot.Robot;
import pro.beam.minecraft.action.TactileInput;
import pro.beam.minecraft.action.impl.ChangeTimeAction;
import pro.beam.minecraft.action.impl.CreateBonusIslandAction;
import pro.beam.minecraft.action.impl.RandomEnchantAction;
import pro.beam.minecraft.action.impl.RandomPotionAction;
import pro.beam.minecraft.action.impl.ReplaceCurrentItemAction;
import pro.beam.minecraft.action.impl.SpawnFriendlyMobAction;
import pro.beam.minecraft.action.impl.SpawnHostileMobAction;
import pro.beam.minecraft.action.impl.SpawnTreeAction;
import pro.beam.minecraft.api.ActionDispatchEventListener;
import pro.beam.minecraft.api.TetrisBukkitConnector;
import pro.beam.minecraft.containers.IslandPosition;
import pro.beam.minecraft.game.Game;
import pro.beam.minecraft.logger.BeamBukkitLogger;
import pro.beam.minecraft.logger.BeamChatLogger;
import pro.beam.minecraft.logger.ILogger;

public class InteractivePlugin extends JavaPlugin implements Listener {
    protected final Game game;
    public final ILogger logger;

    public BeamAPI beam;
    public Robot robot;

    public InteractivePlugin() {
        this.game = new Game(this);

        // @formatter:off
        this.game.actions
            .register(new TactileInput(0, 0.5),	new ReplaceCurrentItemAction(this.game.plugin.getServer()))
            .register(new TactileInput(1, 0.5), new CreateBonusIslandAction(this.game.plugin.getServer()))
            .register(new TactileInput(2, 0.5),	new SpawnHostileMobAction(this.game.plugin.getServer()))
            .register(new TactileInput(3, 0.5),	new SpawnFriendlyMobAction(this.game.plugin.getServer()))
            .register(new TactileInput(4, 0.5),	new SpawnTreeAction(this.game.plugin.getServer()))
            .register(new TactileInput(5, 0.5),	new RandomEnchantAction(this.game.plugin.getServer()))
            .register(new TactileInput(6, 0.5),	new ChangeTimeAction(this.game.plugin.getServer()))
            .register(new TactileInput(7, 0.5),	new RandomPotionAction(this.game.plugin.getServer()));
        // @formatter:on

        this.logger = new BeamBukkitLogger(this);
    }

    public static InteractivePlugin INSTANCE;

    FileConfiguration islandsConfig;

    List<String> islandsStrings;
    List<IslandPosition> islands;

    boolean mainlandReached;

    @Override
    public void onEnable() {
        InteractivePlugin.INSTANCE = this;
        connectToBeam();
        getServer().getPluginManager().registerEvents(this, this);
        this.logger.log(ILogger.Level.NORMAL, "Beam Interactive enabled... ");

        this.islandsConfig = YamlConfiguration.loadConfiguration(new File(new File(getServer().getWorldContainer(), "world"), "islands.yml"));

        this.islandsStrings = this.islandsConfig.getStringList("islands");
        this.islands = new ArrayList<IslandPosition>();

        for (String island : this.islandsStrings) {
            String[] islandSplit = island.split(",");
            this.islands.add(new IslandPosition(Integer.parseInt(islandSplit[0]), Integer.parseInt(islandSplit[1])));
        }

        this.mainlandReached = this.islandsConfig.getBoolean("mainlandReached");

        System.out.println("Beam Interactive enabled... done");
    }

    public void addIsland(int x, int z) {
        this.islandsStrings.add(x + "," + z);
        this.islands.add(new IslandPosition(x, z));

        this.islandsConfig.set("islands", this.islandsStrings);

        try {
            this.islandsConfig.save(new File(new File(getServer().getWorldContainer(), "world"), "islands.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        InteractivePlugin.INSTANCE = null;
        this.logger.log(ILogger.Level.NORMAL, "Beam Interactive disabled...");
    }

    protected void connectToBeam() {
        this.beam = getBridge().getBeam();

        try {
            this.robot = getBridge().getRobot(this.beam, getBridge().getUser(this.beam)).get();
            this.robot.on(Protocol.Report.class, new ActionDispatchEventListener(this.game.actions));
        } catch (InterruptedException | ExecutionException | BeamException e) {
            e.getCause().printStackTrace();
        }

        try {
            new BeamChatLogger(this).start();
        } catch (Exception e) {
            this.logger.log(ILogger.Level.URGENT, e.getMessage());
        }
    }

    public TetrisBukkitConnector getBridge() {
        return new TetrisBukkitConnector(getConfig());
    }

    private IslandPosition spawn = new IslandPosition(15, 275);

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location locationTo = event.getTo();
        IslandPosition closest = this.spawn;
        double distanceToSpawn = this.spawn.distance(locationTo);

        if (this.mainlandReached) {
            return;
        } else if (distanceToSpawn > 210) {
            this.mainlandReached = true;
            this.islandsConfig.set("mainlandReached", true);
            try {
                this.islandsConfig.save(new File(new File(getServer().getWorldContainer(), "world"), "islands.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        double distanceTo = distanceToSpawn;
        for (IslandPosition island : this.islands) {
            double distance = island.distance(locationTo);
            if (distance < distanceTo) {
                distanceTo = distance;
                closest = island;
            }
        }

        double distanceFrom = closest.distance(event.getFrom());
        if (distanceFrom < distanceTo) {
            if (distanceTo >= 40) {
                event.setCancelled(true);
            }
        }
    }
}
