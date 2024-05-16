package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import annotation.AnnotController;

public class ControllerManager {
    public static String getSimpleFileName(String fileName,String extension){
        return fileName.substring(0,(fileName.length()-extension.length())-1);
    }

    // Get all classes in the directory
    public static ArrayList<Class<?>> getClasses(String packageName ) throws ClassNotFoundException, IOException{
        ArrayList<Class<?>> classes= new ArrayList<Class<?>>();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        URL ressource= classloader.getResource(path);

        if(ressource == null){
            return classes;
        }

        File packageDirectory = new File(ressource.getFile().replace("%20", " "));

        for (File file : packageDirectory.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(ControllerManager.getClasses(packageName +"."+file.getName()));
            }else{
                String className= packageName+"."+ControllerManager.getSimpleFileName(file.getName(), "class");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }    

    // Check foreach class if it is a controller
    public static ArrayList<Class<?>> getControllerClasses(String packageName) throws ClassNotFoundException, IOException{
        ArrayList<Class<?>> classes=getClasses(packageName); // Alaina lony ny class rehetra possible
        ArrayList<Class<?>> controllers=new ArrayList<Class<?>>();
        for (Class<?> class1 : classes) {
            if (class1.isAnnotationPresent(AnnotController.class)) { // Ho an'izay manana annotation 'AnnotController'
                controllers.add((class1));  // Ampidirina ao @liste controllers
            }
        }
        return controllers;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Class<?>> controller=ControllerManager.getControllerClasses("controller");
            for (Class<?> class1 : controller) {
                System.err.println(class1.getSimpleName());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
