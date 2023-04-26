package org.coca.agent.base;

import java.lang.reflect.Field;

public class FieldGetter {
    public static <T> T getValue(Object instance,String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field filed = instance.getClass().getDeclaredField(fieldName);
        filed.setAccessible(true);
        return (T)filed.get(instance);
    }

    public static <T> T getParentFieldValue(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(instance);
    }
    public static <T> T get2LevelParentFieldValue(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getSuperclass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(instance);
    }
}
