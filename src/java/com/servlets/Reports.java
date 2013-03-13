/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.DaoFactory;
import com.bdd.ReportDao;
import com.beans.Report;
import com.forms.ReportsForm;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Enji
 */
public class Reports extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_REPORT = "report";
    public static final String ATT_REPORT_MAP = "reportMap";
    public static final String ATT_MODIFIED_REPORT = "modifiedReport";
    public static final String ATT_FORM = "form";
    public static final String ATT_SITE_LIST = "siteList";
    public static final String ATT_MONTH_LIST = "monthList";
    public static final String ATT_FILTER = "filter";
    public static final String ATT_CURRENT_YEAR = "currentYear";
    public static final String ATT_CURRENT_MONTH = "currentMonth";
    public static final String ATT_SESSION_USER = "sessionUser";
    public static final String ATT_RESULT = "result";
    public static final String ATT_REPORT_TYPE = "type";
    public static final String ATT_REDIRECT_BOOL = "redirectBool";
    public static final String VUE = "/WEB-INF/reports.jsp";
    public static final String urlRedirection = "/Add_Unit_Reports";
    public static final String REDIRECT_VUE = "/WEB-INF/unit_reports/add_unit_reports.jsp";
    public Map<Integer, String> monthList = new HashMap<Integer, String>();
    private ReportDao reportDao;

    @Override
    public void init() throws ServletException {
        /*
         * Récupération d'une instance de notre DAO Utilisateur
         */
        this.reportDao = ((DaoFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getReportDao();

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

    /**
     * Handles the actions executed in doGet and doPost HTTP
     *
     * @param request servlet request
     * @return servlet request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        /*
         * Préparation de l'objet formulaire
         */
        ReportsForm form = new ReportsForm(reportDao);

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(ATT_SESSION_USER);

        Map<Integer, String> siteList = form.constructSiteList(login);

        request.setAttribute(ATT_SITE_LIST, siteList);

        request = actionsGetPost(request);

        if (siteList.isEmpty()) {
            session.setAttribute(ATT_REDIRECT_BOOL, Boolean.TRUE);
            response.sendRedirect(request.getContextPath() + urlRedirection);
        } else {
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
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

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(ATT_SESSION_USER);

        String filterSubmit = request.getParameter("filter");

        if (filterSubmit == null) {

            /*
             * Préparation de l'objet formulaire
             */
            ReportsForm form = new ReportsForm(reportDao);

            Map<Integer, Report> reportMap = (Map<Integer, Report>) session.getAttribute(ATT_REPORT_MAP);

            int idReport = 0;
            String submitReport = null;
            for (int i = 1; submitReport == null && i <= 12; i++) {
                submitReport = request.getParameter("report" + i);
                idReport = i;
            }

            int month = reportMap.get(idReport).getMonth();
            int year = reportMap.get(idReport).getYear();
            String refContract = reportMap.get(idReport).getSite();
            String type = (String) session.getAttribute(ATT_REPORT_TYPE);
            boolean editable = reportMap.get(idReport).getEditable();

            /*
             * Appel au traitement et à la validation de la requête, et
             * récupération du bean en résultant
             */
            if (editable) {
                reportMap.put(idReport, form.saveReport(refContract, type, month, year, request));
            } else {
                reportMap.put(idReport, form.updateReport(refContract, type, month, year, request));
            }

            Map<Integer, String> siteList = form.constructSiteList(login);

            String result = form.getResult();

            /*
             * Stockage du formulaire et du bean dans l'objet request
             */
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_REPORT_MAP, reportMap);
            request.setAttribute(ATT_MODIFIED_REPORT, idReport);
            request.setAttribute(ATT_SITE_LIST, siteList);
            request.setAttribute(ATT_RESULT, result);

            session.setAttribute(ATT_REPORT_MAP, reportMap);
        } else {

            int month = Integer.parseInt(request.getParameter("month"));
            int year = Integer.parseInt(request.getParameter("year"));
            String refContract = request.getParameter("site");
            int currentMonth = Integer.parseInt(request.getAttribute(ATT_CURRENT_MONTH).toString());
            int currentYear = Integer.parseInt(request.getAttribute(ATT_CURRENT_YEAR).toString());

            /*
             * Préparation de l'objet formulaire
             */
            ReportsForm form = new ReportsForm(reportDao);

            String type = form.getType(refContract);

            /*
             * Appel au traitement et à la validation de la requête, et
             * récupération du bean en résultant
             */
            Map<Integer, Report> reportMap = form.constructList(refContract, month, year);

            if (month == 0) {
                if (currentYear > year + 1) {
                    for (int i = 1; i <= 12; i++) {
                        if (reportMap.get(i) == null) {
                            Report report = new Report();
                            report.setMonth(i);
                            if (i <= 3) {
                                report.setYear(year + 1);
                            } else {
                                report.setYear(year);
                            }
                            report.setSite(refContract);
                            report.setType(type);
                            report.setEditable(Boolean.TRUE);
                            reportMap.put(i, report);
                        }
                    }
                } else if (currentYear == year + 1) {
                    if (currentMonth <= 3) {
                        for (int i = 1; i <= 12; i++) {
                            if (reportMap.get(i) == null) {
                                Report report = new Report();
                                report.setMonth(i);
                                if (i <= 3) {
                                    report.setYear(year + 1);
                                } else {
                                    report.setYear(year);
                                }
                                report.setSite(refContract);
                                report.setType(type);
                                report.setEditable(Boolean.TRUE);
                                reportMap.put(i, report);
                                if (currentMonth == 1 && i == 1 || currentMonth == 2 && i == 2) {
                                    i = 3;
                                }
                            }
                        }
                    } else {
                        for (int i = 1; i <= 12; i++) {
                            if (reportMap.get(i) == null) {
                                Report report = new Report();
                                report.setMonth(i);
                                if (i <= 3) {
                                    report.setYear(year + 1);
                                } else {
                                    report.setYear(year);
                                }
                                report.setSite(refContract);
                                report.setType(type);
                                report.setEditable(Boolean.TRUE);
                                reportMap.put(i, report);
                            }
                        }
                    }
                } else {
                    for (int i = 4; i <= currentMonth; i++) {
                        if (reportMap.get(i) == null) {
                            Report report = new Report();
                            report.setMonth(i);
                            report.setYear(year);
                            report.setSite(refContract);
                            report.setType(type);
                            report.setEditable(Boolean.TRUE);
                            reportMap.put(i, report);
                        }
                    }
                }
            } else {
                if (currentYear > year + 1) {
                    if (reportMap.get(month) == null) {
                        Report report = new Report();
                        report.setMonth(month);
                        if (month <= 3) {
                            report.setYear(year + 1);
                        } else {
                            report.setYear(year);
                        }
                        report.setSite(refContract);
                        report.setType(type);
                        report.setEditable(Boolean.TRUE);
                        reportMap.put(month, report);
                    }
                } else if (currentYear == year + 1) {
                    if (month < 4 && month <= currentMonth) {
                        if (reportMap.get(month) == null) {
                            Report report = new Report();
                            report.setMonth(month);
                            report.setYear(year + 1);
                            report.setSite(refContract);
                            report.setType(type);
                            report.setEditable(Boolean.TRUE);
                            reportMap.put(month, report);
                        }
                    } else if (month >= 4) {
                        if (reportMap.get(month) == null) {
                            Report report = new Report();
                            report.setMonth(month);
                            report.setYear(year);
                            report.setSite(refContract);
                            report.setType(type);
                            report.setEditable(Boolean.TRUE);
                            reportMap.put(month, report);
                        }
                    }
                } else {
                    if (month < 4 && month <= currentMonth && currentMonth < 4) {
                        if (reportMap.get(month) == null) {
                            Report report = new Report();
                            report.setMonth(month);
                            report.setYear(year + 1);
                            report.setSite(refContract);
                            report.setType(type);
                            report.setEditable(Boolean.TRUE);
                            reportMap.put(month, report);
                        }
                    } else if (month >= 4 && (month <= currentMonth || currentMonth < 4)) {
                        if (reportMap.get(month) == null) {
                            Report report = new Report();
                            report.setMonth(month);
                            report.setYear(year);
                            report.setSite(refContract);
                            report.setType(type);
                            report.setEditable(Boolean.TRUE);
                            reportMap.put(month, report);
                        }
                    }
                }
            }

            Map<Integer, String> siteList = form.constructSiteList(login);

            Map<String, String> filter = new HashMap<String, String>();

            filter.put("refContract", refContract);
            filter.put("year", String.valueOf(year));
            filter.put("month", String.valueOf(month));
            session.setAttribute(ATT_FILTER, filter);

            /*
             * Stockage du formulaire et du bean dans l'objet request
             */
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_SITE_LIST, siteList);

            session.setAttribute(ATT_REPORT_TYPE, type);
            session.setAttribute(ATT_REPORT_MAP, reportMap);
        }

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
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
