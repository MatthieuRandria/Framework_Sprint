package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import annotation.Get;

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

    public static void main(String[] args) {
        Fonction manager=new Fonction();
        Method[] methods=getAnnotateMethods(manager, Get.class);
        for (Method method : methods) {
            System.out.println(method.getAnnotation(Get.class).url());
        }
    }
}
