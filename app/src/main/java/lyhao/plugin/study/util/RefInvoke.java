package lyhao.plugin.study.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by luyanhao on 2019/11/15.
 */
public class RefInvoke {
    /**
     * 调用某个静态方法
     *
     * @param class_name
     *            类名
     * @param method_name
     *            方法名
     * @param pareTyple
     *            参数类型
     * @param pareVaules
     *            参数值
     * @return
     */
    public static Object invokeStaticMethod(String class_name,
                                            String method_name, Class[] pareTyple, Object[] pareVaules) {
        try {
            Class obj_class = Class.forName(class_name);
            Method method = obj_class.getMethod(method_name, pareTyple);
            return method.invoke(null, pareVaules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 调用方法
     *
     * @param class_name
     *            类名
     * @param method_name
     *            方法名
     * @param obj
     *            实例对象
     * @param pareType
     *            参数对象
     * @param pareVaules
     *            参数数组
     * @return
     */
    public static Object invokeMethod(String class_name, String method_name,
                                      Object obj, Class[] pareType, Object[] pareVaules) {
        try {
            Class obj_class = Class.forName(class_name);
            Method method = obj_class.getMethod(method_name, pareType);
            return method.invoke(obj, pareVaules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Object invokeMethod(Class obj_class, Object obj, String method_name, Class[] pareType, Object[] pareVaules) {
        try {
            Method method = obj_class.getMethod(method_name, pareType);
            return method.invoke(obj, pareVaules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 从某个实例中取得某个变量的值
     *
     * @param class_name
     *            类名
     * @param obj
     *            实例
     * @param filedName
     *            字段名
     * @return
     */
    public static Object getFieldOjbect(String class_name, Object obj,
                                        String filedName) {
        try {
            Class obj_class = Class.forName(class_name);
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public static Object getFieldOjbect(Class obj_class, Object obj,
                                        String filedName) {
        try {
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 取得某个类中的静态变量的值
     *
     * @param class_name
     * @param filedName
     * @return
     */
    public static Object getStaticFieldOjbect(String class_name,
                                              String filedName) {
        try {
            Class obj_class = Class.forName(class_name);
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Object getStaticFieldOjbect(Class clazz, Object obj,
                                              String filedName) {
        try {
            Field field = clazz.getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 将某个类中的filedName字段设置为 filedVaule值
     *
     * @param classname
     *            类名
     * @param filedName
     *            字段名
     * @param obj
     *            实例对象
     * @param filedVaule
     *            字段值
     */
    public static void setFieldOjbect(String classname, Object obj, String filedName, Object filedVaule) {
        try {
            Class obj_class = Class.forName(classname);
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, filedVaule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFieldOjbect(Class obj_class, Object obj, String filedName, Object filedVaule) {
        try {
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, filedVaule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置某个静态变量的值
     *
     * @param class_name
     *            类名
     * @param filedName
     *            字段名
     * @param filedVaule
     *            字段值
     */
    public static void setStaticOjbect(String class_name, String filedName,
                                       Object filedVaule) {
        try {
            Class obj_class = Class.forName(class_name);
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(null, filedVaule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
