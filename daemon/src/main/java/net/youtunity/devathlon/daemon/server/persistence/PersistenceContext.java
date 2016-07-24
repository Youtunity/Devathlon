package net.youtunity.devathlon.daemon.server.persistence;

import net.youtunity.devathlon.daemon.Daemon;

import java.sql.SQLException;

/**
 * Created by thecrealm on 25.07.16.
 */
public class PersistenceContext {

    public static void updateMotd(String server, String motd) {
        Daemon.getInstance().getSql().execute(statement -> {
            try {
                statement.executeUpdate("UPDATE server SET 'motd' = `" + motd + "` WHERE 'name' = `" + server + "`");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
