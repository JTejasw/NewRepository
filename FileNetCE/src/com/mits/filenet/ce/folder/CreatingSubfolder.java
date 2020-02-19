package com.mits.filenet.ce.folder;

import org.apache.log4j.Logger;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.mits.filenet.ce.util.ACCEUtil;


/**
 * @author mitsind759
 *Fetching Folder in that creation  sub folder
 */

//class declartion
public class CreatingSubfolder {

	 Connection conn = null;
 // implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(CreatingSubfolder.class);

	public  void createCESubFolder(Connection conn) { // Creating Sub Folder Method

		try {

			// fetching Domain
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			// fetching Object Store
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("Object Store =" + objStore.get_DisplayName());
			String folder = "/Tejaswini";

			// Fetching Parent folder
			com.filenet.api.core.Folder folderOj = Factory.Folder.fetchInstance(objStore, folder, null);
			LOGGER.info("Floder" + folderOj.get_Name());

			// Creating Sub folder
			com.filenet.api.core.Folder subFolder = folderOj.createSubFolder("veeru");
			subFolder.save(RefreshMode.REFRESH);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end createCESubFolder
//main declaration
	public static void main(String[] args) {
	
		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		// getting connection
		Connection conn = ObjgetDBUtil.getConnection();
	    CreatingSubfolder objCreSubFld=new CreatingSubfolder();
		LOGGER.info("inside main method");
		objCreSubFld.createCESubFolder(conn);
		ObjgetDBUtil.closeConnection();

	}//end main

}//end class
