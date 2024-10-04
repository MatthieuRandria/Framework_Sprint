package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;


public class Reflect{

    public static String getClassName(Object o) {
        return o.getClass().getSimpleName();
    }

    public static String maj(String mot){
        return mot.substring(0,1).toUpperCase() + mot.substring(1);
    }

    public static Field[] getFields(Object o){
        return o.getClass().getDeclaredFields();
    }

    public static String[] getAttributes(Object o) {
        Field[] fields=o.getClass().getDeclaredFields();
        String[] attributes=new String[fields.length];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i]=fields[i].getName();
        }
        return attributes;
    }

    public static Method[] getAnnotatedMethod(Object o,Class<? extends Annotation> annot){
        Method[] methods=o.getClass().getDeclaredMethods();
        Vector<Method> vres=new Vector<Method>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annot)) {
                vres.add(method);
            }
        }
        Object[] objects=vres.toArray();
        Method[] res=new Method[objects.length];
        for (int i = 0; i < res.length; i++) {
            res[i]=(Method)objects[i];
        }
        return res;
    }

    // @Get(url = "/makaAnnot")
    public static Field[] getFieldWithAnnot(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        Vector<Field> listFields=new Vector<Field>();
        for (Field field : fields) {
            if (field.getAnnotations().length>0) {
                listFields.add(field);
            }
        }
        Field[] result=new Field[listFields.size()];
        Object[] fObjects=listFields.toArray();
        for (int i=0;i<result.length;i++) {
            result[i]=(Field)fObjects[i];
        }
        return result;
        
    }

    public static Object executeMethod(Object o,String method,Class<?>... args)throws Exception{
        Method method2=o.getClass().getDeclaredMethod(method, args);
        return method2.invoke(o);
    }

    public static void main(String[] args) {
    }

}