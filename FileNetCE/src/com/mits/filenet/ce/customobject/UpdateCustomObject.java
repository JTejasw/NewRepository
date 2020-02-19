package com.mits.filenet.ce.customobject;

import org.apache.log4j.Logger;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.document.UpdatingDocumentWthoutContent;
import com.mits.filenet.ce.util.ACCEUtil;

public class UpdateCustomObject {

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(UpdatingDocumentWthoutContent.class);
//main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		UpdateCustomObject objUpdateCusObj = new UpdateCustomObject();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objUpdateCusObj.updateCECustomObject(conn);
       ObjgetDBUtil.closeConnection();
	}//end main

	// updating  all documents properties in that only custom properties)
	
	public void updateCECustomObject(Connection conn) { 
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
	
			// Get document and populate property cache.
			PropertyFilter pf = new PropertyFilter();
		
			
			pf.addIncludeProperty(new FilterElement(null, null, null, "namefeild", null));
			pf.addIncludeProperty(new FilterElement(null, null, null, "jtage", null));

			CustomObject doc = Factory.CustomObject.fetchInstance(objStore, new Id("{DFB0D4A8-1E79-4528-88EA-ABF236E67020}"),
					pf);
			// Return document properties.
			com.filenet.api.property.Properties props = doc.getProperties();
			// Change property value.
		
			props.putValue("T_Name", "teju");
			props.putValue("T_Age", 12);

			
		  // Save and update property cache.
			doc.save(RefreshMode.REFRESH);
			LOGGER.info("Updated successfully." + ClassNames.DOCUMENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end updateCEDocumnet
}
