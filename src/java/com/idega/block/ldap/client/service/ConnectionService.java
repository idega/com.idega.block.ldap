/**
 * @(#)ConnectionService.java    1.0.0 11:16:37
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
package com.idega.block.ldap.client.service;

import java.security.GeneralSecurityException;

import com.idega.user.data.bean.Group;
import com.idega.user.data.bean.User;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;

/**
 * <p>A service to make connection to LDAP server</p>
 *
 * @version 1.0.0 2017-08-09
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface ConnectionService {

	/**
	 * No timeout by default
	 */
	static final Integer DEFAULT_TIMEOUT = 0;

	/**
	 * Application property for setting LDAP connection timeout
	 */
	static final String PROPERTY_TIMEOUT = "ldap.timeout";

	/**
	 * No restrictions to response size by default
	 */
	static final Integer DEFAULT_RESPONSE_SIZE_LIMIT = 0;

	/**
	 * Application property defines how much data could be fetched in one request
	 */
	static final String PROPERTY_RESPONSE_SIZE_LIMIT = "ldap.response.size";

	/**
	 * No restrictions to response size by default
	 */
	static final Integer DEFAULT_RESPONSE_TIMEOUT = 0;

	/**
	 * Application property defines how much data could be fetched in one request
	 */
	static final String PROPERTY_RESPONSE_TIMEOUT = "ldap.response.timeout";

	static final String DEFAULT_PROTOCOL = "ldaps";

	/**
	 * Application property for setting LDAP protocol
	 */
	static final String PROPERTY_PROTOCOL = "ldap.protocol";

	/**
	 * Default LDAP server connection domain address is ldap.idega.is because organization name is IDEGA and it is 
	 * located in Iceland
	 */
	static final String DEFAULT_DOMAIN = "ldap.idega.is";

	/**
	 * Application property for setting domain name or IP address of LDAP server
	 */
	static final String PROPERTY_DOMAIN = "ldap.domain";

	/**
	 * Default port is SSL port for security reasons
	 */
	static final String DEFAULT_PORT = "636";

	/**
	 * Application property for setting port of LDAP server
	 */
	static final String PROPERTY_PORT = "ldap.port";

	/**
	 * Default base DN of LDAP matches domain name it will be accessing
	 */
	static final String DEFAULT_BASE_DN = "dc=ldap,dc=idega,dc=is";

	/**
	 * Application property for setting base directory of LDAP server
	 */
	static final String PROPERTY_BASE_DN = "ldap.dn.base";

	/**
	 * Default groups directory DN 
	 */
	static final String DEFAULT_GROUPS_DN = "ou=Groups," + DEFAULT_BASE_DN;

	/**
	 * Application property for setting base groups directory of LDAP server
	 */
	static final String PROPERTY_GROUPS_DN = "ldap.dn.groups";

	/**
	 * Default admin directory DN 
	 */
	static final String DEFAULT_ADMIN_DN = "cn=ldapadm," + DEFAULT_BASE_DN;

	/**
	 * Application property for setting base groups directory of LDAP server
	 */
	static final String PROPERTY_ADMIN_DN = "ldap.dn.admin";

	/**
	 * Default admin directory DN password 
	 */
	static final String DEFAULT_ADMIN_DN_PASSWORD = "";

	/**
	 * Application property for setting base admin directory password of LDAP server
	 */
	static final String PROPERTY_ADMIN_DN_PASSWORD = "ldap.dn.admin.pass";
	
	static final Filter GROUP_SEARCH_FILTER = Filter.createEqualityFilter("objectClass", "group");
	
	/**
	 * 
	 * @return connection object to LDAP server with properties configured on application
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 */
	LDAPConnection getConnection() throws GeneralSecurityException, LDAPException;

	/**
	 * 
	 * @param filter is one of:
	 * <li>{@link Constants#USER_SEARCH_FILTER}</li>
	 * <li>{@link Constants#GROUP_SEARCH_FILTER}</li>
	 * @param commaSeparatedDN is comma separated standard LDAP strings like "cn=Victor"
	 * @return results or <code>null</code> on failure;
	 * @throws LDAPSearchException if the search does not complete successfully, or if a problem is encountered while 
	 * sending the request or reading the response. If one or more entries or references were returned before the 
	 * failure was encountered, then the LDAPSearchException object may be examined to obtain information about those 
	 * entries and/or references.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 */
	SearchResult findByDN(Filter filter, String commaSeparatedDN)
			throws LDAPSearchException, LDAPException, GeneralSecurityException;

	/**
	 * 
	 * @param filter is one of:
	 * <li>{@link Constants#USER_SEARCH_FILTER}</li>
	 * <li>{@link Constants#GROUP_SEARCH_FILTER}</li>
	 * @param uuid is {@link User#getUniqueId()} or similar stuff, not <code>null</code>
	 * @return results or <code>null</code> on failure;
	 * @throws LDAPSearchException if the search does not complete successfully, or if a problem is encountered while 
	 * sending the request or reading the response. If one or more entries or references were returned before the 
	 * failure was encountered, then the LDAPSearchException object may be examined to obtain information about those 
	 * entries and/or references.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 */
	SearchResult findByUUID(Filter filter, String organizationalUnit, String uuid)
			throws LDAPSearchException, LDAPException, GeneralSecurityException;

	/**
	 * 
	 * @param filter is one of:
	 * <li>{@link Constants#USER_SEARCH_FILTER}</li>
	 * <li>{@link Constants#GROUP_SEARCH_FILTER}</li>
	 * @param name of {@link User} or {@link Group}, not <code>null</code>
	 * @return results or <code>null</code> on failure;
	 * @throws LDAPSearchException if the search does not complete successfully, or if a problem is encountered while 
	 * sending the request or reading the response. If one or more entries or references were returned before the 
	 * failure was encountered, then the LDAPSearchException object may be examined to obtain information about those 
	 * entries and/or references.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 */
	SearchResult findByCN(Filter filter, String name)
			throws LDAPSearchException, LDAPException, GeneralSecurityException;
}
