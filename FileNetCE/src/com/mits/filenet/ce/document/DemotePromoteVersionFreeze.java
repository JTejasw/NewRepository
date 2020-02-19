package com.mits.filenet.ce.document;



import org.apache.log4j.Logger;


import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class DemotePromoteVersionFreeze {
	
	
	
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingAllVersion.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		DemotePromoteVersionFreeze objDemoteVersion = new DemotePromoteVersionFreeze();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objDemoteVersion.demotepromoteVersi(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	// DemoteVersion   method to demote a major version to a minor version
	//promoteVersion method to promote a current minor version to a released major version
	//the freeze method to prevent changes to the custom properties of a checked-in document version.

	public void demotepromoteVersi(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
			
			Document doc = Factory.Document.getInstance(objStore, ClassNames.DOCUMENT, new Id("{D0DB0E6F-0000-C990-8CB9-59D9EF1BCA64}") );
			doc.demoteVersion();
			doc.save(RefreshMode.REFRESH);
			doc.promoteVersion();
			doc.save(RefreshMode.REFRESH);
			doc.freeze();
			doc.save(RefreshMode.REFRESH);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		}
	
	

}
