package utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import annotation.AnnotController;
import annotation.Get;
import controller.ControllerManager;

public class Mapping {
    String className;
    String methodName;
    
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Mapping(String className, String methodName) {
        this.setClassName(className);
        this.setMethodName(methodName);
    }

    public Mapping() {
    }

    @SuppressWarnings("deprecation")
    public HashMap<String,Mapping> scanController(ArrayList<Class<?>> controllers) throws Exception{
        HashMap<String,Mapping> map=new HashMap<String,Mapping>();
        for (Class<?> class1 : controllers) {
            Method[] methods=Reflect.getAnnotatedMethod(class1.newInstance(), Get.class);
            System.out.println("Scanning "+class1.getSimpleName());
            if (methods.length>0) {
                // Ampidiriana ao @Map izay methode annote
                for (Method method : methods) {
                    System.out.println("method found: "+method.getAnnotation(Get.class).url());
                    map.put(method.getAnnotation(Get.class).url(), new Mapping(class1.getName(), method.getName()));
                }
            }else{
                System.out.println("No method annotated in "+class1.getName());
            }
        }
        return map;
    }

    @SuppressWarnings("deprecation")
    public String executeMethod(String className,String methodName) throws Exception{
        Object obj=Class.forName(className).newInstance();
        String res=Reflect.executeMethod(obj,methodName).toString();
        return res;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Class<?>> controllers=ControllerManager.getAnnotatedClasses("utils", AnnotController.class);
            Mapping mapping=new Mapping();
            HashMap<String,Mapping> map=mapping.scanController(controllers);
            Mapping mapping2=map.get("/test");
            System.out.println(mapping2.getClassName());
            System.out.println(mapping2.getMethodName());
            System.out.println(mapping2.executeMethod(mapping2.getClassName(), mapping2.getMethodName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
}
