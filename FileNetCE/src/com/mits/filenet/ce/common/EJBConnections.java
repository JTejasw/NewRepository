package com.mits.filenet.ce.common;

import java.util.Iterator;

import javax.security.auth.Subject;

import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;




public class EJBConnections {
	public static void main(String[] args) {
		 
	String uri="iiop://172.16.8.245:9080/FileNet/Engine";

		
	String username="p8admin";
	String password="filenet";
	
	Connection con=Factory.Connection.getConnection(uri);
	Subject sub=UserContext.createSubject(con, username, password, "FileNetP8");
	UserContext.get().pushSubject(sub);
	
	Domain domain=Factory.Domain.fetchInstance(con, null, null);
	  System.out.println("Domain: " + domain.get_Name());
	ObjectStoreSet objset=domain.get_ObjectStores();
	
	Iterator<?> it=objset.iterator();
	
	while(it.hasNext()){
		
		ObjectStore os=(ObjectStore) it.next();
		
		System.out.println(os.get_Name());
	
			
	}
	
	}
	
	
	
}
