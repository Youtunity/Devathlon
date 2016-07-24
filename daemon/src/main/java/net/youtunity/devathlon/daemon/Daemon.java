package net.youtunity.devathlon.daemon;

import net.youtunity.devathlon.api.protocol.control.ServerStartRequest;
import net.youtunity.devathlon.api.protocol.control.ServerStopRequest;
import net.youtunity.devathlon.api.protocol.event.RegisterListenerRequest;
import net.youtunity.devathlon.api.protocol.event.UnregisterListenerRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;
import net.youtunity.devathlon.api.net.NettyServer;
import net.youtunity.devathlon.daemon.command.CommandManager;
import net.youtunity.devathlon.daemon.command.commands.SQLCommand;
import net.youtunity.devathlon.daemon.net.control.ServerStartRequestHandler;
import net.youtunity.devathlon.daemon.net.control.ServerStopRequestHandler;
import net.youtunity.devathlon.daemon.net.event.RegisterListenerRequestHandler;
import net.youtunity.devathlon.daemon.net.event.UnregisterListenerRequestHandler;
import net.youtunity.devathlon.daemon.server.ServerRegistry;
import net.youtunity.devathlon.daemon.sql.SQLiteConnection;

/**
 * Created by thecrealm on 23.07.16.
 */
public class Daemon {

    private static Daemon instance;

    public static Daemon getInstance() {

        if(null == instance) {
            instance = new Daemon();
        }

        return instance;
    }

    private NettyServer server;
    private ServerRegistry serverRegistry;
    private SQLiteConnection sql;
    private CommandManager commandManager;

    // package local
    void init(String[] args) {

        // REGISTRY
        this.serverRegistry = new ServerRegistry();

        // SQL
        this.sql = new SQLiteConnection();
        this.sql.connect();

        // SERVER
        this.server = new NettyServer();

        // In - control
        this.server.getMessageRegistry().register(ServerStartRequest.class, new ServerStartRequestHandler());
        this.server.getMessageRegistry().register(ServerStopRequest.class, new ServerStopRequestHandler());

        // In - event
        this.server.getMessageRegistry().register(RegisterListenerRequest.class, new RegisterListenerRequestHandler());
        this.server.getMessageRegistry().register(UnregisterListenerRequest.class, new UnregisterListenerRequestHandler());

        // In - info
        this.server.getMessageRegistry().register(ServerInformationRequest.class, null);

        // Out - info
        this.server.getMessageRegistry().register(ServerInformationResponse.class, null);
        this.server.getMessageRegistry().register(ServerStatusUpdate.class, null);

        // Bind
        this.server.bind(Constants.DAEMON_DEFAULT_PORT);

        this.commandManager = new CommandManager();
        this.commandManager.registerCommand("sql", new SQLCommand());

        this.commandManager.startConsoleReader();
    }

    public NettyServer getServer() {
        return server;
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    public SQLiteConnection getSql() {
        return sql;
    }
}
