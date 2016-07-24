package net.youtunity.devathlon.daemon.net.info;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.info.UpdateMOTDRequest;
import net.youtunity.devathlon.daemon.Daemon;

/**
 * Created by thecrealm on 25.07.16.
 */
public class UpdateMOTDRequestHandler implements MessageHandler<UpdateMOTDRequest> {

    @Override
    public void handle(Transport transport, UpdateMOTDRequest message) {
        Daemon.getInstance().getServerRegistry().lookupContext(message.getServer()).setMotd(message.getMotd());
    }
}
