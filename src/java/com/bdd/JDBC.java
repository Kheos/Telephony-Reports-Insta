/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

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
public class JDBC {

    private List<String> messages = new ArrayList<String>();

    public List<String> execute(HttpServletRequest request) {

        /*
         * Chargement du driver JDBC pour MySQL
         */
        try {
            messages.add("Chargement du driver...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            messages.add("Driver chargé !");
        } catch (ClassNotFoundException e) {
            messages.add("Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
                    + e.getMessage());
        }

        /*
         * Connexion à la base de données
         */
        String url = "jdbc:oracle:thin:@ldap://sdora2.ch.power.alstom.com:389/CHITIM1P,cn=OracleContext,dc=srv01,dc=itc,dc=alstom,dc=com";
        String utilisateur = "ITIMADMIN";
        String motDePasse = "AdminItim";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            messages.add("Connexion à la base de données...");
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            messages.add("Connexion réussie !");

            /*
             * Création de l'objet gérant les requêtes
             */
            statement = connexion.createStatement();
            messages.add("Objet requête créé !");

            /*
             * Exécution d'une requête de lecture
             */
            /* Exécution d'une requête d'écriture avec renvoi de l'id auto-généré */
            int statut = statement.executeUpdate("INSERT INTO WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK (REF_UNITCONTRACT, REF_SITE_CODE) VALUES ('France', 'galil')", Statement.RETURN_GENERATED_KEYS);

            /* Formatage pour affichage dans la JSP finale. */
            messages.add("Résultat de la requête d'insertion : " + statut + ".");

            /* Récupération de l'id auto-généré par la requête d'insertion. */
            resultat = statement.getGeneratedKeys();
            /* Parcours du ResultSet et formatage pour affichage de la valeur qu'il contient dans la JSP finale. */
            while (resultat.next()) {
                messages.add("ID retourné lors de la requête d'insertion :" );
            }

            /*
             * Récupération des données du résultat de la requête de lecture
             */

        } catch (SQLException e) {
            messages.add("Erreur lors de la connexion : <br/>"
                    + e.getMessage());
        } finally {
            messages.add("Fermeture de l'objet ResultSet.");
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            messages.add("Fermeture de l'objet Statement.");
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            messages.add("Fermeture de l'objet Connection.");
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
        }

        return messages;
    }
}