package com.mits.filenet.ce.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Connection;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.Property;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class RetrieveDocmentandContentSeperately {

	

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrieveDocmentandContentSeperately.class);
	
	
String path="D:\\CoreJava\\Task\\FileNet\\src\\DownloadContent\\";
 
	
	FileOutputStream fos=null;
	FileWriter filewrite=null;
	BufferedWriter bufferedWriter=null;
	InputStream stream=null;

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		RetrieveDocmentandContentSeperately objRetrAllVer = new RetrieveDocmentandContentSeperately();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objRetrAllVer.retrievingwthOutContentSeperately(conn);
		objRetrAllVer.retrveDocument(conn);
		ObjgetDBUtil.closeConnection();
	}// end main

	//RetrievingAllVersion moves the content for all of the document versions to a different storage location.

	
	public void retrievingwthOutContentSeperately(Connection conn) {
		try{
	Domain domain = Factory.Domain.fetchInstance(conn, null, null);
	LOGGER.info("domain............." + domain.get_Name());

	ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
	LOGGER.info("ObjectStore........" + objStore.get_DisplayName());

	PropertyFilter pf = new PropertyFilter();
	//pf.addIncludeProperty(new FilterElement(null, null, null, "DocumentTitle", null) );
	

	LOGGER.info("ClassNames.DOCUMENT............." + ClassNames.DOCUMENT);

	Document objDocumentObject = Factory.Document.fetchInstance(objStore, new Id("{50EE286F-0000-C011-8B0A-50A41953E723}"),
			pf);

	
	// Fetch selected properties from the server.
	// doc.fetchProperties(pf);
LOGGER.info("properties*************************************************************" + pf);


// Return document properties.
	com.filenet.api.property.Properties props = objDocumentObject.getProperties();

	// Iterate the set and print property values.
	Iterator<?> iter = props.iterator();
	LOGGER.info("Iterator....." + iter.hasNext());

	System.out.println("Property" + "\t" + "Value");
	System.out.println("------------------------");
	
	filewrite=new FileWriter(new File(path +"WithOutContent.txt"));
	bufferedWriter=new BufferedWriter(filewrite);
	
	 System.out.println(path +"WithOutContent.txt");
	while (iter.hasNext()) {
		Property prop = (Property) iter.next();
		System.out.println(prop.getPropertyName() + "\t" + prop.getObjectValue());
		
		String propeties=prop.getPropertyName()+":::::::"+prop.getObjectValue();
		
		
		if (prop.getObjectValue() != null) {
			
			//byte[] formateByte=propeties.getBytes();
		
			
			bufferedWriter.write(propeties);
			bufferedWriter.newLine();
			
			
			
		}
	 
		
		
		/*byte[] a=prop.getPropertyName().getBytes();
		byte[] b=null;
		if(prop.getObjectValue()!=null){
			b=prop.getObjectValue().toString().getBytes();
		fos.write(a);
		fos.write(b);}*/

		
		
		
		
		
	}
	bufferedWriter.close();
	filewrite.close();
} catch (Exception e) {
	e.printStackTrace();
}
}




	public void retrveDocument(Connection conn){
		try{
		
	
			LOGGER.info("inside try block");
		Domain domain = Factory.Domain.fetchInstance(conn, null, null);

		ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
		
		
	
		
		PropertyFilter pf = new PropertyFilter();
		pf.addIncludeProperty(new FilterElement(null, null, null, PropertyNames.CONTENT_SIZE, null) );
		pf.addIncludeProperty(new FilterElement(null, null, null, PropertyNames.CONTENT_ELEMENTS, null) );
		Document doc = Factory.Document.fetchInstance(objStore, "{50EE286F-0000-C011-8B0A-50A41953E723}",pf );
		
		ContentElementList content = doc.get_ContentElements();
		
		Iterator<?> iterator = content.iterator();
		
			
		while(iterator.hasNext()){
			ContentTransfer ct = (ContentTransfer) iterator.next();
			
				
			int docLen = ct.get_ContentSize().intValue();
		    byte[] buf = new byte[docLen];
		    InputStream stream = ct.accessContentStream();
		    try
		    {
		        stream.read(buf, 0, docLen);
		        String readStr = new String(buf);
		       System.out.println("Content:\n " + readStr);
		        
		        fos = new FileOutputStream(new File(path + ct.get_RetrievalName()));
				fos.write(buf);
		        fos.close();
		        stream.close();
		    }
		    catch(IOException ioe)
		    {
		        ioe.printStackTrace();
		    }
					

		
			
			

		}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
