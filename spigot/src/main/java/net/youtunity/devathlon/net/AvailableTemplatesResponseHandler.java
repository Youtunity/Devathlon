package net.youtunity.devathlon.net;

import net.youtunity.devathlon.DevathlonPlugin;
import net.youtunity.devathlon.api.net.Transport;
import net.youtunity.devathlon.api.net.message.Message;
import net.youtunity.devathlon.api.net.message.MessageHandler;
import net.youtunity.devathlon.api.protocol.interaction.AvailableTemplatesResponse;

/**
 * Created by thecrealm on 25.07.16.
 */
public class AvailableTemplatesResponseHandler implements MessageHandler<AvailableTemplatesResponse> {

    private DevathlonPlugin plugin;

    public AvailableTemplatesResponseHandler(DevathlonPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Transport transport, AvailableTemplatesResponse message) {
        plugin.getAvailableTemplates().clear();
        plugin.getAvailableTemplates().addAll(message.getTemplates());
    }
}
