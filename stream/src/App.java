import java.util.ArrayList;
import static java.util.stream.Collectors.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

  static class Person {

    private List<String> hobbies;

    Person(List<String> hobbies) {
      this.hobbies = hobbies;
    }

    public List<String> getHobbies() {
      return hobbies;
    }

  }

  static class Dummy<T> {
    private T dummyValue;

    public Dummy(T dummyValue) {
      this.dummyValue = dummyValue;
    }

    public T getDummy() {
      return dummyValue;
    }
  }

  public static void main(String[] args) {

    Stream<String> stringStream = Stream.of("wryyl", "kaan", "test", "longtext", "sm", "very long text", "sentence2",
        "sentence2",
        "sentence3");

    Map<Character, Character> encodingMap = new HashMap<>();
    encodingMap.put('a', '0');
    encodingMap.put('e', '1');
    encodingMap.put('i', '2');
    encodingMap.put('o', '3');
    encodingMap.put('u', '4');

    String text = "kanoeios";

    String result = text.chars().mapToObj(c -> (char) c).map(c -> encodingMap.getOrDefault(c, c))
        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
        .toString();

    System.out.println("-----: " + result);

    Stream<String> stringStream2 = Stream.of("wryyl", "kaan", "test", "longtext", "sm", "very long text", "sentence2",
        "sentence2",
        "sentence3");

    stringStream2.map(sentence -> sentence.chars().mapToObj(c -> (char) c)
        .map(c -> encodingMap.getOrDefault(c, c))
        .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append))
        .filter(s -> s.chars().filter(Character::isDigit).count() > 0)
        .forEach(System.out::println);

    /* NOTE: DERS */

    int maxLen = stringStream.map(String::length).parallel().reduce(0, Math::max);
    System.out.println("-----: " + maxLen);

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    int sumOfEvenNumbers = numbers.stream().filter(x -> x % 2 == 0).reduce(0, Integer::sum);
    System.out.println(sumOfEvenNumbers);

    List<String> strings = Arrays.asList("hello", "how", "are", "you", "doing?");
    String finalSentence = strings.stream().skip(1).reduce(strings.get(0),
        (s1, s2) -> s1 + " " + s2);
    System.out.println(finalSentence);
    System.out.println("------------------------");

    // Collecting

    String[] text2 = new String[] { "wo", "hello", "hpw", "qwdqwdqw", "nc" };

    List<String> longestWords = Stream.of(text2)
        .filter(w -> w.length() > 0)
        .distinct()
        .sorted(Comparator.comparing(String::length).reversed())
        .limit(3)
        .collect(toList());

    for (String e : longestWords) {
      System.out.println(e);
    }

    System.out.println("-------------");
    List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

    // NOTE: Joining
    String joinedNames = names.stream()
        .collect(Collectors.joining(", "));

    // Print the joined string
    System.out.println(joinedNames);

    // You can also add a prefix and suffix
    String joinedNamesWithPrefixSuffix = names.stream()
        .collect(Collectors.joining(", ", "Names: ", "."));

    // NOTE: TreeSet
    System.out.println(joinedNamesWithPrefixSuffix);

    TreeSet<Integer> treeSet = new TreeSet<>();

    // Add elements to the TreeSet
    treeSet.add(10);
    treeSet.add(5);
    treeSet.add(15);
    treeSet.add(20);

    System.out.println("TreeSet: " + treeSet);

    System.out.println("First element: " + treeSet.first());
    System.out.println("Last element: " + treeSet.last());

    // Removing an element
    treeSet.remove(15);
    System.out.println("TreeSet after removing 15: " + treeSet);

    // NOTE: Grouping Collectors

    System.out.println("-------------");
    List<String> names2 = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

    // Grouping names by the length of the name
    Map<Integer, List<String>> groupedByLength = names2.stream()
        .collect(Collectors.groupingBy(String::length));

    // Displaying the grouped map
    groupedByLength.forEach((length, nameList) -> {
      System.out.println("Names with length " + length + ": " + nameList);
    });

    System.out.println("-------------");

    // NOTE: Partitioning!

    List<Integer> numbers2 = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10);

    // Partitioning numbers into even and odd using partitioningBy
    Map<Boolean, List<Integer>> partitionedNumbers = numbers2.stream()
        .collect(Collectors.partitioningBy(num -> num % 2 == 0));

    // Displaying the partitioned map
    System.out.println("Even numbers: " + partitionedNumbers.get(true));
    System.out.println("Odd numbers: " + partitionedNumbers.get(false));
    System.out.println("-------------");
    Map<Integer, List<String>> wordsMapByLen = Stream.of("hello", "hi", "ne", "apple")
        .collect(Collectors.groupingBy(String::length, TreeMap::new, toList()));

    System.out.println(wordsMapByLen);
    System.out.println("-------------");

    // NOTE: coolstuf, bak buna

    Person[] personsArray = { new Person(Arrays.asList("Coding")),
        new Person(Arrays.asList("Cooking", "Meditating")),
        new Person(Arrays.asList("Singing", "Meditating")) };
    Map<String, Long> personmap = Arrays.stream(personsArray)
        .map(Person::getHobbies)
        .flatMap(List::stream)
        .collect(groupingBy(e -> e, counting()));

    System.out.println(personmap);

    System.out.println("-------------");

    // NOTE: FREQUENCY
    String txta = "originalş text";

    Map<String, Long> frequency = Stream.of(txta)
        .collect(groupingBy(
            w -> w,
            counting()));

    System.out.println("-------------");

    // NOTE: coolstuf2
    Stream<String> someWords = Stream.of("hey", "how", "hey", "are", "hey", "are", "you?");
    List<String> freqSorted = someWords
        .collect(groupingBy(w -> w, counting()))
        .entrySet().stream()
        .sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed().thenComparing(Map.Entry::getKey))
        .map(e -> e.getValue() + ":" + e.getKey())
        .collect(toList());

    System.out.println(freqSorted);
    System.out.println("-------------");
    // NOTE: CollectingAndThen
    List<Integer> numbers3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    // Use collectingAndThen to collect numbers into a list and then count them
    int count = numbers3.stream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.collectingAndThen(
            Collectors.toList(), // Collector to collect numbers into a list
            List::size // Function to count the number of elements in the list
        ));

    System.out.println("Count of numbers: " + count);
    System.out.println("-------------");

    // NOTE: Mapping

    List<Integer> squaredNumbers = numbers3.stream()
        .collect(Collectors.mapping(
            n -> n * n,
            Collectors.toList()));

    System.out.println("Squared numbers: " + squaredNumbers);
    System.out.println("-------------");

    // NOTE: Collecting And Then Mapping

    List<String> names4 = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

    // Use mapping() to transform each name to its length, and collect the lengths
    // Then use collectingAndThen to calculate the sum of lengths
    int totalLength = names4.stream()
        .collect(Collectors.collectingAndThen(
            Collectors.mapping(
                String::length, // Function to transform each name to its length
                Collectors.toList() // Collector to collect the lengths into a list
            ),
            list -> list.stream().mapToInt(Integer::intValue).sum() // Function to calculate the sum of lengths
        ));

    // Print the total length of names
    System.out.println("Total length of names: " + totalLength);
  }
}
