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
public class NameUnitReports {

    private List<String> messageNameOption = new ArrayList<String>();

    public List<String> execute(HttpServletRequest request) {

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

            /* Exécution d'une requête de lecture */
            resultat = statement.executeQuery("SELECT CONTRACT_NAME FROM WEBIDMINT.TELEPHONY_UNITCONTRACT");

            /* Récupération des données du résultat de la requête de lecture */
            while (resultat.next()) {
                String nameUnitReports = resultat.getString("CONTRACT_NAME");
                System.out.println (nameUnitReports);
                /* Formatage des données pour affichage dans la JSP finale. */
                messageNameOption.add("<option value=\"" + nameUnitReports + "\">" + nameUnitReports + "</option>");
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
        
        return messageNameOption;
    }
}
