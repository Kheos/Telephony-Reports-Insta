/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

import java.sql.*;

/**
 *
 * @author Enji
 */
public final class DaoUtilitaire {

    private DaoUtilitaire() {
    }

    /*
     * Fermeture silencieuse du resultset
     */
    public static void silentClosures(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Echec de la fermeture du ResultSet : " + e.getMessage());
            }
        }
    }

    /*
     * Fermeture silencieuse du statement
     */
    public static void silentClosures(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Echec de la fermeture du Statement : " + e.getMessage());
            }
        }
    }

    /*
     * Fermeture silencieuse de la connexion
     */
    public static void silentClosures(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                System.out.println("Echec de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    /*
     * Fermetures silencieuses du statement et de la connexion
     */
    public static void silentClosures(Statement statement, Connection connexion) {
        silentClosures(statement);
        silentClosures(connexion);
    }

    /*
     * Fermetures silencieuses du resultset, du statement et de la connexion
     */
    public static void silentClosures(ResultSet resultSet, Statement statement, Connection connexion) {
        silentClosures(resultSet);
        silentClosures(statement);
        silentClosures(connexion);
    }

    /*
     * Initialise la requête préparée basée sur la connexion passée en argument,
     * avec la requête SQL et les objets donnés.
     */
    public static PreparedStatement initPreparedRequest(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }
}
