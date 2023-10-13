# Introduction
The Grep app is a small Java applications that takes advantage of the Java 8 Stream API, and regex pattern matching to search for specific patterns in files withing a given directory.
The use of Lambda expressions for filtering data makes the underlying code more expressive, and therefore easier to understand. The app uses SLF4J to log any errors to the User, and allows the User to provide an out file where all matches will be written. The Grep app was also Dockerized for easier distribution.

# Quick Start
To get started with the grep app, run the following command 
<br />
```java -cp [path/to/jar] [regex] [rootDirectory] [outFile]```
<br /> 

# Implemenation
## Pseudocode
When the Grep app is launched, it calles a parent method named 'process()'.
The process method goes in order calling other methods in order, namely;

- Read Files
- Read Lines
- Match Lines
- Write Outfile

The result from one method is the input to another, creating a simple program flow that is easy to debug and maintain.
A good diagram looks like this;
<br />
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
        if containsPattern(line)
                matchedLines.add(line)
                writeToFile(matchedLines)
<br />

## Performance Issue
The biggest issue with the Java Grep app was the creation of many different types of Reader and Writer classes for the I/O processes.
Specifically, creating a new Reader for each file. In a large directory, the app could be slow and very memory intensive.
Another problem was the creating of many Lists, when returning a chunk of lines that we read from the User's input. This also weighed heavily on our memory.

# Test
The application was tested by comparing data against the same regex, in multiple applications, numerous times, ensuring that the result was always as expected.

# Deployment
The Grep app was also dockerized for easier distribution. It uses the openjdk:8-alpine base image to run our application. It creates a volume in the current directory called `data` that contains the code, and another volume called `log` to write the results to.

# Improvement
- This app would be simpler to use if it was bundled into a native executable
- A good feature would be the ability to write to stdout, rather than creating a log file.
- A lot of try catch blocks that could degrade performance, this could possibly be improved.
