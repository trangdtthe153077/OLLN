
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.CourseDAO;
import dal.PricePackageDAO;
import dal.SendEmail;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Course;
import model.Price_Package;
import model.User;
import utils.EncodeData;

/**
 *
 * @author ADMIN
 */
public class courseregisterServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet courseregisterServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet courseregisterServlet at " + request.getContextPath() + "</h1>");
        out.println("</body>");
        out.println("</html>");
        String action = (String) request.getParameter("action");
        CourseDAO coursedao = new CourseDAO();

        if (action != null && action.equals("active")) {
            String id = EncodeData.deCode(request.getParameter("id"));
            String cid = EncodeData.deCode(request.getParameter("cid"));
            coursedao.UpdateRegister(id, cid);

            request.getRequestDispatcher("activesuccess.jsp").forward(request, response);
            return;
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
        CourseDAO coursedao = new CourseDAO();

        {
            int price;
            String pkg = request.getParameter("pkg");
            if (pkg.equals("1")) {
                price = 3200;
            }
            if (pkg.equals("2")) {
                price = 6000;
            }
            if (pkg.equals("3")) {
                price = 9800;
            }
            HttpSession session = request.getSession();
            session.setAttribute(pkg, pkg);
            User acc = (User) session.getAttribute("account");
            String subjectid = request.getParameter("subject");
            Course course = coursedao.getSingleCourse(subjectid);
            PricePackageDAO pricedao = new PricePackageDAO();
            Price_Package pri = pricedao.getPricePackage(pkg);

            coursedao.CourseRegister(acc.getId(), subjectid, pkg);
            SendEmail sm = new SendEmail(acc.getEmail(), "123");
            String content = ("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n"
                    + "\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n"
                    + "    <meta name=\"x-apple-disable-message-reformatting\">\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                    + "    <meta content=\"telephone=no\" name=\"format-detection\">\n"
                    + "    <title></title>\n"
                    + "    <!--[if (mso 16)]>\n"
                    + "    <style type=\"text/css\">\n"
                    + "    a {text-decoration: none;}\n"
                    + "    </style>\n"
                    + "    <![endif]-->\n"
                    + "    <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\n"
                    + "    <!--[if gte mso 9]>\n"
                    + "<xml>\n"
                    + "    <o:OfficeDocumentSettings>\n"
                    + "    <o:AllowPNG></o:AllowPNG>\n"
                    + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                    + "    </o:OfficeDocumentSettings>\n"
                    + "</xml>\n"
                    + "<![endif]-->\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "    <div class=\"es-wrapper-color\">\n"
                    + "        <!--[if gte mso 9]>\n"
                    + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                    + "				<v:fill type=\"tile\" color=\"#555555\"></v:fill>\n"
                    + "			</v:background>\n"
                    + "		<![endif]-->\n"
                    + "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "            <tbody>\n"
                    + "                <tr>\n"
                    + "                    <td class=\"esd-email-paddings\" valign=\"top\">\n"
                    + "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content esd-header-popover\" align=\"center\">\n"
                    + "                            <tbody>\n"
                    + "                                <tr>\n"
                    + "                                    <td class=\"esd-stripe\" align=\"center\" esd-custom-block-id=\"88701\">\n"
                    + "                                        <table class=\"es-content-body\" style=\"background-color: transparent;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
                    + "                                            <tbody>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p5b es-p10r es-p10l\" esd-general-paddings-checked=\"false\" align=\"left\">\n"
                    + "                                                        <!--[if mso]><table width=\"580\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"280\" valign=\"top\"><![endif]-->\n"
                    + "                                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"es-m-p0r es-m-p20b esd-container-frame\" width=\"280\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"280\" valign=\"top\"><![endif]-->\n"
                    + "                                                        <table cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"280\" align=\"left\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                        <!--[if mso]></td></tr></table><![endif]-->\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                            </tbody>\n"
                    + "                                        </table>\n"
                    + "                                    </td>\n"
                    + "                                </tr>\n"
                    + "                            </tbody>\n"
                    + "                        </table>\n"
                    + "                        <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
                    + "                            <tbody>\n"
                    + "                                <tr>\n"
                    + "                                    <td class=\"esd-stripe\" align=\"center\">\n"
                    + "                                        <table class=\"es-content-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
                    + "                                            <tbody>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p20t es-p20b es-p10r es-p10l\" style=\"background-color: #191919;\" esd-general-paddings-checked=\"false\" bgcolor=\"#191919\" align=\"left\">\n"
                    + "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"580\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td align=\"center\" class=\"esd-block-image\" style=\"font-size: 0px;\"><a target=\"_blank\"><img class=\"adapt-img\" src=\"https://demo.stripocdn.email/content/guids/32182399-4e09-4c49-a189-0255d4d09475/images/simple.png\" alt style=\"display: block;\" width=\"100%;\"></a></td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p20t es-p20b es-p20r es-p20l\" esd-general-paddings-checked=\"false\" style=\"background-color: #ffcc99;\" bgcolor=\"#ffcc99\" align=\"left\">\n"
                    + "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"560\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p15t es-p15b\" align=\"center\">\n"
                    + "                                                                                        <div class=\"esd-text\">\n"
                    + "                                                                                            <h2 style=\"color: #242424; font-size: 30px;\"><strong>Start your course from today</strong></h2>\n"
                    + "                                                                                        </div>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p10l\" align=\"center\">\n"
                    + "                                                                                        <p style=\"color: #242424;\">Hi " + acc.getFullname() + ", this is your confirmation email on registering to the course " + course.getName() + "</p>\n"
                    + "                                                                                        <p style=\" color: #242424;\">Your register date will start when you click this button</p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-button es-p15t es-p15b es-p10r es-p10l\" align=\"center\"><span  class=\"es-button-border\" style=\" padding:20px; border-radius: 20px; background: #191919 none repeat scroll 0% 0%; border-style: solid; border-color: #2cb543; border-width: 0px;\"><a href=\"http://localhost:8084/Online-Learning-SWP/courseregister?action=active&id=" + EncodeData.enCode(acc.getId() + "") + "&cid=" + EncodeData.enCode(subjectid) + "\" class=\"es-button es-button-1655207450996\" target=\"_blank\" style=\"border-radius: 20px; font-family: lucida sans unicode,lucida grande,sans-serif; font-weight: normal; font-size: 18px; border-width: 10px 35px; background: #191919 none repeat scroll 0% 0%; border-color: #191919; color: #ffffff;\">Click here to confirm</a></span></td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p15t es-p10b es-p10r es-p10l\" style=\"background-color: #f8f8f8;\" esd-general-paddings-checked=\"false\" bgcolor=\"#f8f8f8\" align=\"left\">\n"
                    + "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"580\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text\" align=\"center\">\n"
                    + "                                                                                        <h2 style=\"color: #191919;\">Course register</h2>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p25t es-p5b es-p20r es-p20l\" esd-general-paddings-checked=\"false\" style=\"background-color: #f8f8f8;\" bgcolor=\"#f8f8f8\" align=\"left\">\n"
                    + "                                                        <!--[if mso]><table width=\"560\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"270\" valign=\"top\"><![endif]-->\n"
                    + "                                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"es-m-p20b esd-container-frame\" width=\"270\" align=\"left\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-image\" align=\"center\" style=\"font-size: 0px;\"><a target=\"_blank\" href=\"https://viewstripo.email/\"><img class=\"adapt-img\" src=\"" + course.getThumbnail() + "\" alt width=\"270\" style=\"display: block;\"></a></td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"270\" valign=\"top\"><![endif]-->\n"
                    + "                                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"270\" align=\"left\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text\" align=\"left\">\n"
                    + "                                                                                        <p style=\"font-size: 16px;\"><strong style=\"line-height: 150%;\">" + course.getName() + "</strong></p>\n"
                    + "                                                                                        <p><br></p>\n"
                    + "                                                                                        <p>" + course.getTagline() + "</p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p20t\" align=\"left\">\n"
                    + "                                                                                        <p style=\"font-size: 15px;\"><b>Package: " + pri.getName() + "</b><br>Course instructors, course assistant online support<br>Step by step studying guide Comprehensive study the studying guide<br>Free entry to a Total Success Open Course<b></b></p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                        <!--[if mso]></td></tr></table><![endif]-->\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p10t es-p10b es-p10r es-p10l\" style=\"background-color: #f8f8f8;\" esd-general-paddings-checked=\"false\" bgcolor=\"#f8f8f8\" align=\"left\">\n"
                    + "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"580\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-spacer es-p20t es-p20b es-p10r es-p10l\" bgcolor=\"#f8f8f8\" align=\"center\" style=\"font-size:0\">\n"
                    + "                                                                                        <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n"
                    + "                                                                                            <tbody>\n"
                    + "                                                                                                <tr>\n"
                    + "                                                                                                    <td style=\"border-bottom: 1px solid #191919; background: rgba(0, 0, 0, 0) none repeat scroll 0% 0%; height: 1px; width: 100%; margin: 0px;\"></td>\n"
                    + "                                                                                                </tr>\n"
                    + "                                                                                            </tbody>\n"
                    + "                                                                                        </table>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p15b\" align=\"center\">\n"
                    + "                                                                                        <table class=\"cke_show_border\" width=\"240\" height=\"101\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\">\n"
                    + "                                                                                            <tbody>\n"
                    + "                                                                                                <tr>\n"
                    + "                                                                                                    <td><strong>Old Price</strong></td>\n"
                    + "                                                                                                    <td style=\"text-align: right;\"><del>$" + pri.getListprice() + "</del></td>\n"
                    + "                                                                                                </tr>\n"
                    + "                                                                                                <tr>\n"
                    + "                                                                                                    <td style=\"font-size: 18px; line-height: 200%;\"><strong>Sale Price:</strong></td>\n"
                    + "                                                                                                    <td style=\"text-align: right; font-size: 18px; line-height: 200%;\"><strong>$" + pri.getSaleprice() + "</strong></td>\n"
                    + "                                                                                                </tr>\n"
                    + "                                                                                            </tbody>\n"
                    + "                                                                                        </table>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p25t es-p30b es-p20r es-p20l\" esd-general-paddings-checked=\"false\" style=\"background-color: #f8f8f8;\" bgcolor=\"#f8f8f8\" align=\"left\">\n"
                    + "                                                        <!--[if mso]><table width=\"560\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"270\" valign=\"top\"><![endif]-->\n"
                    + "                                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"es-m-p20b esd-container-frame\" width=\"270\" align=\"left\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p10b\" align=\"center\">\n"
                    + "                                                                                        <h3 style=\"color: #242424;\">We're here to help</h3>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text\" align=\"center\">\n"
                    + "                                                                                        <p style=\"line-height: 150%; color: #242424;\">Call <a target=\"_blank\" style=\"line-height: 150%; \" href=\"tel:123456789\">123456789</a> or <a target=\"_blank\" href=\"http://localhost:8084/Online-Learning-SWP/home\">visit us online</a></p>\n"
                    + "                                                                                        <p style=\"line-height: 150%; color: #242424;\">for expert assistance.</p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"270\" valign=\"top\"><![endif]-->\n"
                    + "                                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"270\" align=\"left\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p10b\" align=\"center\">\n"
                    + "                                                                                        <h3 style=\"color: #242424;\">Our guarantee</h3>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text\" align=\"center\">\n"
                    + "                                                                                        <p style=\"line-height: 150%; color: #242424;\">Your satisfaction is 100% guaranteed.<br></p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                        <!--[if mso]></td></tr></table><![endif]-->\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                            </tbody>\n"
                    + "                                        </table>\n"
                    + "                                    </td>\n"
                    + "                                </tr>\n"
                    + "                            </tbody>\n"
                    + "                        </table>\n"
                    + "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\">\n"
                    + "                            <tbody>\n"
                    + "                                <tr>\n"
                    + "                                    <td class=\"esd-stripe\" align=\"center\" esd-custom-block-id=\"88703\">\n"
                    + "                                        <table class=\"es-footer-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
                    + "                                            <tbody>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p20\" esd-general-paddings-checked=\"false\" align=\"left\">\n"
                    + "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"560\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-social es-p10t es-p20b\" align=\"center\" style=\"font-size:0\">\n"
                    + "                                                                                        <table class=\"es-table-not-adapt es-social\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                                            <tbody>\n"
                    + "                                                                                                <tr>\n"
                    + "                                                                                                    <td class=\"es-p15r\" valign=\"top\" align=\"center\"><a href><img title=\"Twitter\" src=\"https://stripo.email/cabinet/assets/editor/assets/img/social-icons/circle-gray/twitter-circle-gray.png\" alt=\"Tw\" width=\"32\" height=\"32\"></a></td>\n"
                    + "                                                                                                    <td class=\"es-p15r\" valign=\"top\" align=\"center\"><a href><img title=\"Facebook\" src=\"https://stripo.email/cabinet/assets/editor/assets/img/social-icons/circle-gray/facebook-circle-gray.png\" alt=\"Fb\" width=\"32\" height=\"32\"></a></td>\n"
                    + "                                                                                                    <td class=\"es-p15r\" valign=\"top\" align=\"center\"><a href><img title=\"Youtube\" src=\"https://stripo.email/cabinet/assets/editor/assets/img/social-icons/circle-gray/youtube-circle-gray.png\" alt=\"Yt\" width=\"32\" height=\"32\"></a></td>\n"
                    + "                                                                                                    <td valign=\"top\" align=\"center\"><a href><img title=\"Linkedin\" src=\"https://stripo.email/cabinet/assets/editor/assets/img/social-icons/circle-gray/linkedin-circle-gray.png\" alt=\"In\" width=\"32\" height=\"32\"></a></td>\n"
                    + "                                                                                                </tr>\n"
                    + "                                                                                            </tbody>\n"
                    + "                                                                                        </table>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text\" align=\"center\">\n"
                    + "                                                                                        <p><strong><a target=\"_blank\" style=\"line-height: 150%;\" href=\"http://localhost:8084/Online-Learning-SWP/home\">Browse</a>&nbsp;more course on our system</strong></p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td align=\"center\" class=\"esd-block-text es-p20t es-p20b\">\n"
                    + "                                                                                        <p style=\"line-height: 120%;\">FPT University</p>\n"
                    + "                                                                                        <p style=\"line-height: 120%;\"><b>elearning2401@gmail.com</b></p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td align=\"center\" class=\"esd-block-text\">\n"
                    + "                                                                                        <p><strong><a target=\"_blank\" style=\"line-height: 150%;\" class=\"unsubscribe\" href>Unsubscribe</a> • <a target=\"_blank\" style=\"line-height: 150%;\" href=\"https://viewstripo.email\">Update Preferences</a> • <a target=\"_blank\" style=\"line-height: 150%;\" href=\"https://viewstripo.email\">Customer Support</a></strong></p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td class=\"esd-block-text es-p10t es-p10b\" align=\"center\">\n"
                    + "                                                                                        <p><em><span style=\"font-size: 11px; line-height: 150%;\">You are receiving this email because you have visited our site or asked us about regular newsletter</span></em></p>\n"
                    + "                                                                                    </td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                            </tbody>\n"
                    + "                                        </table>\n"
                    + "                                    </td>\n"
                    + "                                </tr>\n"
                    + "                            </tbody>\n"
                    + "                        </table>\n"
                    + "                        <table class=\"esd-footer-popover es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
                    + "                            <tbody>\n"
                    + "                                <tr>\n"
                    + "                                    <td class=\"esd-stripe\" align=\"center\">\n"
                    + "                                        <table class=\"es-content-body\" style=\"background-color: transparent;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n"
                    + "                                            <tbody>\n"
                    + "                                                <tr>\n"
                    + "                                                    <td class=\"esd-structure es-p30t es-p30b es-p20r es-p20l\" align=\"left\">\n"
                    + "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                            <tbody>\n"
                    + "                                                                <tr>\n"
                    + "                                                                    <td class=\"esd-container-frame\" width=\"560\" valign=\"top\" align=\"center\">\n"
                    + "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n"
                    + "                                                                            <tbody>\n"
                    + "                                                                                <tr>\n"
                    + "                                                                                    <td align=\"center\" class=\"esd-empty-container\" style=\"display: none;\"></td>\n"
                    + "                                                                                </tr>\n"
                    + "                                                                            </tbody>\n"
                    + "                                                                        </table>\n"
                    + "                                                                    </td>\n"
                    + "                                                                </tr>\n"
                    + "                                                            </tbody>\n"
                    + "                                                        </table>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>\n"
                    + "                                            </tbody>\n"
                    + "                                        </table>\n"
                    + "                                    </td>\n"
                    + "                                </tr>\n"
                    + "                            </tbody>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "    </div>\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>");

//        request.getRequestDispatcher("checkout.jsp").forward(request, response);
            sm.sendConfirmEMail(acc.getEmail(), course.getName(), content);
            request.getRequestDispatcher("activecourse.jsp").forward(request, response);
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
