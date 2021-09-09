package es.eoi.common.utils;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CommonUtils {

    private CommonUtils(){
        // Do nothing
    }

    public static <K, V> Map<K, V> filterByKey(Map<K, V> map, Predicate<K> predicate) {
        return map.entrySet()
                .stream()
                .parallel()
                .filter(x -> predicate.test(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
