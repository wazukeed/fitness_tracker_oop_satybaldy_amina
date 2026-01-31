package utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingUtils {

    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
