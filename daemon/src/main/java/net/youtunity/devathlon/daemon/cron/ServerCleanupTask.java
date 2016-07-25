package net.youtunity.devathlon.daemon.cron;

import net.youtunity.devathlon.api.ServerStatus;
import net.youtunity.devathlon.daemon.Daemon;
import net.youtunity.devathlon.daemon.server.ServerContext;
import net.youtunity.devathlon.daemon.server.ServerProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by thecrealm on 25.07.16.
 */
public class ServerCleanupTask extends Thread {

    private List<String> round = new ArrayList<>();

    @Override
    public void run() {

        while (true) {

            //check list
            for (String server : round) {
                ServerContext context = Daemon.getInstance().getServerRegistry().lookupContext(server);
                if (context.getOnlinePlayers() == 0) {
                    ServerProcess process = context.getProcess();
                    if (process != null) {
                        process.stop();
                        System.out.println("Stopped server '" + context.getServer() + "'");
                    }
                }
            }

            round.clear();

            //fill round
            List<Map.Entry<String, ServerContext>> running = Daemon.getInstance().getServerRegistry().getServers().entrySet().stream()
                    .filter(entry -> entry.getValue().getStatus() == ServerStatus.RUNNING)
                    .collect(Collectors.toList());

            for (Map.Entry<String, ServerContext> entry : running) {
                if (entry.getValue().getOnlinePlayers() == 0) {
                    round.add(entry.getKey());
                    System.out.println("Added '" + entry.getKey() + "' to cleanup queue");
                }
            }

            try {
                Thread.sleep(2 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
