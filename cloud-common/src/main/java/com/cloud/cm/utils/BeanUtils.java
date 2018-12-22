package com.cloud.cm.utils;

import com.alibaba.fastjson.JSONArray;
import com.cloud.cm.domain.BaseModel;
import org.springframework.util.MultiValueMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象转换处理
 */
public class BeanUtils {
    /**
     * rest用put传参时，MultiValueMap传成bean
     */
    public static Object map2Bean(Class object,
                                  MultiValueMap<String, String> map) throws Exception {
        Field[] fields = object.getDeclaredFields();
        Object classInstance = object.newInstance();
        for (Field f : fields) {
            String fieldName = f.getName();
            if (map.get(fieldName) != null) {
                String fieldValue = map.get(fieldName).get(0);
                if (!"".equals(fieldValue)) {
                    Method method = object.getMethod("set"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1), String.class);
                    method.invoke(classInstance, fieldValue);
                }
            }
        }
        return classInstance;

    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     * @throws SecurityException 不安全的操作
     * @throws NoSuchFieldException 没有此文件
     */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException, NoSuchFieldException, SecurityException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                // by kqy filter中的date类型时的转化
                Class clz = descriptor.getPropertyType();
                if (clz == Date.class) {
                	if(value instanceof Long) {
                		value = new Date((Long)value);
                	} else if(value instanceof String){
                		value = new Date(Long.parseLong((String)value));
                	}
                } else if(List.class.isAssignableFrom(clz) && value instanceof JSONArray) {
                	value = JSONArray.parseArray(value.toString(), (Class)((ParameterizedType) type.getDeclaredField(propertyName).getGenericType()).getActualTypeArguments()[0]);
                } else if(BaseModel.class.isAssignableFrom(clz)){
                	if(value instanceof Map){
                		value = convertMap(clz, (Map) value);
                	}
                }

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     */
    public static Map convertBean(Object bean) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<String, Object> returnMap = new HashMap<>();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    returnMap.put(propertyName, result);
                }
            }
        }
        return returnMap;
    }

    /**
     * 从源对象复制相同属性的值到目标对象
     *
     * @param dest 目标对象
     * @param orig 源对象
     */
    public static void copyProperties(Object dest, Object orig) {
        if (orig == null || dest == null) {
            return;
        }
        try {
            BeanInfo origInfo = Introspector.getBeanInfo(orig.getClass());
            PropertyDescriptor[] origPropertyDescriptors = origInfo.getPropertyDescriptors();
            BeanInfo destInfo = Introspector.getBeanInfo(dest.getClass());
            PropertyDescriptor[] destPropertyDescriptors = destInfo.getPropertyDescriptors();

            Map<String, Method> destPropertyMap = new HashMap<>();
            for (PropertyDescriptor propertyDescriptor : destPropertyDescriptors) {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null && propertyDescriptor.getReadMethod() != null) {
                    destPropertyMap.put(propertyDescriptor.getName(), writeMethod);
                }
            }

            for (PropertyDescriptor propertyDescriptor : origPropertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (destPropertyMap.containsKey(propertyName)) {
                    Object[] args = new Object[1];
                    args[0] = propertyDescriptor.getReadMethod().invoke(orig);
                    destPropertyMap.get(propertyName).invoke(dest, args);
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
