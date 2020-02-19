package com.mits.filenet.ce.annotation;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.collection.AnnotationSet;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.collection.StringList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Annotation;
import com.filenet.api.core.Connection;
import com.filenet.api.core.CustomObject;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.core.UpdatingBatch;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.batchprocess.CreateBatchDemo;
import com.mits.filenet.ce.util.ACCEUtil;

public class RetreiveAnnotations {

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
				RetreiveAnnotations objRetreiveAnnotations = new RetreiveAnnotations();
				objRetreiveAnnotations.RetreivingAnnotations(conn);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ObjgetDBUtil.closeConnection();
			}

		}// end main

		// creating custom object class in acce
		public void RetreivingAnnotations(Connection conn) {
			LOGGER.info("inside CreateCustomObject method");

			try {

				Domain domain = Factory.Domain.fetchInstance(conn, null, null);
				LOGGER.info("Domain: " + domain.get_Name());

				// Get object stores for domain.fetching Object Store
				ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
				
			
				// Create PropertyFilter for annotations.
				PropertyFilter pf = new PropertyFilter();
				pf.addIncludeProperty(new FilterElement(null, null, null, "Annotations", null)); 

				// Get document.
				Document doc=Factory.Document.fetchInstance(objStore, "{90945A6F-0700-C8FD-B25B-5A7E512DAF40}", pf);

				// Get AnnotationSet from document.
				// Iterate AnnotationSet and print property values of each annotation.
				AnnotationSet as = doc.get_Annotations();
				Iterator iter = as.iterator();
				while (iter.hasNext() )
				{
				   Annotation annObject = (Annotation)iter.next();
				   System.out.println("Name: " + annObject.get_Name() + "\n" +
				      "Description: " + annObject.get_DescriptiveText() + "\n" +
				      "Document's content element that's annotated: " + annObject.get_AnnotatedContentElement() + "\n" +
				      "Content size: " + annObject.get_ContentSize().toString() + "\n" +
				      "Storage area: " + annObject.get_StorageArea().get_DisplayName() + "\n" +
				      "No. of content elements in annotation: " + annObject.get_ContentElements().size()
				   );
				   // Print the MIME type of the annotation's content element.
				   // Some annotations may have more than one content element.
				   StringList sl = annObject.get_ContentElementsPresent();
				   for(int i=0; i < sl.size(); i++ )
				   {
				      System.out.println("MIME type of annotation content element #" + (i+1) + ": " + 
				          sl.get(i) );
				   }
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}

		}
}
