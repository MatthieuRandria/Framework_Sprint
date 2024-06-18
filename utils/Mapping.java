package utils;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import annotation.AnnotController;
import annotation.Get;
import controller.ControllerManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
                    String url=method.getAnnotation(Get.class).url();
                    System.out.println("method found: "+url);
                    if (map.get(url)==null) {
                        map.put(url, new Mapping(class1.getName(), method.getName()));
                    }else{
                        throw new Exception("Duplicate URL :"+url);
                    }
                }
            }else{
                System.out.println("No method annotated in "+class1.getName());
            }
        }
        return map;
    }

    @SuppressWarnings("deprecation")
    public Object executeMethod(String className,String methodName,Class<?>... args) throws Exception{
        Object obj=Class.forName(className).newInstance();
        Object res=Reflect.executeMethod(obj,methodName,args);
        return res;
    }

    public void dispatchModelView(ModelView modelView,HttpServletRequest request,HttpServletResponse response) throws Exception{
        HashMap<String,Object> data=modelView.getData();
        for (Map.Entry<String,Object> values : data.entrySet()) {
            request.setAttribute(values.getKey(),values.getValue());
        }
        try{
            RequestDispatcher dispatcher=request.getServletContext().getRequestDispatcher("/"+modelView.getUrl()); // Mandefa any @.jsp miaraka @data 
            dispatcher.forward(request, response);
        }catch(Exception e){
            PrintWriter out=response.getWriter();
            out.println("Error: "+e.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    public void proceedMethod(PrintWriter out,Mapping mapping,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Method method=(Class.forName(mapping.getClassName())).getMethod(mapping.getMethodName());
        
        Object result=null;
        // Raha misy arguments:
        if (method.getParameters().length > 0) {
            Object object=(Class.forName(mapping.getClassName())).newInstance();
            ArrayList<Object> methodParam = new ArrayList<>();
            methodParam=Fonction.prepareParameter(object, method, request, response);
            result=method.invoke(object, methodParam.toArray(new Object[]{}));
        }
        else{ // Raha tsy mila argument ilay methode :
            result=mapping.executeMethod(mapping.getClassName(), mapping.getMethodName());
        }

        if (result==null) throw new Exception("Error with method :");

        if (method.getReturnType().equals(ModelView.class)) {
            // Si elle return ModelView, charger les data stockés dans son HashMap comme attribut et les stocker par request.setAttribute()
            ModelView modelView=(ModelView) result;
            this.dispatchModelView(modelView, request, response);

        }else if (method.getReturnType().equals(String.class)) { 
            // Si la methode return String printer le.
            out.println(result.toString());
        
        }else{
            throw new Exception("Sorry, The return type '"+method.getReturnType()+"' of the method "+mapping.getMethodName()+" from "+mapping.getClassName()+" is undefined for me.");
        }
    }


    public static void main(String[] args) {
        try {
            ArrayList<Class<?>> controllers=ControllerManager.getAnnotatedClasses("utils", AnnotController.class);
            Mapping mapping=new Mapping();
            HashMap<String,Mapping> map=mapping.scanController(controllers);
            Mapping mapping2=map.get("/testModel");
            System.out.println(mapping2.getClassName());
            System.out.println(mapping2.getMethodName());
            // System.out.println(mapping2.executeMethod(mapping2.getClassName(), mapping2.getMethodName()));
            // mapping.proceedTest(mapping2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
}
