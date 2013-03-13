package com.bdd.UnitReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Enji
 *
 * This class allows you to insert a new Unit Reports into the Database.
 * This class is calling into the page : Add_Unit_Reports_Result
 *
 */

public class InsertUnitReports {

    private String messageResult = null;
    public static final String ATT_SESSION_USER = "sessionUser";

    public String execute(HttpServletRequest request) {

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
             * Recovery of the result request's data
             * Preparation of the result  to the final display into the JSP
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
