/**
 * @(#)InternetOrganizationalPerson.java    1.0.0 11:45:01
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
package com.idega.block.ldap.client.constants;

/**
 * <p>
 * The inetOrgPerson represents people who are associated with an organization
 * in some way. It is a structural class and is derived from the
 * organizationalPerson class which is defined in X.521 [X521].
 * </p>
 * 
 * <pre>
 * ( 2.16.840.1.113730.3.2.2
 *     NAME 'inetOrgPerson'
 *     SUP organizationalPerson
 *     STRUCTURAL
 *     MAY (
 *         audio $ businessCategory $ carLicense $ departmentNumber $
 *         displayName $ employeeNumber $ employeeType $ givenName $
 *         homePhone $ homePostalAddress $ initials $ jpegPhoto $
 *         labeledURI $ mail $ manager $ mobile $ o $ pager $
 *         photo $ roomNumber $ secretary $ uid $ userCertificate $
 *         x500uniqueIdentifier $ preferredLanguage $
 *         userSMIMECertificate $ userPKCS12
 *    )
 * )
 * </pre>
 * 
 * <p>
 * For reference, we list the following additional attribute types that are part
 * of the inetOrgPerson object class. These attribute types are inherited from
 * organizationalPerson (which in turn is derived from the person object class):
 * </p>
 * 
 * <pre>
 * MUST (
 *     cn $ objectClass $ sn
 * )
 * MAY (
 *     description $ destinationIndicator $ facsimileTelephoneNumber $
 *     internationaliSDNNumber $ l $ ou $ physicalDeliveryOfficeName $
 *     postalAddress $ postalCode $ postOfficeBox $
 *     preferredDeliveryMethod $ registeredAddress $ seeAlso $
 *     st $ street $ telephoneNumber $ teletexTerminalIdentifier $
 *     telexNumber $ title $ userPassword $ x121Address
 * )
 * </pre>
 *
 * <p>The following example is expressed using the LDIF notation defined in [LDIF].</p>
 * 
 * <pre>
 *  version: 1
 *  dn: cn=Barbara Jensen,ou=Product Development,dc=siroe,dc=com
 *  objectClass: top
 *  objectClass: person
 *  objectClass: organizationalPerson
 *  objectClass: inetOrgPerson
 *  cn: Barbara Jensen
 *  cn: Babs Jensen
 *  displayName: Babs Jensen
 *  sn: Jensen
 *  givenName: Barbara
 *  initials: BJJ
 *  title: manager, product development
 *  uid: bjensen
 *  mail: bjensen@siroe.com
 *  telephoneNumber: +1 408 555 1862
 *  facsimileTelephoneNumber: +1 408 555 1992
 *  mobile: +1 408 555 1941
 *  roomNumber: 0209
 *  carLicense: 6ABC246
 *  o: Siroe
 *  ou: Product Development
 *  departmentNumber: 2604
 *  employeeNumber: 42
 *  employeeType: full time
 *  preferredLanguage: fr, en-gb;q=0.8, en;q=0.7
 *  labeledURI: http://www.siroe.com/users/bjensen My Home Page
 * </pre>
 * 
 * @version 1.0.0 2017-09-08
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface InternetOrganizationalPerson extends OrganizationalPerson {
	
	static final String OBJECT_CLASS = "inetOrgPerson";
	
	/**
	 * <p>When displaying an entry, especially within a one-line summary list, it
	 * is useful to be able to identify a name to be used. Since other attribute
	 * types such as 'cn' are multivalued, an additional attribute type is
	 * needed. Display name is defined for this purpose.</p>
	 * 
	 * <pre>
	 *     ( 2.16.840.1.113730.3.1.241 NAME 'displayName' 
	 *         DESC 'preferred name of a person to be used when displaying entries' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15
	 *         SINGLE-VALUE )
	 * </pre>
	 */
	static final String DISPLAY_NAME = "displayName";

	/**
	 * <p>Used to indicate an individual's preferred written or spoken language.
	 * This is useful for international correspondence or human- computer
	 * interaction. Values for this attribute type MUST conform to the
	 * definition of the Accept-Language header field defined in [RFC2068] with
	 * one exception: the sequence "Accept-Language" ":" should be omitted. This
	 * is a single valued attribute type.</p>
	 * 
	 * <pre>
	 *     ( 2.16.840.1.113730.3.1.39 NAME 'preferredLanguage' 
	 *         DESC 'preferred written or spoken language for a person' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15
	 *         SINGLE-VALUE ) )
	 * </pre>
	 */
	static final String PREFERRED_LANGUAGE = "preferredLanguage";

	/**
	 * <p>The 'givenName' attribute type contains name strings that are the part of
	 * a person's name that is not their surname. Each string is one value of
	 * this multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.42 NAME 'givenName' 
	 *         SUP name )
	 * </pre>
	 * 
	 * Examples: "Andrew", "Charles", and "Joanne".
	 */
	static final String FIRST_NAME = "givenName";

	/**
	 * <p>The Home Telephone Number attribute type specifies a home telephone
	 * number associated with a person. Attribute values should follow the
	 * agreed format for international telephone numbers: i.e., "+44 71 123
	 * 4567".</p>
	 * 
	 * homeTelephoneNumber ATTRIBUTE WITH ATTRIBUTE-SYNTAX telephoneNumberSyntax
	 * ::= {pilotAttributeType 20}
	 */
	static final String HOME_PHONE = "homeTelephoneNumber";

	/**
	 * <p>The initials attribute contains the initials of some or all of an
	 * individuals names, but not the surname(s).</p>
	 * 
	 * <pre>
	 * ( 2.5.4.43 NAME 'initials' SUP name )
	 * </pre>
	 */
	static final String INITIALS = "initials";

	/**
	 * <p>The RFC822 Mailbox attribute type specifies an electronic mailbox
	 * attribute following the syntax specified in RFC 822. Note that this
	 * attribute should not be used for greybook or other non-Internet order
	 * mailboxes.</p>
	 * 
	 * rfc822Mailbox ATTRIBUTE WITH ATTRIBUTE-SYNTAX caseIgnoreIA5StringSyntax
	 * (SIZE (1 .. ub-rfc822-mailbox)) ::= {pilotAttributeType 3}
	 */
	static final String E_MAIL = "rfc822Mailbox";

	/**
	 * <p>The Mobile Telephone Number attribute type specifies a mobile telephone
	 * number associated with a person. Attribute values should follow the
	 * agreed format for international telephone numbers: i.e., "+44 71 123
	 * 4567".</p>
	 * 
	 * <pre>
	 *     mobileTelephoneNumber ATTRIBUTE WITH ATTRIBUTE-SYNTAX
	 *     telephoneNumberSyntax ::= {pilotAttributeType 41}
	 * </pre>
	 */
	static final String MOBILE_PHONE = "mobileTelephoneNumber";

	/**
	 * <p>The 'uid' ('userid' in RFC 1274) attribute type contains computer system
	 * login names associated with the object. Each name is one value of this
	 * multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: RFC 2798 [RFC2798] and RFC 1274 [RFC1274])
	 *     ( 0.9.2342.19200300.100.1.1 NAME 'uid' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.15 refers to the Directory String syntax [RFC4517].</pre>
	 * 
	 * Examples: "s9709015", "admin", and "Administrator".
	 */
	static final String USER_ID = "uid";

	/**
	 * </p>The 'x500UniqueIdentifier' attribute type contains binary strings that
	 * are used to distinguish between objects when a distinguished name has
	 * been reused. Each string is one value of this multi-valued attribute.</p>
	 * 
	 * <p>In X.520 [X.520], this attribute type is called 'uniqueIdentifier'. This
	 * is a different attribute type from both the 'uid' and 'uniqueIdentifier'
	 * LDAP attribute types. The 'uniqueIdentifier' attribute type is defined in
	 * [RFC4524].</p> 
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.45 NAME 'x500UniqueIdentifier' 
	 *         EQUALITY bitStringMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.6 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.6 refers to the Bit String syntax [RFC4517].</p>
	 */
	static final String UUID_X_500 = "x500uniqueIdentifier";

	/**
	 * <p>The 'o' ('organizationName' in X.500) attribute type contains the names
	 * of an organization. Each name is one value of this multi-valued
	 * attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.10 NAME 'o' 
	 *         SUP name )
	 * </pre>
	 * 
	 * Examples: "Widget", "Widget, Inc.", and "Widget, Incorporated.".
	 */
	static final String ORGANIZATION_NAME = "o";
}
