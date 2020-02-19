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
 *creating folder through Filenet API
 */
//class declaration
public class CreatingFolder {
	
	//implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(CreatingFolder.class);
	static Connection conn = null;
    
     //creating folder ,sub folder  in Root Folder
	public  void createCEFolder() { 
		try {
			// fetching Domain
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain" + domain.get_Name());
			// fetching Object Store
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("objStore" + objStore.get_Name());

			LOGGER.info("Object Store =" + objStore.get_DisplayName());

			// Creating Root folder
			com.filenet.api.core.Folder testFolder = Factory.Folder.createInstance(objStore, null);
			LOGGER.info("testfolder:" + Factory.Folder.createInstance(objStore, null));

			com.filenet.api.core.Folder rootFolder = objStore.get_RootFolder();

			LOGGER.info("rootFolder:" + objStore.get_RootFolder());

			testFolder.set_Parent(rootFolder);
			testFolder.set_FolderName("Teju123"); // Folder Name
			testFolder.save(RefreshMode.REFRESH);

			// Creating Sub folder
			com.filenet.api.core.Folder subFolder = testFolder.createSubFolder("Teju123");
			subFolder.save(RefreshMode.REFRESH);

		} catch (Exception e) {
			e.printStackTrace();
		}

	} //end createCEFolder method
	
	//main declaration
	public static void main(String[] args) {
		ACCEUtil ObjDBUtil = new ACCEUtil();
		CreatingFolder objCteFld = new CreatingFolder();
		LOGGER.info("inside main method");
		// FileNet CE Connection
        conn = ObjDBUtil.getConnection();
		objCteFld.createCEFolder();
		ObjDBUtil.closeConnection();

	}//end main

}//end class
