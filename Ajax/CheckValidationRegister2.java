/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajax;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Laptop88
 */
public class CheckValidationRegister2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckValidationRegister2</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckValidationRegister2 at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String pass = request.getParameter("password");
        String patternpass = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
            if(pass.matches(patternpass)){
                out.print("<i class=\"text-success\">This password invalid</i>"+"  <i class=\"fa-solid fa-circle-check text-success\"></i>");
            }else{
                out.print("<i class=\"text-danger\">Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters</i>"+"   <i class=\"fa-solid fa-circle-xmark text-danger\"></i>");
            }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String patternemail = "[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
            if(email.matches(patternemail)){
                out.print("<i class=\"text-success\">This email invalid</i>"+"  <i class=\"fa-solid fa-circle-check text-success\"></i>");
            }else{
                out.print("<i class=\"text-danger\">Please input the correct email format (Ex: abc@example.com)</i>"+"  <i class=\"fa-solid fa-circle-xmark text-danger\"></i>");
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
