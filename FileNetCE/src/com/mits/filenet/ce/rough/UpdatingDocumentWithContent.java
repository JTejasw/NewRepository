package com.mits.filenet.ce.rough;
import java.io.File;
import java.io.FileInputStream;
import org.apache.log4j.Logger;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.Connection;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class UpdatingDocumentWithContent {

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(UpdatingDocumentWithContent.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		UpdatingDocumentWithContent objRetriDocum = new UpdatingDocumentWithContent();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objRetriDocum.updateCEDocumnet(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	// updating all documents properties in that only custom properties)


	@SuppressWarnings("unchecked")
	public void updateCEDocumnet(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());

			PropertyFilter pf = new PropertyFilter();
			pf.addIncludeProperty(new FilterElement(null, null, null, "DocumentTitle", null));
			pf.addIncludeProperty(new FilterElement(null, null, null, "namefeild", null));
			pf.addIncludeProperty(new FilterElement(null, null, null, "jtage", null));

			Document doc = Factory.Document.fetchInstance(objStore, new Id("{E0F80E6F-0000-C6B7-9BFE-16E0BB8873DC}"),pf);
			com.filenet.api.property.Properties props = doc.getProperties();
			// Change property value.
			props.putValue("DocumentTitle", "shathru");
			props.putValue("namefeild", "Documen");
			props.putValue("jtage", 21);

					
					 //Check out the Document object and save it.
					doc.checkout(ReservationType.OBJECT_STORE_DEFAULT, null, null, null);
					doc.save(RefreshMode.REFRESH);      

					// Get the reservation object from the Document object.
					Document reservation = (Document) doc.get_Reservation();

					// Check in reservation object as minor version.
					reservation.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
					reservation.save(RefreshMode.REFRESH);
					

					// Add the second of two new versions.
					// This version will have content.
					/*VersionSeries verSeries = doc.get_VersionSeries();

					// Print information about new version 1 of 2.
					Versionable version = verSeries.get_CurrentVersion();
					System.out.println("Status of current version: " + version.get_VersionStatus().toString() +
					   "\n Number of current version: " + version.get_MajorVersionNumber() +"."+ version.get_MinorVersionNumber() );

					// Check out the VersionSeries object and save it.
					verSeries.checkout(ReservationType.OBJECT_STORE_DEFAULT, null, null, null);
					verSeries.save(RefreshMode.REFRESH);

					// Get the reservation object from the VersionSeries object.
					reservation = (Document) verSeries.get_Reservation();
*/
					// Add content to reservation object.
					File file = new File("D:\\CoreJava\\Task\\FileNet\\src\\UploadContent\\2.jpg");
					try {
					   //Create a ContentTransfer object
					   ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
					   FileInputStream fileIS = new FileInputStream(file.getAbsolutePath());
					   @SuppressWarnings("deprecation")
					ContentElementList contentList = Factory.ContentTransfer.createList();
					   ctObject.setCaptureSource(fileIS);
					   // Add ContentTransfer object to list and set on reservation
					   contentList.add(ctObject);
					   reservation.set_ContentElements(contentList);
					}
					catch (Exception e)
					{
					   System.out.println(e.getMessage() );
					}
					 
					// Check in reservation object as major version.
				/*	reservation.checkin(null, CheckinType.MAJOR_VERSION);
					reservation.save(RefreshMode.REFRESH);*/
					 
					// Print information about new version 2 of 2.
					/*version = verSeries.get_CurrentVersion();
					System.out.println("Status of current version: " + version.get_VersionStatus().toString() +
			"\n Number of current version: " + version.get_MajorVersionNumber() +"."+ version.get_MinorVersionNumber() );*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		}

