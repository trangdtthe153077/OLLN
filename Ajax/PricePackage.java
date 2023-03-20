/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajax;

import dal.CourseDAO;
import dal.DimensionDAO;
import dal.PricePackageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Price_Package;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "PricePackage", urlPatterns = {"/PricePackage"})
public class PricePackage extends HttpServlet {

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
        PricePackageDAO dao = new PricePackageDAO();
        String page = request.getParameter("page");
        String total = request.getParameter("total");
        String search = request.getParameter("search");
        String min = request.getParameter("min");
        String max = request.getParameter("max");

        int page1 = Integer.parseInt(page);

        int total1 = dao.getAllTotal(search, min, max);
        if (total1 % 10 != 0) {
            total1 = total1 / 10 + 1;
        } else if (total1 == 0) {
            total1 = 1;
        } else {
            total1 = total1 / 10;
        }
        ArrayList<Price_Package> allPricePackagebyPage = dao.getAllPricePackages(page1, 10, search, min, max);
        PrintWriter out = response.getWriter();
        out.print("<div class=\"my-4\" style=\"clear: both; border: solid black 1px;\">\n"
                + "                                    <div class=\"table-responsive \">\n"
                + "                                        <table class=\"table table-bordered table-hover  \" style=\" margin-bottom: 0px; font-size: 15px; \" > \n"
                + "                                            <tr class=\"table-primary\">\n"
                + "                                                <th>\n"
                + "                                                    ID \n"
                + "                                                </th>\n"
                + "                                                <th >\n"
                + "                                                    Package \n"
                + "                                                </th>\n"
                + "                                                <th >\n"
                + "                                                    Duration\n"
                + "                                                </th>\n"
                + "                                                <th >\n"
                + "                                                    List Price\n"
                + "                                                </th>\n"
                + "                                                <th >\n"
                + "                                                    Sale Price\n"
                + "                                                </th>\n"
                + "                                                <th >\n"
                + "                                                    Status\n"
                + "                                                </th>\n"
                + "                                                <th >\n"
                + "                                                    Action\n"
                + "                                                </th>\n"
                + "\n"
                + "\n"
                + "                                            </tr>");
        for (Price_Package o : allPricePackagebyPage) {
            out.print(" <tr>\n"
                    + "                                                    <td>\n"
                    + "                                                        " + o.getId() + "\n"
                    + "                                                    </td>\n"
                    + "                                                    <td>\n"
                    + "                                                        " + o.getName() + "\n"
                    + "                                                    </td>\n"
                    + "                                                    <td>\n"
                    + "                                                        " + o.getDuration() + "\n"
                    + "                                                    </td>\n"
                    + "                                                    <td>\n"
                    + "                                                        " + o.getListprice() + "\n"
                    + "                                                    </td>\n"
                    + "                                                    <td>\n"
                    + "                                                        " + o.getSaleprice() + "\n"
                    + "                                                    </td>\n"
                    + "                                                    <td>\n"
                    + "                                                        " + o.getStatus() + "\n"
                    + "                                                    </td>\n"
                    + "                                                    <td >\n"
                    + "                                                        <span alt=\"view\"  class=\"mr-2\"><a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#viewpricepackage" + o.getId() + "\" >  <i class=\"fa-solid fa-eye\"></i> View </a></span>\n"
                    + "                                                        <span alt=\"edit\"  class=\"mr-2\"><a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#edit" + o.getId() + "\" >  <i class=\"fa-solid fa-pen-to-square\"></i> Edit </a></span>\n"
                    + "                                                        <span alt=\"delete\"  class=\"mr-2\"><a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#deletepricepackage" + o.getId() + "\" >  <i class=\"fa-solid fa-trash-can\"></i> Delete</a></span>\n"
                    + "                                                        <div class=\"modal fade\" id=\"viewpricepackage" + o.getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"#viewpricepackage" + o.getId() + "\" aria-hidden=\"true\">\n"
                    + "                                                            <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\n"
                    + "                                                                <div class=\"modal-content\">\n"
                    + "                                                                    <div class=\"modal-header\">\n"
                    + "                                                                        <h5 class=\"modal-title\" id=\"exampleModalLongTitle\">View Price Package ID = " + o.getId() + "</h5>\n"
                    + "                                                                    </div>\n"
                    + "                                                                    <div class=\"modal-body\">\n"
                    + "                                                                        <form class=\"was-validated\">\n"
                    + "\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"name\" class=\"form-label\" >Name</label>\n"
                    + "                                                                                <input type=\"text\"  name=\"name\" class=\"form-control\" disabled value=\"" + o.getName() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input price package name</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"duration\" class=\"form-label\" >Duration</label>\n"
                    + "                                                                                <input type=\"number\" min=\"0\"  name=\"duration\" class=\"form-control\"  disabled value=\"" + o.getDuration() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Duration</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"listprice\" class=\"form-label\" >List Price</label>\n"
                    + "                                                                                <input type=\"number\" min=\"0\" step=\"0.01\"  name=\"listprice\" class=\"form-control\"  disabled value=\"" + o.getListprice() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Duration</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"saleprice\" class=\"form-label\" >Sale Price</label>\n"
                    + "                                                                                <input type=\"number\" min=\"0\" step=\"0.01\" name=\"saleprice\" class=\"form-control\"  disabled value=\"" + o.getSaleprice() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Duration</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"status\" class=\"form-label\" >Status</label>\n"
                    + "                                                                                <input type=\"text\" min=\"0\" step=\"0.01\" name=\"status\" id=\"status\" class=\"form-control\"  disabled value=\"" + o.getStatus() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Status</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"desribe\" class=\"form-check-label\">Description</label>\n"
                    + "                                                                                <textarea rows=\"5\" cols=\"70\"  style=\"resize: none;\" disabled class=\"form-control\"\n"
                    + "                                                                                          required>" + o.getDescription() + "</textarea>\n"
                    + "                                                                            </div>\n"
                    + "\n"
                    + "                                                                        </form>\n"
                    + "\n"
                    + "                                                                    </div>\n"
                    + "\n"
                    + "                                                                </div>\n"
                    + "                                                            </div>\n"
                    + "                                                        </div>\n"
                    + "\n"
                    + "                                                        <div class=\"modal fade\" id=\"edit" + o.getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"#edit" + o.getId() + "\" aria-hidden=\"true\">\n"
                    + "                                                            <div class=\"modal-dialog modal-dialog-centered modal-lg\" role=\"document\">\n"
                    + "                                                                <div class=\"modal-content\">\n"
                    + "                                                                    <div class=\"modal-header\">\n"
                    + "                                                                        <h5 class=\"modal-title\" >Edit Price Package ID = " + o.getId() + "</h5>\n"
                    + "                                                                    </div>\n"
                    + "\n"
                    + "                                                                    <div class=\"modal-body\">\n"
                    + "                                                                        <form class=\"was-validated\" method=\"post\" action=\"pricepackage?action=edit&id=" + o.getId() + "\">\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"name\" class=\"form-label\" >Name</label>\n"
                    + "                                                                                <input type=\"text\"  name=\"name\" class=\"form-control\"  value=\"" + o.getName() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input price package name</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"duration\" class=\"form-label\" >Duration</label>\n"
                    + "                                                                                <input type=\"number\" min=\"0\"  name=\"duration\" class=\"form-control\"   value=\"" + o.getDuration() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Duration</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"listprice\" class=\"form-label\" >List Price</label>\n"
                    + "                                                                                <input type=\"number\" min=\"0\" step=\"0.1\"  name=\"listprice\" class=\"form-control\"   value=\"" + o.getListprice() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input List Price</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"saleprice\" class=\"form-label\" >Sale Price</label>\n"
                    + "                                                                                <input type=\"number\" min=\"0\" step=\"0.1\" name=\"saleprice\" class=\"form-control\"   value=\"" + o.getSaleprice() + "\">\n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Sale Price</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label class=\"form-label\"  for=\"status\" class=\"form-label\" >Status</label>\n"
                    + "                                                                                <select class=\"form-control\" name=\"status\" id=\"status\" value=\"Active\">\n"
                    + "                                                                                    <option value=\"Active\"");
            if (o.getStatus().equals("Active")) {
                out.print(" Selected");
            }
            out.print(
                    "                                                                                     >   Active\n"
                    + "                                                                                    </option>\n"
                    + "                                                                                    <option value=\"Inactive\" ");
            if (o.getStatus().equals("Inactive")) {
                out.print(" Selected");
            }
            out.print(" >\n"
                    + "                                                                                        Inactive\n"
                    + "                                                                                    </option>\n"
                    + "                                                                                </select>\n"
                    + "                                                                              \n"
                    + "                                                                                <div class=\"invalid-feedback\">Input Status</div>\n"
                    + "                                                                            </div>\n"
                    + "                                                                            <div class=\"mb-3\">\n"
                    + "                                                                                <label for=\"desribe\" class=\"form-check-label\">Description</label>\n"
                    + "                                                                                <textarea rows=\"5\" cols=\"70\"  style=\"resize: none;\" id=\"description\" name=\"description\"  class=\"form-control\"\n"
                    + "                                                                                          >" + o.getDescription()+ "</textarea>\n"
                    + "                                                                            </div>\n"
                    + "\n"
                    + "                                                                            <div class=\"modal-footer\">\n"
                    + "                                                                                <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                    + "                                                                                <button class=\"btn btn-primary\"  type=\"submit\" >Save </button>\n"
                    + "                                                                            </div>\n"
                    + "                                                                        </form>\n"
                    + "                                                                    </div>\n"
                    + "                                                                </div>\n"
                    + "                                                            </div>\n"
                    + "                                                        </div>\n"
                    + "                                                        <div class=\"modal fade\" id=\"deletepricepackage" + o.getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"#deletepricepackage" + o.getId() + "\" aria-hidden=\"true\">\n"
                    + "                                                            <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\n"
                    + "                                                                <div class=\"modal-content\">\n"
                    + "                                                                    <div class=\"modal-header\">\n"
                    + "                                                                        <h5 class=\"modal-title\" id=\"exampleModalLongTitle\">Delete Price Package</h5>\n"
                    + "                                                                    </div>\n"
                    + "                                                                    <div class=\"modal-body\">\n"
                    + "                                                                        Do you want to delete this price package? ID = " + o.getId() + " \n"
                    + "                                                                    </div>\n"
                    + "                                                                    <div class=\"modal-footer\">\n"
                    + "                                                                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                    + "                                                                        <a class=\"btn btn-primary\"  href=\"pricepackage?action=deletepricepackage&id=" + o.getId() + "\">Delete</a>\n"
                    + "                                                                    </div>\n"
                    + "                                                                </div>\n"
                    + "                                                            </div>\n"
                    + "                                                        </div>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>");

        }
        
          out.print(
                "                     \n"
                + "                    \n"
                + "                  </table>\n"
                + "              </div> \n"
                + "\n"
                + "         \n"
                + "          </div>\n"
                + "             <nav aria-label=\"Page navigation\">\n"
                + "            <ul class=\"pagination justify-content-end\">\n");
        if (page1 == 1) {
            out.print(
                    "              <li class=\"page-item disabled\">\n"
                    + "                <a class=\"page-link\" href=\"#\" tabindex=\"-1\" onclick=\"PricePackage("+ 1 + "," + total + ")\">First</a>\n"
                    + "              </li>\n");
        } else {
            out.print(
                    "              <li class=\"page-item \">\n"
                    + "                <a class=\"page-link\" href=\"#\" tabindex=\"-1\" onclick=\"PricePackage(" + 1 + "," + total + ")\">First</a>\n"
                    + "              </li>\n");
        }
        for (int i = page1 - 2; i < page1 + 2; i++) {
            if (i >= 1 && i <= total1) {
                out.print(
                        "                    <li class=\"page-item");
                if (i == page1) {
                    out.print(" active  ");
                }
                out.print("\"  ><a class=\"page-link\" href=\"#\" onclick=\"PricePackage(" + i + "," + total + ")\">" + i + "</a></li>\n"
                        + "              \n"
                );
            }
        }

        if (page1 == total1) {
            out.print(
                    "                <li class=\"page-item disabled\"> \n"
                    + "                    <a class=\"page-link \" href=\"#\" onclick=\"PricePackage("+ total + "," + total + ")\">Last</a>\n"
                    + "              </li>\n"
                    + "            </ul>\n"
                    + "          </nav>");
        } else {
            out.print("                  <li class=\"page-item \" >                   <a class=\"page-link \" href=\"#\" onclick=\"PricePackage("+ total + "," + total + ")\">Last</a>\n"
                    + "              </li>\n"
                    + "            </ul>\n"
                    + "          </nav>");
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
