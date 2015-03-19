package org.mclarke.command;

import org.mclarke.Main;

import java.io.File;

public class ChangeDirectory implements Command {

    private String newDirString;

    @Override
    public void setParams(String[] params) {
        newDirString = params[0];
    }

    @Override
    public void execute() {
        File newDir = new File(newDirString);
        if(newDir.exists()&& newDir.isDirectory()){
            Main.setPwd(newDir);
        }
    }
}
