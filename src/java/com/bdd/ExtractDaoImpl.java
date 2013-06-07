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
	 * @param extractTab Bean où encapasuler les paramètres récupérés du
	 * ResultSet
	 * @return Bean d'extract complet (mappé)
	 * @throws DaoException
	 */
	private static ExtractTab mapExtractTabMonth(ResultSet resultSet, ExtractTab extractTab) throws SQLException {

		//Récupération du status (0 pour inrempli, 1 pour rempli en parti et 2 pour complet)
		int status = extractTab.getExtractStatus();

		if (status == 0) {
			//Mapping final des informations générales et premier mapping des quantités et des coûts totaux
			extractTab.setContractName(resultSet.getString("REF_UNITCONTRACT"));
			extractTab.setCountry(resultSet.getString("REF_COUNTRY_CODE"));
			extractTab.setQuantity(resultSet.getInt("LINE_NUMBER"));
			extractTab.setTotalCost(resultSet.getInt("TOTAL"));
			extractTab.setType(resultSet.getString("CATEGORIE"));
			extractTab.setExtractStatus(1);
		} else if (status == 1) {
			//Mapping des quantités totales et des coûts totaux par calculs successifs
			extractTab.setQuantity(extractTab.getQuantity() + resultSet.getInt("LINE_NUMBER"));
			extractTab.setTotalCost(extractTab.getTotalCost() + resultSet.getInt("TOTAL"));
		} else {
		}

		//On retourne un Bean mappé en partie ou complètement si la boucle de mapping est terminée
		return extractTab;
	}
	
	private static final String SQL_SELECT_COUNTRY_ONE_MONTH = "SELECT UC.REF_COUNTRY_CODE, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.REF_COUNTRY_CODE = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(MONTH FROM C.DATE_REPORTS) = ? AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, UC.CONTRACT_NAME, C.DATE_REPORTS";
	private static final String SQL_SELECT_COUNTRY_ALL_MONTH = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.REF_COUNTRY_CODE = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, UC.CONTRACT_NAME, C.DATE_REPORTS";

	public Map<Integer, ExtractTab> extractCountryMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ExtractTab extract = null;
		//Récupération du mois et de l'année
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		try {
			try {
				//Récupération d'une connexion depuis la Factory
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (allMonth) {
				//Si tous les mois choisis, on prépare une requête SQL qui extrait les données d'une année entière
				preparedStatement = initPreparedRequest(connexion, SQL_SELECT_COUNTRY_ALL_MONTH, false, nameExtract, year);

			} else {
				//Sinon, on prépare une requête qui extrait les données d'un seul mois
				preparedStatement = initPreparedRequest(connexion, SQL_SELECT_COUNTRY_ONE_MONTH, false, nameExtract, month, year);
			}
			//On récupère le ResultSet de la requête construite ci-dessus après l'avoir exécutée
			resultSet = preparedStatement.executeQuery();

			if (!allMonth) {
				/*
				 * Si un seul mois, on crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
				 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
				 */
				int nbTimes = 0;
				int i = 0;
				//Instanciation d'un nouveau Bean d'extract à l'aide d'un constructeur encapsulant le mois et l'année correspondant à l'extract
				extract = new ExtractTab(month, year);

				while (resultSet.next()) {
					//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
					extract = mapExtractTabMonth(resultSet, extract);
					nbTimes++;
					if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
						/*
						 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
						 * donc le Bean sera complet :
						 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
						 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
						 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
						 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
						 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
						 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i, on instancie un nouveau Bean et on réinitialise nbTimes
						 */
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
				/*
				 * Si un tous les mois, on crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
				 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
				 */
				int nbTimes = 0;
				int i = 0;

				while (resultSet.next()) {
					//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
					if (nbTimes == 0) {
						/*
						 * Si une nouvelle boucle est lancée, on récupère le mois de la ligne actuelle, 
						 * puis on instancie un Bean à l'aide du constructeur
						 */
						month = resultSet.getInt("MONTH");
						extract = new ExtractTab(month, year);
					}
					extract = mapExtractTabMonth(resultSet, extract);
					nbTimes++;
					if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
						/*
						 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
						 * donc le Bean sera complet :
						 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
						 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
						 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
						 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
						 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
						 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i et on réinitialise nbTimes
						 */
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
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		//On retourne la Map d'extract complète
		return extractMap;
	}
	
	private static final String SQL_SELECT_CONTRACT_ONE_MONTH = "SELECT UC.REF_COUNTRY_CODE, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.CONTRACT_NAME = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(MONTH FROM C.DATE_REPORTS) = ? AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, C.DATE_REPORTS";
	private static final String SQL_SELECT_CONTRACT_ALL_MONTH = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.CONTRACT_NAME = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, C.DATE_REPORTS";

	public Map<Integer, ExtractTab> extractContractMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ExtractTab extract = null;
		//Récupération du mois et de l'année
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		try {
			try {
				//Récupération d'une connexion depuis la Factory
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (allMonth) {
				//Si tous les mois choisis, on prépare une requête SQL qui extrait les données d'une année entière
				preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_ALL_MONTH, false, nameExtract, year);
			} else {
				//Sinon, on prépare une requête qui extrait les données d'un seul mois
				preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_ONE_MONTH, false, nameExtract, month, year);
			}
			//On récupère le ResultSet de la requête construite ci-dessus après l'avoir exécutée
			resultSet = preparedStatement.executeQuery();

			if (!allMonth) {
				/*
				 * Si un seul mois, on crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
				 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
				 */
				int nbTimes = 0;
				int i = 0;
				//Instanciation d'un nouveau Bean d'extract à l'aide d'un constructeur encapsulant le mois et l'année correspondant à l'extract
				extract = new ExtractTab(month, year);

				while (resultSet.next()) {
					//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
					extract = mapExtractTabMonth(resultSet, extract);
					nbTimes++;
					if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
						/*
						 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
						 * donc le Bean sera complet :
						 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
						 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
						 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
						 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
						 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
						 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i, on instancie un nouveau Bean et on réinitialise nbTimes
						 */
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
				/*
				 * Si tous les mois, on crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
				 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
				 */
				int nbTimes = 0;
				int i = 0;

				while (resultSet.next()) {
					//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
					if (nbTimes == 0) {
						/*
						 * Si une nouvelle boucle est lancée, on récupère le mois de la ligne actuelle, 
						 * puis on instancie un Bean à l'aide du constructeur
						 */
						month = resultSet.getInt("MONTH");
						extract = new ExtractTab(month, year);
					}
					extract = mapExtractTabMonth(resultSet, extract);
					nbTimes++;
					if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
						/*
						 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
						 * donc le Bean sera complet :
						 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
						 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
						 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
						 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
						 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
						 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i et on réinitialise nbTimes
						 */
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
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		//On retourne la Map d'extract complète
		return extractMap;
	}
	
	private static final String SQL_SELECT_GLOBAL_ONE_MONTH = "SELECT UC.REF_COUNTRY_CODE, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(MONTH FROM C.DATE_REPORTS) = ? AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, UC.REF_COUNTRY_CODE, UC.CONTRACT_NAME, C.DATE_REPORTS";
	private static final String SQL_SELECT_GLOBAL_ALL_MONTH = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND EXTRACT(YEAR FROM C.DATE_REPORTS) = ? ORDER BY CATEGORIE, UC.REF_COUNTRY_CODE, UC.CONTRACT_NAME, C.DATE_REPORTS";
	
	public Map<Integer, ExtractTab> extractGlobalMonth(Map<Integer, ExtractTab> extractMap, GregorianCalendar calendar, Boolean allMonth) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ExtractTab extract = null;
		//Récupération du mois et de l'année
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		try {
			try {
				//Récupération d'une connexion depuis la Factory
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (allMonth) {
				//Si tous les mois choisis, on prépare une requête SQL qui extrait les données d'une année entière
				preparedStatement = initPreparedRequest(connexion, SQL_SELECT_GLOBAL_ALL_MONTH, false, year);
			} else {
				//Sinon, on prépare une requête qui extrait les données d'un seul mois
				preparedStatement = initPreparedRequest(connexion, SQL_SELECT_GLOBAL_ONE_MONTH, false, month, year);
			}
			//On récupère le ResultSet de la requête construite ci-dessus après l'avoir exécutée
			resultSet = preparedStatement.executeQuery();

			if (!allMonth) {
				/*
				 * Si un seul mois, on crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
				 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
				 */
				int nbTimes = 0;
				int i = 0;
				//Instanciation d'un nouveau Bean d'extract à l'aide d'un constructeur encapsulant le mois et l'année correspondant à l'extract
				extract = new ExtractTab(month, year);

				while (resultSet.next()) {
					//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
					extract = mapExtractTabMonth(resultSet, extract);
					nbTimes++;
					if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
						/*
						 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
						 * donc le Bean sera complet :
						 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
						 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
						 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
						 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
						 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
						 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i, on instancie un nouveau Bean et on réinitialise nbTimes
						 */
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
				/*
				 * Si tous les mois, on crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
				 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
				 */
				int nbTimes = 0;
				int i = 0;

				while (resultSet.next()) {
					//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
					if (nbTimes == 0) {
						/*
						 * Si une nouvelle boucle est lancée, on récupère le mois de la ligne actuelle, 
						 * puis on instancie un Bean à l'aide du constructeur
						 */
						month = resultSet.getInt("MONTH");
						extract = new ExtractTab(month, year);
					}
					extract = mapExtractTabMonth(resultSet, extract);
					nbTimes++;
					if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
						/*
						 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
						 * donc le Bean sera complet :
						 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
						 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
						 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
						 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
						 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
						 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i et on réinitialise nbTimes
						 */
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
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		//On retourne la Map d'extract complète
		return extractMap;
	}
	
	private static final String SQL_SELECT_COUNTRY_FISCAL_YEAR = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, EXTRACT(YEAR FROM C.DATE_REPORTS) AS YEAR, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.REF_COUNTRY_CODE = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND ((EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) >= 4) OR (EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) <= 3)) ORDER BY CATEGORIE, UC.CONTRACT_NAME, C.DATE_REPORTS";

	public Map<Integer, ExtractTab> extractCountryFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ExtractTab extract = null;
		//Instanciation d'une variable de mois
		int month = 0;

		try {
			try {
				//Récupération d'une connexion depuis la Factory
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare une requête SQL qui extrait les données d'une année fiscale entière
			preparedStatement = initPreparedRequest(connexion, SQL_SELECT_COUNTRY_FISCAL_YEAR, false, nameExtract, year, year + 1);

			//On récupère le ResultSet de la requête construite ci-dessus après l'avoir exécutée
			resultSet = preparedStatement.executeQuery();

			/*
			 * On crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
			 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
			 */
			int nbTimes = 0;
			int i = 0;

			while (resultSet.next()) {
				//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
				if (nbTimes == 0) {
					/*
					 * Si une nouvelle boucle est lancée, on récupère le mois de la ligne actuelle, 
					 * puis on instancie un Bean à l'aide du constructeur
					 */
					month = resultSet.getInt("MONTH");
					year = resultSet.getInt("YEAR");
					extract = new ExtractTab(month, year);
				}
				extract = mapExtractTabMonth(resultSet, extract);
				nbTimes++;
				if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
					/*
					 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
					 * donc le Bean sera complet :
					 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
					 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
					 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
					 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
					 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
					 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i et on réinitialise nbTimes
					 */
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
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		//On retourne la Map d'extract complète
		return extractMap;

	}
	
	private static final String SQL_SELECT_CONTRACT_FISCAL_YEAR = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, EXTRACT(YEAR FROM C.DATE_REPORTS) AS YEAR, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND UC.CONTRACT_NAME = ? AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND ((EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) >= 4) OR (EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) <= 3)) ORDER BY CATEGORIE, C.DATE_REPORTS";

	public Map<Integer, ExtractTab> extractContractFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ExtractTab extract = null;
		//Instanciation d'une variable de mois
		int month = 0;

		try {
			try {
				//Récupération d'une connexion depuis la Factory
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare une requête SQL qui extrait les données d'une année fiscale entière
			preparedStatement = initPreparedRequest(connexion, SQL_SELECT_CONTRACT_FISCAL_YEAR, false, nameExtract, year, year + 1);

			//On récupère le ResultSet de la requête construite ci-dessus après l'avoir exécutée
			resultSet = preparedStatement.executeQuery();

			/*
			 * On crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
			 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
			 */
			int nbTimes = 0;
			int i = 0;

			while (resultSet.next()) {
				//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
				if (nbTimes == 0) {
					/*
					 * Si une nouvelle boucle est lancée, on récupère le mois et l'année de la ligne actuelle, 
					 * puis on instancie un Bean à l'aide du constructeur
					 */
					month = resultSet.getInt("MONTH");
					year = resultSet.getInt("YEAR");
					extract = new ExtractTab(month, year);
				}
				extract = mapExtractTabMonth(resultSet, extract);
				nbTimes++;
				if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
					/*
					 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
					 * donc le Bean sera complet :
					 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
					 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
					 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
					 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
					 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
					 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i et on réinitialise nbTimes
					 */
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
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		//On retourne la Map d'extract complète
		return extractMap;
	}
	
	private static final String SQL_SELECT_GLOBAL_FISCAL_YEAR = "SELECT UC.REF_COUNTRY_CODE, EXTRACT(MONTH FROM C.DATE_REPORTS) AS MONTH, EXTRACT(YEAR FROM C.DATE_REPORTS) AS YEAR, C.REF_UNITCONTRACT, L.LINE_NUMBER, C.TOTAL, CASE WHEN C.SERVICE_TYPE = 'Fix' THEN 'Fix' ELSE 'Mobile' END AS CATEGORIE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT UC, WEBIDMINT.TELEPHONY_CONSUMPTION C, WEBIDMINT.TELEPHONY_LINECOUNT L WHERE UC.CONTRACT_NAME = L.REF_UNITREPORTS AND L.REF_UNITREPORTS = C.REF_UNITCONTRACT AND C.DATE_REPORTS = L.DATE_CREATION AND C.SERVICE_TYPE = L.SERVICE_TYPE AND ((EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) >= 4) OR (EXTRACT(YEAR FROM C.DATE_REPORTS) = ? AND EXTRACT(MONTH FROM C.DATE_REPORTS) <= 3)) ORDER BY CATEGORIE, UC.REF_COUNTRY_CODE, UC.CONTRACT_NAME, C.DATE_REPORTS";

	public Map<Integer, ExtractTab> extractGlobalFiscalYear(Map<Integer, ExtractTab> extractMap, int year) {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ExtractTab extract = null;
		//Instanciation d'une variable de mois
		int month = 0;

		try {
			try {
				//Récupération d'une connexion depuis la Factory
				connexion = daoFactory.getConnection();
			} catch (SQLException ex) {
				Logger.getLogger(ExtractDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
			}

			//On prépare une requête SQL qui extrait les données d'une année fiscale entière
			preparedStatement = initPreparedRequest(connexion, SQL_SELECT_GLOBAL_FISCAL_YEAR, false, year, year + 1);

			//On récupère le ResultSet de la requête construite ci-dessus après l'avoir exécutée
			resultSet = preparedStatement.executeQuery();

			/*
			 * On crée un entier (nbTimes) pour compter le nombre de fois que la boucle s'effectue
			 * et un autre (i) qui servira d'index pour insérer un Bean d'extract complet dans la Map associée
			 */
			int nbTimes = 0;
			int i = 0;

			while (resultSet.next()) {
				//Tant qu'il y a au moins une ligne dans le ResultSet, on lance un mapping puis on incrément nbTimes
				if (nbTimes == 0) {
					/*
					 * Si une nouvelle boucle est lancée, on récupère le mois et l'année de la ligne actuelle, 
					 * puis on instancie un Bean à l'aide du constructeur
					 */
					month = resultSet.getInt("MONTH");
					year = resultSet.getInt("YEAR");
					extract = new ExtractTab(month, year);
				}
				extract = mapExtractTabMonth(resultSet, extract);
				nbTimes++;
				if ("Fix".equals(extract.getType()) && nbTimes == 2 || "Mobile".equals(extract.getType()) && nbTimes == 6) {
					/*
					 * On sait qu'un type de service possède à chaque fois 2 lignes dans le ResultSet (coûts fixes et variables),
					 * donc le Bean sera complet :
					 * si le Bean est de type "Fix" et qu'il a été mappé 2 fois (la partie "Fix" ne comportant qu'un type de service)
					 * ou si le Bean est de type "Mobile" et qu'il a été mappé 6 fois (la partie "Fix" comportant 3 types de service)
					 * A chaque mapping, la quantité de la ligne du ResultSet est ajoutée.
					 * Comme chaque type de service possède deux lignes dans le ResultSet et donc deux fois la même quantité, on la divise par 2
					 * On intègre l'ARPU (Coût total / Quantité totale) et on passe le statut du Bean à 2 (complet)
					 * Enfin, on l'ajoute dans la Map à l'index i, on incrémente i et on réinitialise nbTimes
					 */
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
			//Fermetures du PreparedStatement, du ResultSet et de la connexion
			silentClosures(resultSet, preparedStatement, connexion);
		}

		//On retourne la Map d'extract complète
		return extractMap;
	}
}
