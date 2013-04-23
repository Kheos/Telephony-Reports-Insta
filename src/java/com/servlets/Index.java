/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlets;

import com.bdd.DaoFactory;
import com.classes.CTLLdapCAD;
import com.classes.Ldap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bdd.ConnectionDao;

/**
 *
 * @author Enji
 */
public class Index extends HttpServlet {

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	public static final String ATT_USER = "login";
	public static final String ATT_SESSION_USER = "sessionUser";
	public static final String ATT_PSW = "password";
	public static final String urlRedirection = "/Reports";
	public static final String ATT_REDIRECT_BOOL = "redirectBool";
	public static final String ERROR = "/Error";
	public static final String CONF_DAO_FACTORY = "daofactory";
	private ConnectionDao connectionDao;
	
	public void init() throws ServletException {
        /*
         * Récupération d'une instance de notre DAO Utilisateur
         */
        this.connectionDao = ((DaoFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getConnectionDao();

    }
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			/*
			 * TODO output your page here. You may use following sample code.
			 */
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet Index</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet Index at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		if (session.getAttribute(ATT_SESSION_USER) != null) {
			/*
			 * Redirection vers la page publique
			 */
			response.sendRedirect(request.getContextPath() + ERROR);
		} else {

			Boolean redirectBool = (Boolean) session.getAttribute(ATT_REDIRECT_BOOL);
			if (redirectBool != null) {
				request.setAttribute(ATT_REDIRECT_BOOL, redirectBool);
				session.removeAttribute(ATT_REDIRECT_BOOL);
			}
			
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(ATT_USER);
		String password = request.getParameter(ATT_PSW);
		System.out.println("- login : " + login);
		System.out.println("- password : " + password);

		HttpSession session = request.getSession();
                
		if (connectionDao.bind(login, password)) {
			System.out.println("<h2>Ok</h2>");
			session.setAttribute(ATT_SESSION_USER, login);
			response.sendRedirect(request.getContextPath() + urlRedirection);

		} else {
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			System.out.println(ATT_USER);
		}

	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
