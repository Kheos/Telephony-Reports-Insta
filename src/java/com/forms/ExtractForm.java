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
	private int firstMonth, month, nbMonth, year, quantity, quantityComplete, numLine, nbEmptyLine;
	private float totalCost, totalCostComplete, arpu;
	private Label countryCell, contractNameCell, periodCell;
	private jxl.write.Number quantityCell, totalCostCell, arpuCell;

	@SuppressWarnings("empty-statement")
	public ExtractForm(ExtractDao extractDao) {
		this.extractDao = extractDao;

		this.country = null;
		this.contractName = null;
		this.type = null;
		this.firstMonth = 0;
		this.month = 0;
		this.nbMonth = 0;
		this.year = 0;
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
	}

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
			extractMap = extractDao.extractCountryMonth(extractMap, nameExtract, calendar, allMonth);
		}

		return extractMap;
	}

	public Map<Integer, ExtractTab> extractFiscalYear(String typeExtract, String nameExtract, int year) {

		Map<Integer, ExtractTab> extractMap = new HashMap<Integer, ExtractTab>();

		if ("country".equals(typeExtract)) {
			extractMap = extractDao.extractCountryFiscalYear(extractMap, nameExtract, year);
		} else {
			extractMap = extractDao.extractContractFiscalYear(extractMap, nameExtract, year);
		}

		return extractMap;
	}

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

		countryCell = new Label(0, numLine, country);
		contractNameCell = new Label(1, numLine, contractName);
		periodCell = new Label(2, numLine, Integer.toString(month) + "/" + Integer.toString(year));
		quantityCell = new jxl.write.Number(3, numLine, quantity);
		totalCostCell = new jxl.write.Number(4, numLine, totalCost);
		arpuCell = new jxl.write.Number(5, numLine, arpu);

		workbook.getSheet(sheetIndex).addCell(countryCell);
		workbook.getSheet(sheetIndex).addCell(contractNameCell);
		workbook.getSheet(sheetIndex).addCell(periodCell);
		workbook.getSheet(sheetIndex).addCell(quantityCell);
		workbook.getSheet(sheetIndex).addCell(totalCostCell);
		workbook.getSheet(sheetIndex).addCell(arpuCell);

		return workbook;
	}

	public WritableWorkbook addTotalRow(WritableWorkbook workbook, int sheetIndex) throws WriteException {

		countryCell = new Label(0, numLine, country);
		contractNameCell = new Label(1, numLine, contractName);
		periodCell = new Label(2, numLine, Integer.toString(firstMonth) + "/" + Integer.toString(year) + " - " + Integer.toString(month) + "/" + Integer.toString(year) + " (" + Integer.toString(nbMonth - nbEmptyLine) + " months)");
		quantityCell = new jxl.write.Number(3, numLine, quantityComplete);
		totalCostCell = new jxl.write.Number(4, numLine, totalCostComplete);
		arpu = (totalCostComplete / (quantityComplete / (nbMonth - nbEmptyLine)));
		arpuCell = new jxl.write.Number(5, numLine, arpu);

		workbook.getSheet(sheetIndex).addCell(countryCell);
		workbook.getSheet(sheetIndex).addCell(contractNameCell);
		workbook.getSheet(sheetIndex).addCell(periodCell);
		workbook.getSheet(sheetIndex).addCell(quantityCell);
		workbook.getSheet(sheetIndex).addCell(totalCostCell);
		workbook.getSheet(sheetIndex).addCell(arpuCell);

		return workbook;
	}

	public WritableWorkbook constructExtract(Map<Integer, ExtractTab> extractMap, WritableWorkbook workbook) throws WriteException, IOException {

		try {

			WritableSheet fixSheet = workbook.createSheet("Fix Report", 0);
			WritableSheet mobileSheet = workbook.createSheet("Mobile Report", 1);

			fixSheet.addCell(new Label(0, 0, "Country Code"));
			fixSheet.addCell(new Label(1, 0, "Contract Name"));
			fixSheet.addCell(new Label(2, 0, "Date / Period"));
			fixSheet.addCell(new Label(3, 0, "Line Count"));
			fixSheet.addCell(new Label(4, 0, "Total Cost"));
			fixSheet.addCell(new Label(5, 0, "ARPU"));

			mobileSheet.addCell(new Label(0, 0, "Country Code"));
			mobileSheet.addCell(new Label(1, 0, "Contract Name"));
			mobileSheet.addCell(new Label(2, 0, "Date / Period"));
			mobileSheet.addCell(new Label(3, 0, "Line Count"));
			mobileSheet.addCell(new Label(4, 0, "Total Cost"));
			mobileSheet.addCell(new Label(5, 0, "ARPU"));

			int numSheet = 0;

			contractName = extractMap.get(0).getContractName();
			type = extractMap.get(0).getType();
			firstMonth = 1;
			month = extractMap.get(0).getMonth();

			for (int i = 0; i <= extractMap.size(); i++) {
				System.out.println("i : " + i + " / nbMonth : " + nbMonth + " / month : " + month + " / contractName : " + contractName);
				ExtractTab extract = extractMap.get(i);
				if (i == (extractMap.size())) {
					if ((nbMonth - nbEmptyLine) > 1) {
						workbook = addTotalRow(workbook, numSheet);
					}
				} else if (!contractName.equals(extract.getContractName()) || !type.equals(extract.getType())) {
					System.out.println("TOTAL");
					if ((nbMonth - nbEmptyLine) > 1) {
						workbook = addTotalRow(workbook, numSheet);
					}
					if (!type.equals(extract.getType())) {
						numLine = 1;
						numSheet = 1;
						type = extract.getType();
					} else {
						numLine++;
					}
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
