package br.ufsc.framework.services.util;

import java.util.Comparator;
import java.util.Map;

public class Comparison {

    public static String ASC_ORDER = "ASC_ORDER";
    public static String DESC_ORDER = "DESC_ORDER";

    private Comparison() {

    }

    public static Comparator<Map<String, Object>> getMapListValueComparator(
        final String valueKey, String direction) {

        if (direction.equals(ASC_ORDER)) {
            return new Comparator<Map<String, Object>>() {

                @Override
                @SuppressWarnings("unchecked")

                public int compare(Map<String, Object> o1,
                                   Map<String, Object> o2) {

                    if (o1.get(valueKey) == null)
                        return -1;

                    if (o2.get(valueKey) == null)
                        return 1;

                    return ((Comparable) o1.get(valueKey))
                        .compareTo(o2.get(valueKey));
                }
            };
        }

        return new Comparator<Map<String, Object>>() {

            @Override
            @SuppressWarnings("unchecked")

            public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                if (o1.get(valueKey) == null)
                    return 1;

                if (o2.get(valueKey) == null)
                    return -1;

                return ((Comparable) o2.get(valueKey))
                    .compareTo(o1.get(valueKey));
            }
        };

    }

}
