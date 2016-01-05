package pro.beam.minecraft.game.state;

public interface State {
    void transition(State next);
}
