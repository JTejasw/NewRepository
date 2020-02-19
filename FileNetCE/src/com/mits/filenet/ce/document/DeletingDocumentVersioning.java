package com.mits.filenet.ce.document;

import java.util.Iterator;
import org.apache.log4j.Logger;
import com.filenet.api.collection.VersionableSet;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.VersionSeries;
import com.filenet.api.core.Versionable;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class DeletingDocumentVersioning {
	
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingAllVersion.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		DeletingDocumentVersioning objDelDocVers = new DeletingDocumentVersioning();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objDelDocVers.DeleteCEDocumnetVer(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	// updating all documents properties in that only custom properties)

	public void DeleteCEDocumnetVer(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
	
	 PropertyFilter pf = new PropertyFilter();  
	 //pf.addIncludeProperty(new FilterElement(null, null, null, "VersionSeries Id", null) );
	 //pf.addIncludeProperty(new FilterElement(null, null, null, "Version Status", null) );
	 Document doc = Factory.Document.fetchInstance(objStore, new Id("{800D0F6F-0000-CB11-8907-FFE3983154D1}"),pf );   

	// Get set of document versions from VersionSeries
	VersionSeries vs = doc.get_VersionSeries();
	VersionableSet vss = vs.get_Versions();

	// Iterate versions and delete the third one.
	Iterator<?> vssiter = vss.iterator();
	while (vssiter.hasNext()){
	   Versionable ver = (Versionable)vssiter.next();
	   System.out.println("Major = " + ver.get_MajorVersionNumber() + "; Minor = " + ver.get_MinorVersionNumber()+  "; Version Status = " +ver.get_VersionStatus());
	   if (ver.get_MajorVersionNumber().intValue() == 3){
	      // To delete, cast the Versionable object to Document.
	      Document verdoc = (Document) ver;
	      verdoc.delete();
	      verdoc.save(RefreshMode.REFRESH);
	   }
	}
}catch (Exception e) {
	e.printStackTrace();
}
	}
}
