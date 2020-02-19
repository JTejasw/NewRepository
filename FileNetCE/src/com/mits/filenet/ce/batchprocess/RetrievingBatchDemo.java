package com.mits.filenet.ce.batchprocess;

import java.util.Iterator;
import org.apache.log4j.Logger;
import com.filenet.api.core.BatchItemHandle;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.RetrievingBatch;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.property.Properties;
import com.filenet.api.property.Property;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class RetrievingBatchDemo {

	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingBatchDemo.class);

	static Connection conn = null;
	// declaration ACCEUtil object for getting connection
	static ACCEUtil ObjgetDBUtil = new ACCEUtil();

	// main declaration
	public static void main(String[] args) {
		LOGGER.info("inside main method");

		try {

			conn = ObjgetDBUtil.getConnection();
			RetrievingBatchDemo objRetrievingBatchDemo = new RetrievingBatchDemo();
			objRetrievingBatchDemo.retrievingBatchDemo(conn);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ObjgetDBUtil.closeConnection();
		}

	}// end main

	// creating custom object class in acce
	public void retrievingBatchDemo(Connection conn) {
		LOGGER.info("inside CreateCustomObject method");

		try {

			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("Domain: " + domain.get_Name());

			// Get object stores for domain.fetching Object Store
			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			// ObjectStoreSet osSet = domain.get_ObjectStores();

			RetrievingBatch retrievingBatch = RetrievingBatch.createRetrievingBatchInstance(domain);

			// Add objects to the batch. New objects are used for the purposes
			// here.

			CustomObject objCustomObject = Factory.CustomObject.fetchInstance(objStore,
					new Id("{50FA4A6F-0000-CF11-BFD8-F0A6B06441FD}"), null);
			
			Document objDocument = Factory.Document.fetchInstance(objStore,
					new Id("{E0F9216F-0000-C21D-8C59-C3C898DE0DD3}"), null);

			// No filtering is used.
			retrievingBatch.add(objCustomObject, null);
			retrievingBatch.add(objDocument, null);

			// Execute the batch retrieval operation:
			retrievingBatch.retrieveBatch();

			Iterator<?> iterator1 = retrievingBatch.getBatchItemHandles(null).iterator();
			while (iterator1.hasNext()) {
				
				BatchItemHandle obj = (BatchItemHandle) iterator1.next();
				IndependentObject object = obj.getObject();
				Properties properties = object.getProperties();
				
				Iterator<?> iter = properties.iterator();
				LOGGER.info("Iterator....." + iter.hasNext());

				System.out.println("Property" + "\t" + "Value");
				System.out.println("------------------------");
				while (iter.hasNext()) {
					Property prop = (Property) iter.next();
					System.out.println(prop.getPropertyName() + "\t" + prop.getObjectValue());
				}
           	if (obj.hasException()) {
					// Displays the exception, for the purpose of brevity here.
					EngineRuntimeException thrown = obj.getException();
					System.out.println("Exception: " + thrown.getMessage());
				}
			}

	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
}
