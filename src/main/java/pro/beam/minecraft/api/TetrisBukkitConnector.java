package pro.beam.minecraft.api;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.configuration.Configuration;
import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.services.impl.UsersService;
import pro.beam.interactive.robot.Robot;
import pro.beam.interactive.robot.RobotBuilder;

import java.net.URI;

public class TetrisBukkitConnector {
    protected final Configuration config;

    public TetrisBukkitConnector(Configuration config) {
        this.config = config;
    }

    public BeamAPI getBeam() {
        URI uri = URI.create(this.config.getString("beam.http.url"));
        String username = this.config.getString("beam.http.username");
        String password = this.config.getString("beam.http.password");

        return new BeamAPI(uri, username, password);
    }

    public BeamUser getUser(BeamAPI beam) throws BeamException {
        String username = this.config.getString("beam.auth.username");
        String password = this.config.getString("beam.auth.password");
        String twoFactor = this.config.getString("beam.auth.twoFactor");

        CheckedFuture<BeamUser, BeamException> task;
        if (twoFactor == null) {
            task = beam.use(UsersService.class).login(username, password);
        } else {
            task = beam.use(UsersService.class).login(username, password, twoFactor);
        }

        return task.checkedGet();
    }

    public ListenableFuture<Robot> getRobot() {
        return this.getRobot(this.getBeam());
    }

    public ListenableFuture<Robot> getRobot(BeamAPI beam) {
        RobotBuilder builder = new RobotBuilder();

        builder.username(this.config.getString("beam.auth.username"));
        builder.password(this.config.getString("beam.auth.password"));
        builder.channel(this.config.getInt("beam.auth.channel"));
        if (this.config.isSet("beam.auth.twoFactor")) {
            builder.twoFactor(this.config.getString("beam.auth.twoFactor"));
        }

        return builder.build(beam);
    }
}
