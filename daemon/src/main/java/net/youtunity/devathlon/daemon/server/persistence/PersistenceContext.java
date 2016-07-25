package net.youtunity.devathlon.daemon.server.persistence;

import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by thecrealm on 25.07.16.
 */
public class PersistenceContext {

    public static ServerContext findContext(String server) {

        ServerContext context = null;
        Connection connection = Daemon.getInstance().getSql().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM servers WHERE name = ?")) {
            statement.setString(1, server);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    String motd = res.getString("motd");
                    context = new ServerContext(server);
                    context.setMotd(motd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return context;
    }

    public static void insertContext(ServerContext context) {
        Connection connection = Daemon.getInstance().getSql().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO servers (`name`, `motd`) VALUES (?, ?)")) {
            statement.setString(1, context.getServer());
            statement.setString(2, context.getMotd());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMotd(String server, String motd) {

        Connection connection = Daemon.getInstance().getSql().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE servers SET motd = ? WHERE name = ?")) {
            statement.setString(1, server);
            statement.setString(2, motd);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
