/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.config;
    import javax.servlet.ServletContext;
    import javax.servlet.ServletContextEvent;
    import javax.servlet.ServletContextListener;

    import com.bdd.DaoFactory;
import com.bdd.DaoFactory;

/**
 *
 * @author Enji
 */
public class InitialisationDaoFactory implements ServletContextListener{
    private static final String ATT_DAO_FACTORY = "daofactory";

    private DaoFactory daoFactory;

    @Override
    public void contextInitialized( ServletContextEvent event ) {
        /* RÃ©cupÃ©ration du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
        this.daoFactory = DaoFactory.getInstance();
        /* Enregistrement dans un attribut ayant pour portÃ©e toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
    }

    @Override
    public void contextDestroyed( ServletContextEvent event ) {
        /* Rien Ã  rÃ©aliser lors de la fermeture de l'application... */
    }
}