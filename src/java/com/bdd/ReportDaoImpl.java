/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import static com.bdd.DaoUtilitaire.initPreparedRequest;
import static com.bdd.DaoUtilitaire.silentClosures;
import com.beans.Report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nico
 */
class ReportDaoImpl implements ReportDao {

    private DaoFactory daoFactory;

    ReportDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /*
     * Method which encapsulate resultSet datas consmption in a Report bean.
     */
    private static Report mapConsumption(ResultSet resultSet, Report report) throws SQLException {

        String serviceType = resultSet.getString("SERVICE_TYPE");
        String costType = resultSet.getString("COST_TYPE");

        if ("Fix".equals(serviceType)) {
            if ("Fix".equals(costType)) {
                report.setFixLocalCallsFix(resultSet.getString("LOCAL_CALL"));
                report.setFixInternationalCallsFix(resultSet.getString("INTER_CALL"));
                report.setFixTotalFix(resultSet.getString("TOTAL"));
            } else if ("Var".equals(costType)) {
                report.setVarLocalCallsFix(resultSet.getString("LOCAL_CALL"));
                report.setVarInternationalCallsFix(resultSet.getString("INTER_CALL"));
                report.setVarTotalFix(resultSet.getString("TOTAL"));
            }
        } else if ("3G".equals(serviceType)) {
            if ("Fix".equals(costType)) {
                report.setFixLocalData3G(resultSet.getString("LOCAL_DATA"));
                report.setFixInternationalData3G(resultSet.getString("INTER_DATA"));
                report.setFixTotal3G(resultSet.getString("TOTAL"));
            } else if ("Var".equals(costType)) {
                report.setVarLocalData3G(resultSet.getString("LOCAL_DATA"));
                report.setVarInternationalData3G(resultSet.getString("INTER_DATA"));
                report.setVarTotal3G(resultSet.getString("TOTAL"));
            }
        } else if ("Mobile".equals(serviceType)) {
            if ("Fix".equals(costType)) {
                report.setFixLocalCallsMobile(resultSet.getString("LOCAL_CALL"));
                report.setFixLocalDataMobile(resultSet.getString("LOCAL_DATA"));
                report.setFixInternationalCallsMobile(resultSet.getString("INTER_CALL"));
                report.setFixInternationalDataMobile(resultSet.getString("INTER_DATA"));
                report.setFixTotalMobile(resultSet.getString("TOTAL"));
            } else if ("Var".equals(costType)) {
                report.setVarLocalCallsMobile(resultSet.getString("LOCAL_CALL"));
                report.setVarLocalDataMobile(resultSet.getString("LOCAL_DATA"));
                report.setVarInternationalCallsMobile(resultSet.getString("INTER_CALL"));
                report.setVarInternationalDataMobile(resultSet.getString("INTER_DATA"));
                report.setVarTotalMobile(resultSet.getString("TOTAL"));
            }
        } else if ("Blackberry".equals(serviceType)) {
            if ("Fix".equals(costType)) {
                report.setFixLocalCallsBB(resultSet.getString("LOCAL_CALL"));
                report.setFixLocalDataBB(resultSet.getString("LOCAL_DATA"));
                report.setFixInternationalCallsBB(resultSet.getString("INTER_CALL"));
                report.setFixInternationalDataBB(resultSet.getString("INTER_DATA"));
                report.setFixTotalBB(resultSet.getString("TOTAL"));
            } else if ("Var".equals(costType)) {
                report.setVarLocalCallsBB(resultSet.getString("LOCAL_CALL"));
                report.setVarLocalDataBB(resultSet.getString("LOCAL_DATA"));
                report.setVarInternationalCallsBB(resultSet.getString("INTER_CALL"));
                report.setVarInternationalDataBB(resultSet.getString("INTER_DATA"));
                report.setVarTotalBB(resultSet.getString("TOTAL"));
            }
        }

        return report;
    }

    /*
     * Method which encapsulate resultSet datas lines in a Report bean.
     */
    private static Report mapLineCount(ResultSet resultSet, Report report) throws SQLException {

        String serviceType = resultSet.getString("SERVICE_TYPE");

        if ("Fix".equals(serviceType)) {
            report.setLinesFix(resultSet.getString("LINE_NUMBER"));
        } else if ("3G".equals(serviceType)) {
            report.setLines3G(resultSet.getString("LINE_NUMBER"));
        } else if ("Mobile".equals(serviceType)) {
            report.setLinesMobile(resultSet.getString("LINE_NUMBER"));
        } else if ("Blackberry".equals(serviceType)) {
            report.setLinesBB(resultSet.getString("LINE_NUMBER"));
        }

        return report;
    }
    private static final String SQL_SELECT_TYPE = "SELECT DESCRIPTION FROM WEBIDMINT.TELEPHONY_UNITCONTRACT WHERE CONTRACT_NAME = ?";
    private static final String SQL_SELECT_CONSUMPTION_BY_YEAR = "SELECT DATE_REPORTS, SERVICE_TYPE, COST_TYPE, LOCAL_CALL, LOCAL_DATA, INTER_CALL, INTER_DATA, TOTAL FROM WEBIDMINT.TELEPHONY_CONSUMPTION WHERE REF_UNITCONTRACT = ? AND ((EXTRACT(YEAR FROM DATE_REPORTS) = ? AND EXTRACT(MONTH FROM DATE_REPORTS) >= 4) OR (EXTRACT(YEAR FROM DATE_REPORTS) = ? AND EXTRACT(MONTH FROM DATE_REPORTS) <= 3)) ORDER BY DATE_REPORTS";
    private static final String SQL_SELECT_CONSUMPTION_BY_MONTH = "SELECT SERVICE_TYPE, COST_TYPE, LOCAL_CALL, LOCAL_DATA, INTER_CALL, INTER_DATA, TOTAL FROM WEBIDMINT.TELEPHONY_CONSUMPTION WHERE REF_UNITCONTRACT = ? AND EXTRACT(MONTH FROM DATE_REPORTS) = ? AND EXTRACT(YEAR FROM DATE_REPORTS) = ? ORDER BY DATE_REPORTS";
    private static final String SQL_SELECT_LINE_NUMBER_BY_YEAR = "SELECT DATE_CREATION, SERVICE_TYPE, LINE_NUMBER FROM WEBIDMINT.TELEPHONY_LINECOUNT WHERE REF_UNITREPORTS = ? AND ((EXTRACT(YEAR FROM DATE_CREATION) = ? AND EXTRACT(MONTH FROM DATE_CREATION) >= 4) OR (EXTRACT(YEAR FROM DATE_CREATION) = ? AND EXTRACT(MONTH FROM DATE_CREATION) <= 3)) ORDER BY DATE_CREATION";
    private static final String SQL_SELECT_LINE_NUMBER_BY_MONTH = "SELECT SERVICE_TYPE, LINE_NUMBER FROM WEBIDMINT.TELEPHONY_LINECOUNT WHERE REF_UNITREPORTS = ? AND EXTRACT(MONTH FROM DATE_CREATION) = ? AND EXTRACT(YEAR FROM DATE_CREATION) = ? ORDER BY DATE_CREATION";

    public Map<Integer, Report> list(Map<Integer, Report> reportMap, String refContract, Calendar calendar, Boolean allMonth) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatementConsumption = null;
        ResultSet resultSetConsumption = null;
        PreparedStatement preparedStatementLineCount = null;
        ResultSet resultSetLineCount = null;
        PreparedStatement preparedStatementType = null;
        ResultSet resultSetType = null;
        String selectOptionConsumption = null;
        String selectOptionLineNumber = null;
        Report report = null;
        int selectValueSearched = 0;
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        if (allMonth) {
            selectOptionConsumption = SQL_SELECT_CONSUMPTION_BY_YEAR;
            selectOptionLineNumber = SQL_SELECT_LINE_NUMBER_BY_YEAR;
            selectValueSearched = year;
        } else {
            selectOptionConsumption = SQL_SELECT_CONSUMPTION_BY_MONTH;
            selectOptionLineNumber = SQL_SELECT_LINE_NUMBER_BY_MONTH;
            selectValueSearched = month;
        }

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (allMonth) {
                preparedStatementConsumption = initPreparedRequest(connexion, selectOptionConsumption, false, refContract, selectValueSearched, selectValueSearched + 1);
                preparedStatementLineCount = initPreparedRequest(connexion, selectOptionLineNumber, false, refContract, selectValueSearched, selectValueSearched + 1);
            } else {
                preparedStatementConsumption = initPreparedRequest(connexion, selectOptionConsumption, false, refContract, selectValueSearched, year);
                preparedStatementLineCount = initPreparedRequest(connexion, selectOptionLineNumber, false, refContract, selectValueSearched, year);
            }
            preparedStatementType = initPreparedRequest(connexion, SQL_SELECT_TYPE, false, refContract);
            resultSetType = preparedStatementType.executeQuery();
            resultSetConsumption = preparedStatementConsumption.executeQuery();
            resultSetLineCount = preparedStatementLineCount.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */

            if (!allMonth) {

                boolean consumption = false;
                boolean lineCount = false;
                boolean type = false;

                report = new Report();

                while (resultSetConsumption.next()) {
                    report = mapConsumption(resultSetConsumption, report);
                    consumption = true;
                }
                while (resultSetLineCount.next()) {
                    report = mapLineCount(resultSetLineCount, report);
                    lineCount = true;
                }
                report = handleInformations(refContract, month, year, false, report);
                if (resultSetType.next()) {
                    report.setType(resultSetType.getString("DESCRIPTION"));
                    type = true;
                }
                if (consumption && lineCount && type) {
                    reportMap.put(month, report);
                }
            } else {

                DateFormat dateFormat = new SimpleDateFormat("MM");
                int rsConsumptionMonth = 0;
                int rsLineCountMonth = 0;
                boolean dateChange = true;
                String type = null;
                if (resultSetType.next()) {
                    type = resultSetType.getString("DESCRIPTION");
                }

                while (resultSetConsumption.next()) {
                    if (dateChange) {
                        report = new Report();
                        rsConsumptionMonth = Integer.parseInt(dateFormat.format(resultSetConsumption.getDate("DATE_REPORTS")));
                        dateChange = false;
                    }

                    if (Integer.parseInt(dateFormat.format(resultSetConsumption.getDate("DATE_REPORTS"))) == rsConsumptionMonth) {
                        report = mapConsumption(resultSetConsumption, report);
                        reportMap.put(rsConsumptionMonth, report);
                    } else {
                        if (resultSetConsumption.previous()) {
                            dateChange = true;
                        }
                    }
                }

                dateChange = true;
                report = null;

                while (resultSetLineCount.next()) {
                    if (dateChange) {
                        rsLineCountMonth = Integer.parseInt(dateFormat.format(resultSetLineCount.getDate("DATE_CREATION")));
                        dateChange = false;
                        if (reportMap.containsKey(rsLineCountMonth)) {
                            report = reportMap.get(rsLineCountMonth);
                            if (rsConsumptionMonth >= 4) {
                                report = handleInformations(refContract, rsConsumptionMonth, year, false, report);
                            } else {
                                report = handleInformations(refContract, rsConsumptionMonth, year + 1, false, report);
                            }
                            report.setType(type);
                        }
                    }

                    if (Integer.parseInt(dateFormat.format(resultSetLineCount.getDate("DATE_CREATION"))) == rsLineCountMonth) {
                        report = mapLineCount(resultSetLineCount, report);
                        reportMap.put(rsLineCountMonth, report);
                    } else {
                        if (resultSetLineCount.previous()) {
                            dateChange = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSetConsumption, preparedStatementConsumption, connexion);
            silentClosures(resultSetLineCount, preparedStatementLineCount, connexion);
            silentClosures(resultSetType, preparedStatementType, connexion);
        }

        return reportMap;
    }

    public Report handleInformations(String refContract, int month, int year, Boolean editable, Report report) {

        report.setEditable(editable);
        report.setSite(refContract);
        report.setMonth(month);
        report.setYear(year);

        return report;
    }
    /*
     * Commenté pour v2
     * 
     private static final String SQL_SELECT_SITES = "SELECT CONTRACT_NAME FROM WEBIDMINT.TELEPHONY_UNITCONTRACT WHERE REF_OWNER_ID = ? ORDER BY CONTRACT_NAME";
     * 
     */
    private static final String SQL_SELECT_SITES = "SELECT CONTRACT_NAME FROM WEBIDMINT.TELEPHONY_UNITCONTRACT ORDER BY CONTRACT_NAME";

    public Map<Integer, String> siteList(Map<Integer, String> siteList, String login) throws DaoException {

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
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            /*
             * Commenté pour v2
             * 
             * preparedStatement = initPreparedRequest(connexion, SQL_SELECT_SITES, false, login);
             * 
             */
            preparedStatement = initPreparedRequest(connexion, SQL_SELECT_SITES, false);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            int i = 0;
            while (resultSet.next()) {
                siteList.put(i, resultSet.getString("CONTRACT_NAME"));
                i++;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSet, preparedStatement, connexion);
        }

        return siteList;
    }
    private static final String SQL_INSERT_CONSUMPTION = "INSERT INTO WEBIDMINT.TELEPHONY_CONSUMPTION (SERVICE_TYPE, COST_TYPE, LOCAL_CALL, LOCAL_DATA, INTER_CALL, INTER_DATA, TOTAL, REF_UNITCONTRACT, DATE_REPORTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_LINE_NUMBER = "INSERT INTO WEBIDMINT.TELEPHONY_LINECOUNT (SERVICE_TYPE, LINE_NUMBER, REF_UNITREPORTS, DATE_CREATION) VALUES (?, ?, ?, ?)";

    public Report save(int month, int year, String serviceType, String refContract, Report report) {

        Connection connexion = null;
        PreparedStatement preparedStatementFixConsumption = null;
        ResultSet resultSetFixConsumption = null;
        PreparedStatement preparedStatementVarConsumption = null;
        ResultSet resultSetVarConsumption = null;
        PreparedStatement preparedStatementLineCount = null;
        ResultSet resultSetLineCount = null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.YEAR, year);
        calendar.set(GregorianCalendar.MONTH, month - 1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.format(calendar.getTime());
        java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());

        int lineCount = 0;
        int fixLocalCall = 0;
        int fixLocalData = 0;
        int fixInternationalCall = 0;
        int fixInternationalData = 0;
        int fixTotal = 0;
        int varLocalCall = 0;
        int varLocalData = 0;
        int varInternationalCall = 0;
        int varInternationalData = 0;
        int varTotal = 0;

        if ("Fix".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLinesFix());

            if (report.getFixTotalFix() == null) {
                fixLocalCall = Integer.parseInt(report.getFixLocalCallsFix());
                fixLocalData = 0;
                fixInternationalCall = Integer.parseInt(report.getFixInternationalCallsFix());
                fixInternationalData = 0;
                fixTotal = fixLocalCall + fixInternationalCall;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotalFix());
            }

            if (report.getVarTotalFix() == null) {
                varLocalCall = Integer.parseInt(report.getVarLocalCallsFix());
                varLocalData = 0;
                varInternationalCall = Integer.parseInt(report.getVarInternationalCallsFix());
                varInternationalData = 0;
                varTotal = varLocalCall + varInternationalCall;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotalFix());
            }
        } else if ("3G".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLines3G());

            if (report.getFixTotal3G() == null) {
                fixLocalCall = 0;
                fixLocalData = Integer.parseInt(report.getFixLocalData3G());
                fixInternationalCall = 0;
                fixInternationalData = Integer.parseInt(report.getFixInternationalData3G());
                fixTotal = fixLocalData + fixInternationalData;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotal3G());
            }

            if (report.getVarTotal3G() == null) {
                varLocalCall = 0;
                varLocalData = Integer.parseInt(report.getVarLocalData3G());
                varInternationalCall = 0;
                varInternationalData = Integer.parseInt(report.getVarInternationalData3G());
                varTotal = varLocalData + varInternationalData;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotal3G());
            }
        } else if ("Mobile".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLinesMobile());

            if (report.getFixTotalMobile() == null) {
                fixLocalCall = Integer.parseInt(report.getFixLocalCallsMobile());
                fixLocalData = Integer.parseInt(report.getFixLocalDataMobile());
                fixInternationalCall = Integer.parseInt(report.getFixInternationalCallsMobile());
                fixInternationalData = Integer.parseInt(report.getFixInternationalDataMobile());
                fixTotal = fixLocalCall + fixLocalData + fixInternationalCall + fixInternationalData;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotalMobile());
            }

            if (report.getVarTotalMobile() == null) {
                varLocalCall = Integer.parseInt(report.getVarLocalCallsMobile());
                varLocalData = Integer.parseInt(report.getVarLocalDataMobile());
                varInternationalCall = Integer.parseInt(report.getVarInternationalCallsMobile());
                varInternationalData = Integer.parseInt(report.getVarInternationalDataMobile());
                varTotal = varLocalCall + varLocalData + varInternationalCall + varInternationalData;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotalMobile());
            }
        } else if ("Blackberry".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLinesBB());

            if (report.getFixTotalBB() == null) {
                fixLocalCall = Integer.parseInt(report.getFixLocalCallsBB());
                fixLocalData = Integer.parseInt(report.getFixLocalDataBB());
                fixInternationalCall = Integer.parseInt(report.getFixInternationalCallsBB());
                fixInternationalData = Integer.parseInt(report.getFixInternationalDataBB());
                fixTotal = fixLocalCall + fixLocalData + fixInternationalCall + fixInternationalData;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotalBB());
            }

            if (report.getVarTotalBB() == null) {
                varLocalCall = Integer.parseInt(report.getVarLocalCallsBB());
                varLocalData = Integer.parseInt(report.getVarLocalDataBB());
                varInternationalCall = Integer.parseInt(report.getVarInternationalCallsBB());
                varInternationalData = Integer.parseInt(report.getVarInternationalDataBB());
                varTotal = varLocalCall + varLocalData + varInternationalCall + varInternationalData;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotalBB());
            }
        }

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            preparedStatementFixConsumption = initPreparedRequest(connexion, SQL_INSERT_CONSUMPTION, false, serviceType, "Fix", fixLocalCall, fixLocalData, fixInternationalCall, fixInternationalData, fixTotal, refContract, date);
            preparedStatementFixConsumption.executeUpdate();
            preparedStatementVarConsumption = initPreparedRequest(connexion, SQL_INSERT_CONSUMPTION, false, serviceType, "Var", varLocalCall, varLocalData, varInternationalCall, varInternationalData, varTotal, refContract, date);
            preparedStatementVarConsumption.executeUpdate();
            preparedStatementLineCount = initPreparedRequest(connexion, SQL_INSERT_LINE_NUMBER, false, serviceType, lineCount, refContract, date);
            preparedStatementLineCount.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSetFixConsumption, preparedStatementFixConsumption, connexion);
            silentClosures(resultSetVarConsumption, preparedStatementVarConsumption, connexion);
            silentClosures(resultSetLineCount, preparedStatementLineCount, connexion);
        }

        return report;
    }
    private static final String SQL_UPDATE_CONSUMPTION = "UPDATE WEBIDMINT.TELEPHONY_CONSUMPTION SET LOCAL_CALL = ?, LOCAL_DATA = ?, INTER_CALL = ?, INTER_DATA = ?, TOTAL = ? WHERE SERVICE_TYPE = ? AND COST_TYPE = ? AND REF_UNITCONTRACT = ? AND EXTRACT(MONTH FROM DATE_REPORTS) = ? AND EXTRACT(YEAR FROM DATE_REPORTS) = ?";
    private static final String SQL_UPDATE_LINE_NUMBER = "UPDATE WEBIDMINT.TELEPHONY_LINECOUNT SET LINE_NUMBER = ? WHERE SERVICE_TYPE = ? AND REF_UNITREPORTS = ? AND EXTRACT(MONTH FROM DATE_CREATION) = ? AND EXTRACT(YEAR FROM DATE_CREATION) = ?";

    public Report update(int month, int year, String serviceType, String refContract, Report report) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatementFixConsumption = null;
        ResultSet resultSetFixConsumption = null;
        PreparedStatement preparedStatementVarConsumption = null;
        ResultSet resultSetVarConsumption = null;
        PreparedStatement preparedStatementLineCount = null;
        ResultSet resultSetLineCount = null;

        int lineCount = 0;
        int fixLocalCall = 0;
        int fixLocalData = 0;
        int fixInternationalCall = 0;
        int fixInternationalData = 0;
        int fixTotal = 0;
        int varLocalCall = 0;
        int varLocalData = 0;
        int varInternationalCall = 0;
        int varInternationalData = 0;
        int varTotal = 0;

        if ("Fix".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLinesFix());

            if (report.getFixTotalFix() == null) {
                fixLocalCall = Integer.parseInt(report.getFixLocalCallsFix());
                fixLocalData = 0;
                fixInternationalCall = Integer.parseInt(report.getFixInternationalCallsFix());
                fixInternationalData = 0;
                fixTotal = fixLocalCall + fixInternationalCall;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotalFix());
            }

            if (report.getVarTotalFix() == null) {
                varLocalCall = Integer.parseInt(report.getVarLocalCallsFix());
                varLocalData = 0;
                varInternationalCall = Integer.parseInt(report.getVarInternationalCallsFix());
                varInternationalData = 0;
                varTotal = varLocalCall + varInternationalCall;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotalFix());
            }
        } else if ("3G".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLines3G());

            if (report.getFixTotal3G() == null) {
                fixLocalCall = 0;
                fixLocalData = Integer.parseInt(report.getFixLocalData3G());
                fixInternationalCall = 0;
                fixInternationalData = Integer.parseInt(report.getFixInternationalData3G());
                fixTotal = fixLocalData + fixInternationalData;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotal3G());
            }

            if (report.getVarTotal3G() == null) {
                varLocalCall = 0;
                varLocalData = Integer.parseInt(report.getVarLocalData3G());
                varInternationalCall = 0;
                varInternationalData = Integer.parseInt(report.getVarInternationalData3G());
                varTotal = varLocalData + varInternationalData;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotal3G());
            }
        } else if ("Mobile".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLinesMobile());

            if (report.getFixTotalMobile() == null) {
                fixLocalCall = Integer.parseInt(report.getFixLocalCallsMobile());
                fixLocalData = Integer.parseInt(report.getFixLocalDataMobile());
                fixInternationalCall = Integer.parseInt(report.getFixInternationalCallsMobile());
                fixInternationalData = Integer.parseInt(report.getFixInternationalDataMobile());
                fixTotal = fixLocalCall + fixLocalData + fixInternationalCall + fixInternationalData;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotalMobile());
            }

            if (report.getVarTotalMobile() == null) {
                varLocalCall = Integer.parseInt(report.getVarLocalCallsMobile());
                varLocalData = Integer.parseInt(report.getVarLocalDataMobile());
                varInternationalCall = Integer.parseInt(report.getVarInternationalCallsMobile());
                varInternationalData = Integer.parseInt(report.getVarInternationalDataMobile());
                varTotal = varLocalCall + varLocalData + varInternationalCall + varInternationalData;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotalMobile());
            }
        } else if ("Blackberry".equals(serviceType)) {
            lineCount = Integer.parseInt(report.getLinesBB());

            if (report.getFixTotalBB() == null) {
                fixLocalCall = Integer.parseInt(report.getFixLocalCallsBB());
                fixLocalData = Integer.parseInt(report.getFixLocalDataBB());
                fixInternationalCall = Integer.parseInt(report.getFixInternationalCallsBB());
                fixInternationalData = Integer.parseInt(report.getFixInternationalDataBB());
                fixTotal = fixLocalCall + fixLocalData + fixInternationalCall + fixInternationalData;
            } else {
                fixLocalCall = 0;
                fixLocalData = 0;
                fixInternationalCall = 0;
                fixInternationalData = 0;
                fixTotal = Integer.parseInt(report.getFixTotalBB());
            }

            if (report.getVarTotalBB() == null) {
                varLocalCall = Integer.parseInt(report.getVarLocalCallsBB());
                varLocalData = Integer.parseInt(report.getVarLocalDataBB());
                varInternationalCall = Integer.parseInt(report.getVarInternationalCallsBB());
                varInternationalData = Integer.parseInt(report.getVarInternationalDataBB());
                varTotal = varLocalCall + varLocalData + varInternationalCall + varInternationalData;
            } else {
                varLocalCall = 0;
                varLocalData = 0;
                varInternationalCall = 0;
                varInternationalData = 0;
                varTotal = Integer.parseInt(report.getVarTotalBB());
            }
        }


        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            preparedStatementFixConsumption = initPreparedRequest(connexion, SQL_UPDATE_CONSUMPTION, false, fixLocalCall, fixLocalData, fixInternationalCall, fixInternationalData, fixTotal, serviceType, "Fix", refContract, month, year);
            resultSetFixConsumption = preparedStatementFixConsumption.executeQuery();
            preparedStatementVarConsumption = initPreparedRequest(connexion, SQL_UPDATE_CONSUMPTION, false, varLocalCall, varLocalData, varInternationalCall, varInternationalData, varTotal, serviceType, "Var", refContract, month, year);
            resultSetVarConsumption = preparedStatementVarConsumption.executeQuery();
            preparedStatementLineCount = initPreparedRequest(connexion, SQL_UPDATE_LINE_NUMBER, false, lineCount, serviceType, refContract, month, year);
            resultSetLineCount = preparedStatementLineCount.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSetFixConsumption, preparedStatementFixConsumption, connexion);
            silentClosures(resultSetVarConsumption, preparedStatementVarConsumption, connexion);
            silentClosures(resultSetLineCount, preparedStatementLineCount, connexion);
        }

        return report;
    }

    public String getType(String refContract) {
        Connection connexion = null;
        String type = null;
        PreparedStatement preparedStatementType = null;
        ResultSet resultSetType = null;

        try {
            try {
                /*
                 * Récupération d'une connexion depuis la Factory
                 */
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            preparedStatementType = initPreparedRequest(connexion, SQL_SELECT_TYPE, false, refContract);
            resultSetType = preparedStatementType.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */

            if (resultSetType.next()) {
                type = resultSetType.getString("DESCRIPTION");
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            silentClosures(resultSetType, preparedStatementType, connexion);
        }

        return type;
    }
}
