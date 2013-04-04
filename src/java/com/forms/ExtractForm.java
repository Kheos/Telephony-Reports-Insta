/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import com.bdd.ExtractDao;
import com.beans.ExtractTab;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author 250665
 */
public class ExtractForm {

	private ExtractDao extractDao;
	private String country, contractName, type;
	private int firstMonth, lastMonth, month, nbMonth, year, fiscalYear, quantity, quantityComplete, numLine, nbEmptyLine;
	private float totalCost, totalCostComplete, arpu;
	private Label countryCell, contractNameCell, periodCell;
	private jxl.write.Number quantityCell, totalCostCell, arpuCell;
	private WritableCellFormat cellFormat, totalCellFormat;
	private WritableFont totalFont;

	/**
	 *
	 * @param extractDao
	 * @throws WriteException
	 */
	@SuppressWarnings("empty-statement")
	public ExtractForm(ExtractDao extractDao) throws WriteException {
		this.extractDao = extractDao;

		//On initialise des variables utilisées dans différentes méthodes

		this.country = null;
		this.contractName = null;
		this.type = null;
		this.firstMonth = 0;
		this.lastMonth = 0;
		this.month = 0;
		this.nbMonth = 0;
		this.year = 0;
		this.fiscalYear = 0;
		this.quantity = 0;
		this.quantityComplete = 0;
		this.totalCost = 0;
		this.totalCostComplete = 0;
		this.arpu = 0;
		this.numLine = 1;
		this.nbEmptyLine = 0;

		this.countryCell = null;
		this.contractNameCell = null;
		this.periodCell = null;
		this.quantityCell = null;
		this.totalCostCell = null;
		this.arpuCell = null;

		this.cellFormat = new WritableCellFormat();
		this.cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		this.totalFont = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, true);
		this.totalCellFormat = new WritableCellFormat(totalFont);
		totalCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
	}

	/**
	 *
	 * @param typeExtract Type d'extract ("country" ou "contract")
	 * @param nameExtract Nom du contrat ou du pays selon le type d'extract
	 * @param month Mois de l'extract demandé
	 * @param year Année de l'extract demandé
	 * @return Map contenant le ou les mois de l'extract demandé
	 */
	public Map<Integer, ExtractTab> extractMonth(String typeExtract, String nameExtract, int month, int year) {

		/*
		 * Instanciation d'une Map qui contiendra toutes les lignes de l'Extract sous forme de Bean "ExtractTab"
		 * Instanciation d'un booléen permettant de savoir si tous les mois ont été demandés
		 * Instanciation d'un calendrier auquel on attribue l'année demandée dans le filtre
		 */
		Map<Integer, ExtractTab> extractMap = new HashMap<Integer, ExtractTab>();
		java.lang.Boolean allMonth = false;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(GregorianCalendar.YEAR, year);
		if (month != 0) {
			//Si la variable mois du filtre est différente de "All" (soit, tous les mois), on attribue au calendrier le mois demandé
			calendar.set(GregorianCalendar.MONTH, month - 1);
		} else {
			//Si tous les mois ont été demandées, on passe le booléen à true
			allMonth = true;
		}

		if ("country".equals(typeExtract)) {
			//Si le type d'Extract est par pays, on lance la méthode d'extract par pays avec les paramètres du filtre
			extractMap = extractDao.extractCountryMonth(extractMap, nameExtract, calendar, allMonth);
		} else {
			//Sinon, le type d'Extract est par contrat, on lance la méthode d'extract par contrat avec les paramètres du filtre
			extractMap = extractDao.extractContractMonth(extractMap, nameExtract, calendar, allMonth);
		}

		//On retourne la Map remplie, contenant maintenant les lignes d'extract
		return extractMap;
	}

	/**
	 *
	 * @param typeExtract Type d'extract ("country" ou "contract")
	 * @param nameExtract Nom du contrat ou du pays selon le type d'extract
	 * @param year Première année de l'année fiscale demandée à extraire
	 * @return Map contenant les mois de l'année fiscale demandée
	 */
	public Map<Integer, ExtractTab> extractFiscalYear(String typeExtract, String nameExtract, int year) {

		//Instanciation d'une Map qui contiendra toutes les lignes de l'Extract sous forme de Bean "ExtractTab"
		Map<Integer, ExtractTab> extractMap = new HashMap<Integer, ExtractTab>();

		if ("country".equals(typeExtract)) {
			//Si le type d'Extract est par pays, on lance la méthode d'extract par pays avec les paramètres du filtre
			extractMap = extractDao.extractCountryFiscalYear(extractMap, nameExtract, year);
		} else {
			//Sinon, le type d'Extract est par contrat, on lance la méthode d'extract par contrat avec les paramètres du filtre
			extractMap = extractDao.extractContractFiscalYear(extractMap, nameExtract, year);
		}

		//On retourne la Map remplie, contenant maintenant les lignes d'extract
		return extractMap;
	}

	/**
	 *
	 * @param workbook Workbook en construction
	 * @param sheetIndex Index de la feuille sur laquelle écrire
	 * @param extract Bean de la ligne d'extract à ajouter au Workbook
	 * @return Workbook en construction avec la ligne ajoutée
	 * @throws WriteException
	 */
	public WritableWorkbook addSimpleRow(WritableWorkbook workbook, int sheetIndex, ExtractTab extract) throws WriteException {

		//Récupération des données contenues dans le Bean et préparation de calculs pour l'ajout d'une ligne de total
		country = extract.getCountry();
		contractName = extract.getContractName();
		month = extract.getMonth();
		year = extract.getYear();
		quantity = extract.getQuantity();
		quantityComplete = quantityComplete + quantity;
		totalCost = extract.getTotalCost();
		totalCostComplete = totalCostComplete + totalCost;
		arpu = extract.getArpu();

		//Instanciation des cellules à ajouter au Workbook avec les données récupérées ci-dessus
		countryCell = new Label(0, numLine, country, cellFormat);
		contractNameCell = new Label(1, numLine, contractName, cellFormat);
		periodCell = new Label(2, numLine, Integer.toString(month) + "/" + Integer.toString(year), cellFormat);
		quantityCell = new jxl.write.Number(3, numLine, quantity, cellFormat);
		totalCostCell = new jxl.write.Number(4, numLine, totalCost, cellFormat);
		arpuCell = new jxl.write.Number(5, numLine, arpu, cellFormat);

		//Ajout des cellules dans le Workbook sur la feuille associée au type de données (Fixes ou Mobiles)
		workbook.getSheet(sheetIndex).addCell(countryCell);
		workbook.getSheet(sheetIndex).addCell(contractNameCell);
		workbook.getSheet(sheetIndex).addCell(periodCell);
		workbook.getSheet(sheetIndex).addCell(quantityCell);
		workbook.getSheet(sheetIndex).addCell(totalCostCell);
		workbook.getSheet(sheetIndex).addCell(arpuCell);

		//On retourne le nouveau Workbook où a été ajouté une ligne simple
		return workbook;
	}

	/**
	 *
	 * @param workbook Workbook en construction
	 * @param sheetIndex Index de la feuille sur laquelle écrire
	 * @return Workbook en construction avec la ligne de total ajoutée
	 * @throws WriteException
	 */
	public WritableWorkbook addTotalRow(WritableWorkbook workbook, int sheetIndex) throws WriteException {

		//Récupération des données contenues dans les variables private de l'objet et calculs et/ou manipulations pour l'ajout de la ligne de total
		countryCell = new Label(0, numLine, country, totalCellFormat);
		contractNameCell = new Label(1, numLine, contractName, totalCellFormat);
		if (fiscalYear != 0) {
			//Si c'est un extract sur une année fiscale
			periodCell = new Label(2, numLine, Integer.toString(firstMonth) + "/" + Integer.toString(fiscalYear) + " - " + Integer.toString(lastMonth) + "/" + Integer.toString(fiscalYear + 1) + " (" + Integer.toString(12 - nbEmptyLine) + " months)", totalCellFormat);
		} else {
			//Sinon
			periodCell = new Label(2, numLine, Integer.toString(firstMonth) + "/" + Integer.toString(year) + " - " + Integer.toString(lastMonth) + "/" + Integer.toString(year) + " (" + Integer.toString(nbMonth - nbEmptyLine) + " months)", totalCellFormat);
		}
		arpu = totalCostComplete / quantityComplete;
		quantityCell = new jxl.write.Number(3, numLine, quantityComplete, totalCellFormat);
		totalCostCell = new jxl.write.Number(4, numLine, totalCostComplete, totalCellFormat);
		arpuCell = new jxl.write.Number(5, numLine, arpu, totalCellFormat);

		//Ajout des cellules dans le Workbook sur la feuille associée au type de données (Fixes ou Mobiles)
		workbook.getSheet(sheetIndex).addCell(countryCell);
		workbook.getSheet(sheetIndex).addCell(contractNameCell);
		workbook.getSheet(sheetIndex).addCell(periodCell);
		workbook.getSheet(sheetIndex).addCell(quantityCell);
		workbook.getSheet(sheetIndex).addCell(totalCostCell);
		workbook.getSheet(sheetIndex).addCell(arpuCell);

		//On retourne le nouveau Workbook où a été ajouté une ligne totale
		return workbook;
	}

	/**
	 *
	 * @param extractMap Map contenant les Beans d'extract à insérer dans le
	 * Workbook
	 * @param workbook Workbook en construction
	 * @param firstMonth Premier mois de l'extract à réaliser
	 * @param lastMonth Dernier mois de l'extract à réaliser
	 * @param fiscalYear Première année de l'année fiscale demandée (si c'est un
	 * extract par année fiscale, fiscalYear vaut 0)
	 * @return Workbook complété à envoyer en téléchargement
	 * @throws WriteException
	 * @throws IOException
	 */
	public WritableWorkbook constructExtract(Map<Integer, ExtractTab> extractMap, WritableWorkbook workbook, int firstMonth, int lastMonth, int fiscalYear) throws WriteException, IOException {

		try {

			//Création des feuilles du Workbook (Fix et Mobile)
			WritableSheet fixSheet = workbook.createSheet("Fix Report", 0);
			WritableSheet mobileSheet = workbook.createSheet("Mobile Report", 1);

			//Instanciation d'un format de cellule pour les titres des colonnes et paramétrage de celui-ci
			WritableCellFormat titleFormat = new WritableCellFormat();
			titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
			titleFormat.setBackground(jxl.format.Colour.ORANGE);
			titleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);

			//Configuration de la largeur des colonnes pour la feuille "Fix Report" du Workbook
			fixSheet.setColumnView(0, 15);
			fixSheet.setColumnView(1, 30);
			fixSheet.setColumnView(2, 30);
			fixSheet.setColumnView(3, 25);
			fixSheet.setColumnView(4, 25);
			fixSheet.setColumnView(5, 25);

			//Ajout des titres des colonnes de la feuille "Fix Report" en appliquand le format de cellule "titleFormat"
			fixSheet.addCell(new Label(0, 0, "Country Code", titleFormat));
			fixSheet.addCell(new Label(1, 0, "Contract Name", titleFormat));
			fixSheet.addCell(new Label(2, 0, "Date / Period", titleFormat));
			fixSheet.addCell(new Label(3, 0, "Line Count", titleFormat));
			fixSheet.addCell(new Label(4, 0, "Total Cost", titleFormat));
			fixSheet.addCell(new Label(5, 0, "ARPU", titleFormat));

			//Configuration de la largeur des colonnes pour la feuille "Mobile Report" du Workbook
			mobileSheet.setColumnView(0, 15);
			mobileSheet.setColumnView(1, 30);
			mobileSheet.setColumnView(2, 30);
			mobileSheet.setColumnView(3, 25);
			mobileSheet.setColumnView(4, 25);
			mobileSheet.setColumnView(5, 25);

			//Ajout des titres des colonnes de la feuille "Mobile Report" en appliquand le format de cellule "titleFormat"
			mobileSheet.addCell(new Label(0, 0, "Country Code", titleFormat));
			mobileSheet.addCell(new Label(1, 0, "Contract Name", titleFormat));
			mobileSheet.addCell(new Label(2, 0, "Date / Period", titleFormat));
			mobileSheet.addCell(new Label(3, 0, "Line Count", titleFormat));
			mobileSheet.addCell(new Label(4, 0, "Total Cost", titleFormat));
			mobileSheet.addCell(new Label(5, 0, "ARPU", titleFormat));

			//On initialise le numéro de la feuille sur laquelle on écrit en premier
			int numSheet = 0;

			//On récupère des données du 1er Bean de l'extractMap
			contractName = extractMap.get(0).getContractName();
			type = extractMap.get(0).getType();
			month = extractMap.get(0).getMonth();
			//On attribut à des variables private la valeur de paramètres de la méthode pour les utiliser dans d'autres méthodes
			this.firstMonth = firstMonth;
			this.lastMonth = lastMonth;
			this.fiscalYear = fiscalYear;
			//Initialisation d'un booléen annonçant qu'une année fiscale d'extract a été remplie à false
			boolean fiscalYearCompleted = false;

			if ("Mobile".equals(type)) {
				//Si le type du 1er Bean d'Extract est "Mobile", alors il n'y a aucune donnée dans la partie Fix et on passe sur la feuille "Mobile Report"
				numSheet = 1;
			}

			for (int i = 0; i <= extractMap.size(); i++) {
				//On parcourt toute la Map d'extract en initialisant à chaque boucle un Bean d'extract à la valeur du Bean de l'extractMap placé à l'index i
				System.out.println("i : " + i + " / nbMonth : " + nbMonth + " / month : " + month + " / contractName : " + contractName);
				ExtractTab extract = extractMap.get(i);
				if (i == (extractMap.size())) {
					//Si i est égal à la taille de l'extractMap, la Map est complètement parcourue et il ne reste plus qu'à faire les dernières lignes
					if ((nbMonth - nbEmptyLine) > 1 || (this.fiscalYear != 0 && nbEmptyLine < 11)) {
						/*
						 * Si il y a au moins deux lignes qui ont été ajoutées pour l'extract courant (depuis la dernière ligne de total par exemple), 
						 * on peut ajouter une dernière ligne de total
						 */
						while (nbMonth < lastMonth || (this.fiscalYear != 0 && !fiscalYearCompleted)) {
							//Tant que l'extract courant n'a pas les lignes de tous ces mois, on ajoute des lignes vides
							System.out.println("MOIS MANQUANT");
							if (this.fiscalYear != 0 && nbMonth >= 8) {
								//Si c'est un extract par année fiscale, on passe le booléen disant qu'une année fiscale d'extract a été remplie à true
								fiscalYearCompleted = true;
								firstMonth = 1;
								nbMonth = 0;
							} else {
								//On incrémente le nombre de mois parcourus
								nbMonth++;
							}
							//On incrémente le numéro de la ligne où écrire et le nombre de lignes vides
							numLine++;
							nbEmptyLine++;
						}
						if ((this.fiscalYear != 0 && nbEmptyLine < 11) || (this.fiscalYear == 0 && (nbMonth - nbEmptyLine) > 1)) {
							/*
							 * Si il y a au moins deux lignes qui ont été ajoutées pour l'extract courant (depuis la dernière ligne de total par exemple), 
							 * on peut ajouter une dernière ligne de total
							 */
							workbook = addTotalRow(workbook, numSheet);
						}
					}
				} else if (!contractName.equals(extract.getContractName()) || !type.equals(extract.getType())) {
					System.out.println("TOTAL");
					if ((nbMonth - nbEmptyLine) > 1 || (this.fiscalYear != 0 && nbEmptyLine < 11)) {
						/*
						 * Si il y a au moins deux lignes qui ont été ajoutées pour l'extract courant (depuis la dernière ligne de total par exemple), 
						 * on peut ajouter une dernière ligne de total
						 */
						while (nbMonth < lastMonth || (this.fiscalYear != 0 && !fiscalYearCompleted)) {
							//Tant que l'extract courant n'a pas les lignes de tous ces mois, on ajoute des lignes vides
							System.out.println("MOIS MANQUANT");
							if (this.fiscalYear != 0 && nbMonth >= 8) {
								//Si c'est un extract par année fiscale, on passe le booléen disant qu'une année fiscale d'extract a été remplie à true
								fiscalYearCompleted = true;
								firstMonth = 1;
								nbMonth = 0;
							} else {
								//On incrémente le nombre de mois parcourus
								nbMonth++;
							}
							//On incrémente le numéro de la ligne où écrire et le nombre de lignes vides
							numLine++;
							nbEmptyLine++;
						}
						if ((this.fiscalYear != 0 && nbEmptyLine < 11) || (this.fiscalYear == 0 && (nbMonth - nbEmptyLine) > 1)) {
							/*
							 * Si il y a au moins deux lignes qui ont été ajoutées pour l'extract courant (depuis la dernière ligne de total par exemple), 
							 * on peut ajouter une dernière ligne de total
							 */
							workbook = addTotalRow(workbook, numSheet);
						}
					}

					if (!type.equals(extract.getType())) {
						//Si le type (Fix ou Mobile) n'est plus le même, on change de feuille et on réinitialise le numéro de ligne à 1
						numLine = 1;
						numSheet = 1;
						type = extract.getType();
					} else {
						//Sinon, on incrément le numéro de ligne
						numLine++;
					}
					//On réinitialise les paramètres puisqu'on vient de passer une ligne de total
					fiscalYearCompleted = false;
					firstMonth = this.firstMonth;
					quantityComplete = 0;
					totalCostComplete = 0;
					nbMonth = 0;
					nbEmptyLine = 0;
					if (nbMonth <= 12 && (nbMonth + firstMonth) != (extract.getMonth())) {
						/*
						 * Si le Bean parcouru ne correspond pas au Bean du (nbMonth + firstMonth)ème mois,
						 * On incrémente le numéro de ligne, le nombre de mois insérés dans le Workbook pour l'année extract courante
						 * et le nombre de lignes vides, puis, 
						 * on décrémente i pour rester sur le même Bean (puisque le mois attendu ne correspond pas à ce Bean)
						 */
						System.out.println("MOIS MANQUANT");
						contractName = extract.getContractName();
						numLine++;
						nbMonth++;
						nbEmptyLine++;
						i--;
					} else {
						//Sinon, on ajoute une ligne simple et on incrémente le numéro de ligne et le nombre de mois insérés dans le Workbook
						System.out.println("SIMPLE");
						workbook = addSimpleRow(workbook, numSheet, extract);
						nbMonth++;
						numLine++;
					}
				} else if (this.fiscalYear != 0 && nbMonth >= 8 && !fiscalYearCompleted) {
					System.out.println("ANNEE FISCALE");
					if ((nbMonth + firstMonth) != (extract.getMonth())) {
						/*
						 * Si le Bean parcouru ne correspond pas au Bean du (nbMonth + firstMonth)ème mois,
						 * on incrémente le nombre de lignes vides et 
						 * on décrémente i pour rester sur le même Bean (puisque le mois attendu ne correspond pas à ce Bean)
						 */
						System.out.println("MOIS MANQUANT");
						nbEmptyLine++;
						i--;
					} else {
						//Sinon, on ajoute une ligne simple
						System.out.println("SIMPLE");
						workbook = addSimpleRow(workbook, numSheet, extract);
					}
					/*
					 * On passe le booléen annonçant une année fiscale d'extract complétée à true,
					 * on réinitialise les paramètres de mois et on incrémente le numéro de ligne
					 */
					fiscalYearCompleted = true;
					firstMonth = 1;
					nbMonth = 0;
					numLine++;
				} else if (nbMonth <= 12 && (nbMonth + firstMonth) != (extract.getMonth())) {
					/*
					 * Si le Bean parcouru ne correspond pas au Bean du (nbMonth + firstMonth)ème mois,
					 * On incrémente le numéro de ligne, le nombre de mois insérés dans le Workbook pour l'année extract courante
					 * et le nombre de lignes vides, puis, 
					 * on décrémente i pour rester sur le même Bean (puisque le mois attendu ne correspond pas à ce Bean)
					 */
					System.out.println("MOIS MANQUANT");
					numLine++;
					nbMonth++;
					nbEmptyLine++;
					i--;
				} else {
					//Sinon, on ajoute une ligne simple et on incrémente le numéro de la ligne et le nombre de mois insérés dans le Workbook
					System.out.println("SIMPLE");
					workbook = addSimpleRow(workbook, numSheet, extract);
					nbMonth++;
					numLine++;
				}
			}

		} catch (WriteException ex) {
			Logger.getLogger(ExtractForm.class.getName()).log(Level.SEVERE, null, ex);
		}

		return workbook;

	}
}
