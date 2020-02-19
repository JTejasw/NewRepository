package com.mits.filenet.ce.document;

import org.apache.log4j.Logger;

import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class DeleteDocumentVersion {

	
	static Connection conn=null;
	//implementting logger to debuge the code
		static final Logger LOGGER = Logger.getLogger(DeletingDocument.class);
		//main declaration
		public static void main(String[] args) {
			
			 ACCEUtil ObjgetDBUtil=new ACCEUtil();
			 DeletingDocument objRetriDocum=new  DeletingDocument();
			  LOGGER.info("inside main");
			//FileNet CE Connection 
			  conn=ObjgetDBUtil.getConnection();
			  LOGGER.error("calling method");
			  objRetriDocum.deletingCEDocument(conn);
			   ObjgetDBUtil.closeConnection();
			}//end main
		//Deleting   document (FileNet CE API)
	public void deletingCEDocument(Connection conn) {  
		   
		  try {
			    Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			     LOGGER.info("domain............."+domain.get_Name());
			     
				ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
				LOGGER.info("ObjectStore........"+objStore.get_DisplayName());
				
				//PropertyFilter pf = new PropertyFilter();
				
				Document doc = Factory.Document.getInstance(objStore, ClassNames.DOCUMENT, new Id("{00B60E6F-0000-C218-8B9E-BFB47A56B479}") );
				
				//Document doc = Factory.Document.fetchInstance(objStore,  new Id("{A090036F-0000-C417-AB68-59C53E6C2C0E}"), pf);
				doc.delete();
				// Get document and populate property cache.
				doc.save(RefreshMode.REFRESH );
				LOGGER.info("Deleted successfully."+ClassNames.DOCUMENT);
		  } catch (Exception e) {
				e.printStackTrace();
		}
		
	}//end deletingCEDocument
}
