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
public class ModifyUnitReports {

    private String messageDisplay = null;

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

            String DisplayName = request.getParameter("nameUnitReports");
            String SQL_SELECT_SITE_NAME = "SELECT WEBIDMINT.LOCATION_SITE.SITE_NAME FROM WEBIDMINT.LOCATION_SITE, WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK WHERE WEBIDMINT.LOCATION_SITE.SITE_CODE = WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK.REF_SITE_CODE AND WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK.REF_UNITCONTRACT = '"+DisplayName+"'";
            resultat = statement.executeQuery(SQL_SELECT_SITE_NAME);

            while (resultat.next()) {
                String parametreDisplaySite = resultat.getString("SITE_NAME");
                if (messageDisplay == null) {
                    messageDisplay = "";
                }
                /* Formatage des données pour affichage dans la JSP finale. */
                messageDisplay += ("<p>" + parametreDisplaySite + "</p>");
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

        return messageDisplay;
    }
}
