package org.mclarke.command;

import org.mclarke.Main;

public class Exit implements Command {

    private int exitCode = 0;
    public Exit(int exitCode){
        this.exitCode = exitCode;
    }

    @Override
    public void setParams(String[] params) {

    }

    @Override
    public void execute() {
        Main.exitRelp(exitCode);
    }


}
