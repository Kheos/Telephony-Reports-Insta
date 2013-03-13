package com.classes;

import java.util.ArrayList;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;


/**
 * Class containing methods for LDAP values
 * @author e_bgirau
 *
 */
public class HelperLdap {

	/**
	 * Get the values of an multi-valued attribute
	 * @param sAttributeName the attribute's name
	 * @param atts all attributes retrieved
	 * @return the values
	 */
	public static ArrayList<String> getMultiValues(String sAttributeName, Attributes atts){
		ArrayList<String> alMember = new ArrayList<String>();
		
		if (sAttributeName != null && atts != null){
			Attribute a;
	    	NamingEnumeration e;
	    	String sMemberTmp = null;
	    	
		    try{
				// Get all values of the attribute
				a = atts.get(sAttributeName);
				if (a != null){
					e = a.getAll();
					if (e != null){
					    while( e.hasMore()) {
					    	sMemberTmp = (String)e.next();
							if (sMemberTmp != null){
								alMember.add(sMemberTmp);
							}
					    }
					}
				}
		    } catch (NamingException e1){
		    	if (Parameter.DEBUG_MODE){
		    		e1.printStackTrace();
		    	}
				return null;
			}
		}
		
	    return alMember;
	}
	
	public static ArrayList<byte[]> getMultiValuesBinaries(String sAttributeName, Attributes atts){
		ArrayList<byte[]> alMember = new ArrayList<byte[]>();
		
		if (sAttributeName != null && atts != null){
			Attribute a;
	    	NamingEnumeration e;
	    	byte[] sMemberTmp = null;
	    	
		    try{
				// Get all values of the attribute
				a = atts.get(sAttributeName);
				if (a != null){
					e = a.getAll();
					if (e != null){
					    while( e.hasMore()) {
					    	sMemberTmp = (byte[])e.next();
							if (sMemberTmp != null){
								alMember.add(sMemberTmp);
							}
					    }
					}
				}
		    } catch (NamingException e1){
		    	if (Parameter.DEBUG_MODE){
		    		e1.printStackTrace();
		    	}
				return null;
			}
		}
		
	    return alMember;
	}
	
	/**
	 * Clean a distinguished name
	 * @param sDistinguishedName the distinguished name
	 * @return the cleaned distinguished name
	 */
	public static String cleanDistinguishedName(String sDistinguishedName){
		if (sDistinguishedName != null){
			sDistinguishedName = sDistinguishedName.replaceAll(" ou=", "ou=");
			sDistinguishedName = sDistinguishedName.replaceAll(" cn=", "cn=");
			sDistinguishedName = sDistinguishedName.replaceAll(" o=", "o=");
			
			sDistinguishedName = sDistinguishedName.toLowerCase();
		}
		return sDistinguishedName;
	}
	
	/**
	 * Escape a distinguished name
	 * @param name the distinguished name
	 * @return the escaped distinguished name
	 */
	public static String escapeDN(String name) {
       StringBuffer sb = new StringBuffer();
       if ((name.length() > 0) && ((name.charAt(0) == ' ') || (name.charAt(0) == '#'))) {
           sb.append('\\'); // add the leading backslash if needed
       }
       for (int i = 0; i < name.length(); i++) {
           char curChar = name.charAt(i);
           switch (curChar) {
               case '\\':
                   sb.append("\\\\");
                   break;
               case ',':
                   sb.append("\\,");
                   break;
               case '+':
                   sb.append("\\+");
                   break;
               case '"':
                   sb.append("\\\"");
                   break;
               case '<':
                   sb.append("\\<");
                   break;
               case '>':
                   sb.append("\\>");
                   break;
               case ';':
                   sb.append("\\;");
                   break;
               default:
                   sb.append(curChar);
           }
       }
       if ((name.length() > 1) && (name.charAt(name.length() - 1) == ' ')) {
           sb.insert(sb.length() - 1, '\\'); // add the trailing backslash if needed
       }
       return sb.toString();
   }

	/**
	 * Escape LDAP search filter 
	 * @param filter the filter to escape
	 * @return the escaped filter
	 */
	public static final String escapeLDAPSearchFilter(String filter) {
       StringBuffer sb = new StringBuffer(); // If using JDK >= 1.5 consider using StringBuilder
       for (int i = 0; i < filter.length(); i++) {
           char curChar = filter.charAt(i);
           switch (curChar) {
               case '\\': {
                   sb.append("\\5c");
                   break;
               }
               case '*': {
                   sb.append("\\2a");
                   break;
               }
               case '(': {
                   sb.append("\\28");
                   break;
               }
               case ')': {
                   sb.append("\\29");
                   break;
               }
               case '\u0000': {
                   sb.append("\\00"); 
                   break;
               }
               case '/' : {
            	   sb.append("\\2F");
            	   break;
               }
               default: {
                   sb.append(curChar);
               }
           }
       }
       return sb.toString();
	}
}

								