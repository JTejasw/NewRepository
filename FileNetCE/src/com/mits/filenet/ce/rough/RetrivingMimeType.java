package com.mits.filenet.ce.rough;

public class RetrivingMimeType {

	// Retrieving in document class structure(FileNet CE API)
/*
	  public void downloadDoc(Connection conn) {
	  try { Domain domain = Factory.Domain.fetchInstance(conn, null, null);
	  LOGGER.info("domain.............downloadDoc method..." +
	 domain.get_Name());
	  
	 ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "FNOS",
	 null); LOGGER.info("ObjectStore........downloadDoc method......" +
	 objStore.get_DisplayName());
	
	 PropertyFilter pf = new PropertyFilter();
	 
	  pf.addIncludeProperty(new FilterElement(null, null, null,
	  PropertyNames.CONTENT_SIZE, null)); pf.addIncludeProperty(new
	 FilterElement(null, null, null, PropertyNames.CONTENT_ELEMENTS, null));
	 pf.addIncludeProperty(new FilterElement(null, null, null,
	  PropertyNames.MIME_TYPE, null) );
	  
	 Document doc = Factory.Document.fetchInstance(objStore, new
	 Id("{50CEDE6E-0000-C31A-A259-B8BCAEBB6D33}"), pf);
	 ContentElementList contentElementList = doc.get_ContentElements();
	 Iterator<?> itrObj = contentElementList.iterator(); LOGGER.info(
	 "itrObj.hasNext()*************************************************" +
	 itrObj.hasNext()); while (itrObj.hasNext()) {
	 
	 // Return document properties. com.filenet.api.property.Properties props
	= doc.getProperties();
	  
	 // Iterate the set and print property values. Iterator<?> iter =
	 props.iterator(); 
	 LOGGER.info("Iterator....." + iter.hasNext());
	 
	 System.out.println("Property" + "\t" + "Value");
	 System.out.println("------------------------"); while (iter.hasNext()) {
	 Property prop = (Property) iter.next();
	 
	 if (prop.getPropertyName().equals(PropertyNames.MIME_TYPE) ){
	 ContentTransfer ct = (ContentTransfer) itrObj.next(); // Print element
	 sequence number and content type of the element. System.out.println(
	 "\nElement Sequence number: " + ct.get_ElementSequenceNumber().intValue()
	 + "\n" + "Content type: " + ct.get_ContentType() + "\n");
	 
	 int docLen = ct.get_ContentSize().intValue(); byte[] buf = new
	 byte[docLen];
	 
	 InputStream stream = ct.accessContentStream();
	 
	 stream.read(buf, 0, docLen); String readStr = new String(buf);
	 
	  System.out.println("Content:\n " + readStr);
	 
	 String Mimetype = prop.getStringValue(); String extension="";
	 
	 if (Mimetype.endsWith("image/jpeg")) { //Mimetype = "image/jpg";
	 extension = ".jpg"; } else if (Mimetype.endsWith("application/pdf")) {
	 extension = ".pdf"; } else if (Mimetype.endsWith("image/png")) {
	 extension = ".png"; } else { extension = "application/octet-stream"; }
	 
	 
	 // FileOutputStream objFile=new FileOutputStream(
	 "D:\\CoreJava\\Task\\FileNet\\src\\DownloadContent\\DownCon"+extension);
	 FileOutputStream objFile=new
	 FileOutputStream("D:\\CoreJava\\Task\\FileNet\\src\\DownloadContent\\"+ct
	 .get_RetrievalName());
	 
	  objFile.write(buf); objFile.close(); LOGGER.info("done!"); //
	 fos.close(); stream.close(); }
	 //System.out.println(prop.getPropertyName() + "\t" +);
	 
	 }
	 */
}
