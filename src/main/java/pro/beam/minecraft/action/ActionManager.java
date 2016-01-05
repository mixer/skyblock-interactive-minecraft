package pro.beam.minecraft.action;

import com.google.common.collect.Maps;
import org.bukkit.Server;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.minecraft.action.impl.ReplaceCurrentItemAction;

import java.util.Map;

public class ActionManager {
    protected final Map<Input, Action> actions;

    public ActionManager() {
        this.actions = Maps.newHashMap();
    }

    public ActionManager register(Input i, Action a) {
        this.actions.put(i, a);
        return this;
    }

    public void dispatch(Protocol.Report report) {
        for (Map.Entry<Input, Action> e : this.actions.entrySet()) {
            if (!e.getKey().isMet(report)) continue;

            e.getValue().take(report);
        }
    }

    public static ActionManager getDefault(Server server) {
        return new ActionManager().register(new TactileInput(0, 0.5), new ReplaceCurrentItemAction(server));
    }
}
