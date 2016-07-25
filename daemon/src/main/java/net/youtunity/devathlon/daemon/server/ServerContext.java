package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;
import net.youtunity.devathlon.api.protocol.info.ServerInformationResponse;
import net.youtunity.devathlon.api.protocol.info.ServerStatusUpdate;
import net.youtunity.devathlon.daemon.Constants;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.persistence.PersistenceContext;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerContext {

    private String server;
    private ServerStatus status = ServerStatus.OFFLINE;
    private ServerProcess process;
    private ServerDirectory directory;

    private String host = "0.0.0.0";
    private String motd = "";
    private int port = -1;

    private int onlinePlayers = 0;

    public ServerContext(String server) {
        this.server = server;
        this.directory = new ServerDirectory(Constants.getServerDir(server));
    }

    public String getServer() {
        return server;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String newHost) {
        this.host = newHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int newPort) {
        this.port = newPort;
    }

    public ServerStatus getStatus() {
        return this.status;
    }

    public void setStatus(ServerStatus newStatus) {
        this.status = newStatus;

        for (MessageHandler handler : Daemon.getInstance().getServer().getHandlers()) {
            handler.sendMessage(new ServerStatusUpdate(server, newStatus));
        }
    }

    public String getMotd() {
        return this.motd;
    }

    public void setMotd(String newMotd) {

        if (!this.motd.isEmpty()) {
            PersistenceContext.updateMotd(server, newMotd);
            Daemon.getInstance().broadcastMessage(new ServerInformationResponse(getServer(), getHost(), getPort(), newMotd, getStatus()));
        }

        this.motd = newMotd;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;

        // Notify the servers
        //Daemon.getInstance().broadcastMessage(new ServerOnlinePlayersUpdate(getServer(), onlinePlayers));
    }

    public ServerDirectory getDirectory() {
        return directory;
    }

    public ServerProcess getProcess() {
        return process;
    }

    public void setRunning(boolean running) {

        if (getStatus() == ServerStatus.RUNNING && !running) {
            stopServer();
        } else if (getStatus() == ServerStatus.OFFLINE || getStatus() == ServerStatus.IDLE && running) {
            starServer();
        }
    }

    private void starServer() {

        if (!directory.checkFiles()) {
            directory.mkdirs();
            directory.copyTemplate("default"); // DEFAULT TEMPLATE, later you can choose :3
        }

        this.process = new ServerProcess(this);
        this.process.start();
        Daemon.getInstance().broadcastMessage(new ServerInformationResponse(server, host, port, motd, ServerStatus.STARTING));
    }

    private void stopServer() {
        this.process.stop();
    }
}
