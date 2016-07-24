package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.daemon.Constants;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by thecrealm on 24.07.16.
 */
public class ServerDirectory {

    private File serverDir;

    public ServerDirectory(File serverDir) {
        this.serverDir = serverDir;
    }

    public boolean exists() {
        return serverDir.exists();
    }

    public boolean mkdirs() {
        return serverDir.mkdirs();
    }

    /**
     * Checks if all necessary files are present if not it returns false.
     */
    public boolean checkFiles() {

        return new File(asFile(), Constants.SPIGOT_JAR_NAME).exists() &&
                new File(asFile(), Constants.SERVER_PROPERTIES_NAME).exists();

    }

    public boolean clear() {
        // all, without data file
        try {
            FileUtils.deleteDirectory(asFile());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Properties loadData() {

        try (FileReader reader = new FileReader(new File(asFile(), Constants.DATA_FILE_NAME))) {
            Properties data = new Properties();
            data.load(reader);
            return data;
        } catch (IOException e) {
            // not exists?
            return new Properties();
        }
    }

    public void saveData(Properties data) {

        try (OutputStream out = new FileOutputStream(new File(asFile(), Constants.DATA_FILE_NAME))) {
            data.store(out, "Data File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyTemplate(String template) {

        this.clear();
        File from = Constants.getTemplateDir(template);

        try {
            FileUtils.copyDirectory(from, asFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File asFile() {
        return this.serverDir;
    }
}
