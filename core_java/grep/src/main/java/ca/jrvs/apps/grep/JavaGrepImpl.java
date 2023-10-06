package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImpl implements JavaGrep {
    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
    private String regex;
    private String rootPath;
    private String outFile;

    public JavaGrepImpl(String regex, String rootPath, String outFile) {
        this.regex = regex;
        this.rootPath = rootPath;
        this.outFile = outFile;
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<String>();

        // List Files
        // Read All Lines of Listed Files
        // Check each line if it containsPattern
        // WriteToFile each matched line to outFile
    }

    @Override
    public List<File> listFiles(String rootDir) {
        return null;
    }

    @Override
    public List<String> readLines(File inputFile) {
        return null;
    }

    @Override
    public boolean containsPattern(String line) {
        return false;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return this.outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
