package org.mclarke;

import org.mclarke.command.ChangeDirectory;
import org.mclarke.command.Command;
import org.mclarke.command.Exit;
import org.mclarke.command.ListDirectory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {

    private Map<String, Command> commandMap = new HashMap<>();

    public CommandParser(){
        commandMap.put("ls", new ListDirectory());
        commandMap.put("cd", new ChangeDirectory());
        commandMap.put("exit", new Exit(0));
    }

    public Command parse(String[] args) {

        String root = args[0];

        if(commandMap.containsKey(root)){
            Command command = commandMap.get(root);

            command.setParams(Arrays.copyOfRange(args, 1, args.length));

            return command;
        }
        System.out.println("Command: " + root + " not found");
        return new Exit(1);

    }
}
