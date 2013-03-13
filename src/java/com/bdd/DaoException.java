/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bdd;

/**
 *
 * @author Enji
 */
public class DaoException  extends RuntimeException{
    /*
     * Constructeurs
     */
    public DaoException( String message ) {
        super( message );
    }

    public DaoException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DaoException( Throwable cause ) {
        super( cause );
    }
}
