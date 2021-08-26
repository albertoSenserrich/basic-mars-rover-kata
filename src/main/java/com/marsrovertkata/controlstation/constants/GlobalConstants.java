package com.marsrovertkata.controlstation.constants;

import java.io.File;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class GlobalConstants {

	public static final String DEFAULT_REQUEST_INPUT_FILE_PATH = System.getProperty("java.io.tmpdir")+File.separator+"testBasePath"+File.separator;
	
	public static final String FILE_EXTENSION_FOR_PENDING_REQUEST = "request";
	public static final String FILE_EXTENSION_FOR_PROCESSED_REQUEST = "processed";
	public static final String FILE_EXTENSION_FOR_RESPONSE = "response";
	
	public static final String VALID_NUMBERS_TO_PROCESS = "0987654321";

	
}
