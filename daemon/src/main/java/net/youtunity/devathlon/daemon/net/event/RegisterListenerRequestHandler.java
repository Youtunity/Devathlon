package net.youtunity.devathlon.daemon.net.event;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.event.RegisterListenerRequest;

/**
 * Created by thecrealm on 24.07.16.
 */
public class RegisterListenerRequestHandler implements MessageHandler<RegisterListenerRequest> {

    @Override
    public void handle(Transport transport, RegisterListenerRequest message) {

    }
}
