package net.youtunity.devathlon.daemon;

import java.io.File;

/**
 * Created by thecrealm on 24.07.16.
 */
public class Constants {

    // DAEMON

    public static final String DATA_FILE_NAME = "data.properties";

    // DIRs
    public static final String SPIGOT_JAR_NAME = "spigot.jar";
    public static final String SERVER_PROPERTIES_NAME = "server.properties";
    public static int DAEMON_DEFAULT_PORT = 4040;
    public static File TEMPLATE_DIR = new File("templates");

    // FILES
    public static File SERVERS_DIR = new File("servers");

    public static File getServerDir(String server) {
        return new File(SERVERS_DIR, server);
    }

    public static File getTemplateDir(String template) {
        return new File(TEMPLATE_DIR, template);
    }

}
