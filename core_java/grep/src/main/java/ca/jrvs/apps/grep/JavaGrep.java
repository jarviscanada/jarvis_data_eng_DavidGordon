package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Traverse a giveen directory and return all files
     * @param rootDir input directory
     * @return files under the rootDir
     */
    List<File> listFiles(String rootDir);

    /**
     * Read a file and return all the lines
     *
     * Uses FileReader and BufferedReader APIs to read from a file
     *
     * @param inputFile file to be read
     * @return lines
     * @throws IllegalArgumentException if inputFile is not a file
     */
    List<String> readLines(File inputFile);

    /**
     * Check if a line contains the regex pattern provided by user
     * @param line input string
     * @return true if a match is found
     */
    boolean containsPattern(String line);

    /**
     * Writes lines to a file
     *
     * Uses the FileOutputStream, OutputStreamWriter and BufferedWriter APIs
     *
     * @param lines matched line
     * @throws IOException If write failed
     */
    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();
    void setRootPath(String rootPath);
    String getRegex();
    void setRegex(String regex);
    String getOutFile();
    void setOutFile(String outFile);
}
