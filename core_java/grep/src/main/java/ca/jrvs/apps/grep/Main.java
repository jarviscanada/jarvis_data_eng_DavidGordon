package ca.jrvs.apps.grep;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length != 0) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepImpl javaGrep = new JavaGrepImpl("", "C:\\Users\\akali\\Documents", "");

        try {
            javaGrep.process();
        } catch (IOException ex) {
            javaGrep.logger.error("Error - Unable to process: ", ex);
        }

        System.out.println("tyesy");
    }
}
