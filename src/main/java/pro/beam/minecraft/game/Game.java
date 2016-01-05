package pro.beam.minecraft.game;

import pro.beam.api.BeamAPI;
import pro.beam.interactive.robot.Robot;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.action.ActionManager;
import pro.beam.minecraft.game.state.State;

public class Game {
    public final InteractivePlugin plugin;
    public final ActionManager actions;

    public BeamAPI beam;
    public Robot robot;
    protected State state;

    public Game(InteractivePlugin plugin) {
        this.plugin = plugin;

        this.actions = new ActionManager();
    }

    public void transition(State newState) {
        this.state.transition(newState);
        this.state = newState;
    }
}
