package net.youtunity.devathlon.daemon.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

/**
 * Created by thecrealm on 24.07.16.
 */
public class SQLiteConnection {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void execute(Consumer<Statement> consumer) {

        try (Statement statement = getConnection().createStatement()) {
            consumer.accept(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() {

        if(connection != null) {
            throw new IllegalStateException("SQLite connection already established!");
        }

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:system.db");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
