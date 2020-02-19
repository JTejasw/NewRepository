package com.mits.filenet.ce.document;

import org.apache.log4j.Logger;

import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;
import com.mits.filenet.ce.util.ACCEUtil;

public class CancellingCheckout {
	static Connection conn = null;
	// implementting logger to debuge the code
	static final Logger LOGGER = Logger.getLogger(RetrievingAllVersion.class);

	// main declaration
	public static void main(String[] args) {

		ACCEUtil ObjgetDBUtil = new ACCEUtil();
		CancellingCheckout objCnlCheckout = new CancellingCheckout();
		LOGGER.info("inside main");
		// FileNet CE Connection
		conn = ObjgetDBUtil.getConnection();
		LOGGER.error("calling method");
		objCnlCheckout.CancelliCheckout(conn);
		ObjgetDBUtil.closeConnection();
		
		
	}// end main

	// cancel checkout  after checkin

	public void CancelliCheckout(Connection conn) {
		try {
			Domain domain = Factory.Domain.fetchInstance(conn, null, null);
			LOGGER.info("domain............." + domain.get_Name());

			ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS", null);
			LOGGER.info("ObjectStore........" + objStore.get_DisplayName());
			
			// Create Document instance and check it out.
			Document doc = Factory.Document.getInstance(objStore, ClassNames.DOCUMENT,  new Id("{800D0F6F-0000-CB11-8907-FFE3983154D1}") );
			doc.checkout(ReservationType.EXCLUSIVE, null, null, null);
			doc.save(RefreshMode.REFRESH);

			// Get the reservation object.
			Document reservation = (Document) doc.get_Reservation();

			// Cancel checkout on Document object and
			// save the reservation object.
			doc.cancelCheckout(); 
			reservation.save(RefreshMode.REFRESH);
			
			
	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
