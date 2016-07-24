package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.daemon.Daemon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerContextFactory {

    public static ServerContext create(String server) {

        final AtomicReference<String> motd = new AtomicReference<>(server + "'s Server =)");
        final AtomicBoolean exists = new AtomicBoolean(false);

        Daemon.getInstance().getSql().execute(statement -> {
            try (ResultSet res = statement.executeQuery("SELECT * FROM servers WHERE `name` = '" + server + "'")) {
                if (res.next()) {
                    motd.set(res.getString("motd"));
                    exists.set(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        ServerContext context = new ServerContext(server);
        if (exists.get()) {
            context.setMotd(motd.get());
        } else {
            Daemon.getInstance().getSql().execute(statement -> {
                try {
                    statement.executeUpdate("INSERT INTO servers (`name`, `motd`) VALUES ('" + server + "', '" + motd.get() + "')");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        return context;
    }
}
