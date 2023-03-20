
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.RoleDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Role;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Laptop88
 */
public class userdetailServlet extends HttpServlet {

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
        String uid = request.getParameter("user_id");
        if(uid == null){
            response.sendRedirect("home");
            return;
        }
        int id = Integer.parseInt(uid);
        UserDAO ud = new UserDAO();
        RoleDAO rd = new RoleDAO();
        List<Role> allrole = rd.getAllRole();
        User u = ud.getUserById(id);
        request.setAttribute("allrole", allrole);
        request.setAttribute("userinfo", u);
        request.getRequestDispatcher("userdetail.jsp").forward(request, response);
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
        processRequest(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String[] genders = request.getParameterValues("gender");
        String[] role = request.getParameterValues("role");
//        Start randomhash
        Random rd = new Random();
        rd.nextInt(999999);
        String myHash = DigestUtils.md5Hex("" + rd);
//        End randomhash
//    <!--Start random password-->
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++)
        {
            int randomIndex = rd.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        String password = sb.toString();
//    <!--End random password-->
        UserDAO ad = new UserDAO();
        RoleDAO roledao = new RoleDAO();
        User check = ad.checkAccountExist(email);
        if(check != null){
            return;
        }else{
            if(genders[0].equals("male")){
                User u = new User();
                u.setRole(roledao.getRoleById(Integer.parseInt(role[0])));
                u.setPassword(password);
                u.setFullname(fullname);
                u.setGender(true);
                u.setEmail(email);
                u.setPhone(phone);
                u.setActive(1);
                u.setHash(myHash);
                u.setAddress(address);
                ad.AdminAddUser(u);
                response.sendRedirect("userlist");
            }
            if(genders[0].equals("female")){
                 User u = new User();
                u.setRole(roledao.getRoleById(Integer.parseInt(role[0])));
                u.setPassword(password);
                u.setFullname(fullname);
                u.setGender(false);
                u.setEmail(email);
                u.setPhone(phone);
                u.setActive(1);
                u.setHash(myHash);
                u.setAddress(address);
                ad.AdminAddUser(u);
                response.sendRedirect("userlist");
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