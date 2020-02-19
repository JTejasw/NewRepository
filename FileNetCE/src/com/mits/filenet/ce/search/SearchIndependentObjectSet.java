package com.mits.filenet.ce.search;



import java.util.Iterator;
import org.apache.log4j.Logger;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.constants.FilteredPropertyType;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.Properties;
import com.filenet.api.property.Property;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.mits.filenet.ce.document.CreatingDocumentWthContent;
import com.mits.filenet.ce.util.ACCEUtil;
import com.mits.filenet.ce.util.Log4jUtil;

public class SearchIndependentObjectSet {

	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(CreatingDocumentWthContent.class);

	// declaration main
	public static void main(String[] args) {
		LOGGER.info("inside main method");
		// creating object
		Log4jUtil objLog4jUtil = new Log4jUtil();
		SearchIndependentObjectSet objSearchIndependentObjectSet = new SearchIndependentObjectSet();
		ACCEUtil ObjgetDBUtil = new ACCEUtil();

		try{
        objLog4jUtil.log4jProperties();
		LOGGER.info("inside main method");
		conn = ObjgetDBUtil.getConnection();
		LOGGER.info("calling method");
		objSearchIndependentObjectSet.createobjSearchIndependentObjectSet(conn);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		ObjgetDBUtil.closeConnection();
		}
		LOGGER.debug("closing connection");

	}//end main


	// creating createobjSearchIndependentObjectSet
	public void createobjSearchIndependentObjectSet(Connection conn) {

		try {

			LOGGER.info("inside try block");
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			
			// Create a SearchSQL instance and specify the SQL statement (using the helper methods).
		//SearchSQL sqlObject = new SearchSQL("SELECT [This],  [TDate],[DateCreated]  FROM [jtdoc] WHERE [DocumentTitle] = 'Vignesh' OPTIONS(TIMELIMIT 180)");
		String dateTest="SELECT [This], [DateCreated],[DocumentTitle] FROM [jtdoc] WHERE  [TDate] >= 20191218T120000Z OPTIONS(TIMELIMIT 180)";
	
			SearchSQL sqlObject = new SearchSQL(dateTest);
			/*sqlObject.setSelectList("d.Id");
			sqlObject.setMaxRecords(20);
			sqlObject.setFromClauseInitialValue("jtdoc", "d", false);  */

			  
            /* SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
             
             Date date = s1.parse("19/12/2019 18:40");
             
             
             
	        System.out.println(date);
            sqlObject.setWhereClause("[TDate] <= "+date);
			    */
			
			
			// Check the SQL statement.  
			System.out.println("SQL: " + sqlObject.toString()); 

			// Create a SearchScope instance. (Assumes you have the object store object.)
			SearchScope search = new SearchScope(objStore);

			// Set the page size (Long) to use for a page of query result data. This value is passed 
			// in the pageSize parameter. If null, this defaults to the value of 
			// ServerCacheConfiguration.QueryPageDefaultSize.
			Integer myPageSize = new Integer(100);

			// Specify a property filter to use for the filter parameter, if needed. 
			// This can be null if you are not filtering properties.
			PropertyFilter myFilter = new PropertyFilter();
			int myFilterLevel = 1;
			myFilter.setMaxRecursion(myFilterLevel);
			myFilter.addIncludeType(new FilterElement(null, null, null, FilteredPropertyType.ANY, null)); 

			// Set the (Boolean) value for the continuable parameter. This indicates 
			// whether to iterate requests for subsequent pages of result data.
			Boolean continuable = new Boolean(true);

			// Execute the fetchObjects method using the specified parameters.
			IndependentObjectSet myObjects = search.fetchObjects(sqlObject, myPageSize, myFilter, continuable);
			 

			// You can then iterate through the collection of rows to access the properties.
			
			Iterator<?> iter = myObjects.iterator();
			LOGGER.info("property name::::::::::::property value");
			while (iter.hasNext()) {
				IndependentObject independentObject = (IndependentObject) iter.next();
			            
				Properties properties = independentObject.getProperties();
				Iterator<?> iterator = properties.iterator();  
			 while(iterator.hasNext()){
			 Property property = (Property)iterator.next();
		
			 LOGGER.info(property.getPropertyName()+"::::::::::::::::::::"+property.getObjectValue());
			 }
			                         
			} 
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end CreateDocumentObject method

}
