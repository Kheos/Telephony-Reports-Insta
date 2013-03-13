package com.bdd.UnitReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * This class allows you to load the country into a select into the page :
 * Add_Unit_Reports
 *
 */
public class CountryUnitReports {

    private List<String> messageCountry = new ArrayList<String>();

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
            // Sending of the request to the database
            PreparedStatement ps = connexion.prepareStatement("SELECT COUNTRY_NAME, COUNTRY_CODE FROM WEBIDMINT.LOCATION_COUNTRY WHERE COUNTRY_STATUS='ACTIVE' ORDER BY COUNTRY_NAME");

            resultat = ps.executeQuery();

            while (resultat.next()) {
                String countryUnitReports = resultat.getString("COUNTRY_NAME");
                String codeCountryUnitReports = resultat.getString("COUNTRY_CODE");

                /* Preparation of the data to the final display into the JSP */
                messageCountry.add("<option value=" + codeCountryUnitReports + ">" + countryUnitReports + "</option>");
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

        return messageCountry;
    }
}
