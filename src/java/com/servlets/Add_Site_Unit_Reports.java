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

/**
 *
 * @author Enji
 */
public class Add_Site_Unit_Reports extends HttpServlet {

	public static final String ATT_MESSAGES_SITE = "messageSite";
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
		String messageSite = form.listActiveSites(request);
		request.setAttribute(ATT_MESSAGES_SITE, messageSite);
		this.getServletContext().getRequestDispatcher("/WEB-INF/unit_reports/add_site_unit_reports.jsp").forward(request, response);
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
