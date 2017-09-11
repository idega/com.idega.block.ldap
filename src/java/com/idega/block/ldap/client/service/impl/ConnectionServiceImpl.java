/**
 * @(#)ConnectionServiceImpl.java    1.0.0 14:38:57
 *
 * Idega Software hf. Source Code Licence Agreement x
 *
 * This agreement, made this 10th of February 2006 by and between 
 * Idega Software hf., a business formed and operating under laws 
 * of Iceland, having its principal place of business in Reykjavik, 
 * Iceland, hereinafter after referred to as "Manufacturer" and Agura 
 * IT hereinafter referred to as "Licensee".
 * 1.  License Grant: Upon completion of this agreement, the source 
 *     code that may be made available according to the documentation for 
 *     a particular software product (Software) from Manufacturer 
 *     (Source Code) shall be provided to Licensee, provided that 
 *     (1) funds have been received for payment of the License for Software and 
 *     (2) the appropriate License has been purchased as stated in the 
 *     documentation for Software. As used in this License Agreement, 
 *     Licensee shall also mean the individual using or installing 
 *     the source code together with any individual or entity, including 
 *     but not limited to your employer, on whose behalf you are acting 
 *     in using or installing the Source Code. By completing this agreement, 
 *     Licensee agrees to be bound by the terms and conditions of this Source 
 *     Code License Agreement. This Source Code License Agreement shall 
 *     be an extension of the Software License Agreement for the associated 
 *     product. No additional amendment or modification shall be made 
 *     to this Agreement except in writing signed by Licensee and 
 *     Manufacturer. This Agreement is effective indefinitely and once
 *     completed, cannot be terminated. Manufacturer hereby grants to 
 *     Licensee a non-transferable, worldwide license during the term of 
 *     this Agreement to use the Source Code for the associated product 
 *     purchased. In the event the Software License Agreement to the 
 *     associated product is terminated; (1) Licensee's rights to use 
 *     the Source Code are revoked and (2) Licensee shall destroy all 
 *     copies of the Source Code including any Source Code used in 
 *     Licensee's applications.
 * 2.  License Limitations
 *     2.1 Licensee may not resell, rent, lease or distribute the 
 *         Source Code alone, it shall only be distributed as a 
 *         compiled component of an application.
 *     2.2 Licensee shall protect and keep secure all Source Code 
 *         provided by this this Source Code License Agreement. 
 *         All Source Code provided by this Agreement that is used 
 *         with an application that is distributed or accessible outside
 *         Licensee's organization (including use from the Internet), 
 *         must be protected to the extent that it cannot be easily 
 *         extracted or decompiled.
 *     2.3 The Licensee shall not resell, rent, lease or distribute 
 *         the products created from the Source Code in any way that 
 *         would compete with Idega Software.
 *     2.4 Manufacturer's copyright notices may not be removed from 
 *         the Source Code.
 *     2.5 All modifications on the source code by Licencee must 
 *         be submitted to or provided to Manufacturer.
 * 3.  Copyright: Manufacturer's source code is copyrighted and contains 
 *     proprietary information. Licensee shall not distribute or 
 *     reveal the Source Code to anyone other than the software 
 *     developers of Licensee's organization. Licensee may be held 
 *     legally responsible for any infringement of intellectual property 
 *     rights that is caused or encouraged by Licensee's failure to abide 
 *     by the terms of this Agreement. Licensee may make copies of the 
 *     Source Code provided the copyright and trademark notices are 
 *     reproduced in their entirety on the copy. Manufacturer reserves 
 *     all rights not specifically granted to Licensee.
 *
 * 4.  Warranty & Risks: Although efforts have been made to assure that the 
 *     Source Code is correct, reliable, date compliant, and technically 
 *     accurate, the Source Code is licensed to Licensee as is and without 
 *     warranties as to performance of merchantability, fitness for a 
 *     particular purpose or use, or any other warranties whether 
 *     expressed or implied. Licensee's organization and all users 
 *     of the source code assume all risks when using it. The manufacturers, 
 *     distributors and resellers of the Source Code shall not be liable 
 *     for any consequential, incidental, punitive or special damages 
 *     arising out of the use of or inability to use the source code or 
 *     the provision of or failure to provide support services, even if we 
 *     have been advised of the possibility of such damages. In any case, 
 *     the entire liability under any provision of this agreement shall be 
 *     limited to the greater of the amount actually paid by Licensee for the 
 *     Software or 5.00 USD. No returns will be provided for the associated 
 *     License that was purchased to become eligible to receive the Source 
 *     Code after Licensee receives the source code. 
 */
package com.idega.block.ldap.client.service.impl;

import java.security.GeneralSecurityException;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.ldap.client.constants.InternetOrganizationalPerson;
import com.idega.block.ldap.client.constants.Person;
import com.idega.block.ldap.client.service.ConnectionService;
import com.idega.core.business.DefaultSpringBean;
import com.idega.util.CoreConstants;
import com.idega.util.StringUtil;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.util.ssl.AggregateTrustManager;
import com.unboundid.util.ssl.HostNameTrustManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.ValidityDateTrustManager;

/**
 * @version 1.0.0 2017-08-10
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ConnectionServiceImpl extends DefaultSpringBean implements ConnectionService {

	private LDAPConnectionOptions connectionOptions = null;

	private SSLSocketFactory socketFactory = null;

	private TrustManager trustManager = null;

	private TrustManager getTrustManager() {
		if (this.trustManager == null) {
			String domain = getApplicationProperty(PROPERTY_DOMAIN, DEFAULT_DOMAIN);

			this.trustManager = new AggregateTrustManager(Boolean.TRUE, 
					new HostNameTrustManager(Boolean.FALSE, domain),
					new ValidityDateTrustManager());
		}

		return this.trustManager;
	}

	private LDAPConnectionOptions getConnectionOptions() {
		if (this.connectionOptions == null) {
			String timeout = getApplicationProperty(PROPERTY_TIMEOUT, DEFAULT_TIMEOUT.toString());
			
			this.connectionOptions = new LDAPConnectionOptions();
			this.connectionOptions.setAbandonOnTimeout(Boolean.TRUE);
			this.connectionOptions.setConnectTimeoutMillis(Integer.valueOf(timeout));
		}

		return this.connectionOptions;
	}

	private SSLSocketFactory getSocketFactory() throws GeneralSecurityException {
		if (this.socketFactory == null) {
		    this.socketFactory = new SSLUtil(null, getTrustManager()).createSSLSocketFactory();
		}

		return this.socketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.ConnectionService#getConnection()
	 */
	@Override
	public LDAPConnection getConnection() throws GeneralSecurityException, LDAPException {
		String domain = getApplicationProperty(PROPERTY_DOMAIN, DEFAULT_DOMAIN);
		String port = getApplicationProperty(PROPERTY_PORT, DEFAULT_PORT);
		String adminDN = getApplicationProperty(PROPERTY_ADMIN_DN, DEFAULT_ADMIN_DN);
		String adminDNPassword = getApplicationProperty(PROPERTY_ADMIN_DN_PASSWORD, DEFAULT_ADMIN_DN_PASSWORD);
		adminDNPassword = "r0undp3gs";

		return new LDAPConnection(getSocketFactory(), getConnectionOptions(), domain, Integer.valueOf(port), adminDN, adminDNPassword);
	}

	@Override
	public SearchResult findByDN(Filter filter, DN distinguishedName) throws LDAPSearchException, LDAPException, GeneralSecurityException {
		String timeout = getApplicationProperty(PROPERTY_RESPONSE_TIMEOUT, DEFAULT_RESPONSE_TIMEOUT.toString());
		String size = getApplicationProperty(PROPERTY_RESPONSE_SIZE_LIMIT, DEFAULT_RESPONSE_SIZE_LIMIT.toString());

		SearchRequest request = new SearchRequest(distinguishedName.toString(), SearchScope.SUB, filter);
		request.setSizeLimit(Integer.valueOf(size));
		request.setTimeLimitSeconds(Integer.valueOf(timeout));
		
		LDAPConnection connection = getConnection();
		SearchResult response = connection.search(request);
		connection.close();

		return response;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.ConnectionService#findBy(com.unboundid.ldap.sdk.Filter, java.lang.String[])
	 */
	@Override
	public SearchResult findByDN(Filter filter, String commaSeparatedDN) throws LDAPSearchException, LDAPException, GeneralSecurityException {
		String baseDN = getApplicationProperty(PROPERTY_BASE_DN, DEFAULT_BASE_DN);
		if (!StringUtil.isEmpty(baseDN)) {
			return findByDN(filter, new DN(commaSeparatedDN + "," + baseDN));
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.ConnectionService#findByUUID(com.unboundid.ldap.sdk.Filter, java.lang.String)
	 */
	@Override
	public SearchResult findByUUID(Filter filter, String organizationalUnit, String uuid) throws LDAPSearchException, LDAPException, GeneralSecurityException {
		if (!StringUtil.isEmpty(uuid)) {
			return findByDN(filter, InternetOrganizationalPerson.USER_ID + CoreConstants.EQ + uuid + "," + organizationalUnit);
		}

		throw new IllegalArgumentException("UUID is not provided, but required!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.ConnectionService#findByCN(com.unboundid.ldap.sdk.Filter, java.lang.String)
	 */
	@Override
	public SearchResult findByCN(Filter filter, String name) throws LDAPSearchException, LDAPException, GeneralSecurityException {
		if (!StringUtil.isEmpty(name)) {
			return findByDN(filter, Person.COMMON_NAME + CoreConstants.EQ + name);
		}

		throw new IllegalArgumentException("Name is not provided, but required!");
	}
}
