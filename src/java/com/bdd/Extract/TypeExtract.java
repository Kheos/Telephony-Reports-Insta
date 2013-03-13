/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd.Extract;

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
 * @author 250665
 */
public class TypeExtract {
     private List<String> messageTypeExtract = new ArrayList<String>();

    public List<String> execute(HttpServletRequest request) {

        /* Loading of the JDBC driver for Oracle Database */
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        /* Connection to the Database*/
        String url = "jdbc:oracle:thin:@ldap://sdora2.ch.power.alstom.com:389/CHITIM1P,cn=OracleContext,dc=srv01,dc=itc,dc=alstom,dc=com";
        String utilisateur = "ITIMADMIN";
        String motDePasse = "AdminItim";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            // Sending of the request to the database
            String parametreNameUnitReport = request.getParameter( "nameUnitReports" );
            PreparedStatement ps = connexion.prepareStatement("SELECT DISTINCT SERVICE_TYPE FROM WEBIDMINT.TELEPHONY_CONSUMPTION WHERE REF_UNITCONTRACT = '"+parametreNameUnitReport+"'");

            resultat = ps.executeQuery();

            while (resultat.next()) {
                String typeUnitReport = resultat.getString("SERVICE_TYPE");

                /* Preparation of the data to the final display into the JSP */
                messageTypeExtract.add("<option value=" + typeUnitReport + ">" + typeUnitReport + "</option>");
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

        return messageTypeExtract;
    }
}
