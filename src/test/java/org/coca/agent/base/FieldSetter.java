package org.coca.agent.base;

import java.lang.reflect.Field;

public class FieldSetter {
    public static <T> void setValue(Object instance, String fieldName,
                                    T value) throws IllegalAccessException, NoSuchFieldException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance,value);
    }
    public static <T> void setStaticValue(Class instance, String fieldName,
                                          T value) throws IllegalAccessException, NoSuchFieldException {
        Field field = instance.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }
}
