package com.mits.filenet.ce.customobject;

import org.apache.log4j.Logger;

import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;

import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;

import com.mits.filenet.ce.util.ACCEUtil;

public class DeleteCustomObject {

	
	
	static Connection conn=null;
	//implementting logger to debuge the code
		static final Logger LOGGER = Logger.getLogger(DeleteCustomObject.class);
		//main declaration
		public static void main(String[] args) {
			
			 ACCEUtil ObjgetDBUtil=new ACCEUtil();
			 DeleteCustomObject objDltCustObje=new  DeleteCustomObject();
			  LOGGER.info("inside main");
			//FileNet CE Connection 
			  conn=ObjgetDBUtil.getConnection();
			  LOGGER.error("calling method");
			  objDltCustObje.deletingCustomObject(conn);
			   ObjgetDBUtil.closeConnection();
			}//end main
		//Deleting   document (FileNet CE API)
	public void deletingCustomObject(Connection conn) {  
		   
		  try {
			    Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			     LOGGER.info("domain............."+domain.get_Name());
			     
				ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
				LOGGER.info("ObjectStore........"+objStore.get_DisplayName());
				
				//PropertyFilter pf = new PropertyFilter();
				
				CustomObject objCustomObject = Factory.CustomObject.fetchInstance(objStore,new Id("{F0BB126F-0000-CB1C-9DB2-57D8B68FD9FA}") ,null);
					
				//Document doc = Factory.Document.fetchInstance(objStore,  new Id("{A090036F-0000-C417-AB68-59C53E6C2C0E}"), pf);
				objCustomObject.delete();
				// Get document and populate property cache.
				objCustomObject.save(RefreshMode.REFRESH );
				LOGGER.info("Deleted successfully."+ClassNames.DOCUMENT);
		  } catch (Exception e) {
				e.printStackTrace();
		}
		
	}//end deletingCEDocument
}
