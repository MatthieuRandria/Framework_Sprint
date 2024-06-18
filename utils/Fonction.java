package utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import annotation.Argument;
import annotation.Get;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Fonction {
    public static Method[] getAnnotateMethods(Object controller,Class<? extends Annotation> annot){
        return Reflect.getAnnotatedMethod(controller, annot);
    }

    
    public static String urlExtract(String url){
        String[] splitUrl=url.split("/");
        String res="/";
        if(splitUrl.length>2){
            res+=splitUrl[splitUrl.length-2]+"/"+splitUrl[splitUrl.length-1];
        }
        else res+=splitUrl[splitUrl.length-1];
        return res;
        // int length=splitUrl.length;
        // return new Mapping(splitUrl[length-2], splitUrl[length-1]);
    }

    public Method getMethod(Class<?> clazz,String methodName){
        Method [] methods = clazz.getDeclaredMethods();
        Method result = null;
        for (int i=0;i< methods.length;i++){
            if (methods[i].isAnnotationPresent(Get.class) && methods[i].getName().compareTo(methodName)==0) {
                result=methods[i];
            }
        }
        return result;
    }

    public static ArrayList<Object> prepareParameter(Object obj, Method method, HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        Parameter[] argument = method.getParameters();
        ArrayList<Object> result = new ArrayList<>();
        for (int i=0;i<argument.length;i++){
            Annotation arg_annotation = argument[i].getAnnotation(Argument.class);
            String name_annotation = "";
            if(arg_annotation != null){
                name_annotation = ((Argument) arg_annotation).name();
            }
            String realName = null;
            if (request.getParameter(name_annotation) != null){
                realName = name_annotation;
            }
            if (request.getParameter(argument[i].getName()) != null){
                realName = argument[i].getName();
            }
            if(realName != null){
                result.add(request.getParameter(realName));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Fonction manager=new Fonction();
        Method[] methods=getAnnotateMethods(manager, Get.class);
        for (Method method : methods) {
            System.out.println(method.getAnnotation(Get.class).url());
        }
    }
}
