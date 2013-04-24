/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import static com.bdd.DaoUtilitaire.initPreparedRequest;
import static com.bdd.DaoUtilitaire.silentClosures;
import com.beans.UnitReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enji
 */
public class UnitReportDaoImpl implements UnitReportDao {

	private DaoFactory daoFactory;

	UnitReportDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	private static final String SQL_SELECT_ACTIVE_COUNTRIES = "SELECT COUNTRY_NAME, COUNTRY_CODE FROM WEBIDMINT.LOCATION_COUNTRY WHERE COUNTRY_STATUS='ACTIVE' ORDER BY COUNTRY_NAME";

	public List<String> listActiveCountries(List<String> messageCountry) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			try {
				/*
				 * Récupération d'une connexion depuis la Factory
				 */
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(UnitReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest" et on récupère le ResultSet en l'exécutant
			preparedStatement = initPreparedRequest(connexion, SQL_SELECT_ACTIVE_COUNTRIES, false);
			resultSet = preparedStatement.executeQuery();

			String countryUnitReports = null;
			String codeCountryUnitReports = null;

			//Parcours des lignes de données de l'éventuel ResulSet retourné
			while (resultSet.next()) {
				countryUnitReports = resultSet.getString("COUNTRY_NAME");
				codeCountryUnitReports = resultSet.getString("COUNTRY_CODE");

				/* Preparation of the data to the final display into the JSP */
				messageCountry.add("<option value=" + codeCountryUnitReports + ">" + countryUnitReports + "</option>");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			//On ferme le ResultSet, le PreparedStatement et la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		return messageCountry;

	}
	private static final String SQL_SELECT_ACTIVE_SITES = "SELECT SITE_CODE, SITE_NAME FROM WEBIDMINT.LOCATION_SITE WHERE SITE_STATUS = 'ACTIVE' AND REF_COUNTRY_CODE = ? ORDER BY SITE_NAME ASC ";

	public String listActiveSites(String messageSite, String parametreCountry) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			try {
				/*
				 * Récupération d'une connexion depuis la Factory
				 */
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(UnitReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest" et on récupère le ResultSet en l'exécutant
			preparedStatement = initPreparedRequest(connexion, SQL_SELECT_ACTIVE_SITES, false, parametreCountry);
			resultSet = preparedStatement.executeQuery();

			int i = 1;
			String siteCodeUnitReports = "";
			String siteNameUnitReports = "";

			//Parcours des lignes de données de l'éventuel ResulSet retourné
			while (resultSet.next()) {
				siteCodeUnitReports = resultSet.getString("SITE_CODE");
				siteNameUnitReports = resultSet.getString("SITE_NAME");
				System.out.println(siteCodeUnitReports);
				System.out.println(siteNameUnitReports);
				/* Formatage des données pour affichage dans la JSP finale. */
				messageSite += "<tr><td style=\"width:40px;\"><input type=\"checkbox\" name=\"site" + i + "\" id=\"" + siteCodeUnitReports + "\" value=\"" + siteCodeUnitReports + "\"/></td><td><span class=\"displaySite\" style=\"text-align:left;\">" + siteCodeUnitReports + "</span></td><td><span class=\"displaySite\" style=\"text-align:left;\">" + siteNameUnitReports + "</span></td></tr>";
				i++;
			}

			if (messageSite != null && i > 0) {
				messageSite += "<input id=\"nbCheckBoxes\" name=\"nbCheckBoxes\" type=\"hidden\" readonly=\"readonly\" value=\"" + i + "\"/>";
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			//On ferme le ResultSet, le PreparedStatement et la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		return messageSite;
	}
	private static final String SQL_INSERT_UNIT_REPORT = "INSERT INTO WEBIDMINT.TELEPHONY_UNITCONTRACT (REF_COUNTRY_CODE, CONTRACT_NAME, DESCRIPTION, REF_OWNER_ID)" + "VALUES (?, ?, ?, ?)";
	private static final String SQL_INSERT_SITES_UNIT_REPORT = "INSERT INTO WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK (REF_UNITCONTRACT, REF_SITE_CODE) VALUES (?, ?)";

	public String insertUnitReport(String messageResult, String parametreLogin, String parametreName, String parametreCountry, String parametreType, List<String> parametreSite) {

		Connection connexion = null;
		PreparedStatement preparedStatementUnitReport = null;
		PreparedStatement preparedStatementSites = null;

		try {
			try {
				/*
				 * Récupération d'une connexion depuis la Factory
				 */
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(UnitReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest"
			preparedStatementUnitReport = initPreparedRequest(connexion, SQL_INSERT_UNIT_REPORT, false, parametreCountry, parametreName, parametreType, parametreLogin);
			int statut = preparedStatementUnitReport.executeUpdate();
			String parametreSiteElement = "";
			for (int i = 0; i < parametreSite.size(); i++) {
				parametreSiteElement = parametreSite.get(i);
				if (parametreSiteElement != null && parametreSiteElement.length() > 0) {
					//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest"
					preparedStatementSites = initPreparedRequest(connexion, SQL_INSERT_SITES_UNIT_REPORT, false, parametreName, parametreSiteElement);
					/* Exécution d'une requête d'insertion */
					preparedStatementSites.executeUpdate();
				}
			}

			/**
			 *
			 * Récupération du résultat de la requête Préparation du résultat à
			 * afficher dans la JSP finale
			 *
			 */
			if ((statut != 1)) {
				messageResult += (" <span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been registered</span><center><br /><br /><br /><a href=\"Add_Unit_Reports\" class=\"button\">Retry</a></center>");
			} else {
				messageResult += ("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been registered</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			//On ferme le PreparedStatement et la connexion
			silentClosures(preparedStatementUnitReport, connexion);
			silentClosures(preparedStatementSites, connexion);
		}

		return messageResult;
	}
	
	private static final String SQL_SELECT_CONTRACT_NAMES = "SELECT CONTRACT_NAME FROM WEBIDMINT.TELEPHONY_UNITCONTRACT ORDER BY CONTRACT_NAME";

	public List<String> listContractNames(List<String> messageContractName) {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			try {
				/*
				 * Récupération d'une connexion depuis la Factory
				 */
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(UnitReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest" et on récupère le ResultSet en l'exécutant
			preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_NAMES, false);
			resultSet = preparedStatement.executeQuery();

			String contractName = null;

			while (resultSet.next()) {
                contractName = resultSet.getString("CONTRACT_NAME");
                /* Formatage des données pour affichage dans la JSP finale. */
                messageContractName.add("<option value='" + contractName + "'>" + contractName + "</option>");
            }

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			//On ferme le ResultSet, le PreparedStatement et la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		return messageContractName;
	}
	
	private static final String SQL_DELETE_UNIT_REPORT = "DELETE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT WHERE CONTRACT_NAME = ?";
	private static final String SQL_DELETE_UNIT_REPORT_LINK = "DELETE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK WHERE REF_UNITCONTRACT = ?";
	
	public String deleteUnitReport(String messageDelete, String nameUnitReport) {
		
		Connection connexion = null;
		PreparedStatement preparedStatementUnitReport = null;
		PreparedStatement preparedStatementUnitReportLink = null;

		try {
			try {
				/*
				 * Récupération d'une connexion depuis la Factory
				 */
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(UnitReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest"
			preparedStatementUnitReport = initPreparedRequest(connexion, SQL_DELETE_UNIT_REPORT, false, nameUnitReport);
			int statut = preparedStatementUnitReport.executeUpdate();
			preparedStatementUnitReportLink = initPreparedRequest(connexion, SQL_DELETE_UNIT_REPORT_LINK, false, nameUnitReport);
			preparedStatementUnitReport.executeUpdate();

			/**
			 *
			 * Récupération du résultat de la requête Préparation du résultat à
			 * afficher dans la JSP finale
			 *
			 */
			if ((statut != 1)) {
                messageDelete += ("<span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been deleted. Try again :</span><br /><br /><br /><center><br /><br /><br /><a href=\"Delete_Unit_Reports\" class=\"button\">Retry</a></center>");
            } else {
                messageDelete += ("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been deleted</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
            }

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			//On ferme le PreparedStatement et la connexion
			silentClosures(preparedStatementUnitReport, connexion);
			silentClosures(preparedStatementUnitReportLink, connexion);
		}

		return messageDelete;
	}
}
