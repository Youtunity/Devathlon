package net.youtunity.devathlon.daemon;

import java.io.File;

/**
 * Created by thecrealm on 24.07.16.
 */
public class Constants {

    // DAEMON

    public static int DAEMON_DEFAULT_PORT = 4040;

    // DIRs

    public static File TEMPLATE_DIR = new File("templates");
    public static File SERVERS_DIR = new File("servers");

    public static File getServerDir(String server) {
        return new File(SERVERS_DIR, server);
    }

    public static File getTemplateDir(String template) {
        return new File(TEMPLATE_DIR, template);
    }

    // FILES

    public static final String DATA_FILE_NAME = "data.properties";

    public static final String SPIGOT_JAR_NAME = "spigot.jar";
    public static final String SERVER_PROPERTIES_NAME = "server.properties";

}
