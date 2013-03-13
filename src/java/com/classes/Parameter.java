package com.classes;

import java.util.Hashtable;
import javax.naming.Context;

public class Parameter{
	public static final int iMaxResultAjaxSearch = 30;
	public static final boolean DEBUG_MODE = true;
	
	public static Hashtable getParameterLDAPCAD(){
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://sacch10172.ad.sys:1390/");
            env.put(Context.SECURITY_PRINCIPAL, "CN=directory manager");
            env.put(Context.SECURITY_CREDENTIALS, "RadiusWIFI2");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("com.sun.jndi.ldap.connect.timeout", "5000");
            return env;
	}
        
        public static Hashtable getParameterLDAPCADBind(){
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://sacch10172.ad.sys:1390/");
            env.put(Context.SECURITY_PRINCIPAL, "CN=directory manager");
            env.put(Context.SECURITY_CREDENTIALS, "RadiusWIFI2");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("com.sun.jndi.ldap.connect.timeout", "5000");

            return env;
	}
}