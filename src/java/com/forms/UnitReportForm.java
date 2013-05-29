/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import com.bdd.UnitReportDao;
import com.beans.UnitReport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Enji
 */
public class UnitReportForm {

	private UnitReportDao unitReportDao;

	/**
	 *
	 * @param UnitReportDao
	 * @throws WriteException
	 */
	@SuppressWarnings("empty-statement")
	public UnitReportForm(UnitReportDao unitReportDao) {
		this.unitReportDao = unitReportDao;
	}

	/**
	 *
	 * @param nameUnitReport Nom du contrat
	 * @param descriptionUnitReport Type de contrat : Fixed, Mobile ou Both
	 * @param siteCodeUnitReport Référence du site
	 * @param siteNameUnitReport Nom du site
	 * @param countryUnitReport Pays référant du contrat
	 * @return Map contenant un contrat avec ses sites
	 */
	public void addUnitReport(String nameUnitReport, String descriptionUnitReport, String siteCodeUnitReportint, List<String> siteNameUnitReport, String countryUnitReport) {
	}

	public List<String> listActiveCountries() {

		List<String> messageCountry = new ArrayList<String>();
		messageCountry = unitReportDao.listActiveCountries(messageCountry);

		return messageCountry;

	}

	public String listActiveSites(HttpServletRequest request) {

		String messageSite = "";
		String nameUnitReport = request.getParameter("countryUnitReports");
		messageSite = unitReportDao.listActiveSites(messageSite, nameUnitReport);

		return messageSite;

	}

	public String insertUnitReport(HttpServletRequest request, String parametreLogin) {

		String messageResult = "";
		String parametreName = request.getParameter("nameUnitReports");
		String parametreCountry = request.getParameter("countryUnitReports");
		String parametreType = request.getParameter("typeUnitReports");
		int iNbCheckBoxes = (request.getParameter("nbCheckBoxes") != null ? Integer.parseInt(request.getParameter("nbCheckBoxes")) : 0);
		List<String> parametreSite = new ArrayList<String>();
		if (iNbCheckBoxes > 0) {
			for (int i = 1; i <= iNbCheckBoxes; i++) {
				parametreSite.add(request.getParameter("site" + i));
			}
		}
		
		messageResult = unitReportDao.insertUnitReport(messageResult, parametreLogin, parametreName, parametreCountry, parametreType, parametreSite);

		return messageResult;
	}
	
	public List<String> listContractNames() {

		List<String> messageContractName = new ArrayList<String>();
		messageContractName = unitReportDao.listContractNames(messageContractName);

		return messageContractName;

	}
	
	public String deleteUnitReport(HttpServletRequest request) {

		String messageDelete = "";
		String nameUnitReport = request.getParameter("nameUnitReports");
		messageDelete = unitReportDao.deleteUnitReport(messageDelete, nameUnitReport);

		return messageDelete;

	}
	
	public boolean checkUnitReportName(String nameUnitReport) {
		
		boolean check = false;
		check = unitReportDao.checkUnitReportName(nameUnitReport, check);
		
		return check;
	}
}
