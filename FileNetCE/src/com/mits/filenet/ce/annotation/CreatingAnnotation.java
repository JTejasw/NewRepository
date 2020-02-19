package com.mits.filenet.ce.annotation;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Annotation;
import com.filenet.api.core.Connection;
import com.filenet.api.core.ContentElement;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.core.UpdatingBatch;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.mits.filenet.ce.batchprocess.CreateBatchDemo;
import com.mits.filenet.ce.util.ACCEUtil;

public class CreatingAnnotation {

	
	

	// implementting logger to debuge the code
		static final Logger LOGGER = Logger.getLogger(CreateBatchDemo.class);

		static Connection conn = null;
		// declaration ACCEUtil object for getting connection
		static ACCEUtil ObjgetDBUtil = new ACCEUtil();

		// main declaration
		public static void main(String[] args) {
			LOGGER.info("inside main method");

			try {

				conn = ObjgetDBUtil.getConnection();
				CreatingAnnotation objCreatingAnnotation = new CreatingAnnotation();
				objCreatingAnnotation.creatingAnnotations(conn);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ObjgetDBUtil.closeConnection();
			}

		}// end main

		// creating custom object class in acce
		public void creatingAnnotations(Connection conn) {
			LOGGER.info("inside CreateCustomObject method");

			try {

				Domain domain = Factory.Domain.fetchInstance(conn, null, null);
				LOGGER.info("Domain: " + domain.get_Name());

				// Get object stores for domain.fetching Object Store
				ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
				// Create property filter for document's content elements, 
				// which are needed to get element sequence numbers that identify elements.
				PropertyFilter pf = new PropertyFilter();       
				pf.addIncludeProperty(new FilterElement(null, null, null, PropertyNames.CONTENT_ELEMENTS, null));

				// Fetch Document object.
				Document doc=Factory.Document.fetchInstance(objStore, "{90945A6F-0700-C8FD-B25B-5A7E512DAF40}", pf);

				// Get element sequence number of 1st content element of the document.
				   ContentElementList docContentList = doc.get_ContentElements();
				   Integer elementSequenceNumber = ((ContentElement) docContentList.get(0)).get_ElementSequenceNumber();

				// Create annotation.
				   Annotation annObject = Factory.Annotation.createInstance(objStore, "Annotation");

				// Set the Document object to which the annotation applies on the annotation.
				   annObject.set_AnnotatedObject(doc);
				                 
				// Identify the document's ContentElement to which the annotation applies.
				// The ContentElement is identified by its element sequence number.
				   annObject.set_AnnotatedContentElement(elementSequenceNumber.intValue() );

				// Set annotation's DescriptiveText property.
				annObject.set_DescriptiveText("Annotation applied to the document's 1st content element.");

				// Create File object with annotation content.
				File annotationFile = new File("C:\\Users\\mitsind759\\Downloads\\tejufile.File");

				// Create ContentTransfer and ContentElementList objects for the annotation.
				ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
				ContentElementList annContentList = Factory.ContentTransfer.createList();
				
				   FileInputStream fileIS = new FileInputStream(annotationFile.getAbsolutePath());
				   ctObject.setCaptureSource(fileIS);
				
				

				// Add ContentTransfer object to the list and set the list on the annotation.
				annContentList.add(ctObject);
				annObject.set_ContentElements(annContentList);

				annObject.save(RefreshMode.REFRESH);
		}catch (Exception e)
			{
			   System.out.println(e.getMessage() );
			}
}
}