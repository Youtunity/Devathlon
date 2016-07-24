package net.youtunity.devathlon.daemon.command.commands;

import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.command.CommandContext;
import net.youtunity.devathlon.daemon.command.CommandHandler;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by thecrealm on 24.07.16.
 */
public class SQLCommand implements CommandHandler {

    @Override
    public void execute(CommandContext context) {

        String command = String.join(" ", context.getArgs());

        if(command.startsWith("SELECT")) {
            Daemon.getInstance().getSql().execute(statement -> {

                try(ResultSet res = statement.executeQuery(command)) {
                    ResultSetMetaData rsmd = res.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    while (res.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print(",  ");
                            String columnValue = res.getString(i);
                            System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        }
                        System.out.println("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        if(command.startsWith("CREATE") || command.startsWith("INSERT")) {
            Daemon.getInstance().getSql().execute(statement -> {
                try {
                    statement.executeUpdate(command);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
