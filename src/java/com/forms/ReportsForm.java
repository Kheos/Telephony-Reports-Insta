/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import com.bdd.DaoException;
import com.bdd.ReportDao;
import com.beans.Report;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Nico
 */
public class ReportsForm {

    private ReportDao reportDao;
    /*
     * Fix Telephony
     */
    private static final String FIELD_LINES_FIX = "linesFix";
    private static final String FIELD_FIX_LOCAL_CALLS_FIX = "fixLocalCallsFix";
    private static final String FIELD_FIX_INTERNATIONAL_CALLS_FIX = "fixInternationalCallsFix";
    private static final String FIELD_FIX_TOTAL_FIX = "fixTotalFix";
    private static final String FIELD_VAR_LOCAL_CALLS_FIX = "varLocalCallsFix";
    private static final String FIELD_VAR_INTERNATIONAL_CALLS_FIX = "varInternationalCallsFix";
    private static final String FIELD_VAR_TOTAL_FIX = "varTotalFix";
    /*
     * 3G Cards
     */
    private static final String FIELD_LINES_3G = "lines3G";
    private static final String FIELD_FIX_LOCAL_DATA_3G = "fixLocalData3G";
    private static final String FIELD_FIX_INTERNATIONAL_DATA_3G = "fixInternationalData3G";
    private static final String FIELD_FIX_TOTAL_3G = "fixTotal3G";
    private static final String FIELD_VAR_LOCAL_DATA_3G = "varLocalData3G";
    private static final String FIELD_VAR_INTERNATIONAL_DATA_3G = "varInternationalData3G";
    private static final String FIELD_VAR_TOTAL_3G = "varTotal3G";
    /*
     * Mobile Telephony
     */
    private static final String FIELD_LINES_MOBILE = "linesMobile";
    private static final String FIELD_FIX_LOCAL_CALLS_MOBILE = "fixLocalCallsMobile";
    private static final String FIELD_FIX_INTERNATIONAL_CALLS_MOBILE = "fixInternationalCallsMobile";
    private static final String FIELD_FIX_LOCAL_DATA_MOBILE = "fixLocalDataMobile";
    private static final String FIELD_FIX_INTERNATIONAL_DATA_MOBILE = "fixInternationalDataMobile";
    private static final String FIELD_FIX_TOTAL_MOBILE = "fixTotalMobile";
    private static final String FIELD_VAR_LOCAL_CALLS_MOBILE = "varLocalCallsMobile";
    private static final String FIELD_VAR_INTERNATIONAL_CALLS_MOBILE = "varInternationalCallsMobile";
    private static final String FIELD_VAR_LOCAL_DATA_MOBILE = "varLocalDataMobile";
    private static final String FIELD_VAR_INTERNATIONAL_DATA_MOBILE = "varInternationalDataMobile";
    private static final String FIELD_VAR_TOTAL_MOBILE = "varTotalMobile";
    /*
     * BlackBerry Smartphones
     */
    private static final String FIELD_LINES_BB = "linesBB";
    private static final String FIELD_FIX_LOCAL_CALLS_BB = "fixLocalCallsBB";
    private static final String FIELD_FIX_INTERNATIONAL_CALLS_BB = "fixInternationalCallsBB";
    private static final String FIELD_FIX_LOCAL_DATA_BB = "fixLocalDataBB";
    private static final String FIELD_FIX_INTERNATIONAL_DATA_BB = "fixInternationalDataBB";
    private static final String FIELD_FIX_TOTAL_BB = "fixTotalBB";
    private static final String FIELD_VAR_LOCAL_CALLS_BB = "varLocalCallsBB";
    private static final String FIELD_VAR_INTERNATIONAL_CALLS_BB = "varInternationalCallsBB";
    private static final String FIELD_VAR_LOCAL_DATA_BB = "varLocalDataBB";
    private static final String FIELD_VAR_INTERNATIONAL_DATA_BB = "varInternationalDataBB";
    private static final String FIELD_VAR_TOTAL_BB = "varTotalBB";
    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    /**
	 *
	 * @param reportDao
	 */
	public ReportsForm(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    /**
	 *
	 * @return String à afficher en résultat dans la vue ("Report Saved" ou "Error(s) :")
	 */
	public String getResult() {
        return result;
    }

    /**
	 *
	 * @return Map d'erreurs construite après validation
	 */
	public Map<String, String> getErrors() {
        return errors;
    }

    /**
	 *
	 * @param refContract Nom du contrat du Report
	 * @return Type de contrat du Report
	 */
	public String getType(String refContract) {

        String type = reportDao.getType(refContract);

        return type;
    }

    /**
	 *
	 * @return Map contenant la liste des contrats
	 */
	public Map<Integer, String> constructSiteList() {
		
		//Instanciation d'une Map qui contiendra la liste des contrats
        Map<Integer, String> siteList = new HashMap<Integer, String>();
		
		//Appel de la méthode de récupération des contrats qui retourne la Map de contrats complète
        siteList = reportDao.siteList(siteList);

        return siteList;
    }

    /**
	 *
	 * @param refContract Nom du contrat du Report
	 * @param month Mois du Report
	 * @param year Année du Report
	 * @return Map contenant les Beans des Reports
	 */
	public Map<Integer, Report> constructList(String refContract, int month, int year) {
		
		/*
		 * Instanciation d'une Map qui contiendra les Beans des Reports
		 * Instanciation d'un booléen permettant de savoir si tous les mois ont été demandés
		 * Instanciation d'un calendrier auquel on attribue l'année demandée dans le filtre
		 */
        Map<Integer, Report> reportMap = new HashMap<Integer, Report>();
        Boolean allMonth = false;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.YEAR, year);
        if (month != 0) {
			//Si la variable mois du filtre est différente de "All" (soit, tous les mois), on attribue au calendrier le mois demandé
            calendar.set(GregorianCalendar.MONTH, month - 1);
        } else {
			//Si tous les mois ont été demandées, on passe le booléen à true
            allMonth = true;
        }
		
		//Appel de la méthode de récupération de données selon les paramètres du filtre
        reportMap = reportDao.list(reportMap, refContract, calendar, allMonth);

        return reportMap;
    }

    /**
	 * @param refContract Nom du contrat du Report
	 * @param type Type du contrat
	 * @param request Requête envoyée
	 * @param month Mois du Report
	 * @param year Année du Report
	 * @return Bean du report sauvegardé
     */
    public Report saveReport(String refContract, String type, int month, int year, HttpServletRequest request) {

        /*
         * Fix Telephony
         */
        String linesFix = "";
        String fixLocalCallsFix = "";
        String fixInternationalCallsFix = "";
        String fixTotalFix = "";
        String varLocalCallsFix = "";
        String varInternationalCallsFix = "";
        String varTotalFix = "";

        /*
         * 3G Cards
         */
        String lines3G = "";
        String fixLocalData3G = "";
        String fixInternationalData3G = "";
        String fixTotal3G = "";
        String varLocalData3G = "";
        String varInternationalData3G = "";
        String varTotal3G = "";

        /*
         * Mobile Telephony
         */
        String linesMobile = "";
        String fixLocalCallsMobile = "";
        String fixInternationalCallsMobile = "";
        String fixLocalDataMobile = "";
        String fixInternationalDataMobile = "";
        String fixTotalMobile = "";
        String varLocalCallsMobile = "";
        String varInternationalCallsMobile = "";
        String varLocalDataMobile = "";
        String varInternationalDataMobile = "";
        String varTotalMobile = "";

        /*
         * BlackBerry Smartphones
         */
        String linesBB = "";
        String fixLocalCallsBB = "";
        String fixInternationalCallsBB = "";
        String fixLocalDataBB = "";
        String fixInternationalDataBB = "";
        String fixTotalBB = "";
        String varLocalCallsBB = "";
        String varInternationalCallsBB = "";
        String varLocalDataBB = "";
        String varInternationalDataBB = "";
        String varTotalBB = "";
		
		//Récupération des variables de tout le formulaire envoyée dans des variables locales
        if ("Fixed".equals(type) || "Both".equals(type)) {
			//Si le contrat est de type fixe ou mixte (fixe et mobile)
            linesFix = getFieldValue(request, FIELD_LINES_FIX);
            fixLocalCallsFix = getFieldValue(request, FIELD_FIX_LOCAL_CALLS_FIX);
            fixInternationalCallsFix = getFieldValue(request, FIELD_FIX_INTERNATIONAL_CALLS_FIX);
            fixTotalFix = getFieldValue(request, FIELD_FIX_TOTAL_FIX);
            varLocalCallsFix = getFieldValue(request, FIELD_VAR_LOCAL_CALLS_FIX);
            varInternationalCallsFix = getFieldValue(request, FIELD_VAR_INTERNATIONAL_CALLS_FIX);
            varTotalFix = getFieldValue(request, FIELD_VAR_TOTAL_FIX);
        }

        if ("Mobile".equals(type) || "Both".equals(type)) {
			//Si le contrat est de type mobile ou mixte (fixe et mobile)
            lines3G = getFieldValue(request, FIELD_LINES_3G);
            fixLocalData3G = getFieldValue(request, FIELD_FIX_LOCAL_DATA_3G);
            fixInternationalData3G = getFieldValue(request, FIELD_FIX_INTERNATIONAL_DATA_3G);
            fixTotal3G = getFieldValue(request, FIELD_FIX_TOTAL_3G);
            varLocalData3G = getFieldValue(request, FIELD_VAR_LOCAL_DATA_3G);
            varInternationalData3G = getFieldValue(request, FIELD_VAR_INTERNATIONAL_DATA_3G);
            varTotal3G = getFieldValue(request, FIELD_VAR_TOTAL_3G);

            linesMobile = getFieldValue(request, FIELD_LINES_MOBILE);
            fixLocalCallsMobile = getFieldValue(request, FIELD_FIX_LOCAL_CALLS_MOBILE);
            fixInternationalCallsMobile = getFieldValue(request, FIELD_FIX_INTERNATIONAL_CALLS_MOBILE);
            fixLocalDataMobile = getFieldValue(request, FIELD_FIX_LOCAL_DATA_MOBILE);
            fixInternationalDataMobile = getFieldValue(request, FIELD_FIX_INTERNATIONAL_DATA_MOBILE);
            fixTotalMobile = getFieldValue(request, FIELD_FIX_TOTAL_MOBILE);
            varLocalCallsMobile = getFieldValue(request, FIELD_VAR_LOCAL_CALLS_MOBILE);
            varInternationalCallsMobile = getFieldValue(request, FIELD_VAR_INTERNATIONAL_CALLS_MOBILE);
            varLocalDataMobile = getFieldValue(request, FIELD_VAR_LOCAL_DATA_MOBILE);
            varInternationalDataMobile = getFieldValue(request, FIELD_VAR_INTERNATIONAL_DATA_MOBILE);
            varTotalMobile = getFieldValue(request, FIELD_VAR_TOTAL_MOBILE);

            linesBB = getFieldValue(request, FIELD_LINES_BB);
            fixLocalCallsBB = getFieldValue(request, FIELD_FIX_LOCAL_CALLS_BB);
            fixInternationalCallsBB = getFieldValue(request, FIELD_FIX_INTERNATIONAL_CALLS_BB);
            fixLocalDataBB = getFieldValue(request, FIELD_FIX_LOCAL_DATA_BB);
            fixInternationalDataBB = getFieldValue(request, FIELD_FIX_INTERNATIONAL_DATA_BB);
            fixTotalBB = getFieldValue(request, FIELD_FIX_TOTAL_BB);
            varLocalCallsBB = getFieldValue(request, FIELD_VAR_LOCAL_CALLS_BB);
            varInternationalCallsBB = getFieldValue(request, FIELD_VAR_INTERNATIONAL_CALLS_BB);
            varLocalDataBB = getFieldValue(request, FIELD_VAR_LOCAL_DATA_BB);
            varInternationalDataBB = getFieldValue(request, FIELD_VAR_INTERNATIONAL_DATA_BB);
            varTotalBB = getFieldValue(request, FIELD_VAR_TOTAL_BB);
        }
		
		//Instanciation d'un Bean de Report
        Report report = new Report();

        try {
			//On vérifie que tout est bien rempli grâce à des méthodes de validation puis on insère la valeur dans le bean s'il n'y a pas d'erreur
            if ("Fixed".equals(type) || "Both".equals(type)) {
				//Si le contrat est de type fixe ou mixte (fixe et mobile)
                handleLinesFix(linesFix, report);
                handleFixFix(fixLocalCallsFix, fixInternationalCallsFix, fixTotalFix, report);
                handleVarFix(varLocalCallsFix, varInternationalCallsFix, varTotalFix, report);
            }

            if ("Mobile".equals(type) || "Both".equals(type)) {
				//Si le contrat est de type mobile ou mixte (fixe et mobile)
                handleLines3G(lines3G, report);
                handleFix3G(fixLocalData3G, fixInternationalData3G, fixTotal3G, report);
                handleVar3G(varLocalData3G, varInternationalData3G, varTotal3G, report);

                handleLinesMobile(linesMobile, report);
                handleFixMobile(fixLocalCallsMobile, fixLocalDataMobile, fixInternationalCallsMobile, fixInternationalDataMobile, fixTotalMobile, report);
                handleVarMobile(varLocalCallsMobile, varLocalDataMobile, varInternationalCallsMobile, varInternationalDataMobile, varTotalMobile, report);

                handleLinesBB(linesBB, report);
                handleFixBB(fixLocalCallsBB, fixLocalDataBB, fixInternationalCallsBB, fixInternationalDataBB, fixTotalBB, report);
                handleVarBB(varLocalCallsBB, varLocalDataBB, varInternationalCallsBB, varInternationalDataBB, varTotalBB, report);
            }

            if (errors.isEmpty()) {
				//Si la Map d'erreurs est vide, on insère les informations générales du Report dans le Bean
                report = reportDao.handleInformations(refContract, month, year, false, report);
                if ("Fixed".equals(type) || "Both".equals(type)) {
					//Si le contrat est de type fixe ou mixte (fixe et mobile), on appelle la méthode de sauvegarde en base de données pour la partie fixe
                    report = reportDao.save(month, year, "Fix", refContract, report);
                }
                if ("Mobile".equals(type) || "Both".equals(type)) {
					//Si le contrat est de type mobile ou mixte (fixe et mobile), on appelle la méthode de sauvegarde en base de données pour la partie mobile
                    report = reportDao.save(month, year, "3G", refContract, report);
                    report = reportDao.save(month, year, "Mobile", refContract, report);
                    report = reportDao.save(month, year, "Blackberry", refContract, report);
                }
                result = "Report saved !";
            } else {
				//Si la Map d'erreurs contient au moins une erreur
                report = reportDao.handleInformations(refContract, month, year, true, report);
                if (errors.size() > 1) {
                    result = "Errors :";
                } else {
                    result = "Error :";
                }
            }
        } catch (DaoException e) {
			//Si une erreur innatendue se produit dans la partie DAO
            result = "Save fail : an enexpected error was returned, thanks to try again in few seconds.";
            e.printStackTrace();
        }
		
		//Retourne le nouveau Bean de Report complété
        return report;
    }

    /**
	 *
	 * @param refContract Nom du contrat du Report
	 * @param type Type du contrat
	 * @param month Mois du contrat
	 * @param year Année du contrat
	 * @param request Requête envoyée
	 * @return Bean du report mis à jour
	 */
	public Report updateReport(String refContract, String type, int month, int year, HttpServletRequest request) {

        /*
         * Fix Telephony
         */
        String linesFix = "";
        String fixLocalCallsFix = "";
        String fixInternationalCallsFix = "";
        String fixTotalFix = "";
        String varLocalCallsFix = "";
        String varInternationalCallsFix = "";
        String varTotalFix = "";

        /*
         * 3G Cards
         */
        String lines3G = "";
        String fixLocalData3G = "";
        String fixInternationalData3G = "";
        String fixTotal3G = "";
        String varLocalData3G = "";
        String varInternationalData3G = "";
        String varTotal3G = "";

        /*
         * Mobile Telephony
         */
        String linesMobile = "";
        String fixLocalCallsMobile = "";
        String fixInternationalCallsMobile = "";
        String fixLocalDataMobile = "";
        String fixInternationalDataMobile = "";
        String fixTotalMobile = "";
        String varLocalCallsMobile = "";
        String varInternationalCallsMobile = "";
        String varLocalDataMobile = "";
        String varInternationalDataMobile = "";
        String varTotalMobile = "";

        /*
         * BlackBerry Smartphones
         */
        String linesBB = "";
        String fixLocalCallsBB = "";
        String fixInternationalCallsBB = "";
        String fixLocalDataBB = "";
        String fixInternationalDataBB = "";
        String fixTotalBB = "";
        String varLocalCallsBB = "";
        String varInternationalCallsBB = "";
        String varLocalDataBB = "";
        String varInternationalDataBB = "";
        String varTotalBB = "";

        //Récupération des variables de tout le formulaire envoyée dans des variables locales
        if ("Fixed".equals(type) || "Both".equals(type)) {
			//Si le contrat est de type fixe ou mixte (fixe et mobile)
            linesFix = getFieldValue(request, FIELD_LINES_FIX);
            fixLocalCallsFix = getFieldValue(request, FIELD_FIX_LOCAL_CALLS_FIX);
            fixInternationalCallsFix = getFieldValue(request, FIELD_FIX_INTERNATIONAL_CALLS_FIX);
            fixTotalFix = getFieldValue(request, FIELD_FIX_TOTAL_FIX);
            varLocalCallsFix = getFieldValue(request, FIELD_VAR_LOCAL_CALLS_FIX);
            varInternationalCallsFix = getFieldValue(request, FIELD_VAR_INTERNATIONAL_CALLS_FIX);
            varTotalFix = getFieldValue(request, FIELD_VAR_TOTAL_FIX);
        }

        if ("Mobile".equals(type) || "Both".equals(type)) {
			//Si le contrat est de type mobile ou mixte (fixe et mobile)
            lines3G = getFieldValue(request, FIELD_LINES_3G);
            fixLocalData3G = getFieldValue(request, FIELD_FIX_LOCAL_DATA_3G);
            fixInternationalData3G = getFieldValue(request, FIELD_FIX_INTERNATIONAL_DATA_3G);
            fixTotal3G = getFieldValue(request, FIELD_FIX_TOTAL_3G);
            varLocalData3G = getFieldValue(request, FIELD_VAR_LOCAL_DATA_3G);
            varInternationalData3G = getFieldValue(request, FIELD_VAR_INTERNATIONAL_DATA_3G);
            varTotal3G = getFieldValue(request, FIELD_VAR_TOTAL_3G);

            linesMobile = getFieldValue(request, FIELD_LINES_MOBILE);
            fixLocalCallsMobile = getFieldValue(request, FIELD_FIX_LOCAL_CALLS_MOBILE);
            fixInternationalCallsMobile = getFieldValue(request, FIELD_FIX_INTERNATIONAL_CALLS_MOBILE);
            fixLocalDataMobile = getFieldValue(request, FIELD_FIX_LOCAL_DATA_MOBILE);
            fixInternationalDataMobile = getFieldValue(request, FIELD_FIX_INTERNATIONAL_DATA_MOBILE);
            fixTotalMobile = getFieldValue(request, FIELD_FIX_TOTAL_MOBILE);
            varLocalCallsMobile = getFieldValue(request, FIELD_VAR_LOCAL_CALLS_MOBILE);
            varInternationalCallsMobile = getFieldValue(request, FIELD_VAR_INTERNATIONAL_CALLS_MOBILE);
            varLocalDataMobile = getFieldValue(request, FIELD_VAR_LOCAL_DATA_MOBILE);
            varInternationalDataMobile = getFieldValue(request, FIELD_VAR_INTERNATIONAL_DATA_MOBILE);
            varTotalMobile = getFieldValue(request, FIELD_VAR_TOTAL_MOBILE);

            linesBB = getFieldValue(request, FIELD_LINES_BB);
            fixLocalCallsBB = getFieldValue(request, FIELD_FIX_LOCAL_CALLS_BB);
            fixInternationalCallsBB = getFieldValue(request, FIELD_FIX_INTERNATIONAL_CALLS_BB);
            fixLocalDataBB = getFieldValue(request, FIELD_FIX_LOCAL_DATA_BB);
            fixInternationalDataBB = getFieldValue(request, FIELD_FIX_INTERNATIONAL_DATA_BB);
            fixTotalBB = getFieldValue(request, FIELD_FIX_TOTAL_BB);
            varLocalCallsBB = getFieldValue(request, FIELD_VAR_LOCAL_CALLS_BB);
            varInternationalCallsBB = getFieldValue(request, FIELD_VAR_INTERNATIONAL_CALLS_BB);
            varLocalDataBB = getFieldValue(request, FIELD_VAR_LOCAL_DATA_BB);
            varInternationalDataBB = getFieldValue(request, FIELD_VAR_INTERNATIONAL_DATA_BB);
            varTotalBB = getFieldValue(request, FIELD_VAR_TOTAL_BB);
        }
		
		//Instanciation d'un Bean de Report
        Report report = new Report();

        try {
            //On vérifie que tout est bien rempli grâce à des méthodes de validation puis on insère la valeur dans le bean s'il n'y a pas d'erreur
            if ("Fixed".equals(type) || "Both".equals(type)) {
				//Si le contrat est de type fixe ou mixte (fixe et mobile)
                handleLinesFix(linesFix, report);
                handleFixFix(fixLocalCallsFix, fixInternationalCallsFix, fixTotalFix, report);
                handleVarFix(varLocalCallsFix, varInternationalCallsFix, varTotalFix, report);
            }

            if ("Mobile".equals(type) || "Both".equals(type)) {
				//Si le contrat est de type mobile ou mixte (fixe et mobile)
                handleLines3G(lines3G, report);
                handleFix3G(fixLocalData3G, fixInternationalData3G, fixTotal3G, report);
                handleVar3G(varLocalData3G, varInternationalData3G, varTotal3G, report);

                handleLinesMobile(linesMobile, report);
                handleFixMobile(fixLocalCallsMobile, fixLocalDataMobile, fixInternationalCallsMobile, fixInternationalDataMobile, fixTotalMobile, report);
                handleVarMobile(varLocalCallsMobile, varLocalDataMobile, varInternationalCallsMobile, varInternationalDataMobile, varTotalMobile, report);

                handleLinesBB(linesBB, report);
                handleFixBB(fixLocalCallsBB, fixLocalDataBB, fixInternationalCallsBB, fixInternationalDataBB, fixTotalBB, report);
                handleVarBB(varLocalCallsBB, varLocalDataBB, varInternationalCallsBB, varInternationalDataBB, varTotalBB, report);
            }

            if (errors.isEmpty()) {
                //Si la Map d'erreurs est vide, on insère les informations générales du Report dans le Bean
                report = reportDao.handleInformations(refContract, month, year, false, report);
                if ("Fixed".equals(type) || "Both".equals(type)) {
					//Si le contrat est de type fixe ou mixte (fixe et mobile), on appelle la méthode de sauvegarde en base de données pour la partie fixe
                    report = reportDao.update(month, year, "Fix", refContract, report);
                }
                if ("Mobile".equals(type) || "Both".equals(type)) {
					//Si le contrat est de type mobile ou mixte (fixe et mobile), on appelle la méthode de sauvegarde en base de données pour la partie mobile
                    report = reportDao.update(month, year, "3G", refContract, report);
                    report = reportDao.update(month, year, "Mobile", refContract, report);
                    report = reportDao.update(month, year, "Blackberry", refContract, report);
                }
                result = "Report saved !";
            } else {
                report = reportDao.handleInformations(refContract, month, year, true, report);
                if (errors.size() > 1) {
                    result = "Please correct your errors or your modification(s) won't be saved :";
                } else {
                    result = "Please correct your error or your modification(s) won't be saved :";
                }
            }
        } catch (DaoException e) {
			//Si une erreur innatendue se produit dans la partie DAO
            result = "Save fail : an enexpected error was returned, thanks to try again in few seconds.";
            e.printStackTrace();
        }
		
		//Retourne le nouveau Bean de Report complété
        return report;
    }

    /**
     * Method which put an error message in the Map "errors"
     *
     * @param field the field (or all the fields) where there is an error
     * @param message the error message
     */
    private void setError(String field, String message) {
        errors.put(field, message);
    }

    /**
     * @return null if the field is empty, or his content else.
     */
    private static String getFieldValue(HttpServletRequest request, String nameField) {
        String value = request.getParameter(nameField);
        String fieldValue;

        if (value == null || value.trim().length() == 0) {
            fieldValue = null;
        } else {
            fieldValue = value.trim();
        }

        return fieldValue;

    }

    private void handleLinesFix(String linesFix, Report report) {
        try {
            validationLinesFix(linesFix);
        } catch (Exception e) {
            setError(FIELD_LINES_FIX, e.getMessage());
        }
        report.setLinesFix(linesFix);
    }

    private void handleFixFix(String fixLocalCallsFix, String fixInternationalCallsFix, String fixTotalFix, Report report) {
        try {
            fixValidationFix(fixLocalCallsFix, fixInternationalCallsFix, fixTotalFix);
        } catch (Exception e) {
            setError("fixFix", e.getMessage());
        }

        report.setFixLocalCallsFix(fixLocalCallsFix);
        report.setFixInternationalCallsFix(fixInternationalCallsFix);
        report.setFixTotalFix(fixTotalFix);
    }

    private void handleVarFix(String varLocalCallsFix, String varInternationalCallsFix, String varTotalFix, Report report) {
        try {
            varValidationFix(varLocalCallsFix, varInternationalCallsFix, varTotalFix);
        } catch (Exception e) {
            setError("varFix", e.getMessage());
        }

        report.setVarLocalCallsFix(varLocalCallsFix);
        report.setVarInternationalCallsFix(varInternationalCallsFix);
        report.setVarTotalFix(varTotalFix);
    }

    private void handleLines3G(String lines3G, Report report) {
        try {
            validationLines3G(lines3G);
        } catch (Exception e) {
            setError("lines3G", e.getMessage());
        }

        report.setLines3G(lines3G);
    }

    private void handleFix3G(String fixLocalData3G, String fixInternationalData3G, String fixTotal3G, Report report) {
        try {
            fixValidation3G(fixLocalData3G, fixInternationalData3G, fixTotal3G);
        } catch (Exception e) {
            setError("fix3G", e.getMessage());
        }

        report.setFixLocalData3G(fixLocalData3G);
        report.setFixInternationalData3G(fixInternationalData3G);
        report.setFixTotal3G(fixTotal3G);
    }

    private void handleVar3G(String varLocalData3G, String varInternationalData3G, String varTotal3G, Report report) {
        try {
            varValidation3G(varLocalData3G, varInternationalData3G, varTotal3G);
        } catch (Exception e) {
            setError("var3G", e.getMessage());
        }

        report.setVarLocalData3G(varLocalData3G);
        report.setVarInternationalData3G(varInternationalData3G);
        report.setVarTotal3G(varTotal3G);
    }

    private void handleLinesMobile(String linesMobile, Report report) {
        try {
            validationLinesMobile(linesMobile);
        } catch (Exception e) {
            setError("linesMobile", e.getMessage());
        }

        report.setLinesMobile(linesMobile);
    }

    private void handleFixMobile(String fixLocalCallsMobile, String fixLocalDataMobile, String fixInternationalCallsMobile, String fixInternationalDataMobile, String fixTotalMobile, Report report) {
        try {
            fixValidationMobile(fixLocalCallsMobile, fixLocalDataMobile, fixInternationalCallsMobile, fixInternationalDataMobile, fixTotalMobile);
        } catch (Exception e) {
            setError("fixMobile", e.getMessage());
        }

        report.setFixLocalCallsMobile(fixLocalCallsMobile);
        report.setFixLocalDataMobile(fixLocalDataMobile);
        report.setFixInternationalCallsMobile(fixInternationalCallsMobile);
        report.setFixInternationalDataMobile(fixInternationalDataMobile);
        report.setFixTotalMobile(fixTotalMobile);
    }

    private void handleVarMobile(String varLocalCallsMobile, String varLocalDataMobile, String varInternationalCallsMobile, String varInternationalDataMobile, String varTotalMobile, Report report) {
        try {
            varValidationMobile(varLocalCallsMobile, varLocalDataMobile, varInternationalCallsMobile, varInternationalDataMobile, varTotalMobile);
        } catch (Exception e) {
            setError("varMobile", e.getMessage());
        }

        report.setVarLocalCallsMobile(varLocalCallsMobile);
        report.setVarLocalDataMobile(varLocalDataMobile);
        report.setVarInternationalCallsMobile(varInternationalCallsMobile);
        report.setVarInternationalDataMobile(varInternationalDataMobile);
        report.setVarTotalMobile(varTotalMobile);
    }

    private void handleLinesBB(String linesBB, Report report) {
        try {
            validationLinesBB(linesBB);
        } catch (Exception e) {
            setError("linesBB", e.getMessage());
        }

        report.setLinesBB(linesBB);
    }

    private void handleFixBB(String fixLocalCallsBB, String fixLocalDataBB, String fixInternationalCallsBB, String fixInternationalDataBB, String fixTotalBB, Report report) {
        try {
            fixValidationBB(fixLocalCallsBB, fixLocalDataBB, fixInternationalCallsBB, fixInternationalDataBB, fixTotalBB);
        } catch (Exception e) {
            setError("fixBB", e.getMessage());
        }

        report.setFixLocalCallsBB(fixLocalCallsBB);
        report.setFixLocalDataBB(fixLocalDataBB);
        report.setFixInternationalCallsBB(fixInternationalCallsBB);
        report.setFixInternationalDataBB(fixInternationalDataBB);
        report.setFixTotalBB(fixTotalBB);
    }

    private void handleVarBB(String varLocalCallsBB, String varLocalDataBB, String varInternationalCallsBB, String varInternationalDataBB, String varTotalBB, Report report) {
        try {
            varValidationBB(varLocalCallsBB, varLocalDataBB, varInternationalCallsBB, varInternationalDataBB, varTotalBB);
        } catch (Exception e) {
            setError("varBB", e.getMessage());
        }

        report.setVarLocalCallsBB(varLocalCallsBB);
        report.setVarLocalDataBB(varLocalDataBB);
        report.setVarInternationalCallsBB(varInternationalCallsBB);
        report.setVarInternationalDataBB(varInternationalDataBB);
        report.setVarTotalBB(varTotalBB);
    }

    /**
     * Method which checks if the field of the number of fix lines isn't empty,
     * else an Exception is added.
     */
    private void validationLinesFix(String linesFix) throws Exception {
        if (linesFix == null) {
            throw new Exception("Number of lines : You have to fill number of lines field.");
        }
    }

    /**
     * Method which checks if the fields of the fix costs of Fix Telephony are
     * well filled, else an Exception is added.
     */
    private void fixValidationFix(String fixLocalCallsFix, String fixInternationalCallsFix, String fixTotalFix) throws Exception {
        if ((fixLocalCallsFix == null || fixInternationalCallsFix == null) && fixTotalFix == null) {
            throw new Exception("Fix Costs : You have to fill all the local and international fix costs fields, or just the total fix costs field.");
        }
    }

    /**
     * Method which checks if the fields of the variable costs of Fix Telephony
     * are well filled, else an Exception is added.
     */
    private void varValidationFix(String varLocalCallsFix, String varInternationalCallsFix, String varTotalFix) throws Exception {
        if ((varLocalCallsFix == null || varInternationalCallsFix == null) && varTotalFix == null) {
            throw new Exception("Variable Costs : You have to fill all the local and international variable costs fields, or just the total variable costs field.");
        }
    }

    /**
     * Method which checks if the field of the number of 3G lines isn't empty,
     * else an Exception is added.
     */
    private void validationLines3G(String lines3G) throws Exception {
        if (lines3G == null) {
            throw new Exception("Number of lines : You have to fill number of lines field.");
        }
    }

    /**
     * Method which checks if the fields of the fix costs of 3G Cards are well
     * filled, else an Exception is added.
     */
    private void fixValidation3G(String fixLocalCalls3G, String fixInternationalCalls3G, String fixTotal3G) throws Exception {
        if ((fixLocalCalls3G == null || fixInternationalCalls3G == null) && fixTotal3G == null) {
            throw new Exception("Fix Costs : You have to fill all the local and international fix costs fields, or just the total fix costs field.");
        }
    }

    /**
     * Method which checks if the fields of the variable costs of 3G Cards are
     * well filled, else an Exception is added.
     */
    private void varValidation3G(String varLocalCalls3G, String varInternationalCalls3G, String varTotal3G) throws Exception {
        if ((varLocalCalls3G == null || varInternationalCalls3G == null) && varTotal3G == null) {
            throw new Exception("Variable Costs : You have to fill all the local and international variable costs fields, or just the total variable costs field.");
        }
    }

    /**
     * Method which checks if the field of the number of mobile lines isn't
     * empty, else an Exception is added.
     */
    private void validationLinesMobile(String linesMobile) throws Exception {
        if (linesMobile == null) {
            throw new Exception("Number of lines : You have to fill number of lines field.");
        }
    }

    /**
     * Method which checks if the fields of the fix costs of Mobile Telephony
     * are well filled, else an Exception is added.
     */
    private void fixValidationMobile(String fixLocalCallsMobile, String fixLocalDataMobile, String fixInternationalCallsMobile, String fixInternationalDataMobile, String fixTotalMobile) throws Exception {
        if ((fixLocalCallsMobile == null || fixLocalDataMobile == null || fixInternationalCallsMobile == null || fixInternationalDataMobile == null) && fixTotalMobile == null) {
            throw new Exception("Fix Costs : You have to fill all the local and international fix costs fields, or just the total fix costs field.");
        }
    }

    /**
     * Method which checks if the fields of the variable costs of Mobile
     * Telephony are well filled, else an Exception is added.
     */
    private void varValidationMobile(String varLocalCallsMobile, String varLocalDataMobile, String varInternationalCallsMobile, String varInternationalDataMobile, String varTotalMobile) throws Exception {
        if ((varLocalCallsMobile == null || varLocalDataMobile == null || varInternationalCallsMobile == null || varInternationalDataMobile == null) && varTotalMobile == null) {
            throw new Exception("Variable Costs : You have to fill all the local and international variable costs fields, or just the total variable costs field.");
        }
    }

    /**
     * Method which checks if the field of the number of BlackBerry lines isn't
     * empty, else an Exception is added.
     */
    private void validationLinesBB(String linesBB) throws Exception {
        if (linesBB == null) {
            throw new Exception("Number of lines : You have to fill number of lines field.");
        }
    }

    /**
     * Method which checks if the fields of the fix costs of BlackBerry
     * Smartphones are well filled, else an Exception is added.
     */
    private void fixValidationBB(String fixLocalCallsBB, String fixLocalDataBB, String fixInternationalCallsBB, String fixInternationalDataBB, String fixTotalBB) throws Exception {
        if ((fixLocalCallsBB == null || fixLocalDataBB == null || fixInternationalCallsBB == null || fixInternationalDataBB == null) && fixTotalBB == null) {
            throw new Exception("Fix Costs : You have to fill all the local and international fix costs fields, or just the total fix costs field.");
        }
    }

    /**
     * Method which checks if the fields of the variable costs of BlackBerry
     * Smartphones are well filled, else an Exception is added.
     */
    private void varValidationBB(String varLocalCallsBB, String varLocalDataBB, String varInternationalCallsBB, String varInternationalDataBB, String varTotalBB) throws Exception {
        if ((varLocalCallsBB == null || varLocalDataBB == null || varInternationalCallsBB == null || varInternationalDataBB == null) && varTotalBB == null) {
            throw new Exception("Variable Costs : You have to fill all the local and international variable costs fields, or just the total variable costs field.");
        }
    }
}