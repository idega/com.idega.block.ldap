/*
 * $Id: LDAPReplicationBusiness.java,v 1.1 2006/03/21 12:08:58 tryggvil Exp $
 * Created on Feb 14, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.core.ldap.replication.business;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import com.idega.business.IBOService;
import com.idega.core.ldap.IWLDAPConstants;
import com.idega.user.data.User;


/**
 * 
 *  Last modified: $Date: 2006/03/21 12:08:58 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:eiki@idega.com">eiki</a>
 * @version $Revision: 1.1 $
 */
public interface LDAPReplicationBusiness extends IBOService, LDAPReplicationConstants, IWLDAPConstants {

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#deleteReplicator
	 */
	public void deleteReplicator(int replicatorNumber) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#copyPropertyBetweenReplicators
	 */
	public void copyPropertyBetweenReplicators(String key, int copyFromReplicatorNumber, int copyToReplicatorNumber)
			throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#setReplicationProperty
	 */
	public void setReplicationProperty(String key, int replicatorNumber, String value) throws IOException,
			java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#getReplicationProperty
	 */
	public String getReplicationProperty(String key, int replicatorNumber) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#removeAllPropertiesOfReplicator
	 */
	public void removeAllPropertiesOfReplicator(int replicatorNumber) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#getNumberOfReplicators
	 */
	public int getNumberOfReplicators() throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#setNumberOfReplicators
	 */
	public void setNumberOfReplicators(int number) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#startReplicator
	 */
	public boolean startReplicator(int replicatorNumber) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#stopReplicator
	 */
	public boolean stopReplicator(int replicatorNumber) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#startAllReplicators
	 */
	public void startAllReplicators() throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#startOrStopAllReplicators
	 */
	public void startOrStopAllReplicators(String startOrStop) throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#getDefaultIWLDAPWebserviceURI
	 */
	public String getDefaultIWLDAPWebserviceURI() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#stopAllReplicators
	 */
	public void stopAllReplicators() throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#createNewReplicationSettings
	 */
	public void createNewReplicationSettings() throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#getReplicationSettings
	 */
	public Properties getReplicationSettings() throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#storeReplicationProperties
	 */
	public void storeReplicationProperties() throws IOException, java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#registerReplicationListener
	 */
	public boolean registerReplicationListener(String serverName, int portNumber, String IWLDAPWSUri)
			throws java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#getReplicationListenerMap
	 */
	public Map getReplicationListenerMap() throws java.rmi.RemoteException;

	/**
	 * @see com.idega.core.ldap.replication.business.LDAPReplicationBusinessBean#replicateUserByUUID
	 */
	public User replicateUserByUUID(String userUUID, String replicationServerHostName, int ldapPort)
			throws NamingException, CreateException, IOException, java.rmi.RemoteException;
}
