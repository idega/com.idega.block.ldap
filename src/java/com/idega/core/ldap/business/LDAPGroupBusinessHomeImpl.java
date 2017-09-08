package com.idega.core.ldap.business;

public class LDAPGroupBusinessHomeImpl extends com.idega.business.IBOHomeImpl implements LDAPGroupBusinessHome {

	private static final long serialVersionUID = 7188495222012153774L;

	protected Class getBeanInterfaceClass() {
		return LDAPGroupBusiness.class;
	}

	public LDAPGroupBusiness create() throws javax.ejb.CreateException {
		return (LDAPGroupBusiness) super.createIBO();
	}
}