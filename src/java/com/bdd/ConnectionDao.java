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
	
	boolean bind(String login, String password) throws DaoException;
	
}
