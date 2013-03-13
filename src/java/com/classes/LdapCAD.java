package com.classes;

import javax.naming.ldap.LdapContext;

public class LdapCAD extends Ldap {
	
	public LdapCAD(LdapContext dirContext) {
		super(dirContext);
	}
}