package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.util.ArrayList;
import annotation.Argument;
import annotation.Get;
import annotation.RestApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.Paranamer;


public class Fonction {
    public static Method[] getAnnotateMethods(Object controller,Class<? extends Annotation> annot){
        return Reflect.getAnnotatedMethod(controller, annot);
    }

    
    public static String urlExtract(String url){
        boolean misyArgument=false;
        for (char car : url.toCharArray()) {
            if (car=='?') misyArgument=true;
        }
        String[] splitUrl=url.split("/");
        if(misyArgument){
            String[] args=url.split("\\?");
            splitUrl=args[0].split("/");
        }
        String res="/";
        if(splitUrl.length>2){
            res+=splitUrl[splitUrl.length-2]+"/"+splitUrl[splitUrl.length-1];
        }
        else res+=splitUrl[splitUrl.length-1];
        System.out.println("url found: "+res);
        return res;
        // int length=splitUrl.length;
        // return new Mapping(splitUrl[length-2], splitUrl[length-1]);
    }

    public static Method getMethod(Class<?> clazz,String methodName){
        Method [] methods = clazz.getDeclaredMethods();
        Method result = null;
        for (int i=0;i< methods.length;i++){
            if (methods[i].isAnnotationPresent(Get.class) && methods[i].getName().compareTo(methodName)==0) {
                result=methods[i];
            }
        }
        return result;
    }

    public static Boolean isRestApi(Method method){
        if (method.isAnnotationPresent(RestApi.class)) {
            return true;
        } 
        return false;
    }

    public static String toJson(Object obj){
        Gson gson=new Gson();
        return gson.toJson(gson);
    }

    @SuppressWarnings("deprecation")
    public static ArrayList<Object> prepareParameter(Object obj, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("Preparing args for:"+method.getName());
        Parameter[] argument = method.getParameters();
        ArrayList<Object> result = new ArrayList<>();
        String[] paramName=Fonction.getParameterName(method);

        for (int i=0;i<argument.length;i++){
            Annotation arg_annotation = argument[i].getAnnotation(Argument.class);
            String name_arg = paramName[i];
            if(arg_annotation != null){
                name_arg = ((Argument) arg_annotation).name();
            }else{
                // throw new Exception("ETU002398 : Misy tsy Annoté ny Argument.");
            }
            Class<?> clazz=argument[i].getType();
            // if clazz is a type of MySession
            if(clazz.isAssignableFrom(MySession.class)){
                System.out.println("Instance of MySession started for "+argument[i].getName());
                MySession session=new MySession(request.getSession());
                result.add(session);
            }

            if (Fonction.isObject(clazz) && !clazz.isAssignableFrom(MySession.class)){ // Raha de type object ilay arguments
                System.out.println("Argument: "+name_arg+", Object: "+argument[i].getType());
                Object o=clazz.newInstance();
                System.out.println(o.getClass().getName());
                result.add(Fonction.prepareObject(name_arg,o,request));
                System.out.println("Parameter Prepared for "+method.getName());
            }
            else{
                if (request.getParameter(name_arg)!=null) {
                    result.add(Fonction.castValueOfParameter(request.getParameter(name_arg), argument[i].getType()));
                }
            }
        }
        return result;
    }

    public static String [] getParameterName(Method method){
        // Parameter[] params=method.getParameters();
        // String[] res=new String[params.length];
        // for (int i = 0; i < res.length; i++) {
        //     res[i]=params[i].getName();
        //     System.out.println("Parameter: "+res[i]);
        // }
        Paranamer paranamer = new AdaptiveParanamer();
        String [] parameterName = paranamer.lookupParameterNames(method);
        return parameterName;
    }

    public static Object castValueOfParameter(String value,Class<?> clazz){
        Object result = null;
        if(clazz == String.class){
            result = value;
        }
        // if clazz is type of int
        if(clazz == Integer.class || clazz == int.class){
            result = Integer.parseInt(value);
        }
        if(clazz == Double.class){
            result = Double.parseDouble(value);
        }
        if(clazz == Date.class){
            result = Date.valueOf(value);
        }
        return result;
    }

    public static Object prepareObject (String name,Object obj, HttpServletRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Preparing object: "+obj.getClass().getName());
        Field[] attributs = obj.getClass().getDeclaredFields();
        for (Field attr : attributs){
            String method_name = "set"+Fonction.maj(attr.getName());
            System.out.println(method_name);
            Method method = obj.getClass().getDeclaredMethod(method_name,attr.getType());
            String input_name = name+":"+attr.getName();
            if(request.getParameter(input_name)!=null){
                method.invoke(obj,Fonction.castValueOfParameter(request.getParameter(input_name),attr.getType()));
            }
        }
        return obj;
    }

    public static String maj(String mot){
        return mot.substring(0,1).toUpperCase() + mot.substring(1);
    }

    public static boolean isObject(Class<?> clazz){
        if(clazz == String.class){
            return false;
        }
        if(clazz == Integer.class || clazz == int.class){
            return false;
        }
        if(clazz == Double.class){
            return false;
        }
        if(clazz == Date.class){
            return false;
        }
        return true;
    }

    public static void addSession(Object obj,HttpServletRequest request,HttpServletResponse response) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
        Field [] attributs = obj.getClass().getDeclaredFields();
        PrintWriter out = response.getWriter();
        for (Field attr : attributs){
            if(attr.getType() == MySession.class){
                String method_name = "set"+Fonction.maj(attr.getName());
                MySession session = new MySession(request.getSession());
                Object [] arguments = new Object[1];
                arguments[0] = session;
                out.println("1 : "+attr.getName());
                obj.getClass().getDeclaredMethod(method_name, MySession.class).invoke(obj,session);
                out.println("2 : "+attr.getName());
                break;
            }
        }
    }


    public static void main(String[] args) {
        Fonction manager=new Fonction();
        Method[] methods=getAnnotateMethods(manager, Get.class);
        for (Method method : methods) {
            System.out.println(method.getAnnotation(Get.class).url());
        }
    }
}
