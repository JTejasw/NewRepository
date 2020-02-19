package com.mits.filenet.ce.customobject;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.filenet.api.collection.PropertyDescriptionList;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.meta.PropertyDescription;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class RetrvingDifCustomSystemProperties {
	static Connection conn = null;
	// using it logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrvingDifCustomSystemProperties.class);
	

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		RetrvingDifCustomSystemProperties objRetrCuObj= new RetrvingDifCustomSystemProperties();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objRetrCuObj.FetchDifCustomSystemPropertiest(conn);
		
		ObjgetDBUtil.closeConnection();
	}

	// Retrieving in document class Unstructure(FileNet CE API)
	public void FetchDifCustomSystemPropertiest(Connection conn) {

		try {

			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
			
			
			// Construct property filter to ensure PropertyDescriptions property of CD is returned as evaluated
			
			Document objDocumentObject = Factory.Document.fetchInstance(objStore, new Id("{00AA176F-0000-CB15-882A-4B615D2009D8}"),null);
			
			// Get PropertyDescriptions property from the property cache
			 
				 
			PropertyDescriptionList objPropDescs = objDocumentObject.get_ClassDescription().get_PropertyDescriptions(); 
			PropertyDescription objPropDesc = null;
			
			
			 
           	Iterator<?> iter = objPropDescs.iterator();
			
			 System.out.println("iter.hasNext()" + iter.hasNext());
			// Loop until property description found
			 System.out.println("Property" + "\t" + "Value");
				
			while (iter.hasNext())
			{  
				objPropDesc = (PropertyDescription)iter.next();
				
				
			   if(!((objPropDesc.get_IsSystemOwned())||(objPropDesc.get_IsSystemGenerated())||(objPropDesc.get_IsHidden())||(objPropDesc.get_SymbolicName().equalsIgnoreCase("SourceDocument")))){
				  
				 	
						System.out.println("Custom properties**********"+objPropDesc.get_SymbolicName() + "\t" +objDocumentObject.getProperties().getObjectValue(objPropDesc.get_SymbolicName()));
			   } 
			   else if(((objPropDesc.get_IsSystemOwned())||(objPropDesc.get_IsSystemGenerated())||(objPropDesc.get_IsHidden())||(objPropDesc.get_SymbolicName().equalsIgnoreCase("SourceDocument"))))
		    	{
				   
				   System.out.println("System properties**********"+objPropDesc.get_SymbolicName() + "\t" +objDocumentObject.getProperties().getObjectValue(objPropDesc.get_SymbolicName()));
			   }
			      
			  
			}
			      
		
			      
			}catch (Exception e) {
			e.printStackTrace();
		}
}
}