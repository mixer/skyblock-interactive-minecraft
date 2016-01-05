package pro.beam.minecraft.game.state;

import pro.beam.minecraft.action.TactileInput;
import pro.beam.minecraft.action.impl.CreateBonusIslandAction;
import pro.beam.minecraft.game.Game;

public class EarlyState implements State {
    protected final Game game;

    public EarlyState(Game game) {
        this.game = game;
    }

    @Override
    public void transition(State next) {
        this.game.actions.register(new TactileInput(1, 0.5), new CreateBonusIslandAction(this.game.plugin.getServer()));
    }
}
