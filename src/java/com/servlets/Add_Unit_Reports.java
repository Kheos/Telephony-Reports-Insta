/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.DaoFactory;
import com.bdd.UnitReportDao;
import com.forms.UnitReportForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Enji
 */
public class Add_Unit_Reports extends HttpServlet {
	
	public static final String ATT_MESSAGES_SITE = "messageSite";
	public static final String ATT_MESSAGES_COUNTRY = "messageCountry";
	public static final String ATT_NAME_UNIT_REPORT = "nameUnitReports";
	public static final String ATT_TYPE_UNIT_REPORT = "typeUnitReports";
	public static final String ATT_COUNTRY_UNIT_REPORT = "countryUnitReports";
	public static final String ATT_REDIRECT_BOOL = "redirectBool";
	public static final String ATT_NAME_ERROR = "nameError";
	public static final String urlRedirection = "/Add_Unit_Reports";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private UnitReportDao unitReportDao;

	@Override
	public void init() throws ServletException {
		/*
		 * Récupération d'une instance de notre DAO Utilisateur
		 */
		this.unitReportDao = ((DaoFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUnitReportDao();

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
		//Préparation de l'objet formulaire
		UnitReportForm form = new UnitReportForm(unitReportDao);
		List<String> messageCountry = form.listActiveCountries();
		System.out.println(request.getAttribute(ATT_REDIRECT_BOOL));
		HttpSession session = request.getSession();
		Boolean redirectBool = (Boolean) session.getAttribute(ATT_REDIRECT_BOOL);
		if (redirectBool != null) {
			request.setAttribute(ATT_REDIRECT_BOOL, redirectBool);
			session.removeAttribute(ATT_REDIRECT_BOOL);
		}
		request.setAttribute(ATT_MESSAGES_COUNTRY, messageCountry);
		this.getServletContext().getRequestDispatcher("/WEB-INF/unit_reports/add_unit_reports.jsp").forward(request, response);
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

		//Préparation de l'objet formulaire
		UnitReportForm form = new UnitReportForm(unitReportDao);

		//Récupération du paramètre "nameUnitReports"
		String nameUnitReport = request.getParameter("nameUnitReports");

		if (!form.checkUnitReportName(nameUnitReport) || "".equals(nameUnitReport)) {
			request.setAttribute(ATT_NAME_ERROR, Boolean.TRUE);
			List<String> messageCountry = form.listActiveCountries();
			request.setAttribute(ATT_MESSAGES_COUNTRY, messageCountry);
			this.getServletContext().getRequestDispatcher("/WEB-INF/unit_reports/add_unit_reports.jsp").forward(request, response);
		}
		else {
			String messageSite = form.listActiveSites(request);
			request.setAttribute(ATT_MESSAGES_SITE, messageSite);
			request.setAttribute(ATT_NAME_UNIT_REPORT, nameUnitReport);
			request.setAttribute(ATT_TYPE_UNIT_REPORT, request.getParameter("typeUnitReports"));
			request.setAttribute(ATT_COUNTRY_UNIT_REPORT, request.getParameter("countryUnitReports"));
			this.getServletContext().getRequestDispatcher("/WEB-INF/unit_reports/add_site_unit_reports.jsp").forward(request, response);
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
