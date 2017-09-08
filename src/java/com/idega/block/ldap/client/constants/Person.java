/**
 * @(#)Person.java    1.0.0 15:36:26
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
 * <h1>3.12  'person'</h1>
 * <p>The 'person' object class is the basis of an entry that represents a human being.</p>
 * <pre>
 *    (Source: X.521 [X.521])
 *    ( 2.5.6.6 NAME 'person'
 *       SUP top
 *       STRUCTURAL
 *       MUST ( sn $ cn )
 *       MAY ( userPassword $ telephoneNumber $ seeAlso $ description ) )
 * </pre>
 *
 * @version 1.0.0 2017-09-07
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface Person {

	static final String OBJECT_CLASS = "person";

	/**
	 * <p>The 'sn' ('surname' in X.500) attribute type contains name strings for the family names of a person. 
	 * Each string is one value of this multi-valued attribute.</p>
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.4 NAME 'sn' SUP name )
	 * </pre>
	 * 
	 * Example: "Smith".
	 */
	static final String LAST_NAME = "sn";
	
	/**
	 * <p>The 'cn' ('commonName' in X.500) attribute type contains names of an object. 
	 * Each name is one value of this multi-valued attribute. If the object corresponds to a person, it is typically 
	 * the person's full name.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.3 NAME 'cn' SUP name )
	 * </pre>
	 * 
	 * Examples: "Martin K Smith", "Marty Smith" and "printer12".
	 */
	static final String COMMON_NAME = "cn";

	/**
	 * <p>The 'userPassword' attribute contains octet strings that are known only
	 * to the user and the system to which the user has access. Each string is
	 * one value of this multi-valued attribute.</p>
	 * 
	 * <p>The application SHOULD prepare textual strings used as passwords by
	 * transcoding them to Unicode, applying SASLprep [RFC4013], and encoding as
	 * UTF-8. The determination of whether a password is textual is a local
	 * client matter.</p>
	 * 
	 * <pre>
	 *     (Source: X.509 [X.509])
	 *     ( 2.5.4.35 NAME 'userPassword' 
	 *         EQUALITY octetStringMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.40 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.40 refers to the Octet String syntax [RFC4517].</p>
	 * 
	 * <p>Passwords are stored using an Octet String syntax and are not encrypted. Transfer of cleartext passwords is 
	 * strongly discouraged where the underlying transport service cannot guarantee confidentiality and may
	 * result in disclosure of the password to unauthorized parties.</p>
	 * 
	 * <p>An example of a need for multiple values in the 'userPassword' attribute
	 * is an environment where every month the user is expected to use a
	 * different password generated by some automated system. During
	 * transitional periods, like the last and first day of the periods, it may
	 * be necessary to allow two passwords for the two consecutive periods to be
	 * valid in the system.</p>
	 */
	static final String PASSWORD = "userPassword";

	/**
	 * <p>The 'telephoneNumber' attribute type contains telephone numbers that
	 * comply with the ITU Recommendation E.123 [E.123]. Each number is one
	 * value of this multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 * 	   ( 2.5.4.20 NAME 'telephoneNumber' 
	 *         EQUALITY telephoneNumberMatch 
	 *         SUBSTR telephoneNumberSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.50 )
	 * </pre>
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.50 refers to the Telephone Number syntax [RFC4517].</p>
	 * 
	 * Example: "+1 234 567 8901".
	 */
	static final String PHONE_NUMBER = "telephoneNumber";

	/**
	 * <p>The 'description' attribute type contains human-readable descriptive phrases about the object. 
	 * Each description is one value of this multi-valued attribute.</p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520])
	 *     ( 2.5.4.13 NAME 'description' 
	 *         EQUALITY caseIgnoreMatch 
	 *         SUBSTR caseIgnoreSubstringsMatch 
	 *         SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 ) 
	 * </pre>        
	 * 
	 * <p>1.3.6.1.4.1.1466.115.121.1.15 refers to the Directory String syntax [RFC4517].</p>
	 * 
	 * Examples: "a color printer", "Maintenance is done every Monday, at 1pm.", and "distribution list for all technical staff".
	 */
	static final String DESCRIPTION = "description";

	/**
	 * <p>
	 * The 'seeAlso' attribute type contains the distinguished names of objects
	 * that are related to the subject object. Each related object name is one
	 * value of this multi-valued attribute.
	 * </p>
	 * 
	 * <pre>
	 *     (Source: X.520 [X.520]) 
	 *     ( 2.5.4.34 NAME 'seeAlso' 
	 *         SUP distinguishedName )
	 * </pre>
	 * 
	 * Example: The person object "cn=James Brown,ou=employee,o=Widget\, Inc."
	 * is related to the role objects "cn=Football Team Captain,ou=sponsored
	 * activities,o=Widget\, Inc." and "cn=Chess Team,ou=sponsored
	 * activities,o=Widget\, Inc.". Since the role objects are related to the
	 * person object, the 'seeAlso' attribute will contain the distinguished
	 * name of each role object as separate values.
	 */
	static final String SEE = "seeAlso";
}
