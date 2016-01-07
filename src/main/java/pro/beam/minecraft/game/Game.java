package pro.beam.minecraft.game;

import pro.beam.api.BeamAPI;
import pro.beam.interactive.robot.Robot;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.action.ActionManager;

public class Game {
    public final InteractivePlugin plugin;
    public final ActionManager actions;
    public static String minecraftUsername;

    public BeamAPI beam;
    public Robot robot;

    public Game(InteractivePlugin plugin) {
        this.plugin = plugin;

        this.actions = new ActionManager();
    }
}
