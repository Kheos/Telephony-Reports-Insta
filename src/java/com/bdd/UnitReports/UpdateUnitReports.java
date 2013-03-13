/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd.UnitReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Enji
 */
public class UpdateUnitReports {
    
    private String messageModifyResult = null;

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
        Statement st = null;

        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();

            /* Exécution d'une requête d'écriture */

            String parametreName = request.getParameter("nameUnitReports");
            String parametreSite = request.getParameter("siteUnitReports");
            /* Exécution d'une requête de lecture */
            int statut = statement.executeUpdate("UPDATE WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK SET REF_SITE_CODE = '" + parametreSite + "' WHERE REF_UNITCONTRACT = '" + parametreName + "'");
           if(messageModifyResult == null){
                    messageModifyResult = "";
                }
            if (statut !=1) {
                messageModifyResult+=("<span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been modified</span><center><br /><br /><br /><a href=\"Add_Unit_Reports\" class=\"button\">Retry</a></center>");
            } else {
                /* Récupération des données du résultat de la requête de lecture */
                messageModifyResult+=("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been modified</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
                /* Récupération de l'id auto-généré par la requête d'insertion. */
            }

        } catch (SQLException e) {
            /* Erreur lors de la connexion */
            e.getMessage();
        } finally {
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

        return messageModifyResult;
    }
}
