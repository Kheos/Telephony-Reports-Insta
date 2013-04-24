/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import com.beans.UnitReport;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Enji
 */
public interface UnitReportDao {

	/**
	 *
	 * @param messageCounry Liste qui contiendra le code HTML à afficher dans la vue
	 * @return La liste contenant le code HTML à afficher
	 */
	public List<String> listActiveCountries(List<String> messageCountry);
	
	/**
	 *
	 * @param messageCounry String qui contiendra le code HTML concaténé à afficher dans la vue
	 * @param parametreCountry Pays où les sites à récupérer sont situés
	 * @return La chaîne de caractères contenant le code HTML à afficher
	 */
	public String listActiveSites(String messageSite, String parametreCountry);
	
	/**
	 *
	 * @param messageResult String qui contiendra le code HTML concaténé à afficher dans la vue
	 * @param parametreLogin Login de l'utilisateur créant l'unité de report
	 * @param parametreName Nom de l'unité de report à insérer dans la base
	 * @param parametreCountry Pays de l'unité de report à insérer dans la base
	 * @param parametreType Type de l'unité de report à insérer dans la base
	 * @param parametreSite Liste des sites de l'unité de report à insérer dans la base
	 * @return La chaîne de caractères contenant le code HTML à afficher
	 */
	public String insertUnitReport(String messageResult, String parametreLogin, String parametreName, String parametreCountry, String parametreType, List<String> parametreSite);
	
	/**
	 *
	 * @param messageContractName Liste qui contiendra le code HTML à afficher dans la vue
	 * @return La liste contenant le code HTML à afficher
	 */
	public List<String> listContractNames(List<String> messageContractName);
	
	/**
	 *
	 * @param messageDelete String qui contiendra le code HTML à afficher dans la vue
	 * @return La châine de caractères contenant le code HTML à afficher
	 */
	public String deleteUnitReport(String messageDelete, String nameUnitReport);

}