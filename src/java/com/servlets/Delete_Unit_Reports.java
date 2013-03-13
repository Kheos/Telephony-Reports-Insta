/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.UnitReports.NameUnitReports;
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
public class Delete_Unit_Reports extends HttpServlet {
    public static final String ATT_MESSAGES_NAME_UNITREPORTS = "messageNameUnitReports";
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
            out.println("<title>Servlet Delete_Unit_Reports</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Delete_Unit_Reports at " + request.getContextPath() + "</h1>");
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
         NameUnitReports connect = new NameUnitReports();
        List<String> messageNameUnitReports = connect.execute(request);
        
        /*DisplayUnitReports connect = new DisplayUnitReports();
        List<String> messageData = connect.execute(request);*/

        /* Enregistrement de la liste des messages dans l'objet requête */
        request.setAttribute(ATT_MESSAGES_NAME_UNITREPORTS, messageNameUnitReports);
        /*request.setAttribute(ATT_MESSAGES_DATA, messageData);*/
        this.getServletContext().getRequestDispatcher( "/WEB-INF/unit_reports/delete_unit_reports.jsp" ).forward( request, response );
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
