package org.mclarke.command;

import org.mclarke.Main;

import java.io.File;
import java.util.function.Function;
import java.util.stream.Stream;

public class ListDirectory implements Command{

    @Override
    public void setParams(String[] params) {

    }

    @Override
    public void execute() {
        Function<File, String> getName = (f) -> {
            if(f.isDirectory()){
                return f.getName()+"/";
            }
            else{
                return f.getName();
            }
        };

        Stream.of(Main.getPwd().listFiles())
                .filter((f) -> !f.isHidden() && !f.getName().startsWith("."))
                .map(getName).forEach(System.out::println);
    }
}
