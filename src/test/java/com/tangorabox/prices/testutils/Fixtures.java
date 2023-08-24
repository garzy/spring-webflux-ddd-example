package com.tangorabox.prices.testutils;

import org.jeasy.random.EasyRandom;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class Fixtures {

    private static final int DEFAULT_LIST_SIZE = 5;

    private static final EasyRandom easyRandom = new EasyRandom();

    private Fixtures() {
    }

    public static List<String> getStringList() {
        return easyRandom.objects(String.class, DEFAULT_LIST_SIZE).toList();
    }

    public static <T> List<T> getList(Class<T> entityClass) {
        return getList(entityClass, DEFAULT_LIST_SIZE);
    }

    public static <T> List<T> getList(Class<T> entityClass, int numValues) {
        final List<T> result = easyRandom.objects(entityClass, numValues).toList();
        result.forEach(entity -> assertThat(entity).describedAs("Error generating fixture for class: " + entityClass.getName())
                .hasNoNullFieldsOrProperties());
        return result;
    }

    public static <T> T getObject(Class<T> entityClass) {
        final T result = easyRandom.nextObject(entityClass);
        assertThat(result).describedAs("Error generating fixture for class: " + entityClass.getName())
                .hasNoNullFieldsOrProperties();
        return result;
    }
}
