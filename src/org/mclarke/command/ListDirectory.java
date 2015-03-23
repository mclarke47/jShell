package org.mclarke.command;

import org.mclarke.Main;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListDirectory implements Command {

    private Map<String, Function<Map<String, File>,Map<String, File>>> options = new HashMap<>();
    private List<Function<Map<String, File>,Map<String, File>>> flagsOperations;

    private Function<File, String> getName = (f) -> {
        if (f.isDirectory()) {
            return f.getName() + "/";
        } else {
            return f.getName();
        }
    };

    public ListDirectory(){
        //options.put("l", (s) -> s);
    }

    @Override
    public void setParams(String[] params) {

        Function<String, String> stripHyphen = (s) -> s.replace("-", "");

        //Check for unsupported flags
        if(Stream.of(params).map(stripHyphen).anyMatch((s) -> !options.containsKey(s))){
            throw new IllegalArgumentException();
        }

        flagsOperations = Stream.of(params).map(stripHyphen).map(options::get).collect(Collectors.toList());

    }

    @Override
    public void execute() {

        Map<String, File> displayStringToFile = Stream.of(Main.getPwd().listFiles()).collect(Collectors.toMap(getName, (Function<File, File>) (f) -> f));

        for(Function<Map<String, File>,Map<String, File>> operation : flagsOperations){
            displayStringToFile = operation.apply(displayStringToFile);
        }

        //TODO boiler plate done, implement solution
        //TODO replace map with List of a new type? Map doesn't seem right for some reason

        Stream.of(displayStringToFile.keySet()).forEach(System.out::println);
    }
}
