/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RestrictedFilter implements Filter {
    public static final String ACCES_INDEX     = "/Index";
    public static final String ATT_SESSION_USER = "sessionUser";
	public static final String ATT_REDIRECT_BOOL = "redirectBool";

    public void init( FilterConfig config ) throws ServletException {
    }
    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
            /* Redirection vers la page publique */
			session.setAttribute(ATT_REDIRECT_BOOL, Boolean.TRUE);
			response.sendRedirect( request.getContextPath() + ACCES_INDEX );
        } else {
            /* Affichage de la page restreinte */
            chain.doFilter( request, response );
        }
    }

    public void destroy() {
    }
}
