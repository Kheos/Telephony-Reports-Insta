/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.DaoFactory;
import com.bdd.UnitReports.CountryUnitReports;
import com.bdd.UnitReports.NameUnitReports;
import com.bdd.Extract.DateMonthExtract;
import com.bdd.ExtractDao;
import com.beans.ExtractTab;
import com.forms.ExtractForm;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author 250665
 */
public class Download_Extract extends HttpServlet {

    public static final String ATT_MESSAGES_NAME_UNITREPORTS = "messageNameUnitReports";
    public static final String ATT_MESSAGES_COUNTRY = "messageCountry";
    public static final String ATT_MESSAGES_DATE_MONTH = "messageDateMonth";
    public static final String ATT_MONTH_LIST = "monthList";
    public static final String ATT_CURRENT_YEAR = "currentYear";
    public static final String ATT_CURRENT_MONTH = "currentMonth";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String VUE = "/WEB-INF/data_extract/download_extract.jsp";
    private ExtractDao extractDao;
    public Map<Integer, String> monthList = new HashMap<Integer, String>();

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
    @Override
    public void init() throws ServletException {
        /*
         * Récupération d'une instance de notre DAO Utilisateur
         */
        this.extractDao = ((DaoFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getExtractDao();

        this.monthList.put(1, "January");
        this.monthList.put(2, "February");
        this.monthList.put(3, "March");
        this.monthList.put(4, "April");
        this.monthList.put(5, "May");
        this.monthList.put(6, "June");
        this.monthList.put(7, "July");
        this.monthList.put(8, "August");
        this.monthList.put(9, "September");
        this.monthList.put(10, "October");
        this.monthList.put(11, "November");
        this.monthList.put(12, "December");

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Download_Extract</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Download_Extract at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    protected HttpServletRequest actionsGetPost(HttpServletRequest request)
            throws ServletException, IOException {

        GregorianCalendar currentCalendar = new GregorianCalendar();
        int currentYear = currentCalendar.get(GregorianCalendar.YEAR);
        int currentMonth = currentCalendar.get(GregorianCalendar.MONTH) + 1;

        request.setAttribute(ATT_MONTH_LIST, this.monthList);
        request.setAttribute(ATT_CURRENT_MONTH, currentMonth);
        request.setAttribute(ATT_CURRENT_YEAR, currentYear);

        return request;
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
        request = actionsGetPost(request);

        NameUnitReports connect = new NameUnitReports();
        List<String> messageNameUnitReports = connect.execute(request);

        request.setAttribute(ATT_MESSAGES_NAME_UNITREPORTS, messageNameUnitReports);

        CountryUnitReports connect2 = new CountryUnitReports();
        List<String> messageCountry = connect2.execute(request);
        request.setAttribute(ATT_MESSAGES_COUNTRY, messageCountry);

        DateMonthExtract connect3 = new DateMonthExtract();
        List<String> messageDateMonth = connect3.execute(request);
        request.setAttribute(ATT_MESSAGES_DATE_MONTH, messageDateMonth);

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
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

        request = actionsGetPost(request);

        ExtractForm form = new ExtractForm(extractDao);

        String typeExtract = request.getParameter("typeExtract");
        String nameExtract = request.getParameter("nameExtract");
        String dateMode = request.getParameter("dateMode");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        int fiscalYear = Integer.parseInt(request.getParameter("fiscalYear"));
        System.out.println(typeExtract);
        Map<Integer, ExtractTab> extractMap = null;

        if ("monthlyMode".equals(dateMode)) {
            extractMap = form.extractMonth(typeExtract, nameExtract, month, year);
        } else {
            extractMap = form.extractFiscalYear(typeExtract, nameExtract, fiscalYear);
        }

        WritableWorkbook workbook = null;

        try {
            response.setContentType("application/vnd.ms-excel");
            
            workbook = Workbook.createWorkbook(response.getOutputStream());
            workbook = form.constructExtract(extractMap, workbook);
            workbook.write();
            workbook.close();
        
        } catch (WriteException ex) {
            Logger.getLogger(Download_Extract.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
