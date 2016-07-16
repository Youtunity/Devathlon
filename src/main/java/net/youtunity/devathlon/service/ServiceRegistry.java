package net.youtunity.devathlon.service;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by thecrealm on 16.07.16.
 */
public class ServiceRegistry {

    private static  Map<Class<?>, Object> services = Maps.newHashMap();

    // SERVICES
    public static void registerService(Class<?> service, Object impl) {
        services.put(service, impl);
        if (impl instanceof Initializable) {
            ((Initializable) impl).init();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T lookupService(Class<T> service) {
        Object impl = services.get(service);
        return (impl == null ? null : ((T) impl));
    }
}
