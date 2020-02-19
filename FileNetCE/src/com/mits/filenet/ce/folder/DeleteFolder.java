package com.mits.filenet.ce.folder;

import org.apache.log4j.Logger;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.mits.filenet.ce.util.ACCEUtil;

public class DeleteFolder {

	//implementting logger to debuge the code
		static final Logger LOGGER = Logger.getLogger(CreatingFolder.class);
		static Connection conn = null;
	    
	     //creating folder ,sub folder  in Root Folder
		public  void DeleteCEFolder() { 
			try {
				// fetching Domain
				Domain domain = Factory.Domain.fetchInstance(conn, null, null);
				LOGGER.info("domain" + domain.get_Name());
				// fetching Object Store
				ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
				LOGGER.info("objStore" + objStore.get_Name());

				LOGGER.info("Object Store =" + objStore.get_DisplayName());

			Folder doc = Factory.Folder.fetchInstance(objStore,"{10EEF86E-0000-C914-85B8-6DA9780D866C}",null );
			doc.delete();
			//Get document and populate property cache.
		    doc.save(RefreshMode.REFRESH );	

			} catch (Exception e) {
				e.printStackTrace();
			}

		} //end createCEFolder method
		
		//main declaration
		public static void main(String[] args) {
			ACCEUtil ObjDBUtil = new ACCEUtil();
			DeleteFolder objDltFld = new DeleteFolder();
			LOGGER.info("inside main method");
			// FileNet CE Connection
	        conn = ObjDBUtil.getConnection();
	        objDltFld.DeleteCEFolder();
			ObjDBUtil.closeConnection();

		}//end main
	
}
