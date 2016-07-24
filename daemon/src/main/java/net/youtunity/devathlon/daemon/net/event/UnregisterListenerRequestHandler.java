package net.youtunity.devathlon.daemon.net.event;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.event.UnregisterListenerRequest;

/**
 * Created by thecrealm on 24.07.16.
 */
public class UnregisterListenerRequestHandler implements MessageHandler<UnregisterListenerRequest> {

    @Override
    public void handle(Transport transport, UnregisterListenerRequest message) {

    }
}
