package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.messages.ServerInformationMessage;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;
import net.youtunity.devathlon.daemon.Constants;
import net.youtunity.devathlon.daemon.Daemon;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerContext {

    private String server;
    private ServerStatus status = ServerStatus.OFFLINE;
    private ServerProcess process;
    private ServerDirectory directory;

    private String host = "0.0.0.0";
    private int port = -1;

    public ServerContext(String server) {
        this.server = server;
        this.directory = new ServerDirectory(Constants.getServerDir(server));
    }

    public String getServer() {
        return server;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public String getHost() {
        return host;
    }

    //package local
    void setHost(String newHost) {
        this.host = newHost;
    }

    public int getPort() {
        return port;
    }

    //package local
    void setPort(int newPort) {
        this.port = newPort;
    }

    //package local
    void setStatus(ServerStatus newStatus) {
        this.status = newStatus;

        for (MessageHandler handler : Daemon.getInstance().getServer().getHandlers()) {
            handler.sendMessage(new ServerStatusMessage(server, newStatus));
        }
    }

    public ServerDirectory getDirectory() {
        return directory;
    }

    public void setRunning(boolean running) {

        if(getStatus() == ServerStatus.RUNNING && !running) {
            stopServer();
        } else if (getStatus() == ServerStatus.OFFLINE && running) {
            starServer();
        }
    }

    private void starServer() {

        if(!directory.checkFiles()) {
            directory.mkdirs();
            directory.copyTemplate("default"); // DEFAULT TEMPLATE, later you can choose :3
        }

        this.process = new ServerProcess(this);
        this.process.start();
        setStatus(ServerStatus.STARTING);

        for (MessageHandler handler : Daemon.getInstance().getServer().getHandlers()) {
            handler.sendMessage(new ServerInformationMessage(server, host, port, server + "' Server"));
        }
    }

    private void stopServer() {
        setStatus(ServerStatus.STOPPING);
        this.process.stop();
    }
}
