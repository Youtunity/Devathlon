package net.youtunity.devathlon.daemon.net.interaction;

import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.interaction.AvailableTemplatesRequest;
import net.youtunity.devathlon.api.protocol.interaction.AvailableTemplatesResponse;
import net.youtunity.devathlon.daemon.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by thecrealm on 25.07.16.
 */
public class AvailableTemplatesRequestHandler implements MessageHandler<AvailableTemplatesRequest> {

    @Override
    public void handle(Transport transport, AvailableTemplatesRequest message) {

        List<String> list = Arrays.asList(Constants.TEMPLATE_DIR.list());
        transport.sendMessage(new AvailableTemplatesResponse(list));
    }
}
