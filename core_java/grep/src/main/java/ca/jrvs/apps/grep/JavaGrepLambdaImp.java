package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImpl {

    public JavaGrepLambdaImp(String regex, String rootPath, String outFile) {
        super(regex, rootPath, outFile);
    }

    @Override
    public void process() throws IOException {
        super.process();
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
        List<String> linesList = new ArrayList<String>();

        try (Stream<String> lines = Files.lines(Path.of(inputFile.getAbsolutePath()))) {
            linesList.addAll(lines.toList());
        } catch (IOException ex) {
            logger.error("Error - Couldn't read file: ", ex);
        }

        return linesList;
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(getRegex());
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        Stream<String> stream = lines.stream();
        Files.write(Paths.get(getOutFile()), (Iterable<String>)stream::iterator);
    }
}
