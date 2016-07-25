package net.youtunity.devathlon.daemon.net.info;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.info.ServerOnlinePlayersUpdate;

/**
 * Created by thecrealm on 25.07.16.
 */
public class ServerOnlinePlayersUpdateHandler implements MessageHandler<ServerOnlinePlayersUpdate> {

    @Override
    public void handle(Transport transport, ServerOnlinePlayersUpdate message) {

    }
}
