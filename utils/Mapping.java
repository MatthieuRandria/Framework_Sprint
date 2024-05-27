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
                    map.put(method.getAnnotation(Get.class).url(), new Mapping(class1.getSimpleName(), method.getName()));
                }
            }else{
                System.out.println("Tsy misy");
            }
        }
        return map;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Class<?>> controllers=ControllerManager.getAnnotatedClasses("utils", AnnotController.class);
            Mapping mapping=new Mapping();
            HashMap<String,Mapping> map=mapping.scanController(controllers);
            System.out.println(map.get("/makaAnnot").getClassName());
            System.out.println(map.get("/makaAnnot").getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
}
