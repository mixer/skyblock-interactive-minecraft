package pro.beam.minecraft.logger;

import org.bukkit.ChatColor;
import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.BeamChat;
import pro.beam.api.resource.chat.BeamChatConnectable;
import pro.beam.api.resource.chat.events.EventHandler;
import pro.beam.api.resource.chat.events.IncomingMessageEvent;
import pro.beam.api.resource.chat.events.data.IncomingMessageData;
import pro.beam.api.resource.chat.methods.AuthenticateMessage;
import pro.beam.api.services.impl.ChatService;
import pro.beam.minecraft.InteractivePlugin;
import pro.beam.minecraft.api.TetrisBukkitConnector;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BeamChatLogger extends BeamBukkitLogger {
    protected final TetrisBukkitConnector bridge;
    protected final BeamAPI beam;
    protected final int channel;

    public BeamChatLogger(InteractivePlugin plugin) {
        super(plugin);

        this.bridge = plugin.getBridge();
        this.beam = this.bridge.getBeam();
        this.channel = plugin.getConfig().getInt("beam.auth.channel");
    }

    public void start() throws ExecutionException, InterruptedException, BeamException {
        BeamUser user = this.bridge.getUser(this.beam);
        BeamChat chat = this.beam.use(ChatService.class).findOne(this.channel).get();
        BeamChatConnectable connectable = chat.makeConnectable(this.beam);

        connectable.connectBlocking();

        connectable.send(AuthenticateMessage.from(user.channel, user, chat.authkey));
        connectable.on(IncomingMessageEvent.class, incomingMessageHandler());
    }

    private EventHandler<IncomingMessageEvent> incomingMessageHandler() {
        return new EventHandler<IncomingMessageEvent>() {
            @Override
            public void onEvent(IncomingMessageEvent e) {
                BeamChatLogger logger = BeamChatLogger.this;
                logger.log(Level.NORMAL, logger.formatChat(e.data));
            }
        };
    }

    private String formatChat(IncomingMessageData data) {
        StringBuffer buf = new StringBuffer();

        buf.append(getColorFor(data.userRoles));
        buf.append(data.userName);

        buf.append(ChatColor.RESET);
        buf.append(ChatColor.DARK_GRAY + ": ");
        buf.append(data.asString());

        return buf.toString();
    }

    private ChatColor getColorFor(List<BeamUser.Role> userRoles) {
        if (userRoles.contains(BeamUser.Role.OWNER) || userRoles.contains(BeamUser.Role.ADMIN)) {
            return ChatColor.DARK_RED;
        } else if (userRoles.contains(BeamUser.Role.DEVELOPER)) {
            return ChatColor.GOLD;
        } else if (userRoles.contains(BeamUser.Role.GLOBAL_MOD)) {
            return ChatColor.DARK_GREEN;
        } else if (userRoles.contains(BeamUser.Role.MOD)) {
            return ChatColor.GREEN;
        } else if (userRoles.contains(BeamUser.Role.SUBSCRIBER)) {
            return ChatColor.AQUA;
        } else if (userRoles.contains(BeamUser.Role.PRO)) {
            return ChatColor.DARK_PURPLE;
        } else if (userRoles.contains(BeamUser.Role.BANNED)) {
            return ChatColor.BLACK;
        }

        return ChatColor.GRAY;
    }
}
