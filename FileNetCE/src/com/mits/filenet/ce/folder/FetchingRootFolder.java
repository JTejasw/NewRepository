package com.mits.filenet.ce.folder;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.filenet.api.collection.FolderSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;

import com.mits.filenet.ce.util.ACCEUtil;

/**
 * @author mitsind759
 *fetching Root folder in that Top Folder,Sub Folder
 */
//class declaration
public class FetchingRootFolder {

	static ACCEUtil ObjgetDBUtil = new ACCEUtil();
	//implementting logger to debuge the code
  static final Logger LOGGER = Logger.getLogger(FetchingRootFolder.class);
		
  //main declaration
	public static void main(String[] args) {
		LOGGER.info("inside main method");
		FetchingRootFolder objFtcRootFld=new FetchingRootFolder();
		// FileNet CE Connection
		Connection conn = ObjgetDBUtil.getConnection();
		objFtcRootFld.FetchCEFolder(conn);
		ObjgetDBUtil.closeConnection();

	}//
    // fetching Root folder in that Top Folder,Sub Folder
	public  void FetchCEFolder(Connection conn) { 

		try {
			// fetching Domain
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			//fetching Object Store
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("Object Store =" + objStore.get_DisplayName());

			FolderSet TopFolders = objStore.get_TopFolders();

			Iterator<?> it = TopFolders.iterator();
			while (it.hasNext()) {
				Folder TopFolder = (Folder) it.next();
				String name = TopFolder.get_FolderName();
				LOGGER.info("TopFolder = " + name);

				// Fetching sub folders
				FolderSet subFolders = TopFolder.get_SubFolders();
				Iterator<?> it1 = subFolders.iterator();
				while (it1.hasNext()) {
					Folder subFolder = (Folder) it1.next();
					String subFolderName = subFolder.get_FolderName();
					LOGGER.info("Subfolder = " + subFolderName);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end FetchCEFolder

}//end class
