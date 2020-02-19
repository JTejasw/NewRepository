package com.mits.filenet.ce.util;

import javax.security.auth.Subject;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Factory;
import com.filenet.api.util.UserContext;
import com.mits.filenet.ce.common.ApplicationLoader;
import com.mits.filenet.ce.common.CEConstantsUsingFile;


/**
 * @author mitsind759
 *to connection acce creating method get connetion,close connection
 */
public class ACCEUtil {//class declaration

	Connection connObj=null;
	public Connection getConnection(){
		try{
		connObj =Factory.Connection.getConnection(ApplicationLoader.getProperty(CEConstantsUsingFile.JDBC_URI));
	    Subject subject = UserContext.createSubject(connObj, ApplicationLoader.getProperty(CEConstantsUsingFile.JDBC_USER), ApplicationLoader.getProperty(CEConstantsUsingFile.JDBC_PASSWORD), null);
	    UserContext.get().pushSubject(subject);
	   //Connection to Content Platform Engine successful
	    return connObj;
		}catch(Exception e){
			e.printStackTrace();
		}
		return connObj;
		
	}//end for getconnection
	
	//close connection methods used to making connection the close form data base 
	public void closeConnection() {
		
		try{
		UserContext.get().popSubject();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}//end close connection

}//end class
