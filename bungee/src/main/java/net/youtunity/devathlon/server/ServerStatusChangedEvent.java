package net.youtunity.devathlon.server;

import net.md_5.bungee.api.plugin.Event;
import net.youtunity.devathlon.api.ServerStatus;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStatusChangedEvent extends Event {

    private String server;
    private ServerStatus newStatus;
    private ServerContext context;

    public ServerStatusChangedEvent(String server, ServerStatus newStatus, ServerContext context) {
        this.server = server;
        this.newStatus = newStatus;
        this.context = context;
    }

    public String getServer() {
        return server;
    }

    public ServerStatus getNewStatus() {
        return newStatus;
    }

    public ServerContext getContext() {
        return context;
    }
}
