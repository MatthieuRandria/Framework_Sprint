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


public class FrontController extends HttpServlet{

    private ArrayList<Class<?>> controllers=null;
    HashMap<String,Mapping> map;

    public void init(){
        try {
            String packageName=this.getInitParameter("packageName");
            this.controllers=ControllerManager.getAnnotatedClasses(packageName,AnnotController.class);
            if(this.controllers!=null){
                // Jerena izay method annoté par Get Pour chaque Controller
                Mapping mapper=new Mapping();
                this.map=mapper.scanController(controllers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processRequest(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException, Exception{
        PrintWriter out=resp.getWriter();
        
        String requestUrl=req.getRequestURI();

        if (this.map!=null) {
            String url=Fonction.urlExtract(requestUrl);
            Mapping mapping=this.map.get(url);
            if (mapping!=null) {
                // out.println("Annotation: "+url);
                // out.println("Controller: "+mapping.getClassName()+", Method: "+mapping.getMethodName());
                try {
                    mapping.proceedMethod(out, mapping, req, resp);
                } catch (Exception e) {
                    out.println("Error : "+e.getLocalizedMessage());
                }
            }else{
                out.println("Error 404 not found, (No Mapping for : '"+url+"'')");
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