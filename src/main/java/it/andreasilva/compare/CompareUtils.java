package it.andreasilva.compare;

import java.util.Comparator;
import java.util.function.Function;

public class CompareUtils {
    public static <T, U extends Comparable<? super U>> Comparator<T> myComparing(Function<T, U> toComparable){
        return (t1, t2) -> toComparable.apply(t1).compareTo(toComparable.apply(t2));
    }
}
