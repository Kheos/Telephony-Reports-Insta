/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.DaoFactory;
import com.bdd.ExtractDao;
import com.bdd.UnitReportDao;
import com.beans.ExtractTab;
import com.forms.ExtractForm;
import com.forms.UnitReportForm;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
	public static final String ATT_MONTH_LIST = "monthList";
	public static final String ATT_CURRENT_YEAR = "currentYear";
	public static final String ATT_CURRENT_MONTH = "currentMonth";
	public static final String ATT_ERROR = "error";
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String VUE = "/WEB-INF/download_extract.jsp";
	private ExtractDao extractDao;
	private UnitReportDao unitReportDao;
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
		this.unitReportDao = ((DaoFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUnitReportDao();
		

		//Configuration d'une liste de mois
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

	protected HttpServletRequest actionsGetPost(HttpServletRequest request)
			throws ServletException, IOException {

		//Instanciation d'un calendrier à la date courante
		GregorianCalendar currentCalendar = new GregorianCalendar();
		int currentYear = currentCalendar.get(GregorianCalendar.YEAR);
		int currentMonth = currentCalendar.get(GregorianCalendar.MONTH) + 1;

		//Mise en attributs d'une liste de mois, du mois courant et de l'année courante qui seront utilisés dans la vue
		request.setAttribute(ATT_MONTH_LIST, this.monthList);
		request.setAttribute(ATT_CURRENT_MONTH, currentMonth);
		request.setAttribute(ATT_CURRENT_YEAR, currentYear);
		
		//Préparation de l'objet formulaire
		UnitReportForm form = new UnitReportForm(unitReportDao);
		
		//Chargement des noms de contrats et mise en attribut
		List<String> messageNameUnitReports = form.listContractNames();
		request.setAttribute(ATT_MESSAGES_NAME_UNITREPORTS, messageNameUnitReports);

		//Chargement des pays des contrats et mise en attribut
		List<String> messageCountry = form.listActiveCountries();
		request.setAttribute(ATT_MESSAGES_COUNTRY, messageCountry);

		//Retourne la requête à laquelle des attributs ont été ajoutés
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

		//Réalisation des actions à effecuter pour la méthode GET et pour la méthode POST
		request = actionsGetPost(request);

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

		//Réalisation des actions à effecuter pour la méthode GET et pour la méthode POST
		request = actionsGetPost(request);

		//Préparation de l'objet formulaire
		ExtractForm form = null;

		try {
			form = new ExtractForm(extractDao);
		} catch (WriteException ex) {
			Logger.getLogger(Download_Extract.class.getName()).log(Level.SEVERE, null, ex);
		}

		//Récupération des données du formulaire
		String typeExtract = request.getParameter("typeExtract");
		String nameExtract = null;
		String dateMode = null;
		int month = 0;
		int year = 0;
		int fiscalYear = 0;

		if ("country".equals(typeExtract)) {
			nameExtract = request.getParameter("nameExtractCountry");
			dateMode = request.getParameter("dateModeCountry");
			month = Integer.parseInt(request.getParameter("monthCountry"));
			year = Integer.parseInt(request.getParameter("yearCountry"));
			fiscalYear = Integer.parseInt(request.getParameter("fiscalYearCountry"));
		} else if ("contract".equals(typeExtract)) {
			nameExtract = request.getParameter("nameExtractContract");
			dateMode = request.getParameter("dateModeContract");
			month = Integer.parseInt(request.getParameter("monthContract"));
			year = Integer.parseInt(request.getParameter("yearContract"));
			fiscalYear = Integer.parseInt(request.getParameter("fiscalYearContract"));
		} else {
			dateMode = request.getParameter("dateModeGlobal");
			month = Integer.parseInt(request.getParameter("monthGlobal"));
			year = Integer.parseInt(request.getParameter("yearGlobal"));
			fiscalYear = Integer.parseInt(request.getParameter("fiscalYearGlobal"));
		}

		//Initialisation d'une Map qui contiendra les lignes d'extract (sous forme de Bean "ExtractTab"
		Map<Integer, ExtractTab> extractMap = null;

		if ("monthlyMode".equals(dateMode)) {
			//Si l'extract est de type mensuel, on construit  la Map à l'aide de la méthode "extractMonth" selon les paramètres du formulaire
			extractMap = form.extractMonth(typeExtract, nameExtract, month, year);
		} else {
			//Sinon, l'extract est de type année fiscale, on construit la Map à l'aide de la méthode "extractFiscalYear" selon les paramètres du formulaire
			extractMap = form.extractFiscalYear(typeExtract, nameExtract, fiscalYear);
		}

		//Création d'une variable de type Workbook (fichier Excel)
		WritableWorkbook workbook = null;

		try {
			if (!extractMap.isEmpty()) {
				//Si la Map d'extract n'est pas vide :
				//On configure la réponse de la servlet en lui donnant un type pour envoi de fichier Excel
				response.setContentType("application/vnd.ms-excel");
				
				//On instancie un Workbook que l'on place directement en OutputStream de la réponse de la servlet
				workbook = Workbook.createWorkbook(response.getOutputStream());
				
				//Préparation de variables pour le remplissage du Workbook
				int firstMonth = 0;
				int firstYear = 0;
				int lastMonth = 0;
				if ("monthlyMode".equals(dateMode) && month != 0) {
					firstMonth = month;
					lastMonth = month;
				} else if ("fiscalYearMode".equals(dateMode)) {
					firstMonth = 4;
					firstYear = fiscalYear;
					lastMonth = 3;
				} else {
					firstMonth = 1;
					lastMonth = 12;
				}
				//On construit le Workbook à l'aide de la méthode "constructExtract" et des paramètres ci-dessus
				workbook = form.constructExtract(extractMap, workbook, firstMonth, lastMonth, firstYear);
				//On écrit le Workbook qui est sauvegardé dans l'OutputStream
				workbook.write();
				//On ferme le Workbook et on libère l'espace mémoire alloué avant l'envoi
				workbook.close();
			} else {
				//Si la Map d'extract est vide, on passe en attribut un message d'erreur
				request.setAttribute(ATT_ERROR, "Error : Data not found. Please fill the reports on Reporting page and then, you will can extract data.");
			}

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
