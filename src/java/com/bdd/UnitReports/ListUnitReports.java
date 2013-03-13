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
public class ListUnitReports {
    
private String messageListUnitReports = null;


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
        
        resultat = statement.executeQuery("SELECT REF_COUNTRY_CODE, CONTRACT_NAME, DESCRIPTION FROM WEBIDMINT.TELEPHONY_UNITCONTRACT ORDER BY CONTRACT_NAME ASC");
        
        while (resultat.next()) {
                String parametreListCountry = resultat.getString("REF_COUNTRY_CODE");
                String parametreListContractName = resultat.getString("CONTRACT_NAME");
                String parametreListType = resultat.getString("DESCRIPTION");
                 if(messageListUnitReports == null){
                    messageListUnitReports = "";
                }
                /* Formatage des données pour affichage dans la JSP finale. */
                     messageListUnitReports+=("<tr><td><center><input type=\"radio\" name=\"modifyChoiceContract\" value=\""+parametreListContractName+"\"></center></td><td style=\"text-align:left; padding-left:30px; font-size:13px; font-weight:bold; color:#FFFFFF\">"+parametreListContractName+"</td><td style=\"padding-left:60px; color:#FFFFFF;\">"+parametreListType+"</td></tr>");
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

        return messageListUnitReports;
    }
}
