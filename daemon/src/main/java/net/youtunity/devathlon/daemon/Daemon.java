package net.youtunity.devathlon.daemon;

import net.youtunity.devathlon.api.net.NettyServer;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;
import net.youtunity.devathlon.api.protocol.control.ServerStartRequest;
import net.youtunity.devathlon.api.protocol.control.ServerStopRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationRequest;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;
import net.youtunity.devathlon.api.protocol.info.UpdateMOTDRequest;
import net.youtunity.devathlon.daemon.command.CommandManager;
import net.youtunity.devathlon.daemon.command.commands.SQLCommand;
import net.youtunity.devathlon.daemon.command.commands.StopCommand;
import net.youtunity.devathlon.daemon.net.control.ServerStartRequestHandler;
import net.youtunity.devathlon.daemon.net.control.ServerStopRequestHandler;
import net.youtunity.devathlon.daemon.net.info.ServerInformationRequestHandler;
import net.youtunity.devathlon.daemon.net.info.UpdateMOTDRequestHandler;
import net.youtunity.devathlon.daemon.server.ServerRegistry;
import net.youtunity.devathlon.daemon.sql.SQLiteConnection;

/**
 * Created by thecrealm on 23.07.16.
 */
public class Daemon {

    private static Daemon instance;
    private NettyServer server;
    private ServerRegistry serverRegistry;
    private SQLiteConnection sql;
    private CommandManager commandManager;
    private Daemon() {
        // private
    }

    public static Daemon getInstance() {

        if (null == instance) {
            instance = new Daemon();
        }

        return instance;
    }

    // package local
    void init(String[] args) {

        // SQL
        this.sql = new SQLiteConnection();
        this.sql.connect();

        // REGISTRY
        this.serverRegistry = new ServerRegistry();

        // SERVER
        this.server = new NettyServer();

        //control
        this.server.getMessageRegistry().register(ServerStartRequest.class, new ServerStartRequestHandler());
        this.server.getMessageRegistry().register(ServerStopRequest.class, new ServerStopRequestHandler());

        //info
        this.server.getMessageRegistry().register(ServerInformationRequest.class, new ServerInformationRequestHandler());
        this.server.getMessageRegistry().register(UpdateMOTDRequest.class, new UpdateMOTDRequestHandler());
        this.server.getMessageRegistry().register(ServerInformationResponse.class, null);
        this.server.getMessageRegistry().register(ServerStatusUpdate.class, null);

        // Bind
        this.server.bind(Constants.DAEMON_DEFAULT_PORT);

        this.commandManager = new CommandManager();
        this.commandManager.registerCommand("stop", new StopCommand());
        this.commandManager.registerCommand("sql", new SQLCommand());
        this.commandManager.startConsoleReader();
    }

    public NettyServer getServer() {
        return server;
    }

    public void broadcastMessage(Message message) {
        for (MessageHandler handler : getServer().getHandlers()) {
            handler.sendMessage(message);
        }
    }

    public ServerRegistry getServerRegistry() {
        return serverRegistry;
    }

    public SQLiteConnection getSql() {
        return sql;
    }
}
