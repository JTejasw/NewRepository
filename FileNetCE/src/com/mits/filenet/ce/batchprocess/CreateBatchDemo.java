package com.mits.filenet.ce.batchprocess;



import org.apache.log4j.Logger;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.core.UpdatingBatch;
import com.mits.filenet.ce.util.ACCEUtil;

public class CreateBatchDemo {

	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(CreateBatchDemo.class);

	static Connection conn = null;
	// declaration ACCEUtil object for getting connection
	static ACCEUtil ObjgetDBUtil = new ACCEUtil();

	// main declaration
	public static void main(String[] args) {
		LOGGER.info("inside main method");

		try {

			conn = ObjgetDBUtil.getConnection();
			CreateBatchDemo objCreateBatchDemo = new CreateBatchDemo();
			objCreateBatchDemo.creatingBatchDemo(conn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ObjgetDBUtil.closeConnection();
		}

	}// end main

	// creating custom object class in acce
	public void creatingBatchDemo(Connection conn) {
		LOGGER.info("inside CreateCustomObject method");

		try {

			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("Domain: " + domain.get_Name());

			// Get object stores for domain.fetching Object Store
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			
			UpdatingBatch updatingBatch = UpdatingBatch.createUpdatingBatchInstance(domain, RefreshMode.REFRESH);

			// Creaing Custome Object and setting properties
			// Object store, Custome Object Class Name
		    CustomObject myCustomObject = Factory.CustomObject.createInstance(objStore, "jtcustomobject");
		    com.filenet.api.property.Properties props3 = myCustomObject.getProperties();
			props3.putValue("jtname", "ABatch11");
			//myCustomObject.save(RefreshMode.NO_REFRESH);
			

			// Creaing Custome Object and setting properties Object store,
			// Custome Object Class Name
			Document myObjectDocument = Factory.Document.createInstance(objStore, "jtdoc");
            com.filenet.api.property.Properties propsDocument = myObjectDocument.getProperties();
			// setting properties
            propsDocument.putValue("DocumentTitle", "ABatch12");
            propsDocument.putValue("namefeild", "stuInfo022");
            propsDocument.putValue("jtage", 23);
			myObjectDocument.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
			//myObjectDocument.save(RefreshMode.NO_REFRESH);
		
			
			
		    Document myObjectDocument1 = Factory.Document.createInstance(objStore, "jtdoc");
			com.filenet.api.property.Properties propsDocument1 = myObjectDocument1.getProperties();
			// setting properties
            propsDocument1.putValue("DocumentTitle", "ABatch13");
            propsDocument1.putValue("namefeild", "stuInfo022");
            propsDocument1.putValue("jtage", 23);
		    myObjectDocument1.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
			//myObjectDocument1.save(RefreshMode.NO_REFRESH);
			
			
			
		    // Saving the Custome Object into folder
		    String folder = "/Tejaswini";
			com.filenet.api.core.Folder folderOj = Factory.Folder.fetchInstance(objStore, folder, null);
			// System.out.println("del"+folderOj.get_Name());
			ReferentialContainmentRelationship referentialContainmentRelationship1 = folderOj.file(myObjectDocument, AutoUniqueName.AUTO_UNIQUE,
					"ABatching11", DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			
			ReferentialContainmentRelationship referentialContainmentRelationship2 = folderOj.file(myObjectDocument1, AutoUniqueName.AUTO_UNIQUE,
					"ABatching12", DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			
			ReferentialContainmentRelationship referentialContainmentRelationship3 = folderOj.file(myCustomObject, AutoUniqueName.AUTO_UNIQUE,
					"ABatching13", DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
		
			LOGGER.info(" Object created successfully");

			// Adds all four updates (to the two Document objects) to the
			// UpdatingBatch instance.
			/*ub.add(referentialContainmentRelationship1, null);
			ub.add(referentialContainmentRelationship2, null);
			ub.add(referentialContainmentRelationship3, null);*/
			
			
			
			updatingBatch.add(myCustomObject, null);
			updatingBatch.add(myObjectDocument, null);
			updatingBatch.add(myObjectDocument1, null);

			// Execute the batch update operation.
			updatingBatch.updateBatch();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

}
