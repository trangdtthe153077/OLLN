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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Laptop88
 */
@WebServlet(name = "ShowError", urlPatterns = {"/error"})
public class CheckValidationRegister extends HttpServlet {

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
            out.println("<title>Servlet ShowError</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowError at " + request.getContextPath() + "</h1>");
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
        String pass = request.getParameter("phone");
        String patternphone = "(84|0[3|5|7|8|9])+([0-9]{8})";
            if(pass.matches(patternphone)){
                out.print("<i class=\"text-success\">This phone invalid</i>"+"  <i class=\"fa-solid fa-circle-check text-success\"></i>");
            }else{
                out.print("<i class=\"text-danger\">Please input the correct phone format (Ex:0123456789)</i>"+"  <i class=\"fa-solid fa-circle-xmark text-danger\"></i>");
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
        String email  = request.getParameter("email");
        UserDAO ad = new UserDAO();
        User u = ad.checkAccountExist(email);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if(u != null){
            out.print("<i class=\"text-danger\">This email has already been taken</i>"+"  <i class=\"fa-solid fa-circle-xmark text-danger\"></i>");
        }else{
            String patternemail = "[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
            if(email.matches(patternemail)){
                out.print("<i class=\"text-success\">This email can be use</i>"+"  <i class=\"fa-solid fa-circle-check text-success\"></i>");
            }else{
                out.print("<i class=\"text-danger\">Please input the correct email format (Ex: abc@example.com)</i>"+"  <i class=\"fa-solid fa-circle-xmark text-danger\"></i>");
            }
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
