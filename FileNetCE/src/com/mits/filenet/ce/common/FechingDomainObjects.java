package com.mits.filenet.ce.common;

import java.util.Iterator;
import org.apache.log4j.Logger;
import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import com.mits.filenet.ce.util.ACCEUtil;
//class declaration
/**
 * @author mitsind759
 *FechingDomain ,Objects in acce through api
 */
public class FechingDomainObjects {
	//implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(FechingDomainObjects.class);
	
    //main method declaration
	public static void main(String[] args) {

		// declaration ACCEUtil object for getting connection
		ACCEUtil objGettConn = new ACCEUtil();

		try {
			Connection conn = objGettConn.getConnection();
			// Get default domain.
			// fetching Domain
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("Domain: " + domain.get_Name());

			// Get object stores for domain.fetching Object Store
			ObjectStoreSet osSet = domain.get_ObjectStores();
			ObjectStore store;
	
			
			Iterator<?> osIter = osSet.iterator();

			while (osIter.hasNext()) {
				store = (ObjectStore) osIter.next();
				LOGGER.info("Object store: " + store.get_Name());
			}
			LOGGER.info("successful completed");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			UserContext.get().popSubject();
		}
	}//end main

}//end class
