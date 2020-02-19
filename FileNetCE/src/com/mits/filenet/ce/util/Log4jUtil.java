package com.mits.filenet.ce.util;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author mitsind759
 *configure log4jProperties
 */
//declaration class
public class Log4jUtil {
	//declaration configure method
	public void log4jProperties() {
		PropertyConfigurator.configure("D:\\CoreJava\\Task\\FileNet\\src\\log4j.properties");
	}
}//end class
