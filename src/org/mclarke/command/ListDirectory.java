package org.mclarke.command;

import org.mclarke.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListDirectory implements Command {

    private final static Function<Map<String, File>, Map<String, File>> LongList = (m) -> {

        Map<String, File> newMap = new HashMap<>();

        for (String key : m.keySet()) {
            String ownerName;
            String size;
            Instant lastModified;
            BasicFileAttributes attrs;
            Path path = Paths.get(m.get(key).getAbsolutePath());

            try {
                attrs = Files.readAttributes(path, BasicFileAttributes.class);
                ownerName = Files.getOwner(path).getName();
                lastModified = attrs.lastModifiedTime().toInstant();
                size = "" + attrs.size();

            } catch (IOException e) {
                throw new RuntimeException();
            }

            if (ownerName.contains("\\")) {
                ownerName = ownerName.split("\\\\")[1];
            }

            LocalDateTime ldt = LocalDateTime.ofInstant(lastModified, ZoneId.systemDefault());

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd HH:mm");

            String line = ownerName + " " + " group " + "  " + size + " " + dateTimeFormatter.format(ldt) + " " + m.get(key).getName() + "\n";

            newMap.put(line, m.get(key));
        }
        return newMap;
    };
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
        options.put("l", LongList);
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

        List<String> formattedFiles = new ArrayList<>();
        formattedFiles.addAll(displayStringToFile.keySet());

        formattedFiles.stream().forEach(System.out::print);
    }
}
