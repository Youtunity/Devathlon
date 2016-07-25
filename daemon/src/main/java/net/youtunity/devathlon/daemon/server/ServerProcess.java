package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.daemon.Constants;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.util.StreamGobbler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by thecrealm on 23.07.16.
 */
public class ServerProcess {

    private ServerContext context;
    private Process process;

    public ServerProcess(ServerContext context) {
        this.context = context;
    }

    public void start() {

        context.setStatus(ServerStatus.STARTING);

        try {
            this.process = build().start();

            try {

                new StreamGobbler(this.process.getInputStream()) {

                    @Override
                    protected void onLine(String line) {

                        if (line.contains("INFO]: Done")) {
                            //Server started, yay
                            context.setStatus(ServerStatus.RUNNING);
                        }
                    }
                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

        if (isRunning()) {

            context.setStatus(ServerStatus.STOPPING);
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write("stop");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                //block thread
                this.process.waitFor();
                context.setStatus(ServerStatus.OFFLINE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return process != null && process.isAlive();
    }

    private ProcessBuilder build() {
        return new ProcessBuilder()
                .directory(context.getDirectory().asFile())
                .command(buildArguments());
    }

    private String[] buildArguments() {
        String command = "";
        command += "java -Dcom.mojang.eula.agree=true ";
        command += "-Dserver=" + context.getServer() + " ";
        command += "-jar " + Constants.SPIGOT_JAR_NAME + " ";
        command += "-h 0.0.0.0 ";
        int port = Daemon.getInstance().getServerRegistry().getAvailablePort();
        context.setPort(port);
        command += "-p " + port;
        return command.split(" ");
    }
}
