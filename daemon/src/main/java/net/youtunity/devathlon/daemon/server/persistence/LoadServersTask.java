package net.youtunity.devathlon.daemon.server.persistence;

import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecrealm on 24.07.16.
 */
public class LoadServersTask {

    public static List<ServerContext> execute() {
        List<ServerContext> contexts = new ArrayList<>();
        Daemon.getInstance().getSql().execute(statement -> {
            try (ResultSet res = statement.executeQuery("SELECT * FROM servers")) {
                while (res.next()) {
                    String name = res.getString("name");
                    String motd = res.getString("motd");
                    ServerContext context = new ServerContext(name);
                    context.setMotd(motd);
                    contexts.add(context);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return contexts;
    }
}
