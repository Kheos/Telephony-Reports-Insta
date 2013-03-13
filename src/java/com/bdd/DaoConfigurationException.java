/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

/**
 *
 * @author Enji
 */
public class DaoConfigurationException extends RuntimeException{
    /*
     * Constructeurs
     */
    public DaoConfigurationException( String message ) {
        super( message );
    }

    public DaoConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DaoConfigurationException( Throwable cause ) {
        super( cause );
    }
}
