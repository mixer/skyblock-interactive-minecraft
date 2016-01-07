package pro.beam.minecraft.action;

import com.google.common.collect.Maps;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.interactive.net.packet.Protocol.ProgressUpdate;
import pro.beam.interactive.net.packet.Protocol.ProgressUpdate.Progress.TargetType;
import pro.beam.interactive.robot.Robot;

import java.io.IOException;
import java.util.Map;

public class ActionManager {
    protected final Map<TactileInput, Action> actions;
    protected final Robot robot;

    public ActionManager(Robot robot) {
        this.actions = Maps.newHashMap();
        this.robot = robot;
    }

    public ActionManager register(TactileInput i, Action a) {
        this.actions.put(i, a);
        return this;
    }

    public void dispatch(Protocol.Report report) throws IOException {
        ProgressUpdate.Builder progress = ProgressUpdate.newBuilder();

        for (Map.Entry<TactileInput, Action> e : this.actions.entrySet()) {
            if (!e.getKey().isMet(report)) {
                continue;
            }

            e.getValue().take(report);

            progress.addProgress(ProgressUpdate.Progress.newBuilder()
                    .setTarget(TargetType.TACTILE)
                    .setCode(e.getKey().code)
                    .setFired(true)
                    .build());
        }

        if (progress.getProgressCount() > 0) {
            this.robot.write(progress.build());
        }
    }
}
