package org.mclarke;

import org.mclarke.command.ChangeDirectory;
import org.mclarke.command.Command;
import org.mclarke.command.Exit;
import org.mclarke.command.NewLine;
import org.mclarke.command.ls.ListDirectory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    public static final String CR = "cr";
    private Map<String, Command> commandMap = new HashMap<>();

    //TODO: find a more scalable solution
    public CommandParser() {
        commandMap.put("ls", new ListDirectory());
        commandMap.put("cd", new ChangeDirectory());
        commandMap.put(CR, new NewLine());
        commandMap.put("exit", new Exit(0));
    }

    public Command parse(String[] args) {

        String root = preParse(args);

        if (commandMap.containsKey(root)) {
            Command command = commandMap.get(root);

            command.setParams(Arrays.copyOfRange(args, 1, args.length));

            return command;
        }
        System.out.println("Command: " + root + " not found");
        return new Exit(1);

    }

    private String preParse(String[] args) {

        if (args.length > 0) {
            return args[0];
        } else {
            return CR;
        }
    }
}
