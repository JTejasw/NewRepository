package com.mits.filenet.ce.batchprocess;

import org.apache.log4j.Logger;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.UpdatingBatch;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;



public class UpdatingBatchDemo {
	
	
	
	// implementting logger to debuge the code
		static final Logger LOGGER = Logger.getLogger(UpdatingBatchDemo.class);

		static Connection conn = null;
		// declaration ACCEUtil object for getting connection
		static ACCEUtil ObjgetDBUtil = new ACCEUtil();

		// main declaration
		public static void main(String[] args) {
			LOGGER.info("inside main method");

			try {

		conn = ObjgetDBUtil.getConnection();
		UpdatingBatchDemo objUpdatingBatch=new UpdatingBatchDemo();
		objUpdatingBatch.updatingBatch(conn);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ObjgetDBUtil.closeConnection();
			}

		}// end main

		// creating custom object class in acce
		public  void updatingBatch(Connection conn) {
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
				Document doc1 = Factory.Document.fetchInstance(objStore, new Id("{20F21D6F-0000-C713-A918-3B69552014CC}"), null);
				Document doc2 = Factory.Document.fetchInstance(objStore, new Id("{2051236F-0000-C21F-9DDA-DCE0ABBD58B7}"), null);

				// First update to be included in batch.
				doc1 = (Document) doc1.get_Reservation();
				doc1.checkin(null, CheckinType.MAJOR_VERSION);
				// Second update to be included in batch.
				doc2 = (Document) doc2.get_Reservation();
				doc2.checkin(null, CheckinType.MAJOR_VERSION); 

				// Third update to be included in batch. Sets the document title and assigns the 
				// specified property values (Properties.putValue) to the retrieved properties for the 
				// doc (the inherited EngineObject.getProperties). 
				doc1.getProperties().putValue("DocumentTitle", "doc1"); 

				// Fourth update to be included in batch.
				doc2.getProperties().putValue("DocumentTitle", "doc2"); 

				// Adds all four updates (to the two Document objects) to the UpdatingBatch instance. 
				updatingBatch.add(doc1, null);  
				updatingBatch.add(doc2, null);  

				// Execute the batch update operation.
				updatingBatch.updateBatch();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
}
