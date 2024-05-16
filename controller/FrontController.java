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

    private void processRequest(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
        PrintWriter out=resp.getWriter();
        out.println("Nitontona tato @  :"+req.getRequestURI());
        
        String packageName=this.getInitParameter("packageName");
        out.println(packageName);

        try {
            ArrayList<Class<?>> controller=ControllerManager.getControllerClasses(packageName);
            if(controller!=null){
                out.println("Liste des Controllers:");
                for (Class<?> class1 : controller) {
                    out.println("   -"+class1.getSimpleName());
                }
            }else{
                out.println("None");
            }
        } catch (Exception e) {
            e.printStackTrace();
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