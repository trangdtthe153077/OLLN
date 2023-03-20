/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Admin
 */
public class userListServlet extends HttpServlet {

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
        try {
            UserDAO user = new UserDAO();
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            String Service = request.getParameter("do");
            HttpSession session = request.getSession();
            if (Service == null) {

                User acc = (User) session.getAttribute("account");

                if (acc == null) {
                    response.sendRedirect("home");
                    return;
                }

                String fullName = null;

                String action = request.getParameter("action");
                if (action != null && action.equalsIgnoreCase("search")) {
                    fullName = request.getParameter("name");

                }
                List<User> userList = user.getAllUserList();
                request.setAttribute("sort", 1);
                request.setAttribute("userList", userList);
                request.getRequestDispatcher("userlist.jsp").forward(request, response);
            }

            if (Service.equals("search")) {
                if ((request.getParameter("name") != null) && (request.getParameter("name") != "")) {
                    String name = request.getParameter("name");
                    String sort_raw = request.getParameter("sort");
                    int sort = Integer.parseInt(sort_raw);
                    List<User> list = user.searchSort(name, sort);
                    request.setAttribute("sort", sort);
                    request.setAttribute("userList", list);
                    request.getRequestDispatcher("userlist.jsp").forward(request, response);
                }
                if ((request.getParameter("name") == null) || (request.getParameter("name") == "")) {
                    String sort = request.getParameter("sort");
                    int sortby = Integer.parseInt(sort);
                    List<User> sortasc = user.SortIdAsc();
                    List<User> sortdesc = user.SortIdDesc();
                    List<User> userList = user.getAllUserList();
                    switch (sortby) {
                        case 1: {
                            request.setAttribute("sort", sortby);
                            request.setAttribute("userList", userList);
                            break;
                        }
                        case 2: {
                            request.setAttribute("sort", sortby);
                            request.setAttribute("userList", sortasc);
                            System.out.println("abcd");
                            break;
                        }
                        case 3: {
                            request.setAttribute("sort", sortby);
                            request.setAttribute("userList", sortdesc);
                            break;
                        }

                    }

                    request.getRequestDispatcher("userlist.jsp").forward(request, response);

                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
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
