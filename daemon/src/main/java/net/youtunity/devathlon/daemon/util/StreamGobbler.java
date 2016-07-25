package net.youtunity.devathlon.daemon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by thecrealm on 23.07.16.
 */
public class StreamGobbler extends Thread {

    private InputStream input;

    public StreamGobbler(InputStream input) {
        this.input = input;
    }

    @Override
    public void run() {

        InputStreamReader inputReader = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(inputReader);

        try {

            String line;
            while ((line = reader.readLine()) != null) {
                onLine(line);
            }
        } catch (IOException e) {
            System.err.println("stdout of stream was closed");
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onLine(String line) {

    }
}
