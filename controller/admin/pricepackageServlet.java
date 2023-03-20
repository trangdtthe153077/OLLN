/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.DimensionDAO;
import dal.PricePackageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Dimension;
import model.Price_Package;

/**
 *
 * @author ADMIN
 */
public class pricepackageServlet extends HttpServlet {

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
        PricePackageDAO pricepackagedao = new PricePackageDAO();

        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {

            ArrayList<Price_Package> pricepackages = pricepackagedao.getAllPricePackages(1, 10, "", "", "");
            int total = pricepackagedao.getAllTotal("", "", "");

            if (total % 10 != 0) {
                total = total / 10 + 1;
            } else if (total == 0) {
                total = 1;
            } else {
                total = total / 10;
            }
            int pagetotal = total;

            request.setAttribute("total", pagetotal);
            request.setAttribute("pricepackages", pricepackages);

        } else if (action != null && action.equals("deletepricepackage")) {

            pricepackagedao.deletePricePackage(id);

            response.sendRedirect("pricepackage");
            return;
        }

        request.getRequestDispatcher("pricepackage.jsp").forward(request, response);
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
        PricePackageDAO dao = new PricePackageDAO();

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action.equals("edit")) {
            String status = request.getParameter("status");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String listprice = request.getParameter("listprice");
            String saleprice = request.getParameter("saleprice");
            String duration = request.getParameter("duration");
            dao.editPricePackage(id, name, duration, listprice, saleprice, description, status);

        } else if (action != null && action.equals("addnew")) {
            String status = request.getParameter("status");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String listprice = request.getParameter("listprice");
            String saleprice = request.getParameter("saleprice");
            String duration = request.getParameter("duration");

            dao.insertNewPricePackage(name, duration, listprice, saleprice, description, status);

            response.sendRedirect("pricepackage");
            return;
        }
        response.sendRedirect("pricepackage");
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
