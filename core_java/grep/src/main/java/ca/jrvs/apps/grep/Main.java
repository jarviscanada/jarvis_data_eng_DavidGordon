package ca.jrvs.apps.grep;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepImpl javaGrep = new JavaGrepImpl(args[0], args[1], args[2]);

        try {
            javaGrep.process();
        } catch (IOException ex) {
            javaGrep.logger.error("Error - Unable to process: ", ex);
        }
    }
}
