/**
 * @(#)GroupDAOImpl.java    1.0.0 09:20:03
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.ejb.FinderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.ldap.client.constants.OrganizationalUnit;
import com.idega.block.ldap.client.service.ConnectionService;
import com.idega.block.ldap.client.service.GroupDAO;
import com.idega.core.business.DefaultSpringBean;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.user.data.GroupHome;
import com.idega.user.data.bean.Group;
import com.idega.user.data.bean.GroupType;
import com.idega.util.CoreConstants;
import com.idega.util.DBUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;
import com.unboundid.ldap.sdk.AddRequest;
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
@Service(GroupDAO.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class GroupDAOImpl extends DefaultSpringBean implements GroupDAO {

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private com.idega.user.dao.GroupDAO groupDAO;

	private ConnectionService getConnectionService() {
		if (this.connectionService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.connectionService;
	}

	private com.idega.user.dao.GroupDAO getGroupDAO() {
		if (this.groupDAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.groupDAO;
	}

	private GroupHome getGroupHome() {
		try {
			return (GroupHome) IDOLookup.getHome(com.idega.user.data.Group.class);
		} catch (IDOLookupException e) {
			getLogger().log(Level.WARNING, "Failed to get " + GroupHome.class + " cause of: ", e);
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
	 * @param group to get distinguished names tree for, not <code>null</code>
	 * @return {@link Map} of distinguished name part and {@link Group} of that name or {@link Collections#emptyMap()}
	 * on failure;
	 */
	private HashMap<String, Group> getPaths(Group group) {
		HashMap<String, Group> paths = new HashMap<>();
		if (group != null) {
			group = DBUtil.getInstance().lazyLoad(group);
			List<Group> parentGroups = getGroupDAO().findParentGroups(group.getID());
			if (!ListUtil.isEmpty(parentGroups)) {
				for (Group parentGroup : parentGroups) {
					HashMap<String, Group> parentPaths = getPaths(parentGroup);
					for (String parentPath : parentPaths.keySet()) {
						Group pathGroup = parentPaths.get(parentPath);
						if (parentGroups.contains(pathGroup)) {
							/*
							 * Ensuring that path is appended only to direct parent group
							 */


							StringBuilder path = new StringBuilder(OrganizationalUnit.ORGANIZATIONAL_UNIT_NAME).append(CoreConstants.EQ).append(getGroupName(group));
							path.append(CoreConstants.COMMA);
							path.append(parentPath);
							paths.put(path.toString(), group);
						}
					}

					/*
					 * Putting all parent group paths
					 */
					paths.putAll(parentPaths);
				}
			} else {
				StringBuilder path = new StringBuilder(OrganizationalUnit.ORGANIZATIONAL_UNIT_NAME).append(CoreConstants.EQ).append(getGroupName(group));
				paths.put(path.toString(), group);
			}
		}

		return paths;
	}

	/**
	 *
	 * @param group to get tree of {@link DN}s fo, not <code>null</code>
	 * @return distinguished name objects or {@link Collections#emptyMap()} on failure
	 * @throws LDAPException if the provided string cannot be parsed as a valid DN.
	 */
	@Override
	public TreeMap<DN, Group> getDistinguishedNames(Group group) throws LDAPException {
		TreeMap<DN, Group> distinguishedNames = new TreeMap<>();

		String baseDistinguishedName = getApplicationProperty(PROPERTY_GROUPS_DN, DEFAULT_GROUPS_DN);
		if (!StringUtil.isEmpty(baseDistinguishedName)) {
			HashMap<String, Group> paths = getPaths(group);
			for (String path : paths.keySet()) {
				StringBuilder fullPath = new StringBuilder(path);
				fullPath.append(CoreConstants.COMMA);
				fullPath.append(baseDistinguishedName);

				distinguishedNames.put(new DN(fullPath.toString()), paths.get(path));
			}
		}

		return distinguishedNames;
	}

	/**
	 *
	 * @param distinguishedName to fetch results by, not <code>null</code>
	 * @return entities by {@link DN} or {@link Collections#emptyList()} on failure
	 * @throws LDAPSearchException if the search does not complete successfully, or if a problem is encountered while
	 * sending the request or reading the response. If one or more entries or references were returned before the
	 * failure was encountered, then the LDAPSearchException object may be examined to obtain information about those
	 * entries and/or references.
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 */
	private Collection<Group> findByDN(DN distinguishedName) throws LDAPSearchException, LDAPException, GeneralSecurityException {
		ArrayList<Group> entities = new ArrayList<>();

		SearchResult results = getConnectionService().findByDN(GROUP_SEARCH_FILTER, distinguishedName);
		if (results != null) {
			List<SearchResultEntry> entries = results.getSearchEntries();
			for (SearchResultEntry entry : entries) {
				com.idega.user.data.Group ejbGroup = null;
				try {
					ejbGroup = getGroupHome().findGroupByUniqueId(entry.getAttributeValue(OrganizationalUnit.DESCRIPTION));
				} catch (FinderException e) {}

				Group entity = ejbGroup == null ? null : getGroupDAO().findGroup((Integer) ejbGroup.getPrimaryKey());
				if (entity != null) {
					entities.add(entity);
				}
			}
		}

		return entities;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.GroupDAO#initialize()
	 */
	@Override
	public void initialize() throws LDAPException, GeneralSecurityException {
		String initializationValue = getApplicationProperty(PROPERTY_GROUPS_OU_INITIALIZED);
		if (Boolean.TRUE.toString().equals(initializationValue)) {
			return;
		}

		String domainDN = getApplicationProperty(ConnectionService.PROPERTY_DOMAIN_DN);
		String ou = getApplicationProperty(PROPERTY_GROUPS_OU, DEFAULT_GROUPS_OU);
		StringBuilder dnBuilder = new StringBuilder(ou).append(CoreConstants.COMMA).append(domainDN);
		getSettings().setProperty(PROPERTY_GROUPS_DN, dnBuilder.toString());


		String dn = getApplicationProperty(PROPERTY_GROUPS_DN, DEFAULT_GROUPS_DN);
		if (!StringUtil.isEmpty(dn)) {
			SearchResult existingEntities = null;

			try {
				existingEntities = getConnectionService().findByDN(GROUP_SEARCH_FILTER, dn);
			} catch (LDAPSearchException e) {}

			if (existingEntities == null) {

				String timeout = getApplicationProperty(
						ConnectionService.PROPERTY_RESPONSE_TIMEOUT,
						ConnectionService.DEFAULT_RESPONSE_TIMEOUT.toString());

				AddRequest request = new AddRequest(new DN(dn));
				request.setResponseTimeoutMillis(Long.valueOf(timeout));
				request.addAttribute("objectClass", "top");
				request.addAttribute("objectClass", OrganizationalUnit.OBJECT_CLASS);
				request.addAttribute(OrganizationalUnit.ORGANIZATIONAL_UNIT_NAME, ou.substring(3));
				request.addAttribute(OrganizationalUnit.DESCRIPTION, "Default directory or containing groups of domain");

				LDAPConnection connection = getConnectionService().getConnection();
				LDAPResult response = connection.add(request);
				connection.close();

				if (response.getResultCode().intValue() != ResultCode.SUCCESS_INT_VALUE) {
					throw new RuntimeException(response.getResultString());
				}
			}

			getSettings().setProperty(PROPERTY_GROUPS_OU_INITIALIZED, Boolean.TRUE.toString());
		}
	}

	/**
	 *
	 * <p>Creates new entity</p>
	 * @param distinguishedName of entity, not <code>null</code>
	 * @param entity itself to write, not <code>null</code>
	 * @return created entity or <code>null</code> on failure
	 * @throws GeneralSecurityException there are problems with TLS/SSL connection.
	 * @throws LDAPException if a problem occurs while attempting to connect to the specified server.
	 */
	private Group create(DN distinguishedName, Group entity) throws LDAPException, GeneralSecurityException {
		if (entity != null) {
			String timeout = getApplicationProperty(
					ConnectionService.PROPERTY_RESPONSE_TIMEOUT,
					ConnectionService.DEFAULT_RESPONSE_TIMEOUT.toString());

			AddRequest request = new AddRequest(distinguishedName);
			request.setResponseTimeoutMillis(Long.valueOf(timeout));
			request.addAttribute("objectClass", "top");

			/*
			 * Organizational unit object information
			 */
			request.addAttribute("objectClass", OrganizationalUnit.OBJECT_CLASS);
			request.addAttribute(OrganizationalUnit.ORGANIZATIONAL_UNIT_NAME, getGroupName(entity));

			if (!StringUtil.isEmpty(entity.getUniqueId())) {
				request.addAttribute(OrganizationalUnit.DESCRIPTION, entity.getUniqueId());
			}

			GroupType type = entity.getGroupType();
			if (type != null && !StringUtil.isEmpty(type.getGroupType())) {
				request.addAttribute(OrganizationalUnit.BUSINESS_CATEGORY, type.getGroupType());
			}

			LDAPConnection connection = getConnectionService().getConnection();
			LDAPResult response = connection.add(request);
			connection.close();

			if (response.getResultCode().intValue() != ResultCode.SUCCESS_INT_VALUE) {
				throw new RuntimeException(response.getResultString());
			}
		}

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.GroupDAO#update(com.unboundid.ldap.sdk.DN, com.idega.user.data.bean.Group)
	 */
	@Override
	public Group update(DN distinguishedName, Group entity) throws LDAPException, GeneralSecurityException {
		if (entity != null) {
			Collection<Group> existingEntities = null;
			try {
				existingEntities = findByDN(distinguishedName);
			} catch (LDAPSearchException e) {
				getLogger().warning("User not found in LDAP repository, cause of: " + e.getMessage());
			}

			if (ListUtil.isEmpty(existingEntities)) {
				return create(distinguishedName, entity);
			}

			ArrayList<Modification> modifications = new ArrayList<>();

			/*
			 * Organizational unit object information
			 */
			String groupName = getGroupName(entity);
			if (!StringUtil.isEmpty(groupName)) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						OrganizationalUnit.ORGANIZATIONAL_UNIT_NAME,
						groupName));
			}

			if (!StringUtil.isEmpty(entity.getUniqueId())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						OrganizationalUnit.DESCRIPTION,
						entity.getUniqueId()));
			}

			GroupType type = entity.getGroupType();
			if (type != null && !StringUtil.isEmpty(type.getGroupType())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						OrganizationalUnit.BUSINESS_CATEGORY,
						type.getGroupType()));
			}

			ModifyRequest modificationRequest = new ModifyRequest(distinguishedName, modifications);
			LDAPConnection connection = getConnectionService().getConnection();
			LDAPResult response = connection.modify(modificationRequest);
			connection.close();

			if (response.getResultCode().intValue() != ResultCode.SUCCESS_INT_VALUE) {
				throw new RuntimeException(response.getResultString());
			}
		}

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.GroupDAO#update(com.idega.user.data.bean.Group)
	 */
	@Override
	public List<Group> update(Group group) throws LDAPException, GeneralSecurityException {
		ArrayList<Group> updatedGroups = new ArrayList<>();

		TreeMap<DN, Group> distinguishedNames = getDistinguishedNames(group);
		for (DN distinguishedName : distinguishedNames.keySet()) {
			Group updatedGroup = update(distinguishedName, distinguishedNames.get(distinguishedName));
			if (updatedGroup != null) {
				updatedGroups.add(updatedGroup);
			}
		}

		return updatedGroups;
	}
}
