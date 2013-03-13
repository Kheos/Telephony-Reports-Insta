/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd.UnitReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Enji
 *
 * This class allows you to delete a Unit Reports of the database. This function
 * is calling by the page : Delete_Unit_Reports
 *
 */
public class DeleteUnitReports {

    private String messageDelete = null;

    public String execute(HttpServletRequest request) {

        /* Loading of the JDBC driver for Oracle Database */
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        /* Connection to the Database*/
        String url = "jdbc:mysql://localhost";
        String utilisateur = "root";
        String motDePasse = "";
        Connection connexion = null;
        Statement statement = null;
        Statement st = null;

        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            statement = connexion.createStatement();

            String parametreDisplayName = request.getParameter("nameUnitReports");
            // Sending of the first request to the database
            int statut = statement.executeUpdate("DELETE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT WHERE CONTRACT_NAME = '" + parametreDisplayName + "'");
            // Sending of the second request to the database
            st = connexion.createStatement();
            int statut2 = st.executeUpdate("DELETE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK WHERE REF_UNITCONTRACT = '" + parametreDisplayName + "'");

            if (messageDelete == null) {
                messageDelete = "";
            }
          /**
             * 
             * Recovery of the result request's data
             * Preparation of the result  to the final display into the JSP
             * 
             */
            if ((statut != 1)) {
                messageDelete += ("<span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been deleted. Try again :</span><br /><br /><br /><center><br /><br /><br /><a href=\"Delete_Unit_Reports\" class=\"button\">Retry</a></center>");
            } else {
                messageDelete += ("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been deleted</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
            }

        } catch (SQLException e) {
            /* Error during the connection */
            e.getMessage();
        } finally {

            /* Closing of the ResultSet's object  */
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                    ignore.getMessage();
                }
            }
            /* Closing of the Statement's object  */
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                    ignore.getMessage();
                }
            }
        }

        return messageDelete;
    }
}
