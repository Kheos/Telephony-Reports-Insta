/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

/**
 *
 * @author Enji
 */


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {

    private static final String FICHIER_PROPERTIES       = "/src/java/com/bdd/dao.properties";
    private static final String PROPERTY_URL             = "jdbc:mysql://localhost";
    private static final String PROPERTY_DRIVER          = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "root";
    private static final String PROPERTY_MOT_DE_PASSE    = "";

    private String              url;
    private String              username;
    private String              password;

    /* package */DaoFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DaoFactory getInstance() throws DaoConfigurationException {

		/*
		 * ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
		
        if ( fichierProperties == null ) {
            throw new DaoConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
        } catch ( FileNotFoundException e ) {
            throw new DaoConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e );
        } catch ( IOException e ) {
            throw new DaoConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        } */

        try {
            Class.forName( PROPERTY_DRIVER );
        } catch ( ClassNotFoundException e ) {
            throw new DaoConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }
		
        DaoFactory instance = new DaoFactory( PROPERTY_URL, PROPERTY_NOM_UTILISATEUR, PROPERTY_MOT_DE_PASSE );
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    /* package */Connection getConnection() throws SQLException {
        return DriverManager.getConnection( PROPERTY_URL, PROPERTY_NOM_UTILISATEUR, PROPERTY_MOT_DE_PASSE );
    }
	
    /**
	 * Méthode de récupération de l'implémentation du DAO de la partie Report
	 * @return Instance de l'implémentation du DAO de la partie Report
	 */
	public ReportDao getReportDao() {
        return new ReportDaoImpl( this );
    }
    
    /**
	 * Méthode de récupération de l'implémentation du DAO de la partie Extract
	 * @return Instance de l'implémentation du DAO de la partie Extract
	 */
	public ExtractDao getExtractDao() {
        return new ExtractDaoImpl( this );
    }
	
	/**
	 * Méthode de récupération de l'implémentation du DAO de la partie Connexion (seulement en local)
	 * @return Instance de l'implémentation du DAO de la partie Connexion
	 */
	public ConnectionDao getConnectionDao() {
        return new ConnectionDaoImpl( this );
    }
	
	/**
	 * Méthode de récupération de l'implémentation du DAO de la partie Unit Report (seulement en local)
	 * @return Instance de l'implémentation du DAO de la partie Unit Report
	 */
	public UnitReportDao getUnitReportDao() {
        return new UnitReportDaoImpl( this );
    }

}