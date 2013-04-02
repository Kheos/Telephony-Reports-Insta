/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

/**
 *
 * @author Nico
 */
public interface ConnectionDao {
	
	/**
	 *
	 * @param login Identifiant de connexion
	 * @param password Mot de passe lié au login
	 * @return True si les identifiants sont vérifiés et False sinon
	 * @throws DaoException
	 */
	boolean bind(String login, String password) throws DaoException;
	
}
