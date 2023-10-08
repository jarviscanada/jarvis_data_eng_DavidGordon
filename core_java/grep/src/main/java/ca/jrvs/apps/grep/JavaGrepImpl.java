package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        List<File> files = listFiles(rootPath);
        List<String> lines = new ArrayList<String>();

        for(File f : files) {
            lines = readLines(f);
        }

        for(String line : lines) {
            if(containsPattern(line)) {
                matchedLines.add(line);
            }
        }

       // Write matchedLines to outfile
       writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new ArrayList<File>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootDir))) {
            paths.forEach(path -> files.add(new File(path.toString())));
        } catch (IOException ex) {
            logger.error("Error - Invalid Root Path: ", ex);
        } catch (UncheckedIOException ex) {
            logger.error("Error - Access to path denied: ", ex);
        }

        return files;
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
