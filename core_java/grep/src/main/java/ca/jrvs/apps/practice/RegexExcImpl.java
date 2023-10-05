package ca.jrvs.apps.practice;

import java.util.regex.*;

public class RegexExcImpl implements RegexExc {
    public boolean matchJpeg(String filename) {
        Pattern pattern = Pattern.compile("[.]jpe?g$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(filename).find();
    }

    public boolean matchIp(String ip) {
        Pattern pattern = Pattern.compile("^\\d\\d?\\d?[.]\\d\\d?\\d?[.]\\d\\d?\\d?[.]\\d\\d?\\d?$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(ip).find();
    }

    public boolean isEmptyLine(String line) {
        Pattern pattern = Pattern.compile("^\\s+$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(line).find();
    }
}