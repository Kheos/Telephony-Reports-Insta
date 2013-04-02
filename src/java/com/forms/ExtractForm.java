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

        Map<Integer, ExtractTab> extractMap = new HashMap<Integer, ExtractTab>();
        java.lang.Boolean allMonth = false;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.YEAR, year);
        if (month != 0) {
            calendar.set(GregorianCalendar.MONTH, month - 1);
        } else {
            allMonth = true;
        }

        if ("country".equals(typeExtract)) {
            extractMap = extractDao.extractCountryMonth(extractMap, nameExtract, calendar, allMonth);
        } else {
            extractMap = extractDao.extractContractMonth(extractMap, nameExtract, calendar, allMonth);
        }

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

        Map<Integer, ExtractTab> extractMap = new HashMap<Integer, ExtractTab>();

        if ("country".equals(typeExtract)) {
            extractMap = extractDao.extractCountryFiscalYear(extractMap, nameExtract, year);
        } else {
            extractMap = extractDao.extractContractFiscalYear(extractMap, nameExtract, year);
        }

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

        country = extract.getCountry();
        contractName = extract.getContractName();
        month = extract.getMonth();
        year = extract.getYear();
        quantity = extract.getQuantity();
        quantityComplete = quantityComplete + quantity;
        totalCost = extract.getTotalCost();
        totalCostComplete = totalCostComplete + totalCost;
        arpu = extract.getArpu();

        countryCell = new Label(0, numLine, country, cellFormat);
        contractNameCell = new Label(1, numLine, contractName, cellFormat);
        periodCell = new Label(2, numLine, Integer.toString(month) + "/" + Integer.toString(year), cellFormat);
        quantityCell = new jxl.write.Number(3, numLine, quantity, cellFormat);
        totalCostCell = new jxl.write.Number(4, numLine, totalCost, cellFormat);
        arpuCell = new jxl.write.Number(5, numLine, arpu, cellFormat);

        workbook.getSheet(sheetIndex).addCell(countryCell);
        workbook.getSheet(sheetIndex).addCell(contractNameCell);
        workbook.getSheet(sheetIndex).addCell(periodCell);
        workbook.getSheet(sheetIndex).addCell(quantityCell);
        workbook.getSheet(sheetIndex).addCell(totalCostCell);
        workbook.getSheet(sheetIndex).addCell(arpuCell);

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

        countryCell = new Label(0, numLine, country, totalCellFormat);
        contractNameCell = new Label(1, numLine, contractName, totalCellFormat);
        if (fiscalYear != 0) {
            periodCell = new Label(2, numLine, Integer.toString(firstMonth) + "/" + Integer.toString(fiscalYear) + " - " + Integer.toString(lastMonth) + "/" + Integer.toString(fiscalYear + 1) + " (" + Integer.toString(12 - nbEmptyLine) + " months)", totalCellFormat);
        } else {
            periodCell = new Label(2, numLine, Integer.toString(firstMonth) + "/" + Integer.toString(year) + " - " + Integer.toString(lastMonth) + "/" + Integer.toString(year) + " (" + Integer.toString(nbMonth - nbEmptyLine) + " months)", totalCellFormat);
        }
        arpu = totalCostComplete / quantityComplete;
        quantityCell = new jxl.write.Number(3, numLine, quantityComplete, totalCellFormat);
        totalCostCell = new jxl.write.Number(4, numLine, totalCostComplete, totalCellFormat);
        arpuCell = new jxl.write.Number(5, numLine, arpu, totalCellFormat);

        workbook.getSheet(sheetIndex).addCell(countryCell);
        workbook.getSheet(sheetIndex).addCell(contractNameCell);
        workbook.getSheet(sheetIndex).addCell(periodCell);
        workbook.getSheet(sheetIndex).addCell(quantityCell);
        workbook.getSheet(sheetIndex).addCell(totalCostCell);
        workbook.getSheet(sheetIndex).addCell(arpuCell);

        return workbook;
    }

    /**
	 *
	 * @param extractMap Map contenant les Beans d'extract à insérer dans le Workbook
	 * @param workbook Workbook en construction
	 * @param firstMonth Premier mois de l'extract à réaliser
	 * @param lastMonth Dernier mois de l'extract à réaliser
	 * @param fiscalYear Première année de l'année fiscale demandée (si c'est un extract par année fiscale, fiscalYear vaut 0)
	 * @return Workbook complété à envoyer en téléchargement
	 * @throws WriteException
	 * @throws IOException
	 */
	public WritableWorkbook constructExtract(Map<Integer, ExtractTab> extractMap, WritableWorkbook workbook, int firstMonth, int lastMonth, int fiscalYear) throws WriteException, IOException {

        try {

            WritableSheet fixSheet = workbook.createSheet("Fix Report", 0);
            WritableSheet mobileSheet = workbook.createSheet("Mobile Report", 1);
            WritableCellFormat titleFormat = new WritableCellFormat();
            titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
            titleFormat.setBackground(jxl.format.Colour.ORANGE);
            titleFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);

            fixSheet.setColumnView(0, 15);
            fixSheet.setColumnView(1, 30);
            fixSheet.setColumnView(2, 30);
            fixSheet.setColumnView(3, 25);
            fixSheet.setColumnView(4, 25);
            fixSheet.setColumnView(5, 25);
            fixSheet.addCell(new Label(0, 0, "Country Code", titleFormat));
            fixSheet.addCell(new Label(1, 0, "Contract Name", titleFormat));
            fixSheet.addCell(new Label(2, 0, "Date / Period", titleFormat));
            fixSheet.addCell(new Label(3, 0, "Line Count", titleFormat));
            fixSheet.addCell(new Label(4, 0, "Total Cost", titleFormat));
            fixSheet.addCell(new Label(5, 0, "ARPU", titleFormat));

            mobileSheet.setColumnView(0, 15);
            mobileSheet.setColumnView(1, 30);
            mobileSheet.setColumnView(2, 30);
            mobileSheet.setColumnView(3, 25);
            mobileSheet.setColumnView(4, 25);
            mobileSheet.setColumnView(5, 25);
            mobileSheet.addCell(new Label(0, 0, "Country Code", titleFormat));
            mobileSheet.addCell(new Label(1, 0, "Contract Name", titleFormat));
            mobileSheet.addCell(new Label(2, 0, "Date / Period", titleFormat));
            mobileSheet.addCell(new Label(3, 0, "Line Count", titleFormat));
            mobileSheet.addCell(new Label(4, 0, "Total Cost", titleFormat));
            mobileSheet.addCell(new Label(5, 0, "ARPU", titleFormat));

            int numSheet = 0;

            contractName = extractMap.get(0).getContractName();
            type = extractMap.get(0).getType();
            month = extractMap.get(0).getMonth();
            this.firstMonth = firstMonth;
            this.lastMonth = lastMonth;
            this.fiscalYear = fiscalYear;
            boolean fiscalYearCompleted = false;
            
            if ("Mobile".equals(type)) {
                numSheet = 1;
            }

            for (int i = 0; i <= extractMap.size(); i++) {
                System.out.println("i : " + i + " / nbMonth : " + nbMonth + " / month : " + month + " / contractName : " + contractName);
                ExtractTab extract = extractMap.get(i);
                if (i == (extractMap.size())) {
                    if ((nbMonth - nbEmptyLine) > 1 || (this.fiscalYear != 0 && nbEmptyLine < 11)) {
                        while (nbMonth < lastMonth || (this.fiscalYear != 0 && !fiscalYearCompleted)) {
                            System.out.println("MOIS MANQUANT");
                            if (this.fiscalYear != 0 && nbMonth >= 8) {
                                fiscalYearCompleted = true;
                                firstMonth = 1;
                                nbMonth = 0;
                            } else {
                                nbMonth++;
                            }
                            numLine++;
                            nbEmptyLine++;
                        }
                        if ((this.fiscalYear != 0 && nbEmptyLine < 11) || (this.fiscalYear == 0 && (nbMonth - nbEmptyLine) > 1)) {
                            workbook = addTotalRow(workbook, numSheet);
                        }
                    }
                } else if (!contractName.equals(extract.getContractName()) || !type.equals(extract.getType())) {
                    System.out.println("TOTAL");
                    if ((nbMonth - nbEmptyLine) > 1 || (this.fiscalYear != 0 && nbEmptyLine < 11)) {
                        while (nbMonth < lastMonth || (this.fiscalYear != 0 && !fiscalYearCompleted)) {
                            System.out.println("MOIS MANQUANT");
                            if (this.fiscalYear != 0 && nbMonth >= 8) {
                                fiscalYearCompleted = true;
                                firstMonth = 1;
                                nbMonth = 0;
                            } else {
                                nbMonth++;
                            }
                            numLine++;
                            nbEmptyLine++;
                        }
                        if ((this.fiscalYear != 0 && nbEmptyLine < 11) || (this.fiscalYear == 0 && (nbMonth - nbEmptyLine) > 1)) {
                            workbook = addTotalRow(workbook, numSheet);
                        }
                    }

                    if (!type.equals(extract.getType())) {
                        numLine = 1;
                        numSheet = 1;
                        type = extract.getType();
                    } else {
                        numLine++;
                    }
                    fiscalYearCompleted = false;
                    firstMonth = this.firstMonth;
                    quantityComplete = 0;
                    totalCostComplete = 0;
                    nbMonth = 0;
                    nbEmptyLine = 0;
                    if (nbMonth <= 12 && (nbMonth + firstMonth) != (extract.getMonth())) {
                        System.out.println("MOIS MANQUANT");
                        contractName = extract.getContractName();
                        numLine++;
                        nbMonth++;
                        nbEmptyLine++;
                        i--;
                    } else {
                        System.out.println("SIMPLE");
                        workbook = addSimpleRow(workbook, numSheet, extract);
                        nbMonth++;
                        numLine++;
                    }
                } else if (this.fiscalYear != 0 && nbMonth >= 8 && !fiscalYearCompleted) {
                    System.out.println("ANNEE FISCALE");
                    if ((nbMonth + firstMonth) != (extract.getMonth())) {
                        System.out.println("MOIS MANQUANT");
                        nbEmptyLine++;
                        i--;
                    } else {
                        System.out.println("SIMPLE");
                        workbook = addSimpleRow(workbook, numSheet, extract);
                    }
                    fiscalYearCompleted = true;
                    firstMonth = 1;
                    nbMonth = 0;
                    numLine++;
                } else if (nbMonth <= 12 && (nbMonth + firstMonth) != (extract.getMonth())) {
                    System.out.println("MOIS MANQUANT");
                    numLine++;
                    nbMonth++;
                    nbEmptyLine++;
                    i--;
                } else {
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
