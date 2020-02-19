package com.mits.filenet.ce.customobject;

import java.util.Iterator;
import org.apache.log4j.Logger;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Property;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;

import com.mits.filenet.ce.util.ACCEUtil;

public class RetrievingCustomObject {
	static Connection conn = null;

	// using it logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingCustomObject.class);
	

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		RetrievingCustomObject objRetrCuObj= new RetrievingCustomObject();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objRetrCuObj.FetchCECustomObject(conn);
		
		ObjgetDBUtil.closeConnection();
	}

	// Retrieving in document class Unstructure(FileNet CE API)
	public void FetchCECustomObject(Connection conn) {

		try {

			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());

			PropertyFilter pf = new PropertyFilter();

			LOGGER.info("ClassNames.DOCUMENT............." + ClassNames.DOCUMENT);

			CustomObject objCustomObject = Factory.CustomObject.fetchInstance(objStore, new Id("{F0BB126F-0000-CB1C-9DB2-57D8B68FD9FA}"),
					pf);
		
			
			// Fetch selected properties from the server.
			// doc.fetchProperties(pf);
	LOGGER.info("properties*************************************************************" + pf);


	// Return document properties.
			com.filenet.api.property.Properties props = objCustomObject.getProperties();

			// Iterate the set and print property values.
			Iterator<?> iter = props.iterator();
			LOGGER.info("Iterator....." + iter.hasNext());

			System.out.println("Property" + "\t" + "Value");
			System.out.println("------------------------");
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				System.out.println(prop.getPropertyName() + "\t" + prop.getObjectValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	

}
