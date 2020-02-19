package com.mits.filenet.ce.folder;

import org.apache.log4j.Logger;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.mits.filenet.ce.document.UpdatingDocumentWthoutContent;
import com.mits.filenet.ce.util.ACCEUtil;

public class MovingFolder {
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(UpdatingDocumentWthoutContent.class);
//main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		MovingFolder objMovFolder = new MovingFolder();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objMovFolder.moveFolder(conn);
       ObjgetDBUtil.closeConnection();
	}//end main

	// updating  all documents properties in that only custom properties)
	public void moveFolder(Connection conn) { 
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
			Folder folderOj = Factory.Folder.fetchInstance(objStore, "/Tejaswi", null);
	        Folder folderOj1 = Factory.Folder.fetchInstance(objStore, "/Tejaswini", null);
	      
	        folderOj.move(folderOj1);
			
	        folderOj.save(RefreshMode.REFRESH);
	        folderOj1.save(RefreshMode.REFRESH);
	        LOGGER.info("Folder moved successful");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end moveFolder
			
			
}
