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
public class SiteUnitReports {
  private String messageSite = null;

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

            /* Exécution d'une requête de lecture */
            String parametreCountry = request.getParameter( "countryUnitReports" );
            resultat = statement.executeQuery("SELECT SITE_CODE, SITE_NAME FROM WEBIDMINT.LOCATION_SITE WHERE REF_COUNTRY_CODE = '"+parametreCountry+"' ORDER BY SITE_NAME ASC ");

            /* Récupération des données du résultat de la requête de lecture */
            int i = 1;
            while (resultat.next()) {
                
                String siteCodeUnitReports = resultat.getString("SITE_CODE");
                String siteNameUnitReports = resultat.getString("SITE_NAME");
                System.out.println(siteCodeUnitReports);
                System.out.println(siteNameUnitReports);
                /* Formatage des données pour affichage dans la JSP finale. */
                if(messageSite == null){
                    messageSite = "";
                }
                messageSite+="<tr><td style=\"width:40px;\"><input type=\"checkbox\" name=\"site"+ i+ "\" id=\""+ siteCodeUnitReports + "\" value=\""+ siteCodeUnitReports + "\"/></td><td><span class=\"displaySite\" style=\"text-align:left;\">"+ siteCodeUnitReports + "</span></td><td><span class=\"displaySite\" style=\"text-align:left;\">"+ siteNameUnitReports + "</span></td></tr>";
                i++;
            }
            if(messageSite != null && i > 0){
                messageSite += "<input id=\"nbCheckBoxes\" name=\"nbCheckBoxes\" type=\"hidden\" readonly=\"readonly\" value=\""+i+"\"/>";
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

        return messageSite;
    }
}
