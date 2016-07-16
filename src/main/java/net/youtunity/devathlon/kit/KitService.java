package net.youtunity.devathlon.kit;

import java.util.Optional;

/**
 * Created by thecrealm on 16.07.16.
 */
public interface KitService {

    Optional<Kit> find(String name);
}
