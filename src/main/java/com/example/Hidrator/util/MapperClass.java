package com.example.Hidrator.util;

import com.example.Hidrator.exception.HidratorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class MapperClass {
    public static Object mapEntityIgnoreNull(Object dto,Object entity) throws HidratorException {
        final BeanWrapper wrapper = new BeanWrapperImpl(dto);
        final PropertyDescriptor[] properties = wrapper.getPropertyDescriptors();
        for (PropertyDescriptor property : properties) {
            String propertyName = property.getName();
            Class<?> propertyType = wrapper.getPropertyType(propertyName);
            if(propertyName.equals("class"))
                continue;
            Object propertyValue = wrapper.getPropertyValue(propertyName);
            if (propertyValue != null) {
                String methodName= "set"+propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                try {
                    Method setter = entity.getClass().getMethod(methodName, propertyType);
                    setter.invoke(entity, propertyValue);
                } catch (NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new HidratorException("Error in mapping entity to dto:" + e.getMessage());
                }
            }
        }
        return entity;
    }

    public static Object mapDtoToEntity(Object dto, Object entity, boolean ignoreNullValues) {
        if (ignoreNullValues) {
            BeanUtils.copyProperties(dto, entity, getNullPropertyNames(dto));
        } else {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
