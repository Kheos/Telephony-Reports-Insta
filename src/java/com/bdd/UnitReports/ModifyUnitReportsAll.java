/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
public class ModifyUnitReportsAll {

    private String messageDisplayAll = null;

    public String execute(HttpServletRequest request) {

        /* Chargement du driver JDBC pour MySQL */
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        /* Connexion à la base de données */
        String url = "jdbc:mysql://localhost";
        String utilisateur = "root";
        String motDePasse = "";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();

            String parametreDisplayName = request.getParameter("nameUnitReports");
            String SQL_SELECT_SITE_NAME_UNCHECKED = "SELECT DISTINCT C.CONTRACT_NAME, C.REF_COUNTRY_CODE, S.SITE_NAME, S.SITE_CODE, L.REF_SITE_CODE FROM (WEBIDMINT.TELEPHONY_UNITCONTRACT C INNER JOIN WEBIDMINT.LOCATION_SITE S ON C.REF_COUNTRY_CODE = S.REF_COUNTRY_CODE) LEFT OUTER JOIN WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK L ON S.SITE_CODE = L.REF_SITE_CODE AND L.REF_UNITCONTRACT = '" + parametreDisplayName + "' WHERE ((L.REF_SITE_CODE Is Null) OR (L.REF_UNITCONTRACT != '" + parametreDisplayName + "')) AND C.CONTRACT_NAME = '" + parametreDisplayName + "' AND S.SITE_STATUS = 'ACTIVE'";
            resultat = statement.executeQuery(SQL_SELECT_SITE_NAME_UNCHECKED);

            int i = 1;
            while (resultat.next()) {
                String parametreDisplayCodeSite = resultat.getString("SITE_CODE");
                String parametreDisplaySite = resultat.getString("SITE_NAME");

                if (messageDisplayAll == null) {
                    messageDisplayAll = "";
                }
                messageDisplayAll += "<tr><td style=\"width:40px;\"><input type=\"checkbox\" name=\"site" + i + "\" id=\"" + parametreDisplayCodeSite + "\" value=\"" + parametreDisplaySite + "\"/></td><td><span class=\"displaySite\" style=\"text-align:left;\">" + parametreDisplayCodeSite + "</span></td><td><span class=\"displaySite\" style=\"text-align:left; width:195px;\">" + parametreDisplaySite + "</span></td></tr>";
                i++;
            }
            if (messageDisplayAll != null && i > 0) {
                messageDisplayAll += "<input id=\"nbCheckBoxes\" name=\"nbCheckBoxes\" type=\"hidden\" readonly=\"readonly\" value=\"" + i + "\"/>";
            }

        } catch (SQLException e) {
            /* Erreur lors de la connexion */
            e.getMessage();

        } finally {
            /* Fermeture de l'objet ResultSet */
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            /* Fermeture de l'objet Statement */
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            /* Fermeture de l'objet Connection */
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
        }

        return messageDisplayAll;
    }
}