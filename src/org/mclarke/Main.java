package org.mclarke;

import org.mclarke.command.Command;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    private static File pwd;
    private static String SP1;

    private static boolean continueRelp = true;
    private static CommandParser commandParser = new CommandParser();
    private static int exitCode = 0;


    public static void main(String[] args) throws UnknownHostException {

        SP1 = System.getProperty("user.name")+"@"+ InetAddress.getLocalHost().getHostName()+": ";
        pwd = new File(System.getProperty("user.home"));
        System.out.print(SP1);
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {

            lineParse(sc.nextLine().split(" "));

            if(!continueRelp){
                break;
            }
            else{
                System.out.print(SP1);
            }
        }
        System.exit(exitCode);
    }

    private static void lineParse(String[] args) {
        if(args.length>0){

            Command command = commandParser.parse(args);
            command.execute();

        }
    }

    public static File getPwd() {
        return pwd;
    }

    public static void setPwd(File pwd) {
        Main.pwd = pwd;
    }

    public static void exitRelp(int exitCode){
        Main.exitCode = exitCode;
        continueRelp = false;
    }
}
