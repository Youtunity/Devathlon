package net.youtunity.devathlon.daemon.command.commands;

import net.youtunity.devathlon.daemon.command.CommandContext;
import net.youtunity.devathlon.daemon.command.CommandHandler;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * Created by thecrealm on 25.07.16.
 */
public class UsageCommand implements CommandHandler {

    @Override
    public void execute(CommandContext context) {

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("Load Average: " + operatingSystemMXBean.getSystemLoadAverage());
        
    }
}
