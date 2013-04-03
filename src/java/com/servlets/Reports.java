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

		//Instanciation d'un calendrier à la date courante
		GregorianCalendar currentCalendar = new GregorianCalendar();
		int currentYear = currentCalendar.get(GregorianCalendar.YEAR);
		int currentMonth = currentCalendar.get(GregorianCalendar.MONTH) + 1;

		//Mise en attributs d'une liste de mois, du mois courant et de l'année courante qui seront utilisés dans la vue
		request.setAttribute(ATT_MONTH_LIST, this.monthList);
		request.setAttribute(ATT_CURRENT_MONTH, currentMonth);
		request.setAttribute(ATT_CURRENT_YEAR, currentYear);

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

		//Préparation de l'objet formulaire
		ReportsForm form = new ReportsForm(reportDao);

		//Récupération de la session
		HttpSession session = request.getSession();

		//Création d'une Map contenant la liste des contrats
		Map<Integer, String> siteList = form.constructSiteList();

		//Mise en attribut de la Map de contrats
		request.setAttribute(ATT_SITE_LIST, siteList);

		if (siteList.isEmpty()) {
			/*
			 * Si il n'y a aucun contrat, alors on met un booléen de redirection en attribut de session
			 * On redirige ensuite vers la page de création de contrat où on affichera un message d'indication grâce au booléen
			 */
			session.setAttribute(ATT_REDIRECT_BOOL, Boolean.TRUE);
			response.sendRedirect(request.getContextPath() + urlRedirection);
		} else {
			//Sinon, on envoie la vue normale (reports.jsp)
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

		//Réalisation des actions à effecuter pour la méthode GET et pour la méthode POST
		request = actionsGetPost(request);

		//Récupération de la session
		HttpSession session = request.getSession();

		//Récupération du paramètre "filter", étant le bouton d'envoi d'un filtre d'affichage
		String filterSubmit = request.getParameter("filter");

		//Préparation de l'objet formulaire
		ReportsForm form = new ReportsForm(reportDao);

		if (filterSubmit == null) {
			/*
			 * Si aucun filtre n'a été envoyé, et qu'une modification ou une sauvegarde à été demandée :
			 * Récupération de la Map contenant les Reports vérifiant le filtre courant
			 */
			Map<Integer, Report> reportMap = (Map<Integer, Report>) session.getAttribute(ATT_REPORT_MAP);

			//Récupération de l'id du Report qui a été modifié en trouvant sur quel bouton l'utilisateur a appuyé
			int idReport = 0;
			String submitReport = null;
			for (int i = 1; submitReport == null && i <= 12; i++) {
				submitReport = request.getParameter("report" + i);
				idReport = i;
			}

			//Récupération des informations principales du Report modifié
			int month = reportMap.get(idReport).getMonth();
			int year = reportMap.get(idReport).getYear();
			String refContract = reportMap.get(idReport).getSite();
			String type = (String) session.getAttribute(ATT_REPORT_TYPE);
			boolean editable = reportMap.get(idReport).getEditable();

			if (editable) {
				/*
				 * Si le Report est éditable directement, alors on effectue une sauvegarde du nouveau report
				 * Puis, on remplace l'ancien Bean de Report dans la Map de Reports par le nouveau
				 */
				reportMap.put(idReport, form.saveReport(refContract, type, month, year, request));
			} else {
				/*
				 * Sinon, alors on effectue une mise-à-jour du report
				 * Puis, on remplace l'ancien Bean de Report dans la Map de Reports par le nouveau
				 */
				reportMap.put(idReport, form.updateReport(refContract, type, month, year, request));
			}

			//Création d'une Map contenant la liste des contrats
			Map<Integer, String> siteList = form.constructSiteList();

			//Récupération du résultat de l'action réalisée
			String result = form.getResult();

			/*
			 * Stockage du Formulaire, de la Map de Reports, de l'id du Report modifié,
			 * de la liste des contrats et du résultat de l'action réalisée dans l'objet request
			 */
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_REPORT_MAP, reportMap);
			request.setAttribute(ATT_MODIFIED_REPORT, idReport);
			request.setAttribute(ATT_SITE_LIST, siteList);
			request.setAttribute(ATT_RESULT, result);
		} else {

			/*
			 * Sinon, un filtre a été demandé :
			 * Récupération des variables de date, du nom du contrat et de son type
			 */
			int month = Integer.parseInt(request.getParameter("month"));
			int year = Integer.parseInt(request.getParameter("year"));
			String refContract = request.getParameter("site");
			int currentMonth = Integer.parseInt(request.getAttribute(ATT_CURRENT_MONTH).toString());
			int currentYear = Integer.parseInt(request.getAttribute(ATT_CURRENT_YEAR).toString());
			String type = form.getType(refContract);

			//Construction d'une Map de Beans de type Report selon le filtre appliqué par l'utilisateur
			Map<Integer, Report> reportMap = form.constructList(refContract, month, year);

			if (month == 0) {
				//Si le filtre demandé est sur tous les mois d'une année fiscale
				if (currentYear > year + 1) {
					//Si l'année courante est supérieure à la dernière année comprise dans l'année fiscale demandée
					for (int i = 1; i <= 12; i++) {
						//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
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
					//Si l'année courante est égale à la dernière année comprise dans l'année fiscale demandée
					if (currentMonth <= 3) {
						//Si le mois courant est inférieur ou égale au dernier mois d'une année fiscale
						for (int i = 1; i <= 12; i++) {
							//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
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
								//Si le mois courant est égal à i, on passe directement i à 3 car aucun report n'existe pour les mois futurs
								if (currentMonth == 1 && i == 1 || currentMonth == 2 && i == 2) {
									i = 3;
								}
							}
						}
					} else {
						//Si le mois courant est supérieur au dernier mois d'une année fiscale
						for (int i = 1; i <= 12; i++) {
							//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
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
					//Si l'année courante est égale à la première année comprise dans l'année fiscale demandée (et pas inférieure car aucun report ne se fait pour le futur)
					for (int i = 4; i <= currentMonth; i++) {
						/*
						 * On initialise i à 4 et on termine la boucle au mois courant car la première année de l'année fiscale est égale à l'anée courante
						 * Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
						 */
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
				//Si le filtre demandé est sur un seul mois d'une année fiscale
				if (currentYear > year + 1) {
					//Si l'année courante est supérieure à la dernière année comprise dans l'année fiscale demandée
					if (reportMap.get(month) == null) {
						//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
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
					//Si l'année courante est égale à la dernière année comprise dans l'année fiscale demandée
					if (month < 4 && month <= currentMonth) {
						//Si le mois est dans la dernière année de l'année fiscale et qu'il est inférieur ou égal au mois courant
						if (reportMap.get(month) == null) {
							//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
							Report report = new Report();
							report.setMonth(month);
							report.setYear(year + 1);
							report.setSite(refContract);
							report.setType(type);
							report.setEditable(Boolean.TRUE);
							reportMap.put(month, report);
						}
					} else if (month >= 4) {
						//Si le mois est dans la première année de l'année fiscale demandée
						if (reportMap.get(month) == null) {
							//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
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
					//Si l'année courante est égale à la première année comprise dans l'année fiscale demandée
					if (month < 4 && month <= currentMonth && currentMonth < 4) {
						//Si le mois demandé et le mois courant sont dans la première année de l'année fiscale demandée et que le mois demandé est inférieur au mois courant
						if (reportMap.get(month) == null) {
							//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
							Report report = new Report();
							report.setMonth(month);
							report.setYear(year + 1);
							report.setSite(refContract);
							report.setType(type);
							report.setEditable(Boolean.TRUE);
							reportMap.put(month, report);
						}
					} else if (month >= 4 && (month <= currentMonth || currentMonth < 4)) {
						//Si le mois est dans la dernière année de l'année fiscale et que le mois courant aussi, ou que le mois demandé est inférieur au mois courant
						if (reportMap.get(month) == null) {
							//Si la Map de Report ne comporte pas de Bean de Report pour le mois i, On insère un nouveau Bean de Report vide et éditable
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

			//Récupération de la liste des contrats
			Map<Integer, String> siteList = form.constructSiteList();

			//Instanciation d'une Map qui contiendra les paramètres du filtre
			Map<String, String> filter = new HashMap<String, String>();

			//Intégration des paramètres du filtre dans sa Map destinée
			filter.put("refContract", refContract);
			filter.put("year", String.valueOf(year));
			filter.put("month", String.valueOf(month));

			//Stockage du formulaire, de la liste de contrats dans l'objet request
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_SITE_LIST, siteList);

			//Mise en session du dernier filtre demandé, du type de contrat qui avait été demandé et de la Map qui résulte du filtre utilisé
			session.setAttribute(ATT_FILTER, filter);
			session.setAttribute(ATT_REPORT_TYPE, type);
			session.setAttribute(ATT_REPORT_MAP, reportMap);
		}

		//Envoi vers la vue normale
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}
