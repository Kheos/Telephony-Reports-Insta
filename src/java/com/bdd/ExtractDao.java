/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import com.beans.ExtractTab;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 *
 * @author Nico
 */
public interface ExtractDao {

    /**
	 *
	 * @param extractMap Map contenant tous les Beans nécessaires à la réalisation de l'extract
	 * @param nameExtract Nom du pays dont extraire les données
	 * @param calendar Calendrier où sont enregistrés le mois et l'année des données de l'extract
	 * @param allMonth Booléen étant True si tous les mois de l'année sont à extraire, False sinon
	 * @return La Map contenant tous les Beans complétés de l'extract
	 */
	public Map<Integer, ExtractTab> extractCountryMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth);

    /**
	 *
	 * @param extractMap Map contenant tous les Beans nécessaires à la réalisation de l'extract
	 * @param nameExtract Nom du contrat dont extraire les données
	 * @param calendar Calendrier où sont enregistrés le mois et l'année des données de l'extract
	 * @param allMonth Booléen étant True si tous les mois de l'année sont à extraire, False sinon
	 * @return La Map contenant tous les Beans complétés de l'extract
	 */
	public Map<Integer, ExtractTab> extractContractMonth(Map<Integer, ExtractTab> extractMap, String nameExtract, GregorianCalendar calendar, Boolean allMonth);
	
    /**
	 *
	 * @param extractMap Map contenant tous les Beans nécessaires à la réalisation de l'extract
	 * @param nameExtract Nom du pays dont extraire les données
	 * @param year Année des données à extraire
	 * @return La Map contenant tous les Beans complétés de l'extract
	 */
	public Map<Integer, ExtractTab> extractCountryFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year);

    /**
	 *
	 * @param extractMap Map contenant tous les Beans nécessaires à la réalisation de l'extract
	 * @param nameExtract Nom du contrat dont extraire les données
	 * @param year Année des données à extraire
	 * @return La Map contenant tous les Beans complétés de l'extract
	 */
	public Map<Integer, ExtractTab> extractContractFiscalYear(Map<Integer, ExtractTab> extractMap, String nameExtract, int year);
    
}