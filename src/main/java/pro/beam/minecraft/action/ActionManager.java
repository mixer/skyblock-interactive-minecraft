package pro.beam.minecraft.action;

import com.google.common.collect.Maps;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.interactive.net.packet.Protocol.ProgressUpdate;
import pro.beam.minecraft.InteractivePlugin;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class ActionManager {
    protected final Map<TactileInput, Action> actions;
    protected final InteractivePlugin plugin;

    protected final Set<Integer> pressedTactiles = new HashSet<Integer>();

    public ActionManager(InteractivePlugin plugin) {
        this.actions = Maps.newHashMap();
        this.plugin = plugin;
    }

    public ActionManager register(TactileInput i, Action a) {
        this.actions.put(i, a);
        return this;
    }

    public void dispatch(Protocol.Report report) throws IOException {
        ProgressUpdate.Builder progress = ProgressUpdate.newBuilder();

        for (Map.Entry<TactileInput, Action> e : this.actions.entrySet()) {
            if (!e.getKey().isMet(report)) {
                if (pressedTactiles.contains(e.getKey().code)) {
                    pressedTactiles.remove(e.getKey().code);
                    progress.addTactile(ProgressUpdate.TactileUpdate.newBuilder()
                        .setId(e.getKey().code)
                        .setProgress(0)
                        .setFired(false)
                        .build()
                    );               
                }
                continue;
            }

            e.getValue().take(report);

            pressedTactiles.add(e.getKey().code);

            progress.addTactile(ProgressUpdate.TactileUpdate.newBuilder()
                    .setId(e.getKey().code)
                    .setProgress(1)
                    .setFired(true)
                    .build()
            );
        }

        if (progress.getTactileCount() + progress.getJoystickCount() > 0) {
            this.plugin.robot.write(progress.build());
        }
    }
}
