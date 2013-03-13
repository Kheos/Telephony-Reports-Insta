/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.bdd.DaoUtilitaire.initPreparedRequest;
import static com.bdd.DaoUtilitaire.silentClosures;

/**
 *
 * @author Nico
 */
class ConnectionDaoImpl implements ConnectionDao {

	private DaoFactory daoFactory;

	ConnectionDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	private static final String SQL_BIND_USER = "SELECT PASSWORD FROM WEBIDMINT.TELEPHONY_USERS WHERE LOGIN = ?";

	public boolean bind(String login, String password) throws DaoException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean bind = false;

		try {
			/*
			 * Récupération d'une connexion depuis la Factory
			 */
			connexion = daoFactory.getConnection();

			preparedStatement = initPreparedRequest(connexion, SQL_BIND_USER, false, login);
			resultSet = preparedStatement.executeQuery();
			String userPassword = null;
			
			while (resultSet.next()) {
				
				 userPassword = resultSet.getString("PASSWORD");
				 System.out.println(userPassword);
				
				if (userPassword.equals(password)) {
					bind = true;
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(ConnectionDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

		return bind;
	}
}
