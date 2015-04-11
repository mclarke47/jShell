package org.mclarke.command.ls;

import java.io.File;

public class FileListing {

    private String fileName;
    private File file;

    public FileListing(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
