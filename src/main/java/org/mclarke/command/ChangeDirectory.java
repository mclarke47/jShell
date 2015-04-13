package org.mclarke.command;

import org.mclarke.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ChangeDirectory implements Command {

    private static final String PREVIOUS_DIR = "..";
    private static final String HOME = "~";
    private static final String ROOT = "/";

    private String newDirString;
    private Map<String, Supplier<String>> dirAliases = new HashMap<>();
    private Consumer<String> aliasStringConsumer = (s) -> newDirString = dirAliases.get(s).get();
    private Predicate<File> directoryExists = (f) -> f.exists() && f.isDirectory();

    public ChangeDirectory() {
        dirAliases.put(PREVIOUS_DIR, () -> Main.getPwd().getParent());
        dirAliases.put(HOME, () -> System.getProperty("user.home"));
        dirAliases.put(ROOT, File.listRoots()[0]::getAbsolutePath);
    }

    @Override
    public void setParams(String[] params) {

        if (params.length == 0) {
            aliasStringConsumer.accept(HOME);
            return; //TODO This sucks
        }

        if (dirAliases.containsKey(params[0])) {
            aliasStringConsumer.accept(params[0]);
        } else {
            String absoluteNewDirString = Main.getPwd() + File.separator + params[0];
            if (Stream.of(Main.getPwd().listFiles()).anyMatch((f) -> f.getAbsolutePath().equals(absoluteNewDirString) && directoryExists.test(f))) {
                newDirString = absoluteNewDirString;
            } else {
                //TODO fail here
            }
        }
    }

    @Override
    public void execute() {
        File newDir = new File(newDirString);
        if (directoryExists.test(newDir)) {
            Main.setPwd(newDir);
        }
    }
}
