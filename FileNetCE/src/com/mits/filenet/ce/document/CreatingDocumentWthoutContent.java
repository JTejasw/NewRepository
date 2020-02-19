package com.mits.filenet.ce.document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.mits.filenet.ce.common.DateUTC;
import com.mits.filenet.ce.util.ACCEUtil;

/**
 * @author mitsind759 
 * creating Document class through CE API without content
 */
// class declaration
public class CreatingDocumentWthoutContent {
	static Connection conn = null;

	static ACCEUtil ObjgetDBUtil = new ACCEUtil();
	DateUTC dateUTC =new DateUTC();
	// using it logger details
	static final Logger LOGGER = Logger.getLogger(CreatingDocumentWthoutContent.class);

	//main declaration
	public static void main(String[] args) {
		LOGGER.info("inside main");
		CreatingDocumentWthoutContent objDocWthOutCon = new CreatingDocumentWthoutContent();
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objDocWthOutCon.CreateDocumentObject(conn);
        ObjgetDBUtil.closeConnection();

	}//end main


// creating Document class  without content setting properties 
	public void CreateDocumentObject(Connection conn) {

		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);

			 LOGGER.info("inside try block");

			// Creaing Custome Object and setting properties Object store, Custome Object Class Name
			Document myObject = Factory.Document.createInstance(objStore, "jtdoc");
			
			com.filenet.api.property.Properties props = myObject.getProperties();
			//setting properties 
			props.putValue("DocumentTitle", "TejuAccountOpening");
			props.putValue("namefeild", "stuInfo022");
            props.putValue("jtage", 23);
            
       	    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss aa");
        	Calendar calobj = Calendar.getInstance();
        		
        	String sDate1=df.format(calobj.getTime());  
        	
            Date date1=new SimpleDateFormat("dd/MM/yy HH:mm:ss aa").parse(sDate1);  
        	
        	System.out.println(date1);
            
            
		   props.putValue("TDate",date1);
		
			myObject.save(RefreshMode.REFRESH);
            //setting checkin , version
			myObject.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
			myObject.save(RefreshMode.NO_REFRESH);

			LOGGER.info("Document " + myObject.get_Name() + " created");

			// Saving the Custome Object into folder
			String folder = "/Tejaswini";
			com.filenet.api.core.Folder folderOj = Factory.Folder.fetchInstance(objStore, folder, null);
			// System.out.println("del"+folderOj.get_Name());
			ReferentialContainmentRelationship rel = folderOj.file(myObject, AutoUniqueName.AUTO_UNIQUE, "TejuAccountOpening",
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			rel.save(RefreshMode.REFRESH);
			LOGGER.info("created document without content");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end CreateDocumentObject method
}//end class
