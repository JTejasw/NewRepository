package com.mits.filenet.ce.document;

import java.io.FileInputStream;

import org.apache.log4j.Logger;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.mits.filenet.ce.util.ACCEUtil;
import com.mits.filenet.ce.util.Log4jUtil;

public class UnfileDocument {

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(CreatingDocumentWthContent.class);

	// declaration main
	public static void main(String[] args) {
		LOGGER.info("inside main method");
		// creating object
		Log4jUtil objLog4jUtil = new Log4jUtil();
		UnfileDocument objUnfileDocument = new UnfileDocument();
		ACCEUtil ObjgetDBUtil = new ACCEUtil();

		objLog4jUtil.log4jProperties();
		LOGGER.info("inside main method");
		conn = ObjgetDBUtil.getConnection();
		LOGGER.info("calling method");
		objUnfileDocument.createDocumentObject(conn);
		ObjgetDBUtil.closeConnection();
		LOGGER.debug("closing connection");

	}//end main

	// intialization felids
	FileInputStream file = null;
	FileInputStream file1 = null;
	String fileName = "Teju";
	int fileSize = 0;

	@SuppressWarnings("unchecked")

	// creating document class with content (both structure,Unstructure)
	public void createDocumentObject(Connection conn) {

		try {

			LOGGER.info("inside try block");
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			// Creaing Document and setting properties
			// Object store,Costome Object Class Name
			Document myObject = Factory.Document.createInstance(objStore, "jtdoc");
			LOGGER.info("Document object");

			file = new FileInputStream("D:\\CoreJava\\Task\\FileNet\\src\\UploadContent\\2.jpg");
			ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
			contentTransfer.setCaptureSource(file);

			file1 = new FileInputStream("D:\\CoreJava\\Task\\FileNet\\src\\UploadContent\\d2.jpg");
			ContentTransfer contentTransfer1 = Factory.ContentTransfer.createInstance();
			contentTransfer1.setCaptureSource(file1);

			ContentElementList contentElementList = Factory.ContentElement.createList();
			contentElementList.add(contentTransfer);
			contentElementList.add(contentTransfer1);

			myObject.set_ContentElements(contentElementList);
			contentTransfer.set_RetrievalName(fileName);
			// myObject.set_MimeType(getMimetype(fileName));

			com.filenet.api.property.Properties props = myObject.getProperties();
			LOGGER.info("Properties ");
			// setting properties
			props.putValue("DocumentTitle", "bhuvan");
			props.putValue("namefeild", "stuInfo022");
			props.putValue("jtage", 23);

			myObject.save(RefreshMode.REFRESH);

			myObject.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
			myObject.save(RefreshMode.NO_REFRESH);

			LOGGER.info("Document " + myObject.get_Name() + " created");

			/* Saving the Custome Object into folder
			String folder = "/";
			 
			com.filenet.api.core.Folder folderOj = Factory.Folder.fetchInstance(objStore, folder, null);
           ReferentialContainmentRelationship rel = folderOj.file(myObject, AutoUniqueName.AUTO_UNIQUE, "document1",
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			rel.save(RefreshMode.REFRESH);*/

			LOGGER.info("Created UnfileDocument class successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end CreateDocumentObject method

}//end class
