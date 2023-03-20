/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import utils.CaptchaUtil;
import utils.EncodeData;
import utils.SendMail;

/**
 *
 * @author Laptop88
 */
public class UserController extends HttpServlet {

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
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserDAO userdao = new UserDAO();
        String action = request.getParameter("action");

        try {
            if (action.equals("recover")) {
                request.getRequestDispatcher("recover.jsp").forward(request, response);
            }
            if (action.equals("recoverpass")) {
                String type = request.getParameter("type");
                request.setAttribute("type", type);
                request.getRequestDispatcher("recover.jsp").forward(request, response);
            }
            if (action.equals("forgot")) {
                String password = request.getParameter("password");
                String repassword = request.getParameter("repassword");

                // luu session la email
                String email = (String) session.getAttribute("email");

                password = DigestUtils.md5Hex(password);
                userdao.Recover(email, password);
                userdao.removeCaptcha(email);
                request.setAttribute("success", "Password has been reset successfully");
//                request.getRequestDispatcher("user?action=login").forward(request, response);
                // sưa lai đường dẫn tới login 
                response.sendRedirect("home");
            }

            if (action.equals("checkemail")) {
                String email = request.getParameter("email");
                User account = userdao.checkAccByEmail(email);
                if (account == null) {
                    request.setAttribute("email", email);
                    request.setAttribute("error", "Email does not exist!");
                    request.getRequestDispatcher("user?action=recover").forward(request, response);
                } else {
                    // gui mail di ne
                    String captcha = CaptchaUtil.getCaptcha(16);
                    String content = "&email=" + account.getEmail() + "&captcha=" + captcha + "&type=recover";
                    String enContent = EncodeData.enCode(content);
                    SendMail.setContentRecover(account.getUsername(),
                            "http://localhost:8084/Online-Learning-SWP/user?action=verification&id=" + enContent, email);
                    userdao.removeCaptcha(account.getEmail());
                    userdao.addCaptcha(account.getEmail(), captcha);
                    request.setAttribute("error", "Link đặt lại mật khẩu được gửi đến email của bạn!");
                    request.getRequestDispatcher("user?action=recover").forward(request, response);
                }
            }

            if (action.equals("verification")) {
                String id = request.getParameter("id");
                String deID = EncodeData.deCode(id);
                request.getRequestDispatcher("user?action=checkcaptcha" + deID).forward(request, response);
            }

            if (action.equals("checkcaptcha")) {
                String captcha = request.getParameter("captcha");
                String email = request.getParameter("email");
                String type = request.getParameter("type");
                User acc = userdao.checkCaptcha(captcha, email);
                if (type.equals("register") && acc != null) {
                    // dang ki moi
                } else if (type.equals("recover") && acc != null) {
                    long seconds = System.currentTimeMillis() / 1000;
                    if (seconds <= acc.getLifetime()) {
                        session.setAttribute("email", email);
                        request.getRequestDispatcher("user?action=recoverpass&type=recover").forward(request, response);
                    } else {
                        userdao.removeCaptcha(email);
                        request.setAttribute("error", "Time out. Please re-enter your email address");
                        request.getRequestDispatcher("user?action=recover").forward(request, response);
                    }
                } else {
                    request.getRequestDispatcher("404.jsp").forward(request, response);
                }
            }
        } catch (IOException | ServletException e) {
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
        processRequest(request, response);
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
