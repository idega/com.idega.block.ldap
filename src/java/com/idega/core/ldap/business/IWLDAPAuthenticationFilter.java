package com.idega.core.ldap.business;

//TODO Eiki work in progress, do not clean!!
public class IWLDAPAuthenticationFilter {

	
	public boolean isAuthenticated(String userName, String password){
//		try
//        {
//            Hashtable env = new Hashtable();
//            env.put(Context.INITIAL_CONTEXT_FACTORY,
//                    "com.sun.jndi.ldap.LdapCtxFactory");
// 
//            env.put(Context.PROVIDER_URL,
//                    "ldap://" + getProperty("SERVER") + ":" +
//                    getProperty("PORT"));
// 
//            env.put(Context.SECURITY_PRINCIPAL,
//                    getProperty("USER_RDN") + "," +
//                    getProperty("BASE_DN"));
//            env.put(Context.SECURITY_CREDENTIALS,
//                    getProperty("USER_PASSWORD"));
// 
//            DirContext ctx = new InitialDirContext(env);
// 
//            ctx.getAttributes(getProperty("BASE_DN"));
// 
//            ctx.close();
// 
//            info("connection_succeeded");
//        }
//        catch (CommunicationException comEx)
//        {
//            error(MessageResource.getMessage("communication_exception",
//                                             comEx.getMessage()));
//            return false;
//        }
//        catch (AuthenticationException authEx)
//        {
//            error(MessageResource.getMessage("authentication_exception",
//                                             authEx.getMessage()));
//            return false;
//        }
//        catch (NamingException nameEx)
//        {
//            error(MessageResource.getMessage("naming_exception",
//                                             nameEx.toString()));
//            return false;
//        }
// 
        return true;
		
	}
}
