package net.youtunity.devathlon.daemon.net.interaction;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.interaction.ChangeTemplateRequest;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;

/**
 * Created by thecrealm on 25.07.16.
 */
public class ChangeTemplateRequestHandler implements MessageHandler<ChangeTemplateRequest> {

    @Override
    public void handle(Transport transport, ChangeTemplateRequest message) {

        ServerContext context = Daemon.getInstance().getServerRegistry().lookupContext(message.getServer());

        if (context.getStatus() == ServerStatus.RUNNING) {
            context.getProcess().stop();
        }

        context.setStatus(ServerStatus.IDLE);
        context.getDirectory().copyTemplate(message.getTemplate());
        context.setRunning(true);
    }
}
