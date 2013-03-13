package com.classes;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class Ldap {
        
	public LdapContext _dirContext;
	
	public Ldap(LdapContext dirContext){
		System.out.println("-- BEGIN Constructor Ldap -- : "+ dirContext);
		this._dirContext = dirContext;
		System.out.println("-- END Constructor Ldap -- : "+this._dirContext );
	}
	
	public void setContext(LdapContext dirContext) {
		_dirContext = dirContext;
	}
	
	public DirContext getContext(){
		return _dirContext;
	}
	
	public void close() throws NamingException{
		this._dirContext.close();
	}
	
	public boolean removeAttribute(String sEntry, String sAttributeName, String sAttributeValue){
		Attributes entry = null;
		try{
			if (Parameter.DEBUG_MODE){
				System.out.println("removeAttribute -> " + sAttributeName + " : " + sAttributeValue);
			}
			
		    // entry's attributes
		    Attribute att = new BasicAttribute(sAttributeName, sAttributeValue);
		    
	        // build the entry
		    entry = new BasicAttributes();
	        entry.put(att);
	        
	        // Edit the attributes
	        _dirContext.modifyAttributes(sEntry, DirContext.REMOVE_ATTRIBUTE, entry);
	        if (Parameter.DEBUG_MODE){
	        	System.out.println( "removeAttribute: remove entry " + sEntry + "Attribute: "+sAttributeName + " Value: " + sAttributeValue+ ".");
	        }
	        
		} catch (NamingException e) {
			System.err.println("Erreur lors de l'acces au serveur Ldap" + e);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void searchUser(String sAlstomID){
		try {
			SearchControls searchControls = new SearchControls();
			searchControls.setCountLimit(Parameter.iMaxResultAjaxSearch);
			
			sAlstomID = HelperLdap.escapeLDAPSearchFilter(sAlstomID);
		    NamingEnumeration<SearchResult> answer = this._dirContext.search(	"ou=people,o=alstom", 
																				"(uid="+sAlstomID+")",
																				searchControls);
		    
		    while(answer.hasMoreElements()){
		    	SearchResult sr =  answer.next();
				System.out.println("uid : "+sr.getAttributes().get("uid").get().toString());
			}
		}catch (NamingException e) {
			System.err.println("Erreur lors de l'acces au serveur Ldap" + e);
			e.printStackTrace();
		}
	}
	
	
	public boolean setAttribute(String sEntry, String sAttributeName, Object sAttributeValue, boolean bReplaceValue){
		Attributes entry = null;

		try{
				System.out.println("setAttribute -> " + sAttributeName + " : " + sAttributeValue); 
			
			
		    // entry's attributes
		    Attribute att = new BasicAttribute(sAttributeName, sAttributeValue);
		    
	        // build the entry
		    entry = new BasicAttributes();
	        entry.put(att);
	        if (bReplaceValue) {
	        	// Try to replace the attribute
	        	_dirContext.modifyAttributes(sEntry, DirContext.REPLACE_ATTRIBUTE, entry);
		        	System.out.println( "Replace on Entry: "+sEntry+" Attribute: "+sAttributeName+" Value: "+sAttributeValue+". bReplace: "+bReplaceValue);
		        
	        } else {
	        	// Try to add the attribute
		        _dirContext.modifyAttributes(sEntry, DirContext.ADD_ATTRIBUTE, entry);
		        	System.out.println( "Add on Entry: "+sEntry+" Attribute: "+sAttributeName+" Value: "+sAttributeValue+". bReplace: "+bReplaceValue);
		        
	        }


		} catch (Exception e) {
			try {				
				if (bReplaceValue) {
					// The attribute doesn't exists, add it.
					_dirContext.modifyAttributes(sEntry, DirContext.ADD_ATTRIBUTE, entry);
			        	System.out.println( "Add on Entry: "+sEntry+" Attribute: "+sAttributeName+" Value:"+sAttributeValue+". bReplace: "+bReplaceValue);
			    }
				
				else {
					// Add fails, the attribute already exists
					// Check in the schema if the attribute is Mono/Multi valued.
					Attribute singleValue = null;
					singleValue = _dirContext.getSchema("").getAttributes("AttributeDefinition/"+sAttributeName).get("SINGLE-VALUE");
					// If it's a mono value attribute, try to replace it.
					if (singleValue != null && singleValue.contains("true")) {
						_dirContext.modifyAttributes(sEntry, DirContext.REPLACE_ATTRIBUTE, entry);
				        	System.out.println( "Replace on Entry: "+sEntry+" Attribute: "+sAttributeName+" Value:"+sAttributeValue+". bReplace: "+bReplaceValue);
					}
				}
			} catch (Exception e1) {
				// Entry DN or Attribute Errors
					e1.printStackTrace();
					System.err.println("Error will accessing to the LDAP Server." + e);
				return false;
			}
		}
		return true;
	}
}