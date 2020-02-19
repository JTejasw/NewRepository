package com.mits.filenet.ce.common;

import java.io.*;
import java.util.*;

/**
 * @author mitsind759 setting the path Data base connectionloading get value
 *         based on key
 */
// class declaration
public class ApplicationLoader {

	private static Properties objProps = null;

	// declaration getproperty method using to get value based key
	public static String getProperty(String propertyName) {

		String propertyValue = null;
		try {
			if (objProps == null)
				loadProperties();

			propertyValue = objProps.getProperty(propertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return propertyValue;

	}

	// declaration loadproperties method using setting the path file
	private static void loadProperties() throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream("D:\\CoreJava\\Task\\FileNet\\src\\acce.properties");
		objProps = new Properties();
		objProps.load(fis);

	}
}// end
