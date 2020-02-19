package com.mits.filenet.ce.document;

import org.apache.log4j.Logger;

import com.filenet.api.admin.FileStorageArea;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.VersionSeries;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class RetrievingAllVersion {

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingAllVersion.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		RetrievingAllVersion objRetrAllVer = new RetrievingAllVersion();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objRetrAllVer.RetAllVersion(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	//RetrievingAllVersion moves the content for all of the document versions to a different storage location.


	public void RetAllVersion(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
			
			// Get document and put VersionSeries object in property cache.
			PropertyFilter pf = new PropertyFilter();
			pf.addIncludeProperty(new FilterElement(null, null, null, PropertyNames.VERSION_SERIES, null)); 
			Document doc = Factory.Document.fetchInstance(objStore, "{90C20E6F-0000-C998-8314-52D8459A5FFA}",pf );

			// Get VersionSeries object.
			VersionSeries verSeries = doc.get_VersionSeries();

			// Get the storage area where you want to move the document content.
			FileStorageArea fsa = Factory.FileStorageArea.fetchInstance(objStore, new Id("{D0DB0E6F-0000-C990-8CB9-59D9EF1BCA64}"), null );

			// Move content.
			verSeries.moveContent(fsa);
			verSeries.save(RefreshMode.REFRESH);
			
} catch (Exception e) {
	e.printStackTrace();
}
	
}
}
