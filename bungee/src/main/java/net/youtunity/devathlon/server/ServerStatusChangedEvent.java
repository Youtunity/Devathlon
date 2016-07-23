package net.youtunity.devathlon.server;

import net.md_5.bungee.api.plugin.Event;
import net.youtunity.devathlon.api.ServerStatus;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerStatusChangedEvent extends Event {

    private ServerStatus newStatus;
    private ServerContext context;

    public ServerStatusChangedEvent(ServerStatus newStatus, ServerContext context) {
        this.newStatus = newStatus;
        this.context = context;
    }

    public ServerStatus getNewStatus() {
        return newStatus;
    }

    public ServerContext getContext() {
        return context;
    }
}
