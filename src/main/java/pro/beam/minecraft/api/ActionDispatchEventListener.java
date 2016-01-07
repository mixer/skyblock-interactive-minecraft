package pro.beam.minecraft.api;

import pro.beam.interactive.event.EventListener;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.ActionManager;

import java.io.IOException;

public class ActionDispatchEventListener implements EventListener<Protocol.Report> {
    protected final ActionManager actions;

    public ActionDispatchEventListener(ActionManager actions) {
        this.actions = actions;
    }

    @Override
    public void handle(Protocol.Report report) {
        try {
            this.actions.dispatch(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
