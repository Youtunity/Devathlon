package net.youtunity.devathlon.daemon.command.commands;

import net.youtunity.devathlon.daemon.command.CommandContext;
import net.youtunity.devathlon.daemon.command.CommandHandler;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * Created by thecrealm on 25.07.16.
 */
public class UsageCommand implements CommandHandler {

    @Override
    public void execute(CommandContext context) {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Load Average: " + operatingSystemMXBean.getSystemLoadAverage());
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long used = total - Runtime.getRuntime().freeMemory() / 1024 / 1024;
        System.out.println("Memory Usage " + used + "/" + total + " MB");
    }
}
