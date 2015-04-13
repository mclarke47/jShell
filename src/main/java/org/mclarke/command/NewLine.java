package org.mclarke.command;

public class NewLine implements Command {
    @Override
    public void setParams(String[] params) {

    }

    @Override
    public void execute() {
        System.out.println();
    }
}
