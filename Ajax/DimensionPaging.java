/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajax;

import dal.DimensionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Dimension;

/**
 *
 * @author ADMIN
 */
public class DimensionPaging extends HttpServlet {

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
        DimensionDAO dao = new DimensionDAO();

        String id = request.getParameter("id");
        String page = request.getParameter("page");
        String total = request.getParameter("total");
        String search = request.getParameter("search");

        int page1 = Integer.parseInt(page);
        int id1 = Integer.parseInt(id);
        int total1 = dao.getTotal(id1, search);
        if (total1 % 10 != 0) {
            total1 = total1 / 10 + 1;
        } else if (total1 == 0) {
            total1 = 1;
        } else {
            total1 = total1 / 10;
        }
        ArrayList<Dimension> dimensions = dao.getCourseDimensionByPage(Integer.parseInt(id), Integer.parseInt(page), 10, search);
        PrintWriter out = response.getWriter();
        out.print("<div class=\"my-4\" style=\"clear: both; border: solid black 1px;\">\n"
                + "              <div class=\"table-responsive \">\n"
                + "                  <table class=\"table table-bordered table-hover  \" style=\" margin-bottom: 0px; font-size: 15px; \" > \n"
                + "                      <tr class=\"table-primary\">\n"
                + "                          <th>\n"
                + "                              ID \n"
                + "                          </th>\n"
                + "                          <th >\n"
                + "                              Type \n"
                + "                          </th>\n"
                + "                          <th >\n"
                + "                              Dimension\n"
                + "                          </th>\n"
                      + "                          <th >\n"
                + "                              Status\n"
                + "                          </th>\n"
                + "                          <th >\n"
                + "                              Action\n"
                + "                          </th>\n"
                + "                      \n"
                + "   </tr>\n");
        for (Dimension o : dimensions) {
            out.print(
                    "<tr>\n"
                    + "                                                <td>\n"
                    + "                                                    " + o.getId() + "\n"
                    + "                                                </td>\n"
                    + "                                                <td>\n"
                    + "                                                    " + o.getType() + "\n"
                    + "                                                </td>\n"
                    + "                                                <td>\n"
                    + "                                                    " + o.getName() + "\n"
                    + "                                                </td>\n"
                    + "                                                <td>\n"
                    + "                                                    " + o.getId() + "\n"
                    + "                                                </td>\n"
                    + "                                                <td >\n"
                    + "                                                    <span alt=\"Change this status\"  class=\"mr-2\"><a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#viewdimension" + o.getId() + "\" >  <i class=\"fa-solid fa-eye\"></i> View </a></span>\n"
                    + "                                                    <span alt=\"Change this status\"  class=\"mr-2\"><a href=\"#\" data-bs-toggle=\"modal\" data-bs-target=\"#edit" + o.getId() + "\" >  <i class=\"fa-solid fa-pen-to-square\"></i> Edit </a></span>\n"
                    + "                                                    <div class=\"modal fade\" id=\"viewdimension" + o.getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"#viewdimension" + o.getId() + "\" aria-hidden=\"true\">\n"
                    + "                                                        <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\n"
                    + "                                                            <div class=\"modal-content\">\n"
                    + "                                                                <div class=\"modal-header\">\n"
                    + "                                                                    <h5 class=\"modal-title\" id=\"exampleModalLongTitle\">View Dimension</h5>\n"
                    + "                                                                </div>\n"
                    + "                                                                <div class=\"modal-body\">\n"
                    + "                                                                    <table>\n"
                    + "                                                                        <th>\n"
                    + "                                                                            ID\n"
                    + "                                                                        </th>\n"
                    + "                                                                        <th>\n"
                    + "                                                                            Type\n"
                    + "                                                                        </th>\n"
                    + "                                                                        <th>\n"
                    + "                                                                            Name\n"
                    + "                                                                        </th>\n"
                    + "                                                                        <th>\n"
                    + "                                                                            Description\n"
                    + "                                                                        </th>\n"
                    + "                                                                        <th>\n"
                    + "                                                                            Status\n"
                    + "                                                                        </th>\n"
                    + "                                                                        <tr>\n"
                    + "                                                                            <td>\n"
                    + "                                                                                " + o.getId() + "  \n"
                    + "                                                                            </td>\n"
                    + "                                                                            <td>\n"
                    + "                                                                                " + o.getType() + "  \n"
                    + "                                                                            </td>\n"
                    + "                                                                            <td>\n"
                    + "                                                                                " + o.getName() + "  \n"
                    + "                                                                            </td>\n"
                    + "                                                                            <td>\n"
                    + "                                                                                " + o.getDescription() + " \n"
                    + "                                                                            </td>\n"
                    + "                                                                            <td>\n"
                    + "                                                                               " + o.getId() + "  \n"
                    + "                                                                            </td>\n"
                    + "                                                                        </tr>\n"
                    + "                                                                    </table>\n"
                    + "\n"
                    + "                                                                </div>\n"
                    + "\n"
                    + "                                                            </div>\n"
                    + "                                                        </div>\n"
                    + "                                                    </div>\n"
                    + "\n"
                    + "                                                    <div class=\"modal fade\" id=\"edit" + o.getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"#deletedimension" + o.getId() + "\" aria-hidden=\"true\">\n"
                    + "                                                        <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\n"
                    + "                                                            <div class=\"modal-content\">\n"
                    + "                                                                <div class=\"modal-header\">\n"
                    + "                                                                    <h5 class=\"modal-title\" id=\"exampleModalLongTitle\">Change Status</h5>\n"
                    + "                                                                </div>\n"
                    + "                                                                <form method=\"post\" action=\"subjectdetails?action=editdimension&id=" + id + "&did=" + o.getId() + "\">\n"
                    + "                                                                    <div class=\"modal-body\">\n"
                    + "                                                                        Change status of this\n"
                    + "                                                                        " + o.getType() + " - " + o.getName() + "?\n"
                    + "\n"
                    + "                                                                        <select name=\"status\">\n"
                    + "                                                                            <option value=\"Active\" ${a.status eq 'Active'?\"selected\":\"\"}>\n"
                    + "                                                                                Active\n"
                    + "                                                                            </option>\n"
                    + "                                                                            <option value=\"Deactive\" ${a.status eq 'Deactive'?\"selected\":\"\"}>\n"
                    + "                                                                                Deactive\n"
                    + "                                                                        </select>\n"
                    + "\n"
                    + "                                                                    </div>\n"
                    + "                                                                    <div class=\"modal-footer\">\n"
                    + "                                                                        <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                    + "                                                                        <button class=\"btn btn-primary\"  type=\"submit\">Save changes</button>\n"
                    + "                                                                    </div>\n"
                    + "                                                                </form>\n"
                    + "\n"
                    + "                                                            </div>\n"
                    + "                                                        </div>\n"
                    + "                                                    </div>\n"
                    + "\n"
                    + "                                                    <div class=\"modal fade\" id=\"deletedimension" + o.getId() + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"#deletedimension" + o.getId() + "\" aria-hidden=\"true\">\n"
                    + "                                                        <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\n"
                    + "                                                            <div class=\"modal-content\">\n"
                    + "                                                                <div class=\"modal-header\">\n"
                    + "                                                                    <h5 class=\"modal-title\" id=\"exampleModalLongTitle\">Change Status</h5>\n"
                    + "                                                                </div>\n"
                    + "                                                                <div class=\"modal-body\">\n"
                    + "                                                                    Do you sure you want to change status of this\n"
                    + "                                                                    " + o.getType() + " - " + o.getName() + "?\n"
                    + "                                                                </div>\n"
                    + "                                                                <div class=\"modal-footer\">\n"
                    + "                                                                    <button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Cancel</button>\n"
                    + "                                                                    <a class=\"btn btn-primary\"  href=\"subjectdetails?action=deletedimension&id=${course.id}&did=" + o.getId() + "\">Save changes</a>\n"
                    + "                                                                </div>\n"
                    + "                                                            </div>\n"
                    + "                                                        </div>\n"
                    + "                                                    </div>\n"
                    + "                                                </td>\n"
                    + "                                            </tr>"
            );
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
                    + "                <a class=\"page-link\" href=\"#\" tabindex=\"-1\" onclick=\"Dimension(" + id + "," + 1 + "," + total + ")\">First</a>\n"
                    + "              </li>\n");
        } else {
            out.print(
                    "              <li class=\"page-item \">\n"
                    + "                <a class=\"page-link\" href=\"#\" tabindex=\"-1\" onclick=\"Dimension(" + id + "," + 1 + "," + total + ")\">First</a>\n"
                    + "              </li>\n");
        }
        for (int i = page1 - 2; i < page1 + 2; i++) {
            if (i >= 1 && i <= total1) {
                out.print(
                        "                    <li class=\"page-item");
                if (i == page1) {
                    out.print(" active  ");
                }
                out.print("\"  ><a class=\"page-link\" href=\"#\" onclick=\"Dimension(" + id + "," + i + "," + total + ")\">" + i + "</a></li>\n"
                        + "              \n"
                );
            }
        }

        if (page1 == total1) {
            out.print(
                    "                <li class=\"page-item disabled\" > \n"
                    + "                    <a class=\"page-link \" href=\"#\" onclick=\"Dimension(" + id + "," + total + "," + total + ")\">Last</a>\n"
                    + "              </li>\n"
                    + "            </ul>\n"
                    + "          </nav>");
        } else {
            out.print(
                    "                  <li class=\"page-item disabled\" >    <a class=\"page-link \" href=\"#\" onclick=\"Dimension(" + id + "," + total1 + "," + total1 + ")\">Last</a>\n"
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

