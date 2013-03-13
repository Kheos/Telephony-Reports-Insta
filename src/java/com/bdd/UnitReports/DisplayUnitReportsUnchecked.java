package com.bdd.UnitReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Enji
 *
 * This class allows you to dispaly the site which are unchecked when you modify
 * a Unit Reports into the page : Modify_Unit_Reports_Site
 *
 */

public class DisplayUnitReportsUnchecked {

    private List<String> messageDisplaySiteUnchecked = new ArrayList<String>();

    public List<String> execute(HttpServletRequest request) {

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
        ResultSet resultat = null;
        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            /* Creation of the Statement's object  */
            statement = connexion.createStatement();

            String parametreDisplayName = request.getParameter("nameUnitReports");
            // Sending of the request to the database
            resultat = statement.executeQuery("SELECT SITE_CODE FROM WEBIDMINT.LOCATION_SITE WHERE REF_COUNTRY_CODE = '" + parametreDisplayName + '"');

            while (resultat.next()) {
                String parametreDisplaySite = resultat.getString("SITE_CODE");
                /* Preparation of the data to the final display into the JSP */
                messageDisplaySiteUnchecked.add("<input type=\"checkbox\" name=\"parametreDisplaySite\" id=\"" + parametreDisplaySite + "\" value=\"" + parametreDisplaySite + "\"/></tr><tr> <span class=\"displaySite\">" + parametreDisplaySite + "</span>");
            }

        } catch (SQLException e) {
            /* Error during the connection */
            e.getMessage();

        } finally {
            /* Closing of the ResultSet's object  */
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            /* Closing of the Statement's object  */
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            /* Closing of the Connection's object */
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
        }

        return messageDisplaySiteUnchecked;
    }
}
