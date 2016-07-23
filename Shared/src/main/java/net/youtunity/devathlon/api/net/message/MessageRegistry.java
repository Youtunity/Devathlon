package net.youtunity.devathlon.api.net.message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by thecrealm on 23.07.16.
 */
public class MessageRegistry {

    private Map<Class<?>, MessageHandler<?>> messageHandlerMap = new ConcurrentHashMap<>();
    private Map<Integer, Class<?>> idMessageMap = new ConcurrentHashMap<>();

    public void register(Class<?> message, MessageHandler<?> handler) {
        idMessageMap.put(lookupId(message), message);

        if(handler != null) {
            messageHandlerMap.put(message, handler);
        }
    }

    public int lookupId(Class<?> message) {
        return message.getName().hashCode();
    }

    @SuppressWarnings("unchecked")
    public <M extends Message> MessageHandler<M> lookupHandler(Class<?> message) {
        return ((MessageHandler<M>) messageHandlerMap.get(message));
    }

    public Message createMessage(int id) {

        Class<?> message = idMessageMap.get(id);

        if(null == message) {
            throw new IllegalStateException("Message with id '" + id + "' does not exists");
        }

        try {
            return ((Message) message.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
