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
	
	/**
	 *
	 * @param resultSet Ligne sur laquelle effectuer le mapping
	 * @param report Bean où encapasuler les paramètres récupérés du ResultSet
	 * @return Bean de Report complet pour les consommations (mappé)
	 * @throws DaoException
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

    /**
	 *
	 * @param resultSet Ligne sur laquelle effectuer le mapping
	 * @param report Bean où encapasuler les paramètres récupérés du ResultSet
	 * @return Bean de Report complet pour les nombres de lignes (mappé)
	 * @throws DaoException
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
		//Récupération du mois et de l'année contenus dans le calendrier
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        if (allMonth) {
			//Si tous les mois ont été demandées, les requêtes à envoyer sont celles sur l'année
            selectOptionConsumption = SQL_SELECT_CONSUMPTION_BY_YEAR;
            selectOptionLineNumber = SQL_SELECT_LINE_NUMBER_BY_YEAR;
            selectValueSearched = year;
        } else {
			//Sinon, les requêtes à envoyer sont celles sur le mois
            selectOptionConsumption = SQL_SELECT_CONSUMPTION_BY_MONTH;
            selectOptionLineNumber = SQL_SELECT_LINE_NUMBER_BY_MONTH;
            selectValueSearched = month;
        }

        try {
            try {
                //Récupération d'une connexion depuis la Factory
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
			//On prépare les requêtes SQL en les construisant avec la méthode "initPreparedRequest"
            if (allMonth) {
                preparedStatementConsumption = initPreparedRequest(connexion, selectOptionConsumption, false, refContract, selectValueSearched, selectValueSearched + 1);
                preparedStatementLineCount = initPreparedRequest(connexion, selectOptionLineNumber, false, refContract, selectValueSearched, selectValueSearched + 1);
            } else {
                preparedStatementConsumption = initPreparedRequest(connexion, selectOptionConsumption, false, refContract, selectValueSearched, year);
                preparedStatementLineCount = initPreparedRequest(connexion, selectOptionLineNumber, false, refContract, selectValueSearched, year);
            }
            preparedStatementType = initPreparedRequest(connexion, SQL_SELECT_TYPE, false, refContract);
			//On exécute les requêtes en récupérant les ResultSet
            resultSetType = preparedStatementType.executeQuery();
            resultSetConsumption = preparedStatementConsumption.executeQuery();
            resultSetLineCount = preparedStatementLineCount.executeQuery();
			
			//Parcours des lignes de données des éventuels ResulSet retournés
			
            if (!allMonth) {
				//Si un seul mois à été demandé
				//Création de 3 booléens de vérification pour savoir si chaque ResultSet a bien été parcouru
                boolean consumption = false;
                boolean lineCount = false;
                boolean type = false;
				//Instanciation d'un Bean de Report qui contiendra les données des ResultSet
                report = new Report();

                while (resultSetConsumption.next()) {
					/*
					 * Tant que le ResultSet des consommations contient une ligne, on effectue le mapping de cette ligne
					 * et on passe le booléen de vérification à true
					 */
                    report = mapConsumption(resultSetConsumption, report);
                    consumption = true;
                }
                while (resultSetLineCount.next()) {
					/*
					 * Tant que le ResultSet du nombre de lignes contient une ligne, on effectue le mapping de cette ligne
					 * et on passe le booléen de vérification à true
					 */
                    report = mapLineCount(resultSetLineCount, report);
                    lineCount = true;
                }
				
				//On insère les informations générales du Report dans le Bean
                report = handleInformations(refContract, month, year, false, report);
				
                if (resultSetType.next()) {
					/*
					 * Si le ResultSet du type contient une ligne, on effectue le mapping de cette ligne
					 * et on passe le booléen de vérification à true
					 */
                    report.setType(resultSetType.getString("DESCRIPTION"));
                    type = true;
                }
                if (consumption && lineCount && type) {
					//Si tous les ResultSet ont bien été parcourus, on insère le Bean de Report dans la Map des Reports
                    reportMap.put(month, report);
                }
            } else {
				/*
				 * Si tous les mois ont été demandés, on crée un format de date permettant de récupérer le mois d'une date,
				 * deux entiers pour le mois correspondant à la ligne du ResultSet parcourue,
				 * un booléen pour savoir quand on doit changer de Bean à remplir
				 * et une chaîne de caractères pour avoir le type du contrat
				 */
                DateFormat dateFormat = new SimpleDateFormat("MM");
                int rsConsumptionMonth = 0;
                int rsLineCountMonth = 0;
                boolean dateChange = true;
                String type = null;
				
				//Récupération du type du contrat
                if (resultSetType.next()) {
                    type = resultSetType.getString("DESCRIPTION");
                }
				
                while (resultSetConsumption.next()) {
					//Tant que le ResultSet des consommations contient une ligne, on effectue le mapping de cette ligne et on passe le booléen de vérification à true
                    if (dateChange) {
						//Si le mois n'est plus le même, on instancie une nouveau Bean de Report et on réattribue la variable du mois parcouru
                        report = new Report();
                        rsConsumptionMonth = Integer.parseInt(dateFormat.format(resultSetConsumption.getDate("DATE_REPORTS")));
                        dateChange = false;
                    }

                    if (Integer.parseInt(dateFormat.format(resultSetConsumption.getDate("DATE_REPORTS"))) == rsConsumptionMonth) {
						/*
						 * Si le mois de la ligne du ResultSet parcourue est égal à la variable du mois, on effectue le mapping de la ligne
						 * et on insère le Bean dans la Map des Reports
						 */ 
						report = mapConsumption(resultSetConsumption, report);
                        reportMap.put(rsConsumptionMonth, report);
                    } else {
						//Si le mois de la ligne du ResultSet parcourue n'est pas égal à la variable du mois
                        if (resultSetConsumption.previous()) {
							//On revient une ligne en arrière dans le ResultSet et on passe le booléen de changement de Bean à true
                            dateChange = true;
                        }
                    }
                }
				
				//Réinitialisation des variables dateChange et report
                dateChange = true;
                report = null;

                while (resultSetLineCount.next()) {
                    //Tant que le ResultSet du nombre de lignes contient une ligne
                    if (dateChange) {
						//Si le mois n'est plus le même, on réattribue la variable du mois parcouru
                        rsLineCountMonth = Integer.parseInt(dateFormat.format(resultSetLineCount.getDate("DATE_CREATION")));
                        dateChange = false;
                        if (reportMap.containsKey(rsLineCountMonth)) {
							//Si la Map de Report contient un Bean de Report pour le mois de la ligne parcourue, on récupère ce Bean
                            report = reportMap.get(rsLineCountMonth);
                            if (rsConsumptionMonth >= 4) {
								//Si le mois parcouru est dans la première année de l'année fiscale demandée
                                report = handleInformations(refContract, rsConsumptionMonth, year, false, report);
                            } else {
								//Si le mois parcouru est dans la dernière année de l'année fiscale demandée
                                report = handleInformations(refContract, rsConsumptionMonth, year + 1, false, report);
                            }
                            report.setType(type);
                        }
                    }

                    if (Integer.parseInt(dateFormat.format(resultSetLineCount.getDate("DATE_CREATION"))) == rsLineCountMonth) {
						/*
						 * Si le mois de la ligne du ResultSet parcourue est égal à la variable du mois, on effectue le mapping de la ligne
						 * et on insère le Bean dans la Map des Reports
						 */ 
                        report = mapLineCount(resultSetLineCount, report);
                        reportMap.put(rsLineCountMonth, report);
                    } else {
						//Si le mois de la ligne du ResultSet parcourue n'est pas égal à la variable du mois
                        if (resultSetLineCount.previous()) {
							//On revient une ligne en arrière dans le ResultSet et on passe le booléen de changement de Bean à true
                            dateChange = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
			//On ferme les ResultSet, les PreparedStatement et les connexions
            silentClosures(resultSetConsumption, preparedStatementConsumption, connexion);
            silentClosures(resultSetLineCount, preparedStatementLineCount, connexion);
            silentClosures(resultSetType, preparedStatementType, connexion);
        }

        return reportMap;
    }

    public Report handleInformations(String refContract, int month, int year, Boolean editable, Report report) {
		
		//On encapsule les informations générale dans le Bean "report"
		
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

    public Map<Integer, String> siteList(Map<Integer, String> siteList) throws DaoException {

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
			
			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest" et on récupère le ResultSet en l'exécutant
            preparedStatement = initPreparedRequest(connexion, SQL_SELECT_SITES, false);
            resultSet = preparedStatement.executeQuery();
			
            //Parcours des lignes de données de l'éventuel ResulSet retourné
			
            int i = 0;
            while (resultSet.next()) {
				//Tant que le ResultSet contient une ligne, on insère le nom du contrat dans la Map des contrats
                siteList.put(i, resultSet.getString("CONTRACT_NAME"));
                i++;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
			//On ferme le ResultSet, le PreparedStatement et la connexion
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
		
		//Initialisation d'entiers pour calculer le coûts totaux
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
		
		//Calcul du coût total selon le type de service
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
                //Récupération d'une connexion depuis la Factory
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
			//On prépare les requêtes SQL en les construisant avec la méthode "initPreparedRequest" puis on les exécute
            preparedStatementFixConsumption = initPreparedRequest(connexion, SQL_INSERT_CONSUMPTION, false, serviceType, "Fix", fixLocalCall, fixLocalData, fixInternationalCall, fixInternationalData, fixTotal, refContract, date);
            preparedStatementFixConsumption.executeUpdate();
            preparedStatementVarConsumption = initPreparedRequest(connexion, SQL_INSERT_CONSUMPTION, false, serviceType, "Var", varLocalCall, varLocalData, varInternationalCall, varInternationalData, varTotal, refContract, date);
            preparedStatementVarConsumption.executeUpdate();
            preparedStatementLineCount = initPreparedRequest(connexion, SQL_INSERT_LINE_NUMBER, false, serviceType, lineCount, refContract, date);
            preparedStatementLineCount.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
			//Fermetures des PreparedStatements, des ResultSets et des connexions
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
		
		//Initialisation d'entiers pour calculer le coûts totaux
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
		
		//Calcul du coût total selon le type de service
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
                //Récupération d'une connexion depuis la Factory
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
			//On prépare les requêtes SQL en les construisant avec la méthode "initPreparedRequest" puis on les exécute
            preparedStatementFixConsumption = initPreparedRequest(connexion, SQL_UPDATE_CONSUMPTION, false, fixLocalCall, fixLocalData, fixInternationalCall, fixInternationalData, fixTotal, serviceType, "Fix", refContract, month, year);
            resultSetFixConsumption = preparedStatementFixConsumption.executeQuery();
            preparedStatementVarConsumption = initPreparedRequest(connexion, SQL_UPDATE_CONSUMPTION, false, varLocalCall, varLocalData, varInternationalCall, varInternationalData, varTotal, serviceType, "Var", refContract, month, year);
            resultSetVarConsumption = preparedStatementVarConsumption.executeQuery();
            preparedStatementLineCount = initPreparedRequest(connexion, SQL_UPDATE_LINE_NUMBER, false, lineCount, serviceType, refContract, month, year);
            resultSetLineCount = preparedStatementLineCount.executeQuery();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
			//Fermetures des PreparedStatements, des ResultSets et des connexions
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
                //Récupération d'une connexion depuis la Factory
                connexion = daoFactory.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(ReportDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
			//On prépare la requête SQL en la construisant avec la méthode "initPreparedRequest" puis on l'exécute
            preparedStatementType = initPreparedRequest(connexion, SQL_SELECT_TYPE, false, refContract);
            resultSetType = preparedStatementType.executeQuery();
            
			//Parcours de la ligne de données de l'éventuel ResulSet retourné
             
            if (resultSetType.next()) {
                type = resultSetType.getString("DESCRIPTION");
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
            silentClosures(resultSetType, preparedStatementType, connexion);
        }

        return type;
    }
}
