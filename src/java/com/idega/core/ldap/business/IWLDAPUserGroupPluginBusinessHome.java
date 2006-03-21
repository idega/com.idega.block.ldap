/*
 * $Id: IWLDAPUserGroupPluginBusinessHome.java,v 1.1 2006/03/21 12:08:58 tryggvil Exp $
 * Created on Nov 30, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.core.ldap.business;

import com.idega.business.IBOHome;


/**
 * 
 *  Last modified: $Date: 2006/03/21 12:08:58 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:eiki@idega.com">eiki</a>
 * @version $Revision: 1.1 $
 */
public interface IWLDAPUserGroupPluginBusinessHome extends IBOHome {

	public IWLDAPUserGroupPluginBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
