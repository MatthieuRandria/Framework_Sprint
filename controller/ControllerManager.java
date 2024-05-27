package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ControllerManager {
    public static String getSimpleFileName(String fileName,String extension){
        return fileName.substring(0,(fileName.length()-extension.length())-1);
    }

    // Get all classes in the directory
    public static ArrayList<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException{
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
    public static ArrayList<Class<?>> getAnnotatedClasses(String packageName,Class<? extends java.lang.annotation.Annotation> annotation) throws ClassNotFoundException, IOException{
        ArrayList<Class<?>> classes=getClasses(packageName); // Alaina lony ny class rehetra possible
        ArrayList<Class<?>> results=new ArrayList<Class<?>>();
        for (Class<?> class1 : classes) {
            if (class1.isAnnotationPresent(annotation)) { // Ho an'izay manana annotation 'AnnotController'
                results.add((class1));  // Ampidirina ao @liste results
            }
        }
        return results;
    }

}
