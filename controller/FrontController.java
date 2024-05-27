package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import annotation.AnnotController;
import utils.Fonction;
import utils.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@AnnotController(name = "FrontController")
public class FrontController extends HttpServlet{
    // private boolean checked=false;
    private ArrayList<Class<?>> controllers=null;
    HashMap<String,Mapping> map;

    public void init(){
        String packageName=this.getInitParameter("packageName");
        try {
            this.controllers=ControllerManager.getAnnotatedClasses(packageName,AnnotController.class);
            if(this.controllers!=null){
                // Jerena izay method annoté par Get Pour chaque Controller
                Mapping mapper=new Mapping();
                this.map=mapper.scanController(controllers);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void processRequest(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException, Exception{
        PrintWriter out=resp.getWriter();
        out.println("Hello");
        String requestUrl=req.getRequestURI();
        // out.println("Here are all the Mapping in HashMap");
        if (this.map!=null) {
            String url=Fonction.urlExtract(requestUrl);
            Mapping mapping=this.map.get(url);
            if (mapping!=null) {
                out.println("Annotation: "+url);
                out.println("Controller: "+mapping.getClassName()+", Method: "+mapping.getMethodName());
            }else{
                out.println("No mapping with key :"+url);
            }

        }else{
            out.println("No Mapping Found");
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(req,resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.processRequest(req, resp);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}