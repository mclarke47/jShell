package org.mclarke.command.ls;

import org.mclarke.Main;
import org.mclarke.command.Command;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListDirectory implements Command {

    private Map<String, Function<List<FileListing>,List<FileListing>>> options = new HashMap<>();
    private List<Function<List<FileListing>,List<FileListing>>> flagsOperations;

    private Function<File, String> getName = (f) -> {
        if (f.isDirectory()) {
            return f.getName() + "/";
        } else {
            return f.getName();
        }
    };

    private Function<File, FileListing> fileToFileListing = (f) -> new FileListing(getName.apply(f), f);

    public ListDirectory(){

        for(ListParameters listParameters : ListParameters.values()){
            options.put(listParameters.getFlag(), listParameters.getFunction());
        }
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

        List<FileListing> displayStringToFile =  getPresentWorkingDirectory().map(fileToFileListing).collect(Collectors.toList());

        for(Function<List<FileListing>,List<FileListing>> operation : flagsOperations){
            displayStringToFile = operation.apply(displayStringToFile);
        }

        fileListingToPrintList(displayStringToFile).stream().forEach((s) -> System.out.print(s + " "));
    }

    private Stream<File> getPresentWorkingDirectory() {
        return Stream.of(Main.getPwd().listFiles());
    }

    private List<String> fileListingToPrintList(List<FileListing> fileListings){

        return fileListings.stream().map(FileListing::getFileName).collect(Collectors.toList());
    }
}
