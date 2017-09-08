/**
 * @(#)OrganizationalPerson.java    1.0.0 11:11:21
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
 * <h1>3.9. 'organizationalPerson'</h1>
 *
 * <p>The 'organizationalPerson' object class is the basis of an entry that
 * represents a person in relation to an organization.</p>
 * 
 * <pre> 
 *    (Source: X.521 [X.521])
 *    ( 2.5.6.7 NAME 'organizationalPerson'
 *       SUP person
 *       STRUCTURAL
 *       MAY ( title $ x121Address $ registeredAddress $
 *             destinationIndicator $ preferredDeliveryMethod $
 *             telexNumber $ teletexTerminalIdentifier $
 *             telephoneNumber $ internationalISDNNumber $
 *             facsimileTelephoneNumber $ street $ postOfficeBox $
 *             postalCode $ postalAddress $ physicalDeliveryOfficeName $
 *             ou $ st $ l ) )
 * </pre>
 * 
 * @version 1.0.0 2017-09-08
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface OrganizationalPerson extends Person {

	static final String OBJECT_CLASS = "organizationalPerson";

	/**
	 * <p>The 'title' attribute type contains the title of a person in their
	 * organizational context. Each title is one value of this multi-valued
	 * attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.12 NAME 'title' 
	 *         SUP name )
	 * </pre>
	 * 
	 * Examples: "Vice President", "Software Engineer", and "CEO".
	 */
	static final String TITLE = "title";

	/**
	 * <p>The 'registeredAddress' attribute type contains postal addresses suitable
	 * for reception of telegrams or expedited documents, where it is necessary
	 * to have the recipient accept delivery. Each address is one value of this
	 * multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.26 NAME 'registeredAddress' 
	 *         SUP postalAddress 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.41 )
	 * </pre>
	 * <p>1.3.6.1.4.1.1466.115.121.1.41 refers to the Postal Address syntax [RFC4517].</p>
	 * 
	 * Example: "Receptionist$Widget, Inc.$15 Main St.$Ottawa$Canada".
	 */
	static final String ADDRESS = "registeredAddress";

	/**
	 * <p>The 'facsimileTelephoneNumber' attribute type contains telephone numbers
	 * (and, optionally, the parameters) for facsimile terminals. Each telephone
	 * number is one value of this multi-valued attribute.</p>
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.23 NAME 'facsimileTelephoneNumber' 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.22 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.22 refers to the Facsimile Telephone Number syntax [RFC4517].</p>
	 * 
	 * Examples: "+61 3 9896 7801" and "+81 3 347 7418$fineResolution".
	 */
	static final String FAX_NUMBER = "facsimileTelephoneNumber";

	/**
	 * <p>The 'street' ('streetAddress' in X.500) attribute type contains site
	 * information from a postal address (i.e., the street name, place, avenue,
	 * and the house number). Each street is one value of this multi-valued
	 * attribute.</p> 
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.9 NAME 'street' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.15 refers to the Directory String syntax [RFC4517].</p>
	 * 
	 * Example: "15 Main St.".
	 */
	static final String STREET = "street";

	/**
	 * <p>The 'postOfficeBox' attribute type contains postal box identifiers that a
	 * Postal Service uses when a customer arranges to receive mail at a box on
	 * the premises of the Postal Service. Each postal box identifier is a
	 * single value of this multi-valued attribute.</p> 
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.18 NAME 'postOfficeBox' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.15 refers to the Directory String syntax [RFC4517].</p>
	 * 
	 * Example: "Box 45".
	 */
	static final String POST_OFFICE_BOX = "postOfficeBox";
	
	/**
	 * <p>The 'postalCode' attribute type contains codes used by a Postal Service
	 * to identify postal service zones. Each code is one value of this
	 * multi-valued attribute.<p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.17 NAME 'postalCode' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.15 refers to the Directory String syntax [RFC4517].</p>
	 * 
	 * Example: "22180", to identify Vienna, VA, in the USA.
	 */
	static final String POSTAL_CODE = "postalCode";

	/**
	 * <p>The 'postalAddress' attribute type contains addresses used by a Postal
	 * Service to perform services for the object. Each address is one value of
	 * this multi-valued attribute.</p> 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.16 NAME 'postalAddress' 
	 *         EQUALITY caseIgnoreListMatch 
	 *         SUBSTR caseIgnoreListSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.41 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.41 refers to the Postal Address syntax [RFC4517].</p>
	 * 
	 * Example: "15 Main St.$Ottawa$Canada".
	 */
	static final String POSTAL_ADDRESS = "postalAddress";
	
	/**
	 * <p>The 'ou' ('organizationalUnitName' in X.500) attribute type contains the
	 * names of an organizational unit. Each name is one value of this
	 * multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.11 NAME 'ou' 
	 *         SUP name )
	 * </pre>
	 * 
	 * Examples: "Finance", "Human Resources", and "Research and Development".
	 */
	static final String ORGANIZATIONAL_UNIT_NAME = "ou";

	/**
	 * <p>The 'st' ('stateOrProvinceName' in X.500) attribute type contains the
	 * full names of states or provinces. Each name is one value of this
	 * multi-valued attribute.</p> 
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.8 NAME 'st' 
	 *         SUP name )
	 * </pre>
	 * 
	 * Example: "California".
	 */
	static final String PROVINCE_NAME = "st";

	/**
	 * <p>The 'l' ('localityName' in X.500) attribute type contains names of a
	 * locality or place, such as a city, county, or other geographic region.
	 * Each name is one value of this multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520]) 
	 *     ( 2.5.4.7 NAME 'l' 
	 *         SUP name )
	 * </pre>
	 * 
	 * Examples: "Geneva", "Paris", and "Edinburgh".
	 */
	static final String LOCALE_NAME = "l";
}
