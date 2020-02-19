package com.mits.filenet.ce.document;

import org.apache.log4j.Logger;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.VersionSeries;
import com.filenet.api.property.PropertyFilter;
import com.mits.filenet.ce.util.ACCEUtil;

public class DeletingVersionSeriesObject {
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingAllVersion.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		DeletingVersionSeriesObject objDltVerSeriObject = new DeletingVersionSeriesObject();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objDltVerSeriObject.DelVersionSeriesObject(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	// cancel checkout  after checkin

	public void DelVersionSeriesObject(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
			
			
			// Get document and put VersionSeries object in property cache.
			PropertyFilter pf = new PropertyFilter();
			//pf.addIncludeProperty(new FilterElement(null, null, null, PropertyNames.VERSION_SERIES, null)); 
			Document doc = Factory.Document.fetchInstance(objStore, "{30BF0E6F-0000-C410-9DE2-E345733EB4C6}", pf );

			// Get VersionSeries object.
			VersionSeries verSeries = doc.get_VersionSeries();

			// Delete VersionSeries object.
			verSeries.delete();
			verSeries.save(RefreshMode.REFRESH);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
