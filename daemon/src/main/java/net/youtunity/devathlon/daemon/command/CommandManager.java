package net.youtunity.devathlon.daemon.command;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by thecrealm on 24.07.16.
 */
public class CommandManager {

    private Map<String, CommandHandler> registeredCommands = new ConcurrentHashMap<>();

    public void registerCommand(String command, CommandHandler handler) {
        registeredCommands.put(command, handler);
    }

    public CommandHandler lookupCommandHandler(String command) {
        return registeredCommands.get(command);
    }

    public void startConsoleReader() {

        Scanner scanner = new Scanner(System.in);

        String line;
        while ((line = scanner.nextLine()) != null) {
            String args[] = line.split(" ");
            CommandHandler commandHandler = lookupCommandHandler(args[0]);

            if(commandHandler != null) {
                commandHandler.execute(new CommandContext(args));
            }
        }
    }
}
