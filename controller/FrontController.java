package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import annotation.AnnotController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@AnnotController(name = "FrontController")
public class FrontController extends HttpServlet{
    private boolean checked=false;
    private ArrayList<Class<?>> controllers=null;

    public void init(){
        String packageName=this.getInitParameter("packageName");
        try {
            this.controllers=ControllerManager.getAnnotatedClasses(packageName,AnnotController.class);
            this.checked=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
        if(!this.checked){
            this.init();
        }

        PrintWriter out=resp.getWriter();
        out.println("Nitontona tato @  :"+req.getRequestURI());

        if(this.controllers!=null){
            out.println("Liste des Controllers:");
            for (Class<?> class1 : this.controllers) {
                out.println("   -"+class1.getSimpleName());
            }
        }else{
            out.println("No controller found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.processRequest(req,resp);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.processRequest(req, resp);
        }catch(Exception e){}
    }
}