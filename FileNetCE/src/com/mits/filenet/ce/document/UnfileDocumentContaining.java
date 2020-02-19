package com.mits.filenet.ce.document;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import com.filenet.api.collection.ReferentialContainmentRelationshipSet;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;
import com.mits.filenet.ce.util.Log4jUtil;

public class UnfileDocumentContaining {
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(UnfileDocumentContaining.class);

	// declaration main
	public static void main(String[] args) {
		LOGGER.info("inside main method");
		// creating object
		Log4jUtil objLog4jUtil = new Log4jUtil();
		UnfileDocumentContaining objUnfileDocument = new UnfileDocumentContaining();
		ACCEUtil ObjgetDBUtil = new ACCEUtil();

		objLog4jUtil.log4jProperties();
		LOGGER.info("inside main method");
		conn = ObjgetDBUtil.getConnection();
		LOGGER.info("calling method");
		objUnfileDocument.UnfileDocContaining(conn);
		ObjgetDBUtil.closeConnection();
		LOGGER.debug("closing connection");

	}//end main

	// intialization felids
	FileInputStream file = null;
	FileInputStream file1 = null;
	String fileName = "Teju";
	int fileSize = 0;

	// creating document class with content (both structure,Unstructure)
	public void UnfileDocContaining(Connection conn) {

		try {

			LOGGER.info("inside try block");
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			
			 PropertyFilter pf = new PropertyFilter(); 

			pf.addIncludeProperty(new FilterElement(null, null, null, "Containers", null));
			// Get document to be unfiled.
			Document doc = Factory.Document.fetchInstance(objStore, new Id("{70B3126F-0000-C112-BEDF-BDB3174A635B}"), pf);

			// Iterate all folders that contain the document, until the desired folder is found.
			ReferentialContainmentRelationshipSet rcrs = doc.get_Containers();
		
			Iterator<?> iter = rcrs.iterator();
			while (iter.hasNext() )
			{
			   ReferentialContainmentRelationship rcr = (ReferentialContainmentRelationship)iter.next();
			   Folder folder = (Folder)rcr.get_Tail();
		  //rcr.get_Head();	 
			   if (folder.get_Id().equals(new Id("{6048F86E-0000-C21F-AB2F-B0B58DBDD939}")))
			   {
			      rcr.delete();
			      rcr.save(RefreshMode.REFRESH);
			      break;
			   }
			}
			LOGGER.info("Created UnfileDocument class successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end CreateDocumentObject method
}
