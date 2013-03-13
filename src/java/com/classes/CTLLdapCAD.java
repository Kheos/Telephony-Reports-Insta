package com.classes;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;

public class CTLLdapCAD extends CTLLdap{
	
	public CTLLdapCAD(){
		System.out.println("-- BEGIN Constructor CTLLdapCAD  --");
		try {
			setDirContext(new InitialLdapContext(Parameter.getParameterLDAPCAD(), null));
			setLdap(new LdapCAD(getDirContext()));
		} catch (NamingException e){
			e.printStackTrace();
		}
		System.out.println("-- END Constructor CTLLdapCAD --");
	}
	
        public static boolean bind(String login, String password){
            if (Parameter.DEBUG_MODE){
                  System.out.println("-- BEGIN Bind Ldap User  --");
            }
            try {
                  Hashtable env = Parameter.getParameterLDAPCADBind();
                  // Overright login and password
				  
                  env.put(Context.SECURITY_PRINCIPAL, login);
                  env.put(Context.SECURITY_CREDENTIALS, password);
                  System.out.println(env.get(Context.SECURITY_PRINCIPAL));
                  System.out.println(env.get(Context.SECURITY_CREDENTIALS));
                  new InitialLdapContext(env, null);
            } catch (NamingException e){
                  if (Parameter.DEBUG_MODE){e.printStackTrace();}
                  return false;
            }
            if (Parameter.DEBUG_MODE){
                  System.out.println("-- END Bind Ldap User --");
            }
            return true;
      }
}