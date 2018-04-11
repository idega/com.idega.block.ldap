/**
 * @(#)UserDAOImpl.java    1.0.0 16:02:45
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
import java.util.List;
import java.util.logging.Level;

import javax.ejb.FinderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.ldap.client.constants.InternetOrganizationalPerson;
import com.idega.block.ldap.client.constants.OrganizationalPerson;
import com.idega.block.ldap.client.constants.OrganizationalUnit;
import com.idega.block.ldap.client.constants.Person;
import com.idega.block.ldap.client.service.ConnectionService;
import com.idega.block.ldap.client.service.GroupDAO;
import com.idega.block.ldap.client.service.UserDAO;
import com.idega.core.accesscontrol.data.LoginTable;
import com.idega.core.accesscontrol.data.LoginTableHome;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.contact.dao.PhoneDAO;
import com.idega.core.contact.data.bean.Phone;
import com.idega.core.contact.data.bean.PhoneType;
import com.idega.core.location.dao.AddressDAO;
import com.idega.core.location.data.bean.Address;
import com.idega.core.location.data.bean.AddressType;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.user.data.bean.User;
import com.idega.util.CoreConstants;
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
 * @version 1.0.0 2017-08-30
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Service(UserDAO.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UserDAOImpl extends DefaultSpringBean implements UserDAO {

	@Autowired
	private ConnectionService connectionService;

	@Autowired
	private com.idega.user.dao.UserDAO userDAO;

	@Autowired
	private PhoneDAO phoneDAO;

	@Autowired
	private AddressDAO addressDAO;

	private AddressDAO getAddressDAO() {
		if (this.addressDAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.addressDAO;
	}

	private com.idega.user.dao.UserDAO getUserDAO() {
		if (this.userDAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.userDAO;
	}

	private ConnectionService getConnectionService() {
		if (this.connectionService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.connectionService;
	}

	private PhoneDAO getPhoneDAO() {
		if (this.phoneDAO == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.phoneDAO;
	}

	private LoginTableHome getLoginTableHome() {
		try {
			return (LoginTableHome) IDOLookup.getHome(LoginTable.class);
		} catch (IDOLookupException e) {
			getLogger().log(Level.WARNING, "Failed to get " + LoginTableHome.class.getSimpleName() + " cause of: ", e);
		}

		return null;
	}

	private LoginTable getLoginObject(User user) {
		if (user != null) {
			try {
				return getLoginTableHome().findLoginForUser(user.getId());
			} catch (FinderException e) {}
		}

		return null;
	}

	/**
	 *
	 * @param user to get login name for, not <code>null</code>
	 * @return user login name or <code>null</code> on failure
	 */
	private String getLoginName(User user) {
		LoginTable login = getLoginObject(user);
		if (login != null) {
			return login.getUserLogin();
		}

		return null;
	}

	/**
	 *
	 * @param user to get phone number for, not <code>null</code>
	 * @return {@link Phone#getNumber()} of {@link User} home phone or <code>null</code> on failure
	 */
	private String getPhoneNumber(User user) {
		if (user != null) {
			Collection<Phone> phones = getPhoneDAO().findByUserId(user.getId(), PhoneType.UNIQUE_NAME_HOME_PHONE);
			for (Phone phone : phones) {
				if (!StringUtil.isEmpty(phone.getNumber())) {
					return phone.getNumber();
				}
			}
		}

		return null;
	}

	/**
	 *
	 * @param user to get phone number for, not <code>null</code>
	 * @return {@link Phone#getNumber()} of {@link User} home phone or <code>null</code> on failure
	 */
	private String getFAXNumber(User user) {
		if (user != null) {
			Collection<Phone> phones = getPhoneDAO().findByUserId(user.getId(), PhoneType.UNIQUE_NAME_FAX_NUMBER);
			for (Phone phone : phones) {
				if (!StringUtil.isEmpty(phone.getNumber())) {
					return phone.getNumber();
				}
			}
		}

		return null;
	}

	/**
	 *
	 * @param user to get {@link Address} for, not <code>null</code>
	 * @return the {@link AddressType#MAIN_ADDRESS_TYPE} {@link Address} of {@link User} or <code>null</code> on failure;
	 */
	private Address getAddress(User user) {
		if (user != null) {
			Collection<Address> addresses = getAddressDAO().findBy(user.getId(), AddressType.MAIN_ADDRESS_TYPE);
			for (Address address : addresses) {
				return address;
			}
		}

		return null;
	}

	/**
	 *
	 * @param uuid is {@link User#getUniqueId()}, no UUID added if <code>null</code>
	 * @return distinguished name object or <code>null</code> on failure
	 * @throws LDAPException if the provided string cannot be parsed as a valid DN.
	 */
	@Override
	public DN getDistinguishedName(String uuid) throws LDAPException {
		String distinguishedName = getApplicationProperty(PROPERTY_USERS_DN, DEFAULT_USERS_DN);
		if (!StringUtil.isEmpty(distinguishedName)) {
			if (!StringUtil.isEmpty(uuid)) {
				StringBuilder dn = new StringBuilder()
				.append(InternetOrganizationalPerson.USER_ID).append(CoreConstants.EQ).append(uuid)
				.append(CoreConstants.COMMA).append(distinguishedName);

				return new DN(dn.toString());
			}

			return new DN(distinguishedName);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.UserDAO#findByUUID(java.lang.String)
	 */
	@Override
	public Collection<User> findByUUID(String uuid) throws LDAPSearchException, LDAPException, GeneralSecurityException {
		ArrayList<User> entities = new ArrayList<>();

		String organizationalUnit = getApplicationProperty(PROPERTY_USERS_OU, DEFAULT_USERS_OU);

		SearchResult results = getConnectionService().findByUUID(USER_SEARCH_FILTER, organizationalUnit, uuid);
		if (results != null) {
			List<SearchResultEntry> entries = results.getSearchEntries();
			for (SearchResultEntry entry : entries) {
				User entity = getUserDAO().getUserByUUID(entry.getAttributeValue(InternetOrganizationalPerson.USER_ID));
				if (entity != null) {
					entity.setLastName(entry.getAttributeValue(Person.LAST_NAME));
					entity.setDescription(entry.getAttributeValue(Person.DESCRIPTION));
					entity.setDisplayName(entry.getAttributeValue(InternetOrganizationalPerson.DISPLAY_NAME));
					entity.setFirstName(entry.getAttributeValue(InternetOrganizationalPerson.FIRST_NAME));
					entities.add(entity);
				}
			}
		}

		return entities;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.UserDAO#initialize()
	 */
	@Override
	public void initialize() throws LDAPException, GeneralSecurityException {
		String initializationValue = getApplicationProperty(PROPERTY_USERS_OU_INITIALIZED);
		if (Boolean.TRUE.toString().equals(initializationValue)) {
			return;
		}

		/*
		 * Updating Users unit DN
		 */
		String domainDN = getApplicationProperty(ConnectionService.PROPERTY_DOMAIN_DN);

		String ou = getApplicationProperty(PROPERTY_USERS_OU, DEFAULT_USERS_OU);
		StringBuilder dnBuilder = new StringBuilder(ou).append(CoreConstants.COMMA).append(domainDN);
		getSettings().setProperty(PROPERTY_USERS_DN, dnBuilder.toString());

		/*
		 * Creating directory on LDAP
		 */
		String dn = getApplicationProperty(PROPERTY_USERS_DN);
		if (!StringUtil.isEmpty(dn)) {
			SearchResult existingEntities = null;
			try {
				existingEntities = getConnectionService().findByDN(GroupDAO.GROUP_SEARCH_FILTER, new DN(dn));
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
				request.addAttribute(OrganizationalUnit.DESCRIPTION, "Default directory for containing users");

				LDAPConnection connection = getConnectionService().getConnection();
				LDAPResult response = connection.add(request);
				connection.close();

				if (response.getResultCode().intValue() != ResultCode.SUCCESS_INT_VALUE) {
					throw new RuntimeException(response.getResultString());
				}
			}

			getSettings().setProperty(PROPERTY_USERS_OU_INITIALIZED, Boolean.TRUE.toString());
		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.idega.block.ldap.client.service.UserDAO#create(com.idega.user.data.bean.User)
	 */
	@Override
	public User create(User entity, String password) throws LDAPException, GeneralSecurityException {
		if (entity != null) {
			String timeout = getApplicationProperty(
					ConnectionService.PROPERTY_RESPONSE_TIMEOUT,
					ConnectionService.DEFAULT_RESPONSE_TIMEOUT.toString());

			AddRequest request = new AddRequest(getDistinguishedName(entity.getUniqueId()));
			request.setResponseTimeoutMillis(Long.valueOf(timeout));
			request.addAttribute("objectClass", "top");

			/*
			 * Person object information
			 */
			request.addAttribute("objectClass", Person.OBJECT_CLASS);
			request.addAttribute(Person.LAST_NAME, !StringUtil.isEmpty(entity.getLastName()) ? entity.getLastName() : "-");

			String loginName = getLoginName(entity);
			if (!StringUtil.isEmpty(loginName)) {
				request.addAttribute(Person.COMMON_NAME, loginName);
			}

			if (!StringUtil.isEmpty(password)) {
				request.addAttribute(Person.PASSWORD, password);
			}

			String phoneNumber = getPhoneNumber(entity);
			if (!StringUtil.isEmpty(phoneNumber)) {
				request.addAttribute(Person.PHONE_NUMBER, phoneNumber);
			}

			if (!StringUtil.isEmpty(entity.getDescription())) {
				request.addAttribute(Person.DESCRIPTION, entity.getDescription());
			}

			/*
			 * Organizational Person object information
			 */
			request.addAttribute("objectClass", OrganizationalPerson.OBJECT_CLASS);

			String faxNumber = getFAXNumber(entity);
			if (!StringUtil.isEmpty(faxNumber)) {
				request.addAttribute(OrganizationalPerson.FAX_NUMBER, faxNumber);
			}

			Address address = getAddress(entity);
			if (address != null) {
				if (!StringUtil.isEmpty(address.getAddress())) {
					request.addAttribute(OrganizationalPerson.ADDRESS, address.getAddress());
				}

				if (!StringUtil.isEmpty(address.getCity())) {
					request.addAttribute(OrganizationalPerson.LOCALE_NAME, address.getCity());
				}

				if (!StringUtil.isEmpty(address.getPostalBox())) {
					request.addAttribute(OrganizationalPerson.POST_OFFICE_BOX, address.getPostalBox());
				}

				if (!StringUtil.isEmpty(address.getStreetAddress())) {
					request.addAttribute(OrganizationalPerson.STREET, address.getStreetAddress());
				}
			}

			/*
			 * Internet Organizational Person object information
			 */
			request.addAttribute("objectClass", InternetOrganizationalPerson.OBJECT_CLASS);

			if (!StringUtil.isEmpty(entity.getDisplayName())) {
				request.addAttribute(InternetOrganizationalPerson.DISPLAY_NAME, entity.getDisplayName());
			}

			if (!StringUtil.isEmpty(entity.getEmailAddress())) {
				request.addAttribute(InternetOrganizationalPerson.E_MAIL, entity.getEmailAddress());
			}

			if (!StringUtil.isEmpty(entity.getFirstName())) {
				request.addAttribute(InternetOrganizationalPerson.FIRST_NAME, entity.getFirstName());
			}

			if (!StringUtil.isEmpty(phoneNumber)) {
				request.addAttribute(InternetOrganizationalPerson.HOME_PHONE, phoneNumber);
			}

			if (!StringUtil.isEmpty(entity.getUniqueId())) {
				request.addAttribute(InternetOrganizationalPerson.USER_ID, entity.getUniqueId());
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
	 * @see com.idega.block.ldap.client.service.UserDAO#update(com.idega.user.data.bean.User)
	 */
	@Override
	public User update(User entity, String password) throws LDAPException, GeneralSecurityException {
		if (entity != null) {
			Collection<User> existingUsers = null;
			try {
				existingUsers = findByUUID(entity.getUniqueId());
			} catch (LDAPSearchException e) {
				getLogger().warning("User not found in LDAP repository, cause of: " + e.getMessage());
			}

			if (ListUtil.isEmpty(existingUsers)) {
				try {
					return create(entity, password);
				} catch (LDAPException e) {
					getLogger().log(Level.WARNING, "Failed to create new record, record will be updated:", e);
				}
			}

			ArrayList<Modification> modifications = new ArrayList<>();

			/*
			 * Person object information
			 */
			if (!StringUtil.isEmpty(entity.getLastName())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						Person.LAST_NAME,
						entity.getLastName()));
			}

			String loginName = getLoginName(entity);
			if (!StringUtil.isEmpty(loginName)) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						Person.COMMON_NAME,
						loginName));
			}

			if (!StringUtil.isEmpty(password)) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						Person.PASSWORD,
						password));
			}

			String phoneNumber = getPhoneNumber(entity);
			if (!StringUtil.isEmpty(phoneNumber)) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						Person.PHONE_NUMBER,
						phoneNumber));
			}

			if (!StringUtil.isEmpty(entity.getDescription())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						Person.DESCRIPTION,
						entity.getDescription()));
			}

			/*
			 * Organizational Person object information
			 */
			String faxNumber = getFAXNumber(entity);
			if (!StringUtil.isEmpty(faxNumber)) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						OrganizationalPerson.FAX_NUMBER,
						faxNumber));
			}

			Address address = getAddress(entity);
			if (address != null) {
				if (!StringUtil.isEmpty(address.getAddress())) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							OrganizationalPerson.ADDRESS,
							address.getAddress()));
				}

				if (!StringUtil.isEmpty(address.getCity())) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							OrganizationalPerson.LOCALE_NAME,
							address.getCity()));
				}

				if (!StringUtil.isEmpty(address.getPostalBox())) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							OrganizationalPerson.POST_OFFICE_BOX,
							address.getPostalBox()));
				}

				if (!StringUtil.isEmpty(address.getStreetAddress())) {
					modifications.add(new Modification(
							ModificationType.REPLACE,
							OrganizationalPerson.STREET,
							address.getStreetAddress()));
				}
			}

			/*
			 * Internet Organizational Person object information
			 */
			if (!StringUtil.isEmpty(entity.getDisplayName())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						InternetOrganizationalPerson.DISPLAY_NAME,
						entity.getDisplayName()));
			}

			if (!StringUtil.isEmpty(entity.getEmailAddress())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						InternetOrganizationalPerson.E_MAIL,
						entity.getEmailAddress()));
			}

			if (!StringUtil.isEmpty(entity.getFirstName())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						InternetOrganizationalPerson.FIRST_NAME,
						entity.getFirstName()));
			}

			if (!StringUtil.isEmpty(phoneNumber)) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						InternetOrganizationalPerson.HOME_PHONE,
						phoneNumber));
			}

			if (!StringUtil.isEmpty(entity.getUniqueId())) {
				modifications.add(new Modification(
						ModificationType.REPLACE,
						InternetOrganizationalPerson.USER_ID,
						entity.getUniqueId()));
			}

			ModifyRequest modificationRequest = new ModifyRequest(getDistinguishedName(entity.getUniqueId()), modifications);
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
	 * @see com.idega.block.ldap.client.service.UserDAO#update(java.lang.String)
	 */
	@Override
	public String update(String personalId, String password) throws LDAPException, GeneralSecurityException {
		/*
		 * Let's not allow change password here, because it is JavaScript
		 */
		User user = update(getUserDAO().getUser(personalId), null);
		if (user != null) {
			return user.getDisplayName();
		}

		return null;
	}
}
