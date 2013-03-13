package com.classes;

import javax.naming.ldap.LdapContext;

public class CTLLdap {

	private LdapContext _dirContext;

	protected Ldap _ldap;

	public CTLLdap(){}
	
	protected CTLLdap(LdapContext dirContext){
		this._dirContext = dirContext;
		if (_dirContext != null){
			_ldap = new Ldap(_dirContext);
		}
	}
	
        public void close(){
            if(this._dirContext != null){
                try{
                    this._dirContext.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        
	public Ldap getLdap() {
		return _ldap;
	}
	public void setLdap(Ldap ldap) {
		_ldap = ldap;
	}
	
	public boolean removeAttribute(String sEntry, String sAttributeName, String sAttributeValue){
		return _ldap.removeAttribute(sEntry, sAttributeName, sAttributeValue);
	}
	
	public void searchUser(String sAlstomID){
		_ldap.searchUser(sAlstomID);
	}
	
	public boolean setAttribute(String sEntry, String sAttributeName, Object sAttributeValue, boolean bReplaceValue){
		return _ldap.setAttribute(sEntry, sAttributeName, sAttributeValue, bReplaceValue);
	}
	
	public void setDirContext(LdapContext dirContext) {
		this._dirContext = dirContext;
	}
	
	public LdapContext getDirContext() {
		return this._dirContext;
	}
	
}