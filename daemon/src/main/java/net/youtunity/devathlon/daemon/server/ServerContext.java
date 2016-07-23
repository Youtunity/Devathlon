package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.messages.ServerInformationMessage;
import net.youtunity.devathlon.api.messages.ServerStatusMessage;
import net.youtunity.devathlon.api.net.pipeline.MessageHandler;
import net.youtunity.devathlon.daemon.Daemon;

import java.io.File;
import java.util.Properties;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerContext {

    private String server;
    private ServerStatus status = ServerStatus.OFFLINE;
    private ServerProcess process;

    private String host = "0.0.0.0";
    private int port = -1;

    public ServerContext(String server) {
        this.server = server;
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
            handler.getChannelHandlerContext().writeAndFlush(new ServerStatusMessage(server, newStatus));
        }
    }

    File getDirectory() {
        File file = new File("servers/" + server);
        if(!file.mkdirs()) {
            return file;
        } else {
            throw new IllegalStateException("server context folder cannot be created.");
        }
    }

    public void setRunning(boolean running) {

        if(getStatus() == ServerStatus.RUNNING && !running) {
            stopServer();
        } else if (getStatus() == ServerStatus.OFFLINE && running) {
            starServer();
        }
    }

    private void starServer() {
        this.process = new ServerProcess(this);
        this.process.start();
        setStatus(ServerStatus.STARTING);

        for (MessageHandler handler : Daemon.getInstance().getServer().getHandlers()) {
            System.out.println("SEND INFO MSG");
            handler.getChannelHandlerContext().writeAndFlush(new ServerInformationMessage(server, host, port, "Its a motd"));
        }
    }

    private void stopServer() {
        setStatus(ServerStatus.STOPPING);
        this.process.stop();
    }
}
