/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import static com.bdd.DaoUtilitaire.initPreparedRequest;
import static com.bdd.DaoUtilitaire.silentClosures;
import com.beans.ExtractTab;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nico
 */
class ExtractDaoImpl implements ExtractDao {

    private DaoFactory daoFactory;

    ExtractDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
	
	/**
	 *
	 * @param resultSet Ligne sur laquelle effectuer le mapping
	 * @param extractTab Bean où encapasuler les paramètres récupérés du ResultSet
	 * @return Bean d'extract complet (mappé)
	 * @throws DaoException
	 */
    private static ExtractTab mapExtractTabMonth(ResultSet resultSet, ExtractTab extractTab) throws SQLException {

        int status = extractTab.getExtractStatus();

        if (status == 0) {
            extractTab.setContractName(resultSet.getString("REF_UNITCONTRACT"));
            extractTab.setCountry(resultSet.getString("REF_COUNTRY_CODE"));
            extractTab.setQuantity(resultSet.getInt("LINE_NUMBER"));
            extractTab.setTotalCost(resultSet.getInt("TOTAL"));
            extractTab.setType(resultSet.getString("CATEGORIE"));
            extractTab.setExtractStatus(1);
        } else if (status == 1) {
            extractTab.setQuantity(extractTab.getQuantity() + resultSet.getInt("LINE_NUMBER"));
            extractTab.setTotalCost(extractTab.getTotalCost() + resultSet.getInt("TOTAL"));
        } else {
        }

        return extractTab;
    }
	
    private static final String SQL_SELECT_COUNTRY_ONE_MONTH = "SELECT UC.REF_COUNTRY_CODE, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.REF_COUNTRY_CODE = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(MONTH FROM C.DATE_REPORTS) = ? AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, UC.CONTRACT_NAME, C.DATE_REPORTS";
    private static final String SQL_SELECT_COUNTRY_ALL_MONTH = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.REF_COUNTRY_CODE = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, UC.CONTRACT_NAME, C.DATE_REPORTS";

    public Map<Integer, ExtractTab> extractCountryMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ExtractTab extract = null;
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (allMonth) {
                preparedStatement = initPreparedRequest(connexion, SQL_SELECT_COUNTRY_ALL_MONTH, false, nameExtract, year);

            } else {
                preparedStatement = initPreparedRequest(connexion, SQL_SELECT_COUNTRY_ONE_MONTH, false, nameExtract, month, year);
            }
            resultSet = preparedStatement.executeQuery();

            if (!allMonth) {

                int nbTimes = 0;
                int i = 0;
                extract = new ExtractTab(month, year);

                while (resultSet.next()) {
                    extract = mapExtractTabMonth(resultSet, extract);
                    nbTimes++;
                    if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
                        extract.setQuantity(extract.getQuantity() / 2);
                        extract.setArpu(extract.getTotalCost() / extract.getQuantity());
                        extract.setExtractStatus(2);
                        extractMap.put(i, extract);
                        i++;
                        extract = new ExtractTab(month, year);
                        nbTimes = 0;
                    }
                }
            } else {
                int nbTimes = 0;
                int i = 0;

                while (resultSet.next()) {
                    if (nbTimes == 0) {
                        month = resultSet.getInt("MONTH");
                        extract = new ExtractTab(month, year);
                    }
                    extract = mapExtractTabMonth(resultSet, extract);
                    nbTimes++;
                    if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
                        extract.setQuantity(extract.getQuantity() / 2);
                        extract.setArpu(extract.getTotalCost() / extract.getQuantity());
                        extract.setExtractStatus(2);
                        extractMap.put(i, extract);
                        i++;
                        nbTimes = 0;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return extractMap;
    }
    private static final String SQL_SELECT_CONTRACT_ONE_MONTH = "SELECT UC.REF_COUNTRY_CODE, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.CONTRACT_NAME = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(MONTH FROM C.DATE_REPORTS) = ? AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, C.DATE_REPORTS";
    private static final String SQL_SELECT_CONTRACT_ALL_MONTH = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.CONTRACT_NAME = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, C.DATE_REPORTS";

    public Map<Integer, ExtractTab> extractContractMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ExtractTab extract = null;
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (allMonth) {
                preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_ALL_MONTH, false, nameExtract, year);

            } else {
                preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_ONE_MONTH, false, nameExtract, month, year);
            }
            resultSet = preparedStatement.executeQuery();

            if (!allMonth) {

                int nbTimes = 0;
                int i = 0;
                extract = new ExtractTab(month, year);

                while (resultSet.next()) {
                    extract = mapExtractTabMonth(resultSet, extract);
                    nbTimes++;
                    if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
                        extract.setQuantity(extract.getQuantity() / 2);
                        extract.setArpu(extract.getTotalCost() / extract.getQuantity());
                        extract.setExtractStatus(2);
                        extractMap.put(i, extract);
                        i++;
                        extract = new ExtractTab(month, year);
                        nbTimes = 0;
                    }
                }
            } else {
                int nbTimes = 0;
                int i = 0;

                while (resultSet.next()) {
                    if (nbTimes == 0) {
                        month = resultSet.getInt("MONTH");
                        extract = new ExtractTab(month, year);
                    }
                    extract = mapExtractTabMonth(resultSet, extract);
                    nbTimes++;
                    if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
                        extract.setQuantity(extract.getQuantity() / 2);
                        extract.setArpu(extract.getTotalCost() / extract.getQuantity());
                        extract.setExtractStatus(2);
                        extractMap.put(i, extract);
                        i++;
                        nbTimes = 0;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return extractMap;
    }
    private static final String SQL_SELECT_COUNTRY_FISCAL_YEAR = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, EXTRACT(YEAR FROM C.DATE_REPORTS) AS YEAR, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.REF_COUNTRY_CODE = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND ((EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) >= 4) OR (EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) <= 3)) ORDER BY CATEGORIE, UC.CONTRACT_NAME, C.DATE_REPORTS";

    public Map<Integer, ExtractTab> extractCountryFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ExtractTab extract = null;
        int month = 0;

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            preparedStatement = initPreparedRequest(connexion, SQL_SELECT_COUNTRY_FISCAL_YEAR, false, nameExtract, year, year + 1);

            resultSet = preparedStatement.executeQuery();


            int nbTimes = 0;
            int i = 0;

            while (resultSet.next()) {
                if (nbTimes == 0) {
                    month = resultSet.getInt("MONTH");
                    year = resultSet.getInt("YEAR");
                    extract = new ExtractTab(month, year);
                }
                extract = mapExtractTabMonth(resultSet, extract);
                nbTimes++;
                if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
                    extract.setQuantity(extract.getQuantity() / 2);
                    extract.setArpu(extract.getTotalCost() / extract.getQuantity());
                    extract.setExtractStatus(2);
                    extractMap.put(i, extract);
                    i++;
                    nbTimes = 0;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return extractMap;

    }
    private static final String SQL_SELECT_CONTRACT_FISCAL_YEAR = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, EXTRACT(YEAR FROM C.DATE_REPORTS) AS YEAR, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.CONTRACT_NAME = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND ((EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) >= 4) OR (EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) <= 3)) ORDER BY CATEGORIE, C.DATE_REPORTS";

    public Map<Integer, ExtractTab> extractContractFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ExtractTab extract = null;
        int month = 0;

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_FISCAL_YEAR, false, nameExtract, year, year + 1);

            resultSet = preparedStatement.executeQuery();


            int nbTimes = 0;
            int i = 0;

            while (resultSet.next()) {
                if (nbTimes == 0) {
                    month = resultSet.getInt("MONTH");
                    year = resultSet.getInt("YEAR");
                    extract = new ExtractTab(month, year);
                }
                extract = mapExtractTabMonth(resultSet, extract);
                nbTimes++;
                if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
                    extract.setQuantity(extract.getQuantity() / 2);
                    extract.setArpu(extract.getTotalCost() / extract.getQuantity());
                    extract.setExtractStatus(2);
                    extractMap.put(i, extract);
                    i++;
                    nbTimes = 0;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return extractMap;

    }
}
