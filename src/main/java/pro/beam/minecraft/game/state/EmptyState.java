package pro.beam.minecraft.game.state;

import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.TactileInput;
import pro.beam.minecraft.action.impl.CreateBonusIslandAction;
import pro.beam.minecraft.action.impl.ReplaceCurrentItemAction;
import pro.beam.minecraft.api.ActionDispatchEventListener;
import pro.beam.minecraft.game.Game;
import pro.beam.minecraft.logger.BeamChatLogger;
import pro.beam.minecraft.logger.ILogger;

import java.util.concurrent.ExecutionException;

public class EmptyState implements State {
    protected final Game game;

    public EmptyState(Game game) {
        this.game = game;
    }

    @Override
    public void transition(State next) {
        this.game.beam = this.game.plugin.getBridge().getBeam();

        try {
            this.game.robot = this.game.plugin.getBridge().getRobot(this.game.beam).get();
            this.game.robot.on(Protocol.Report.class, new ActionDispatchEventListener(this.game.actions));
        } catch (InterruptedException | ExecutionException e) {
            e.getCause().printStackTrace();
        }

        try {
            new BeamChatLogger(this.game.plugin).start();
        } catch (Exception _) { this.game.plugin.logger.log(ILogger.Level.URGENT, _.getMessage()); }

        this.game.actions.register(new TactileInput(0, 0.5), new ReplaceCurrentItemAction(this.game.plugin.getServer()));
    }
}
