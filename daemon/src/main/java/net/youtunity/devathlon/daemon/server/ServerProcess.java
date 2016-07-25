package net.youtunity.devathlon.daemon.server;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.daemon.Constants;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.util.StreamGobbler;

import java.io.IOException;

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

        try {
            this.process = build().start();

            try {

                new StreamGobbler(this.process.getInputStream()) {

                    @Override
                    protected void onLine(String line) {

                        System.out.println(line);

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
            this.process.destroy();
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
        command += "java -Dcom.mojang.eula.agree=true -jar " + Constants.SPIGOT_JAR_NAME + " ";
        command += "-h 0.0.0.0 ";
        int port = Daemon.getInstance().getServerRegistry().getAvailablePort();
        context.setPort(port);
        command += "-p " + port;
        return command.split(" ");
    }
}
