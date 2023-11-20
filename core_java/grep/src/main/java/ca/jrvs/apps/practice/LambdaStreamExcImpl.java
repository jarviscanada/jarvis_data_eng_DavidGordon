package ca.jrvs.apps.practice;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.lang.Math;
public class LambdaStreamExcImpl implements LambdaStreamExc {
    @Override
    public Stream<String> createStrStream(String... strings) {
        return Arrays.stream(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return Arrays.stream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(str -> str.matches(pattern));
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return Arrays.stream(arr);
    }

    @Override
    public IntStream createIntSteam(int[] arr, int start, int end) {
        return Arrays.stream(arr, start, end);
    }

    @Override
    public <T> List<T> toList(Stream<T> stream) {
        return (List<T>)stream.toList();
        // Pre-Java 16
        // (List<T>)Arrays.asList(stream.toArray());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().toList();
        // Pre-Java 16
        // intStream.boxed().collect(Collectors.toList());
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.mapToDouble(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(x -> x % 2 != 0);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return value -> {
            System.out.print(prefix + value + suffix);
        };
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        for (String message : messages) {
            printer.accept(message);
        }
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        getOdd(intStream).forEach(x -> printer.accept(String.valueOf(x)));
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        List<List<Integer>> intList = ints.toList();

        Stream<Integer> emptyStream = Stream.empty();

        for(var list : intList) {
            emptyStream = Stream.concat(emptyStream, list.stream());
        }

        return emptyStream;
    }
}
