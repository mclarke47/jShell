package org.mclarke.command;

public interface Command {
    void setParams(String[] params);
    void execute();
}
