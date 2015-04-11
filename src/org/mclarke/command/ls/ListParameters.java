package org.mclarke.command.ls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public enum ListParameters {
    LONG_LIST{

        @Override
        Function<List<FileListing>, List<FileListing>> getFunction() {
            return (m) -> {

                List<FileListing> directoryList = new ArrayList<>();

                for (FileListing fileListing : m) {
                    LongListFormatter longListFormatter = new LongListFormatter();
                    try {
                        directoryList.add(longListFormatter.format(fileListing));
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                }
                return directoryList;
            };
        }

        @Override
        String getFlag() {
            return "l";
        }
    };
    abstract Function<List<FileListing>, List<FileListing>> getFunction();
    abstract String getFlag();
}
