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
 *Fecting Folders ,Sub Folder Through CE API
 */
//class declaration
public class FetchingFolders {
	// implementting logger to debuge the code
		static final Logger LOGGER = Logger.getLogger(FetchingFolders.class);

	
   //main declaration
	public static void main(String[] args) {
		 ACCEUtil ObjDBUtil = new ACCEUtil();
		FetchingFolders objFetFol=new FetchingFolders();
		Connection conn = ObjDBUtil.getConnection();
		objFetFol.FetchCEFolder(conn);
		ObjDBUtil.closeConnection();

	}
   
	public  void FetchCEFolder(Connection conn) { // FileNet CE Connection

		try {

			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			System.out.println("Object Store =" + objStore.get_DisplayName());

			String folder = "/Tejaswini";

			// Fetching Parent folder
			com.filenet.api.core.Folder folderOj = Factory.Folder.fetchInstance(objStore, folder, null);
			System.out.println("Floder" + folderOj.get_Name());

			// Fetching sub folders
			FolderSet subFolders = folderOj.get_SubFolders();
			Iterator<?> it = subFolders.iterator();
			while (it.hasNext()) {
				Folder subFolder = (Folder) it.next();
				String name = subFolder.get_FolderName();
				System.out.println("Subfolder = " + name);

				// Fetching hidden folders
				if (subFolder.getProperties().getBooleanValue("IsHiddenContainer"))
					System.out.println("Folder " + name + "is hidden");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
