/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dal.CategorycourseDAO;
import dal.CourseDAO;
import dal.CoursestatusDAO;
import dal.DimensionDAO;
import dal.PricePackageDAO;
import dal.SubCategoryCourseDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.CategoryCourse;
import model.Course;
import model.CourseStatus;
import model.Dimension;
import model.Price_Package;
import model.SubCategoryCourse;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
/**
 *
 * @author ADMIN
 */
public class subjectdetailsServlet extends HttpServlet {

    private static final long SerialVersionUID = 1L;
    private static final String UPLOAD_DIR = "web\\img";

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

        CourseDAO dao = new CourseDAO();
        SubCategoryCourseDAO subjectdao = new SubCategoryCourseDAO();
        CategorycourseDAO gradedao = new CategorycourseDAO();
        CoursestatusDAO statusdao = new CoursestatusDAO();
        DimensionDAO dimensiondao = new DimensionDAO();
        PricePackageDAO pricePackageDAO = new PricePackageDAO();

        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String view = request.getParameter("view");
        if (action == null) {
            Course course = dao.getCourseById(Integer.parseInt(id));
            List<CategoryCourse> allGrade = gradedao.getAllCategoryCourse();
            ArrayList<SubCategoryCourse> allSubjects = subjectdao.getSubCategoryCourse();
            ArrayList<CourseStatus> allCourseStatus = statusdao.getAllCourseStatus();
            ArrayList<Dimension> courseDimensionWithoutByPage = dimensiondao.getCourseDimensionWithoutByPage(Integer.parseInt(id));
            ArrayList<Price_Package> allPricePackageWithout = pricePackageDAO.getAllPricePackageWithout(Integer.parseInt(id));
            int total2 = pricePackageDAO.getTotal(Integer.parseInt(id));
            int total = dimensiondao.getTotal(Integer.parseInt(id), "");

            if (total2 % 10 != 0) {
                total2 = total2 / 10 + 1;
            } else if (total2 == 0) {
                total2 = 1;
            } else {
                total2 = total2 / 10;
            }
            if (total % 10 != 0) {
                total = total / 10 + 1;
            } else if (total == 0) {
                total = 1;
            } else {
                total = total / 10;
            }
            int pagetotal = total;
            int pricetotal = total2;

            request.setAttribute("course", course);
            request.setAttribute("grade", allGrade);
            request.setAttribute("subject", allSubjects);
            request.setAttribute("status", allCourseStatus);
            request.setAttribute("total", pagetotal);
            request.setAttribute("pricetotal", pricetotal);
            request.setAttribute("addnewdimension", courseDimensionWithoutByPage);
            request.setAttribute("addnewpricepackage", allPricePackageWithout);

            if (view != null && view.equals("true")) {
                request.getRequestDispatcher("viewsubjectdetails.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("subjectdetails.jsp").forward(request, response);
            }
        } else if (action != null && action.equals("deletedimension")) {
            String did = request.getParameter("did");
            dimensiondao.deleteDimension(id, did);
            response.getWriter().print(id + did + action);
            response.sendRedirect("subjectdetails?id=" + id + "&part=dimension");
        } else if (action != null && action.equals("deletepricepackage")) {
            String pid = request.getParameter("pid");
            pricePackageDAO.deletePricePackage(id, pid);
            response.getWriter().print(id + pid + action);
            response.sendRedirect("subjectdetails?id=" + id + "&part=pricepkg");
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
        CourseDAO dao = new CourseDAO();
        DimensionDAO dimensiondao = new DimensionDAO();
        PricePackageDAO pricePackageDAO = new PricePackageDAO();

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action.equals("update")) {
            String coursename = request.getParameter("coursename");
            String title = request.getParameter("title");
            String tagline = request.getParameter("tagline");
            String gradeselect = request.getParameter("gradeselect");
            String subjectselect = request.getParameter("subjectselect");
            String featuredsubject = request.getParameter("featuredsubject");
            String statusselect = request.getParameter("statusselect");
            String describe = request.getParameter("describe");
            response.getWriter().print("aaa");
            String thumbnail = uploadFile(request, response);
            response.getWriter().print(thumbnail);
            dao.UpdateCourse(id, coursename, title, tagline, gradeselect, subjectselect, featuredsubject, statusselect, describe, thumbnail);
        }
        if (action.equals("addnewdimesnsion")) {
            String[] dimensionvalue = request.getParameterValues("addmultidimension");
            for (String a : dimensionvalue) {
                dimensiondao.addDimension(id, a);
            }
            response.sendRedirect("subjectdetails?id=" + id + "&part=dimension");
            return;

        }
        if (action.equals("addnewpricepackage")) {
            String[] pricevalues = request.getParameterValues("addmultipricepkg");
            if (pricevalues != null) {
                for (String a : pricevalues) {
                    pricePackageDAO.insertNewPricePackage(id, a);
                }
            }
            response.sendRedirect("subjectdetails?id=" + id + "&part=pricepkg");
            return;
        }
        if (action.equals("editdimension")) {
            String status = request.getParameter("status");
            String did = request.getParameter("did");
            dimensiondao.updateDimension(id, did, status);
            response.sendRedirect("subjectdetails?id=" + id + "&part=dimension");
            return;
        }

        response.sendRedirect("subjectdetails?id=" + id);
    }

    private String uploadFile(HttpServletRequest request, HttpServletResponse res) throws IOException, ServletException {
        String fileName = "";

        PrintWriter out = res.getWriter();
        try {
            Part filePart = request.getPart("thumbnail");

//            fileName = (String) getFileName(filePart);
            fileName = "Coursename" + request.getParameter("id") + getFileName(filePart);

            if (getFileName(filePart) == null || getFileName(filePart).equals("")) {
//              out.print("test"+getFileName(filePart));
                return "";
            }
//          else
//          {
//              String username = (String) request.getParameter("username");
//              fileName=username+".jpg";
//          }
            out.print("bbbbbb");

            String applicationPath = request.getServletContext().getRealPath("");
            String contextPath = request.getServletContext().getRealPath("");
            out.println(contextPath);

            out.println(applicationPath + "aaaaaaa" + contextPath);
            String basePath = applicationPath + ".." + File.separator + ".." + File.separator + UPLOAD_DIR + File.separator;
//String basePath="C:\\Users\\ADMIN\\Documents\\Kì 5 Summer 2022\\SWP391\\26thang6\\summer2022-se1616-g3-main\\Online-Learning-SWP"+File.separator;
            out.println(basePath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            fileName = "";
        }
        return "img" + File.separator + fileName;
    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("*****partHeader :" + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
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
