/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import com.beans.Report;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author Nico
 */
public interface ReportDao {
	
    /**
	 *
	 * @param reportMap Map contenant tous les Beans de Report demandés
	 * @param refContract Nom du contrat dont récupérer les données
	 * @param calendar Calendrier où sont enregistrés le mois et l'année des données à récupérer
	 * @param allMonth Booléen étant True si tous les mois de l'année sont à récupérer, False sinon
	 * @return La Map contenant tous les Beans complétés
	 * @throws DaoException
	 */
	Map<Integer, Report> list(Map<Integer, Report> reportMap, String refContract, Calendar calendar, Boolean allMonth) throws DaoException;

    /**
	 *
	 * @param siteList Map qui contiendra tous les noms des différents contrats
	 * @return La Map contenant les noms des contrats remplie
	 * @throws DaoException
	 */
	Map<Integer, String> siteList(Map<Integer, String> siteList) throws DaoException;

    /**
	 *
	 * @param refContract Nom du contrat dont récupérer le type
	 * @return Le type du contrat
	 */
	String getType(String refContract);

    /**
	 *
	 * @param refContract Nom du contrat
	 * @param month Mois du contrat
	 * @param year Année du contrat
	 * @param editable Booléen valant True si le Report est déjà sauvegardé et peut être édité, False sinon
	 * @param report Le Bean de Report où enregistrer les informations précédentes
	 * @return Le Bean de Report complété des informations passées en paramètres
	 */
	Report handleInformations(String refContract, int month, int year, Boolean editable, Report report);

    /**
	 *
	 * @param month Mois du Report à mettre à jour
	 * @param year Année du Report à mettre à jour
	 * @param serviceType Type de service du Report à mettre à jour
	 * @param refContract Nom du contrat du Report à mettre à jour
	 * @param report Bean du Report où sont encapsulées les données à mettre à jour en base
	 * @return Bean du Report mis à jour
	 * @throws DaoException
	 */
	Report update(int month, int year, String serviceType, String refContract, Report report) throws DaoException;

    /**
	 *
	 * @param month Mois du Report à sauvegarder
	 * @param year Année du Report à sauvegarder
	 * @param serviceType Type de service du Report à sauvegarder
	 * @param refContract Nom du contrat du Report à sauvegarder
	 * @param report Bean du Report où sont encapsulées les données à insérer en base
	 * @return Bean du Report sauvegardé
	 * @throws DaoException
	 */
	Report save(int month, int year, String serviceType, String refContract, Report report) throws DaoException;
}
