package com.mits.filenet.ce.document;



import org.apache.log4j.Logger;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

/**
 * @author mitsind759
 *updating document properties only custom properties
 */
//class declaration
public class UpdatingDocumentWthoutContent {

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(UpdatingDocumentWthoutContent.class);
//main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		UpdatingDocumentWthoutContent objRetriDocum = new UpdatingDocumentWthoutContent();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objRetriDocum.updateCEDocumnet(conn);
       ObjgetDBUtil.closeConnection();
	}//end main

	// updating  all documents properties in that only custom properties)
	
	public void updateCEDocumnet(Connection conn) { 
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());

			
			
			// Get document and populate property cache.
			PropertyFilter pf = new PropertyFilter();
		
			pf.addIncludeProperty(new FilterElement(null, null, null, "DocumentTitle", null));
			pf.addIncludeProperty(new FilterElement(null, null, null, "namefeild", null));
			pf.addIncludeProperty(new FilterElement(null, null, null, "jtage", null));

			Document doc = Factory.Document.fetchInstance(objStore, new Id("{E0530E6F-0000-CB1B-91E2-0F57DEB8AB4D}"),
					pf);
			// Return document properties.
			com.filenet.api.property.Properties props = doc.getProperties();
			// Change property value.
			props.putValue("DocumentTitle", "Document java");
			props.putValue("namefeild", "Document java");
			props.putValue("jtage", 12);

			
		  // Save and update property cache.
			doc.save(RefreshMode.REFRESH);
			LOGGER.info("Updated successfully." + ClassNames.DOCUMENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end updateCEDocumnet

}//end class
