package com.mits.filenet.ce.customobject;

import org.apache.log4j.Logger;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.mits.filenet.ce.util.ACCEUtil;


/**
 * @author mitsind759
 * creating custom object class through CE API
 */
//class declaration
public class CreatingCustomObject {

	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(CreatingCustomObject.class);

	static Connection conn = null;

	// main declaration
	public static void main(String[] args) {
		LOGGER.info("inside main method");

		// declaration ACCEUtil object for getting connection
		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		conn = ObjgetDBUtil.getConnection();
		CreateCustomObject(conn);
		ObjgetDBUtil.closeConnection();

	}//end main 

	// creating custom object class in acce
	public static void CreateCustomObject(Connection conn) {
		LOGGER.info("inside CreateCustomObject method");

		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);

			// Creaing Custome Object and setting properties
			// Object store, Custome Object Class Name
			CustomObject myObject = Factory.CustomObject.createInstance(objStore, "jtcustomobject"); 
			com.filenet.api.property.Properties props = myObject.getProperties();
			// setting the properties
			props.putValue("jtname", "stuInfo022");
			myObject.save(RefreshMode.REFRESH);
			LOGGER.info("Custom object " + myObject.get_Name() + " created");

			// Saving the Custome Object into folder
			String folder = "/Tejaswini";
			com.filenet.api.core.Folder folderOj = Factory.Folder.fetchInstance(objStore, folder, null);
			// System.out.println("del"+folderOj.get_Name());
			ReferentialContainmentRelationship rel = folderOj.file(myObject, AutoUniqueName.AUTO_UNIQUE, "eth",
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			rel.save(RefreshMode.REFRESH);
			LOGGER.info("Custom Object created successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end CreateCustomObject method

}// end class
