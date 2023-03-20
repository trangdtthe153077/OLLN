/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CheckOut", urlPatterns = {"/CheckOut"})
public class CheckOut extends HttpServlet {

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
        String pkg = request.getParameter("pkg");
        String value = request.getParameter("value");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("      <div class=\"container\" style=\"background-color: white;\">\n"
                    + "\n"
                    + "<div class=\"row\">\n"
                    + "	<aside class=\"col-sm-12\">\n"
                    + "\n"
                    + "<article class=\"\">\n"
                    + "<div class=\"card-body p-5\">\n"
                    + "\n"
                    + "<ul class=\"nav bg-light nav-pills rounded nav-fill mb-3\" role=\"tablist\">\n"
                    + "	<li class=\"nav-item\">\n"
                    + "		<a class=\"nav-link active\" data-bs-toggle=\"pill\" href=\"#nav-tab-card\">\n"
                    + "		<i class=\"fa fa-credit-card\"></i> Credit Card</a></li>\n"
                    + "	<li class=\"nav-item\">\n"
                    + "		<a class=\"nav-link\" data-bs-toggle=\"pill\" href=\"#nav-tab-paypal\">\n"
                    + "		<i class=\"fab fa-paypal\"></i>  Paypal</a></li>\n"
                    + "	<li class=\"nav-item\">\n"
                    + "		<a class=\"nav-link\" data-bs-toggle=\"pill\" href=\"#nav-tab-bank\">\n"
                    + "		<i class=\"fa fa-university\"></i>  Bank Transfer</a></li>\n"
                    + "</ul>\n"
                    + "\n"
                    + "<div class=\"tab-content\">\n"
                    + "<div class=\"tab-pane fade show active\" id=\"nav-tab-card\">\n"
             
                    + "        <form role=\"form\" method=\"post\" action=\"courseregister?pkg=" + pkg + "&subject=" + value + "\">\n"
                    + "	<div class=\"form-group\">\n"
                    + "		<label for=\"username\">Full name (on the card)</label>\n"
                    + "		<input type=\"text\" class=\"form-control\" name=\"username\" placeholder=\"\" required=\"\">\n"
                    + "	</div> <!-- form-group.// -->\n"
                    + "\n"
                    + "	<div class=\"form-group\">\n"
                    + "		<label for=\"cardNumber\">Card number</label>\n"
                    + "		<div class=\"input-group\">\n"
                    + "			<input type=\"text\" class=\"form-control\" name=\"cardNumber\" placeholder=\"\">\n"
                    + "			<div class=\"input-group-append\">\n"
                    + "				<span class=\"input-group-text text-muted\">\n"
                    + "					<i class=\"fab fa-cc-visa\"></i>  \n"
                    + "                                        <i class=\"fab fa-cc-amex\"></i>   \n"
                    + "					<i class=\"fab fa-cc-mastercard\"></i> \n"
                    + "				</span>\n"
                    + "			</div>\n"
                    + "		</div>\n"
                    + "	</div> <!-- form-group.// -->\n"
                    + "\n"
                    + "	<div class=\"row\">\n"
                    + "	    <div class=\"col-sm-8\">\n"
                    + "	        <div class=\"form-group\">\n"
                    + "	            <label><span class=\"hidden-xs\">Expiration</span> </label>\n"
                    + "	        	<div class=\"input-group\">\n"
                    + "	        		<input type=\"number\" class=\"form-control\" placeholder=\"MM\" name=\"\">\n"
                    + "		            <input type=\"number\" class=\"form-control\" placeholder=\"YY\" name=\"\">\n"
                    + "	        	</div>\n"
                    + "	        </div>\n"
                    + "	    </div>\n"
                    + "	    <div class=\"col-sm-4\">\n"
                    + "	        <div class=\"form-group\">\n"
                    + "	            <label data-toggle=\"tooltip\" title=\"\" data-original-title=\"3 digits code on back side of the card\">CVV <i class=\"fa fa-question-circle\"></i></label>\n"
                    + "	            <input type=\"number\" class=\"form-control\" required=\"\">\n"
                    + "	        </div> <!-- form-group.// -->\n"
                    + "	    </div>\n"
                    + "	</div> <!-- row.// -->\n"
                    + "	<button class=\"subscribe btn btn-primary btn-block mt-3 \" type=\"submit\"> Confirm  </button>\n"
                    + "    </form>\n"
                    + "</div> <!-- tab-pane.// -->\n"
                    + "<div class=\"tab-pane fade\" id=\"nav-tab-paypal\">\n"
                    + "<p>Paypal is easiest way to pay online</p>\n"
                    + "<p>\n"
                    + "<button type=\"button\" class=\"btn btn-primary\"> <i class=\"fab fa-paypal\"></i> Log in my Paypal </button>\n"
                    + "</p>\n"
                    + "<p><strong>Note:</strong> We currently are not support Paypal method please try to use your credit card\n"
                    + "tempor incididunt ut labore et dolore magna aliqua. </p>\n"
                    + "</div>\n"
                    + "<div class=\"tab-pane fade\" id=\"nav-tab-bank\">\n"
                    + "<p>Bank account details</p>\n"
                    + "<dl class=\"param\">\n"
                    + "  <dt>BANK: </dt>\n"
                    + "  <dd> THE WORLD BANK</dd>\n"
                    + "</dl>\n"
                    + "<dl class=\"param\">\n"
                    + "  <dt>Account number: </dt>\n"
                    + "  <dd> 12345678912345</dd>\n"
                    + "</dl>\n"
                    + "<dl class=\"param\">\n"
                    + "  <dt>IBAN: </dt>\n"
                    + "  <dd> 123456789</dd>\n"
                    + "</dl>\n"
                    + "<p><strong>Note:</strong> Please include your fullname, your phonenumber and the course you want to register and contact us. </p>\n"
                    + "</div> <!-- tab-pane.// -->\n"
                    + "</div> <!-- tab-content .// -->\n"
                    + "\n"
                    + "</div> <!-- card-body.// -->\n"
                    + "</article> <!-- card.// -->\n"
                    + "\n"
                    + "\n"
                    + "	</aside> <!-- col.// -->\n"
                    + "</div> <!-- row.// -->\n"
                    + "\n"
                    + "</div> "
                    + "");
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
