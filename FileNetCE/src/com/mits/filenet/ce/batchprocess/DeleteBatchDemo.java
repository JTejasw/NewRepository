package com.mits.filenet.ce.batchprocess;

import org.apache.log4j.Logger;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.UpdatingBatch;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class DeleteBatchDemo {

	
	// implementting logger to debuge the code
			static final Logger LOGGER = Logger.getLogger(DeleteBatchDemo.class);

			static Connection conn = null;
			// declaration ACCEUtil object for getting connection
			static ACCEUtil ObjgetDBUtil = new ACCEUtil();

			// main declaration
			public static void main(String[] args) {
				LOGGER.info("inside main method");

				try {

			conn = ObjgetDBUtil.getConnection();
			DeleteBatchDemo objDeleteBatchDemo=new DeleteBatchDemo();
			objDeleteBatchDemo.deletingBatchDemo(conn);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ObjgetDBUtil.closeConnection();
				}

			}// end main

			// creating custom object class in acce
			public  void deletingBatchDemo(Connection conn) {
				LOGGER.info("inside CreateCustomObject method");

				try {
					
					
					Domain domain = Factory.Domain.fetchInstance(conn, null, null);
					LOGGER.info("Domain: " + domain.get_Name());

					// Get object stores for domain.fetching Object Store
					
					

					ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
					//ObjectStoreSet osSet = domain.get_ObjectStores();
					
					

					// The RefreshMode parameter is set to REFRESH to indicate that the property cache for 
					// this instance is to be refreshed with the updated data.
					UpdatingBatch updatingBatch = UpdatingBatch.createUpdatingBatchInstance(domain, RefreshMode.REFRESH);

					// Add object updates to the batch.
					// Assume, in this case, that these documents have already been checked out.
					// No property filters are used (filter parameters are null).
					CustomObject objCustomObject = Factory.CustomObject.fetchInstance(objStore,new Id("{101E4B6F-0000-CC1B-B366-7894B77DDF3E}") ,null);
					
					Document doc2 = Factory.Document.fetchInstance(objStore, new Id("{30F74A6F-0000-CD27-816B-B9C595A94A2E}"), null);
					
					
					objCustomObject.delete();
					LOGGER.info("Deleted successfully."+ClassNames.DOCUMENT);
					doc2.delete();
					LOGGER.info("Deleted successfully."+ClassNames.DOCUMENT);
					// Adds all four updates (to the two Document objects) to the UpdatingBatch instance. 
					updatingBatch.add(objCustomObject, null);  
					updatingBatch.add(doc2, null);  

					// Execute the batch update operation.
					updatingBatch.updateBatch();
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					
				}
			}
	
}
