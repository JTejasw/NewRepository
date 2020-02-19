package com.mits.filenet.ce.search;
import java.util.Date;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.IndependentObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.property.Property;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.mits.filenet.ce.common.DateUTC;
import com.mits.filenet.ce.util.ACCEUtil;

public class SearchOnDate {
	
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(SearchRepositoryRowSet.class);

	// declaration main
	public static void main(String[] args) {
		LOGGER.info("inside main method");
		// creating object
	
		SearchOnDate objectSearchOnDate = new SearchOnDate();
		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		

		try{
    
		LOGGER.info("inside main method");
		conn = ObjgetDBUtil.getConnection();
		LOGGER.info("calling method");
		objectSearchOnDate.createSearchOnDate(conn);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		ObjgetDBUtil.closeConnection();
		}
		LOGGER.debug("closing connection");

	}//end main


	// creating createSearchOnDate
	public void createSearchOnDate(Connection conn) {

		try {

			LOGGER.info("inside try block");
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			
		
		//String dateTest="SELECT [This],  [TDate],[DateCreated]  FROM [jtdoc] WHERE [DocumentTitle] = 'LoanDoc' OPTIONS(TIMELIMIT 180)";
		
		
			
		Date date=DateUTC.convertToDate("20/12/2019 18:15:00");
		String dateToUTC = DateUTC.dateToUTC(date);
		Date date1=DateUTC.convertToDate("20/12/2019 18:45:00");
		String dateToUTC1 = DateUTC.dateToUTC(date1);
		System.out.println("dateToUTC*************"+dateToUTC);
		System.out.println("dateToUTC1*************"+dateToUTC1);
				
		//String dateTest="SELECT [This],[TDate], [DateCreated],[DocumentTitle] FROM [jtdoc]  WHERE  [TDate] >"+dateToUTC;
		
		System.out.println(" [TDate] >= 20191220T124500Z AND [TDate] < 20191220T131500Z******"+dateToUTC+"*************"+dateToUTC1);
		 
		String dateTest="SELECT [This],[TDate], [DateCreated],[DocumentTitle] FROM [jtdoc]  WHERE  [TDate] >"+dateToUTC+" AND [TDate]<"+dateToUTC1;
		
		
			SearchSQL sqlObject = new SearchSQL(dateTest);
			
			// Check the SQL statement.  
			System.out.println("SQL: " + dateTest); 

			// Create a SearchScope instance. (Assumes you have the object store object.)
			SearchScope search = new SearchScope(objStore);

			// Execute the fetchObjects method using the specified parameters.
			IndependentObjectSet myObjects = search.fetchObjects(sqlObject, null, null, null);
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
	

}
	
}
