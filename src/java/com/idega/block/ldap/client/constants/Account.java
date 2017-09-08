package com.idega.block.ldap.client.constants;

/**
 * 
 * <h1>3.1.  account</h1>
 * <p>The 'account' object class is used to define entries representing computer accounts.  
 * The 'uid' attribute SHOULD be used for naming entries of this object class.</p>
 * <pre>
 *    ( 0.9.2342.19200300.100.4.5 NAME 'account'
 *      SUP top STRUCTURAL
 *      MUST uid
 *      MAY ( description $ seeAlso $ l $ o $ ou $ host ) )
 * </pre>
 * <p>The 'top' object class is described in [RFC4512]. The 'description', 
 * 'seeAlso', 'l', 'o', 'ou', and 'uid' attribute types are described in [RFC4519].  
 * The 'host' attribute type is described in Section 2 of this document.
 * <h2>3.3.  documentSeriesExample:</h2>
 * <pre>
 *    dn: uid=kdz,cn=Accounts,dc=Example,dc=COM
 *    objectClass: account
 *    uid: kdz
 *    seeAlso: cn=Kurt D. Zeilenga,cn=Persons,dc=Example,dc=COM
 * </pre>
 * @version 1.0.0 2017-09-07
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface Account {

	static final String OBJECT_CLASS = "account";
	
	/**
	 * The 'uid' ('userid' in RFC 1274) attribute type contains computer system login names associated with the object.
	 * Each name is one value of this multi-valued attribute. (Source: RFC 2798 [RFC2798] and RFC 1274 [RFC1274]) 
	 * ( 0.9.2342.19200300.100.1.1 NAME 'uid' EQUALITY caseIgnoreMatch SUBSTR caseIgnoreSubstringsMatch 
	 * SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 ) 1.3.6.1.4.1.1466.115.121.1.15 refers to the Directory String syntax [RFC4517].
	 * Examples: "s9709015", "admin", and "Administrator".
	 */
	static final String USER_ID = "userid";
}
