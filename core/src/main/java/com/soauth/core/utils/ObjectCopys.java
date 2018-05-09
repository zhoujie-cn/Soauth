package com.soauth.core.utils;

import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @Auther: zhoujie
 * @Date: 2018/5/9 16:35
 * @Description: 对象拷贝类
 */

public class ObjectCopys {

    private static final ConcurrentMap<String, BeanCopier> beanCopierMap = Maps.newConcurrentMap();


    private static final Map<Class<?>, Class<?>>           primitiveMap  = new HashMap<>(9);

    /**
     * 初始化类型数据
     */
    static {
        primitiveMap.put(String.class, String.class);
        primitiveMap.put(Boolean.class, boolean.class);
        primitiveMap.put(Byte.class, byte.class);
        primitiveMap.put(Character.class, char.class);
        primitiveMap.put(Double.class, double.class);
        primitiveMap.put(Float.class, float.class);
        primitiveMap.put(Integer.class, int.class);
        primitiveMap.put(Long.class, long.class);
        primitiveMap.put(Short.class, short.class);
        primitiveMap.put(BigDecimal.class, BigDecimal.class);
    }

    /**
     * @description 判断基本类型
     * @see java.lang.String#TYPE
     * @see java.lang.Boolean#TYPE
     * @see java.lang.Character#TYPE
     * @see java.lang.Byte#TYPE
     * @see java.lang.Short#TYPE
     * @see java.lang.Integer#TYPE
     * @see java.lang.Long#TYPE
     * @see java.lang.Float#TYPE
     * @see java.lang.Double#TYPE
     * @see java.lang.Boolean#TYPE
     * @see char#TYPE
     * @see byte#TYPE
     * @see short#TYPE
     * @see int#TYPE
     * @see long#TYPE
     * @see float#TYPE
     * @see double#TYPE
     * @param clazz
     * @return boolean
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (primitiveMap.containsKey(clazz)) {
            return true;
        }
        return clazz.isPrimitive();
    }


    /**
     * 对象拷贝工具方法
     *
     * @param s 数据
     * @param descType 转换对象的class
     * @return 转换后的对象
     * @param <S> S
     * @param <D> d
     */
    public static <S, D> D maping(S s, Class<D> descType) {
        if (s == null) {
            return null;
        }
        // 动态生成用于复制的类,false为不使用Converter类
        BeanCopier copier = BeanCopier.create(s.getClass(), descType, false);
        D target = newInstance(descType);
        // 执行source到target的属性复制
        copier.copy(s, target, null);
        return target;
    }

    /**
     *二个对象的属性值进行拷贝, 支持对象中包含其他对象
     *
     * @param source 数据
     * @param destType 转换对象的class
     * @return 转换后的对象
     * @param <S> S
     * @param <D> d
     */
    public static <S, D> List<D> maping(final List<S> sources, final Class<D> destType) {
        if (sources==null) {
            return null;
        }
        List<D> targets = new ArrayList<>(sources.size());

        for (S source : sources) {
            D target = maping(source, destType);
            targets.add(target);
        }
        return targets;
    }


    /**
     *二个对象的属性值进行拷贝, 支持对象中包含其他对象
     *
     * @param sources
     * @param destType
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> List<D> mappingAll(final List<S> sources, final Class<D> destType) {
        if (sources==null) {
            return null;
        }
        List<D> targets = new ArrayList<>(sources.size());
        for (S source : sources) {
            D target = mappingAll(source, destType);
            targets.add(target);
        }
        return targets;
    }

    public static <S, T> T mappingAll(S source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T ret = newInstance(target);
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target);
        beanCopier.copy(source, ret, new DeepCopyConverter(target));
        return ret;
    }

    public static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
        String beanCopierKey = generateBeanKey(source, target);
        if (beanCopierMap.containsKey(beanCopierKey)) {
            return beanCopierMap.get(beanCopierKey);
        } else {
            BeanCopier beanCopier = BeanCopier.create(source, target, true);
            beanCopierMap.putIfAbsent(beanCopierKey, beanCopier);
        }
        return beanCopierMap.get(beanCopierKey);
    }

    /**
     * @description 生成两个类的key
     * @param source
     * @param target
     * @return
     * @return String
     */
    public static String generateBeanKey(Class<?> source, Class<?> target) {
        return source.getName() + "@" + target.getName();
    }

    /**
     * @description 创建一个对象
     * @param klass
     * @param <T>
     * @return
     */
    private  static <T> T newInstance(Class<T> klass) {
        try {
            return klass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return  null;
        }

    }

    /**
     * @description 获取方法返回值类型
     * @param tartget
     * @param fieldName
     * @return
     * @return Class<?>
     */
    public static Class<?> getElementType(Class<?> tartget, String fieldName) {
        Class<?> elementTypeClass = null;
        try {
            Type type = tartget.getDeclaredField(fieldName).getGenericType();
            ParameterizedType t = (ParameterizedType) type;
            String classStr = t.getActualTypeArguments()[0].toString().replace("class ", "");
            elementTypeClass = Thread.currentThread().getContextClassLoader().loadClass(classStr);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
        }
        return elementTypeClass;
    }


    @SuppressWarnings("all")
    public static class DeepCopyConverter implements Converter {

        private Class<?> target;

        public DeepCopyConverter(Class<?> target) {
            this.target = target;
        }

        @Override
        public Object convert(Object value, Class targetClazz, Object methodName) {

            if (value instanceof List) {
                List values = (List) value;
                List retList = new ArrayList<>(values.size());
                for (final Object source : values) {
                    Class clazz = getElementType(target, (methodName.toString().replace("set", "")).toLowerCase());
                    retList.add(ObjectCopys.mappingAll(source, clazz));
                }
                return retList;
            } else if (value instanceof Map) {

            } else if (!isPrimitive(targetClazz)) {

                return ObjectCopys.mappingAll(value, targetClazz);
            }
            return value;
        }
    }

}
