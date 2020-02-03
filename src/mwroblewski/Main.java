package mwroblewski;

import javafx.scene.shape.Path;
import mwroblewski.model.Node;
import mwroblewski.model.Tree;
import mwroblewski.service.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Encoder<Character> encoder = new Encoder<>(getCharsSupplier("Pan_Tadeusz_Ksiega_1.txt"));
        String encoded = encoder.encode();
        writeToFile(encoded,"Pan_Tadeusz_Ksiega_1_encoded.txt");
        Decoder<Character> decoder = new Decoder<>();
        String decoded = decoder.decode(encoder.getTree(), encoder.encode()).stream().map(Object::toString).collect(joining());
        writeToFile(decoded, "Pan_Tadeusz_Ksiega_1_decoded.txt");
        System.out.println("Współczynnik kompresji: " + (16.0 * decoded.length())/encoded.length());
    }

    public static Supplier<LinkedList<Character>> getCharsSupplier(String fileName) {
        return itemsSupplier(fileName, l -> l.chars().mapToObj(c -> (char) c));
    }

    public static <T> Supplier<LinkedList<T>> itemsSupplier(String fileName, Function<String, Stream<T>> mapper) {
        return () -> {
            try {
                return Files.readAllLines(Paths.get(new File(fileName).getPath()))
                        .stream()
                        .flatMap(mapper)
                        .collect(toCollection(LinkedList::new));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    public static void writeToFile(String source, String fileName) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(source);
        writer.close();
    }
}
