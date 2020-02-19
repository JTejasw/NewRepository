package com.mits.filenet.ce.document;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.filenet.api.collection.VersionableSet;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.Versionable;

import com.filenet.api.property.PropertyFilter;
import com.mits.filenet.ce.util.ACCEUtil;

public class VersionsProperty {
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingAllVersion.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		VersionsProperty objVersionsProperty = new VersionsProperty();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objVersionsProperty.VersProperty(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	// cancel checkout  after checkin

	public void VersProperty(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
		// Get document and put Versionable object in property cache.
	PropertyFilter pf = new PropertyFilter();
	//pf.addIncludeType(new FilterElement(null, null, null, PropertyNames.VERSIONS, null)); 
	Document doc = Factory.Document.fetchInstance(objStore, "{D0DB0E6F-0000-C990-8CB9-59D9EF1BCA64}",pf );

	// Return all document versions.
	VersionableSet versions = doc.get_Versions();
	Versionable version;

	// Iterate the set and print information about each version.
	Iterator<?> iter = versions.iterator();
	while (iter.hasNext() )
	{
	   version = (Versionable)iter.next();
	   System.out.println("Status of version: " + version.get_VersionStatus().toString() +
	      "\nNumber of current version: " + version.get_MajorVersionNumber() +"."+ version.get_MinorVersionNumber() +
	      "\nIs reserved: " + version.get_IsReserved() +
	      "\nIs current version: " + version.get_IsCurrentVersion() + 
	      "\nIs frozen version: " + version.get_IsFrozenVersion() +
	      "\n----------------------"
	   );
	}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		}
	
	
}
