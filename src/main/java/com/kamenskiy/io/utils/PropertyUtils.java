package com.kamenskiy.io.utils;

import java.io.IOException;
import java.util.Properties;

public final class PropertyUtils {
    private static final Properties PROPERTIES = new Properties(); //Ассоциативный массив с пропертями в него мы загрузим наши пропертя, креды

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var resourceAsStream = PropertyUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private PropertyUtils() {
    }
}
