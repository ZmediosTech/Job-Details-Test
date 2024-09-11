package com.mbl.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityComparator {

 // Compare simple fields 
    public static Map<String, String> compareFields(Object oldEntity, Object newEntity) {
        Map<String, String> changes = new HashMap<>();
        
        for (Field field : oldEntity.getClass().getDeclaredFields()) {
            field.setAccessible(true);  //  access private fields
            try {
                Object oldValue = field.get(oldEntity);
                Object newValue = field.get(newEntity);

                if (oldValue != null ? !oldValue.equals(newValue) : newValue != null) {
                    changes.put(field.getName(), oldValue + "," + newValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); 
            }
        }
        return changes;
    }
    
    public static Long getId(Object entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (Long) idField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
