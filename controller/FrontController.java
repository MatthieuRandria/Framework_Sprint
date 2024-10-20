package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import annotation.AnnotController;
import utils.Fonction;
import utils.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig // for multipart handling
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

    private void processRequest(HttpServletRequest req,HttpServletResponse resp, String verb)throws ServletException, IOException, Exception{
        PrintWriter out=resp.getWriter();
        
        String requestUrl=req.getRequestURI();

        if (this.map!=null) {
            String url=Fonction.urlExtract(requestUrl);
            Mapping mapping=this.map.get(url);
            if (mapping!=null) {
                if (mapping.getVerb().compareTo(verb)==0) {
                    try {
                        mapping.proceedMethod(out, mapping, req, resp);
                    } catch (Exception e) {
                        resp.setContentType("text/html");
                        out.println(Fonction.pageError(e));
                    }
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
            this.processRequest(req,resp, "GET");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.processRequest(req, resp, "POST");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}