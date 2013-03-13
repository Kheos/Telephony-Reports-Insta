/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd.UnitReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Enji
 */
public class UnitReports {

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

    public class DeleteUnitReports {

        private String messageDelete = null;

        public String execute(HttpServletRequest request) {

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
            Statement st = null;

            try {
                connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

                statement = connexion.createStatement();

                String parametreDisplayName = request.getParameter("nameUnitReports");
                // Sending of the first request to the database
                int statut = statement.executeUpdate("DELETE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT WHERE CONTRACT_NAME = '" + parametreDisplayName + "'");
                // Sending of the second request to the database
                st = connexion.createStatement();
                int statut2 = st.executeUpdate("DELETE FROM WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK WHERE REF_UNITCONTRACT = '" + parametreDisplayName + "'");

                if (messageDelete == null) {
                    messageDelete = "";
                }
                /**
                 *
                 * Recovery of the result request's data Preparation of the
                 * result to the final display into the JSP
                 *
                 */
                if ((statut != 1)) {
                    messageDelete += ("<span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been deleted. Try again :</span><br /><br /><br /><center><br /><br /><br /><a href=\"Delete_Unit_Reports\" class=\"button\">Retry</a></center>");
                } else {
                    messageDelete += ("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been deleted</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
                }

            } catch (SQLException e) {
                /* Error during the connection */
                e.getMessage();
            } finally {

                /* Closing of the ResultSet's object  */
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ignore) {
                        ignore.getMessage();
                    }
                }
                /* Closing of the Statement's object  */
                if (connexion != null) {
                    try {
                        connexion.close();
                    } catch (SQLException ignore) {
                        ignore.getMessage();
                    }
                }
            }

            return messageDelete;
        }
    }

    public class DisplayUnitReportsUnchecked {

        private List<String> messageDisplaySiteUnchecked = new ArrayList<String>();

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

                /* Creation of the Statement's object  */
                statement = connexion.createStatement();

                String parametreDisplayName = request.getParameter("nameUnitReports");
                // Sending of the request to the database
                resultat = statement.executeQuery("SELECT SITE_CODE FROM WEBIDM.LOCATION_SITE WHERE REF_COUNTRY_CODE = '" + parametreDisplayName + '"');

                while (resultat.next()) {
                    String parametreDisplaySite = resultat.getString("SITE_CODE");
                    /* Preparation of the data to the final display into the JSP */
                    messageDisplaySiteUnchecked.add("<input type=\"checkbox\" name=\"parametreDisplaySite\" id=\"" + parametreDisplaySite + "\" value=\"" + parametreDisplaySite + "\"/></tr><tr> <span class=\"displaySite\">" + parametreDisplaySite + "</span>");
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

            return messageDisplaySiteUnchecked;
        }
    }

    public class InsertUnitReports {

        private String messageResult = null;
        public static final String ATT_SESSION_USER = "sessionUser";

        public String execute(HttpServletRequest request) {

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
            Statement st = null;


            try {
                connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

                /* Creation of the Statement's object  */
                statement = connexion.createStatement();

                /* Sending of the insertion's request to the database */

                System.out.println(request.getAttributeNames());
                Enumeration<String> enumAttrs = request.getParameterNames();
                while (enumAttrs.hasMoreElements()) {
                    System.out.println(request.getParameter(enumAttrs.nextElement()));
                }
                String parametreName = request.getParameter("nameUnitReports");
                String parametreCountry = request.getParameter("countryUnitReports");
                String parametreType = request.getParameter("typeUnitReports");
                HttpSession session = request.getSession();
                String parametreLogin = (String) session.getAttribute(ATT_SESSION_USER);
                int statut = statement.executeUpdate("INSERT INTO WEBIDMINT.TELEPHONY_UNITCONTRACT (REF_COUNTRY_CODE, CONTRACT_NAME, DESCRIPTION, REF_OWNER_ID)" + "VALUES ('" + parametreCountry + "', '" + parametreName + "',  '" + parametreType + "', '" + parametreLogin + "')");
                int iNbCheckBoxes = (request.getParameter("nbCheckBoxes") != null ? Integer.parseInt(request.getParameter("nbCheckBoxes")) : 0);
                if (iNbCheckBoxes > 0) {
                    for (int i = 1; i <= iNbCheckBoxes; i++) {
                        String parametreSite = request.getParameter("site" + i);
                        if (parametreSite != null && parametreSite.length() > 0) {
                            /* Exécution d'une requête de lecture */
                            st = connexion.createStatement();
                            int statut2 = st.executeUpdate("INSERT INTO WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK (REF_UNITCONTRACT, REF_SITE_CODE)" + "VALUES ('" + parametreName + "', '" + parametreSite + "')");
                        }
                    }
                }
                if (messageResult == null) {
                    messageResult = "";
                }
                /**
                 *
                 * Recovery of the result request's data Preparation of the
                 * result to the final display into the JSP
                 *
                 */
                if ((statut != 1)) {
                    messageResult += (" <span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been registered</span><center><br /><br /><br /><a href=\"Add_Unit_Reports\" class=\"button\">Retry</a></center>");
                } else {
                    messageResult += ("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been registered</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
                }
            } catch (SQLException e) {
                /* Error during the connection */
                e.getMessage();
            } finally {
                /* Closing of the ResultSet's object  */
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

            return messageResult;
        }
    }

    public class ListUnitReports {

        private String messageListUnitReports = null;

        public String execute(HttpServletRequest request) {

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

                resultat = statement.executeQuery("SELECT REF_COUNTRY_CODE, CONTRACT_NAME, DESCRIPTION FROM WEBIDMINT.TELEPHONY_UNITCONTRACT");

                while (resultat.next()) {
                    String parametreListCountry = resultat.getString("REF_COUNTRY_CODE");
                    String parametreListContractName = resultat.getString("CONTRACT_NAME");
                    String parametreListType = resultat.getString("DESCRIPTION");
                    if (messageListUnitReports == null) {
                        messageListUnitReports = "";
                    }
                    /* Formatage des données pour affichage dans la JSP finale. */
                    messageListUnitReports += ("<tr><td>" + parametreListCountry + "</td><td>" + parametreListContractName + "</td><td>" + parametreListType + "</td></tr>");
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

    public class ModifyUnitReports {

        private String messageDisplay = null;

        public String execute(HttpServletRequest request) {

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

                String DisplayName = request.getParameter("nameUnitReports");
                System.out.println(DisplayName);

                resultat = statement.executeQuery("SELECT SITE_NAME FROM WEBIDMINT.LOCATION_SITE, WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK WHERE WEBIDMINT.LOCATION_SITE.SITE_CODE = WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK.REF_SITE_CODE AND WEBIDMINT.TELEPHONY_UNITCONTRACT_LINK.REF_UNITCONTRACT = ' " + DisplayName + " ' ");

                String parametreDisplaySite = resultat.getString("SITE_NAME");
                System.out.println(parametreDisplaySite);
                while (resultat.next()) {

                    //String parametreDisplaySite = resultat.getString("SITE_NAME");
                    System.out.println(parametreDisplaySite);
                    /* Formatage des données pour affichage dans la JSP finale. */
                    if (messageDisplay == null) {
                        messageDisplay = "";
                    }
                    messageDisplay += "<p>" + parametreDisplaySite + "</p>";
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

    public class ModifyUnitReportsAll {

        private List<String> messageDisplayAll = new ArrayList<String>();

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

                String parametreDisplayName = request.getParameter("nameUnitReports");

                resultat = statement.executeQuery("SELECT SITE_NAME FROM 'WEBIDMINT.LOCATION_SITE', 'WEBIDMINT.TELEPHONY_UNITCONTRACT' WHERE 'WEBIDMINT.LOCATION_SITE'.'REF_COUNTRY_CODE' = 'WEBIDMINT.TELEPHONY_UNITCONTRACT'.'REF_COUNTRY_CODE' AND 'WEBIDMINT.TELEPHONY_UNITCONTRACT'.'CONTRACT_NAME' = '" + parametreDisplayName + "'");
                messageDisplayAll.add("<p>Loose</p>");
                while (resultat.next()) {
                    String parametreDisplaySite = resultat.getString("SITE_NAME");
                    /* Formatage des données pour affichage dans la JSP finale. */
                    messageDisplayAll.add("<input type=\"checkbox\" name=\"siteUnitReports\" id=\"" + parametreDisplaySite + "\" value=\"" + parametreDisplaySite + "\"/></tr><tr> <span class=\"displaySite\">" + parametreDisplaySite + "</span>");
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

    public class NameUnitReports {

        private List<String> messageNameOption = new ArrayList<String>();

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
                resultat = statement.executeQuery("SELECT CONTRACT_NAME FROM WEBIDMINT.TELEPHONY_UNITCONTRACT");

                /* Récupération des données du résultat de la requête de lecture */
                while (resultat.next()) {
                    String nameUnitReports = resultat.getString("CONTRACT_NAME");
                    /* Formatage des données pour affichage dans la JSP finale. */
                    messageNameOption.add("<option value=" + nameUnitReports + ">" + nameUnitReports + "</option>");
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

    public class SiteUnitReports {

        private String messageSite = null;

        public String execute(HttpServletRequest request) {

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
                String parametreCountry = request.getParameter("countryUnitReports");
                resultat = statement.executeQuery("SELECT SITE_CODE, SITE_NAME FROM WEBIDM.LOCATION_SITE WHERE REF_COUNTRY_CODE = '" + parametreCountry + "' ORDER BY SITE_NAME ASC ");

                /* Récupération des données du résultat de la requête de lecture */
                int i = 1;
                while (resultat.next()) {

                    String siteCodeUnitReports = resultat.getString("SITE_CODE");
                    String siteNameUnitReports = resultat.getString("SITE_NAME");
                    System.out.println(siteCodeUnitReports);
                    System.out.println(siteNameUnitReports);
                    /* Formatage des données pour affichage dans la JSP finale. */
                    if (messageSite == null) {
                        messageSite = "";
                    }
                    messageSite += "<tr><td style=\"width:40px;\"><input type=\"checkbox\" name=\"site" + i + "\" id=\"" + siteCodeUnitReports + "\" value=\"" + siteCodeUnitReports + "\"/></td><td><span class=\"displaySite\" style=\"text-align:left;\">" + siteCodeUnitReports + "</span></td><td><span class=\"displaySite\" style=\"text-align:left;\">" + siteNameUnitReports + "</span></td></tr>";
                    i++;
                }
                if (messageSite != null && i > 0) {
                    messageSite += "<input id=\"nbCheckBoxes\" name=\"nbCheckBoxes\" type=\"hidden\" readonly=\"readonly\" value=\"" + i + "\"/>";
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

    public class UpdateUnitReports {

        private String messageModifyResult = null;

        public String execute(HttpServletRequest request) {

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
                if (messageModifyResult == null) {
                    messageModifyResult = "";
                }
                if (statut != 1) {
                    messageModifyResult += ("<span style=\"color:red; font-weight:bold\">Error, your Unit Contract has not been modified</span><center><br /><br /><br /><a href=\"Add_Unit_Reports\" class=\"button\">Retry</a></center>");
                } else {
                    /* Récupération des données du résultat de la requête de lecture */
                    messageModifyResult += ("<span style=\"color:green; font-weight:bold; \">Your Unit Contract has been modified</span><br /><br /><br /><center><br /><br /><br /><a href=\"Unit_Reports\" class=\"button\">Return</a></center>");
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
}
