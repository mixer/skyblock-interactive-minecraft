package pro.beam.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import pro.beam.api.BeamAPI;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.interactive.robot.Robot;
import pro.beam.minecraft.api.ActionDispatchEventListener;
import pro.beam.minecraft.api.TetrisBukkitConnector;
import pro.beam.minecraft.game.Game;
import pro.beam.minecraft.logger.BeamBukkitLogger;
import pro.beam.minecraft.logger.BeamChatLogger;
import pro.beam.minecraft.logger.ILogger;

import java.util.concurrent.ExecutionException;

public class InteractivePlugin extends JavaPlugin {
    protected final Game game;
    public final ILogger logger;


    public InteractivePlugin() {
        this.game = new Game(this);
        this.logger = new BeamBukkitLogger(this);
    }

    @Override public void onEnable() {
        this.connectToBeam();
        this.logger.log(ILogger.Level.NORMAL, "Beam Interactive enabled...");
    }

    @Override public void onDisable() {
        this.logger.log(ILogger.Level.NORMAL, "Beam Interactive disabled...");
    }

    protected void connectToBeam() {

    }

    public TetrisBukkitConnector getBridge() {
        return new TetrisBukkitConnector(this.getConfig());
    }
}
