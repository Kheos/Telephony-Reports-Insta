/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.UnitReports.ModifyUnitReports;
import com.bdd.UnitReports.ModifyUnitReportsAll;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Enji
 */
public class Modify_Unit_Reports_Site extends HttpServlet {
    public static final String ATT_MESSAGES_DISPLAY = "messageDisplay";
    public static final String ATT_MESSAGES_DISPLAY_ALL = "messageDisplayAll";
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Modify_Unit_Reports_Site</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Modify_Unit_Reports_Site at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         ModifyUnitReports connect1 = new ModifyUnitReports();

        String messageDisplay = connect1.execute(request);
        
        request.setAttribute(ATT_MESSAGES_DISPLAY, messageDisplay);
        
        ModifyUnitReportsAll connect2 = new ModifyUnitReportsAll();

        String messageDisplayAll = connect2.execute(request);
        
        request.setAttribute(ATT_MESSAGES_DISPLAY_ALL, messageDisplayAll);
        this.getServletContext().getRequestDispatcher("/WEB-INF/unit_reports/modify_unit_reports_site.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
