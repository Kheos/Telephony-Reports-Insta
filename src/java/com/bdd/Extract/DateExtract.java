/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd.Extract;
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
 * @author 250665
 */
public class DateExtract {
    private List<String> messageDateOption = new ArrayList<String>();

    public List<String> execute(HttpServletRequest request) {

        /* Chargement du driver JDBC pour MySQL */
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        /* Connexion à la base de données */
        String url = "jdbc:oracle:thin:@ldap://sdora2.ch.power.alstom.com:389/CHITIM1P,cn=OracleContext,dc=srv01,dc=itc,dc=alstom,dc=com";
        String utilisateur = "ITIMADMIN";
        String motDePasse = "AdminItim";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();

            /* Exécution d'une requête de lecture */
            String parametreNameUnitReports = request.getParameter( "nameUnitReports" );
            String parametreTypeUnitReports = request.getParameter( "contractType" );
            resultat = statement.executeQuery("SELECT DATE_REPORTS FROM WEBIDMINT.TELEPHONY_CONSUMPTION WHERE REF_UNITCONTRACT = '"+parametreNameUnitReports+"' AND SERVICE_TYPE = '"+parametreTypeUnitReports+"'");

            /* Récupération des données du résultat de la requête de lecture */
            while (resultat.next()) {
                String dateExtract = resultat.getString("DATE_REPORTS");
                /* Formatage des données pour affichage dans la JSP finale. */
                messageDateOption.add("<option value=" + dateExtract + ">" + dateExtract + "</option>");
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
        
        return messageDateOption;
    }
}
