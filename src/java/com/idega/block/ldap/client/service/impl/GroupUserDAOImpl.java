/**
 * @(#)GroupUserDAOImpl.java    1.0.0 17:12:19
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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.ldap.client.constants.GroupOfUniqueNames;
import com.idega.block.ldap.client.service.ConnectionService;
import com.idega.block.ldap.client.service.GroupDAO;
import com.idega.block.ldap.client.service.GroupUserDAO;
import com.idega.block.ldap.client.service.UserDAO;
import com.idega.block.ldap.util.IWLDAPUtil;
import com.idega.core.accesscontrol.data.LoginTable;
import com.idega.core.accesscontrol.data.LoginTableHome;
import com.idega.core.accesscontrol.event.LoggedInUserCredentials;
import com.idega.core.business.DefaultSpringBean;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.user.data.bean.Group;
import com.idega.user.data.bean.GroupType;
import com.idega.user.data.bean.User;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;
import com.unboundid.ldap.sdk.AddRequest;
import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.ModifyRequest;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;

/**
 * @version 1.0.0 2017-09-11
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Service(GroupUserDAO.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class GroupUserDAOImpl extends DefaultSpringBean implements GroupUserDAO, ApplicationListener<LoggedInUserCredentials> {

	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private com.idega.user.dao.UserDAO userJPADAO;

	@Autowired
	private ConnectionService connectionService;

	private GroupDAO getGroupDAO() {
		if (this.groupDAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.groupDAO;
	}

	private UserDAO getUserDAO() {
		if (this.userDAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.userDAO;
	}

	private com.idega.user.dao.UserDAO getUserJPADAO() {
		if (this.userJPADAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.userJPADAO;
	}

	private ConnectionService getConnectionService() {
		if (this.connectionService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.connectionService;
	}

	private LoginTableHome getLoginTableHome() {
		try {
			return (LoginTableHome) IDOLookup.getHome(LoginTable.class);
		} catch (IDOLookupException e) {
			getLogger().log(Level.WARNING, "Failed to get " + LoginTableHome.class.getSimpleName() + " cause of: ", e);
		}

		return null;
	}

	private String getGroupName(Group group) {
		if (group != null) {
			String name = group.getName();
			if (StringUtil.isEmpty(name)) {
				name = group.getUniqueId();
				if (StringUtil.isEmpty(name)) {
					name = group.getId();
				}
			}

			return name;
		}

		return null;
	}

	/**
	 *
	 * <p>Creates {@link GroupOfUniqueNames} entity</p>
	 * @param group to create entity for, not <code>null</code>
	 * @param distinguishedName of {@link Group} to create entity for, not <code>null</code>
	 * @param userDistinguishedName of {@link User} to add to {@link Group}, not <code>null</code>
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 */
	private void create(Group group, DN distinguishedName, DN userDistinguishedName) throws LDAPException, GeneralSecurityException {
		if (group != null && distinguishedName != null && userDistinguishedName != null) {

			String timeout = getApplicationProperty(
					ConnectionService.PROPERTY_RESPONSE_TIMEOUT,
					ConnectionService.DEFAULT_RESPONSE_TIMEOUT.toString());

			StringBuilder relationDistinguishedName = new StringBuilder()
					.append(GroupOfUniqueNames.COMMON_NAME)
					.append(CoreConstants.EQ)
					.append(getGroupName(group))
					.append(CoreConstants.COMMA)
					.append(distinguishedName.toString());

			AddRequest request = new AddRequest(new DN(relationDistinguishedName.toString()));
			request.setResponseTimeoutMillis(Long.valueOf(timeout));
			request.addAttribute("objectClass", "top");

			/*
			 * Organizational unit object information
			 */
			request.addAttribute("objectClass", GroupOfUniqueNames.OBJECT_CLASS);
			request.addAttribute(GroupOfUniqueNames.COMMON_NAME, getGroupName(group));
			request.addAttribute(GroupOfUniqueNames.ORGANIZATIONAL_UNIT_NAME, getGroupName(group));
			request.addAttribute(GroupOfUniqueNames.UNIQUE_MEMBER, userDistinguishedName.toString());
			request.addAttribute(GroupOfUniqueNames.SEE, distinguishedName.toString());

			if (!StringUtil.isEmpty(group.getUniqueId())) {
				request.addAttribute(GroupOfUniqueNames.DESCRIPTION, group.getUniqueId());
			}

			GroupType type = group.getGroupType();
			if (type != null && !StringUtil.isEmpty(type.getGroupType())) {
				request.addAttribute(GroupOfUniqueNames.BUSINESS_CATEGORY, type.getGroupType());
			}

			LDAPConnection connection = getConnectionService().getConnection();
			LDAPResult response = connection.add(request);
			connection.close();

			if (response.getResultCode().intValue() != ResultCode.SUCCESS_INT_VALUE) {
				throw new RuntimeException(response.getResultString());
			}
		}
	}

	/**
	 *
	 * <p>Creates or Updates {@link GroupOfUniqueNames} entity</p>
	 * @param group to create entity for, not <code>null</code>
	 * @param distinguishedName of {@link Group} to create entity for, not <code>null</code>
	 * @param userDistinguishedName of {@link User} to add to {@link Group}, not <code>null</code>
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 */
	private void update(Group group, DN distinguishedName, DN userDistinguishedName) throws LDAPException, GeneralSecurityException {
		if (group != null && distinguishedName != null && userDistinguishedName != null) {

			StringBuilder relationDistinguishedName = new StringBuilder()
					.append(GroupOfUniqueNames.COMMON_NAME)
					.append(CoreConstants.EQ)
					.append(getGroupName(group))
					.append(CoreConstants.COMMA)
					.append(distinguishedName.toString());

			SearchResult existingRelations = null;
			try {
				existingRelations = getConnectionService().findByDN(
							GROUP_SEARCH_FILTER,
							new DN(relationDistinguishedName.toString()));
			} catch (LDAPSearchException e) {
				getLogger().log(Level.WARNING, "Entity by path not found: " + relationDistinguishedName.toString());
			}

			if (existingRelations == null || existingRelations.getEntryCount() == 0) {
				create(group, distinguishedName, userDistinguishedName);
			} else {
				ArrayList<Modification> modifications = new ArrayList<>();

				/*
				 * Organizational unit object information
				 */
				String groupName = getGroupName(group);
				if (!StringUtil.isEmpty(groupName)) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							GroupOfUniqueNames.COMMON_NAME,
							groupName));
					modifications.add(new Modification(
							ModificationType.REPLACE,
							GroupOfUniqueNames.ORGANIZATIONAL_UNIT_NAME,
							groupName));
				}

				if (!StringUtil.isEmpty(group.getUniqueId())) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							GroupOfUniqueNames.DESCRIPTION,
							group.getUniqueId()));
				}

				GroupType type = group.getGroupType();
				if (type != null && !StringUtil.isEmpty(type.getGroupType())) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							GroupOfUniqueNames.BUSINESS_CATEGORY,
							type.getGroupType()));
				}

				/*
				 * Adding user
				 */

				boolean userExists = Boolean.FALSE;
				List<SearchResultEntry> entries = existingRelations.getSearchEntries();
				if (!ListUtil.isEmpty(entries)) {
					for (SearchResultEntry entry : entries) {
						Attribute userDNCollection = entry.getAttribute(GroupOfUniqueNames.UNIQUE_MEMBER);
						if (userDNCollection != null) {
							String value = userDNCollection.getValue();
							if (value != null && value.contains(userDistinguishedName.toString())) {
								userExists = Boolean.TRUE;
							}
						}
					}
				}

				if (!userExists) {
					modifications.add(new Modification(
							ModificationType.ADD,
							GroupOfUniqueNames.UNIQUE_MEMBER,
							userDistinguishedName.toString()));
				}

				ModifyRequest modificationRequest = new ModifyRequest(new DN(relationDistinguishedName.toString()), modifications);
				LDAPConnection connection = getConnectionService().getConnection();
				LDAPResult response = connection.modify(modificationRequest);
				connection.close();

				if (response.getResultCode().intValue() != ResultCode.SUCCESS_INT_VALUE) {
					throw new RuntimeException(response.getResultString());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.GroupUserDAO#update(com.idega.user.data.bean.Group, com.idega.user.data.bean.User)
	 */
	@Override
	public void update(Group group, User user, String password) throws LDAPException, GeneralSecurityException {
		TreeMap<DN, Group> distinguishedNames = getGroupDAO().getDistinguishedNames(group);

		User updatedUser = getUserDAO().update(user, password);
		if (updatedUser != null) {
			DN userDistinguishedName = getUserDAO().getDistinguishedName(updatedUser != null ? updatedUser.getUniqueId() : null);
			for (DN distinguishedName : distinguishedNames.keySet()) {
				Group updatedGroup = getGroupDAO().update(distinguishedName, distinguishedNames.get(distinguishedName));
				if (updatedGroup != null) {
					update(updatedGroup, distinguishedName, userDistinguishedName);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(LoggedInUserCredentials event) {
		if (event == null || !IWLDAPUtil.getInstance().isAutoSyncEnabled()) {
			return;
		}

		try {
			Thread synchronizer = new Thread(new Runnable() {

				@Override
				public void run() {
					LoginTable login = null;
					try {
						login = getLoginTableHome().findByLogin(event.getUserName());
					} catch (Exception e) {
						getLogger().log(Level.WARNING, "Failed to find user by user name: " + event.getUserName(), e);
					}

					if (login == null) {
						return;
					}
					User user = getUserJPADAO().getUser(login.getUserId());
					if (user == null) {
						return;
					}

					try {
						update(user.getGroup(), user, event.getPassword());
					} catch (Exception e) {
						getLogger().log(Level.WARNING, "Failed to sync user: " + event, e);
					}
				}

			});
			synchronizer.start();
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error while trying to sync " + event, e);
		}
	}
}
