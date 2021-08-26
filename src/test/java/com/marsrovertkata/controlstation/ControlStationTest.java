package com.marsrovertkata.controlstation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.marsrovertkata.controlstation.constants.GlobalConstants;
import com.marsrovertkata.controlstation.exceptions.ControlStationInputDataException;
import com.marsrovertkata.controlstation.requestControlers.RequestControler;
import com.marsrovertkata.controlstation.requestControlers.impl.FileRequestControler;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStationTest {
	
	private final static String TEMP_TEST_BASE_PATH = System.getProperty("java.io.tmpdir")+File.separator+"testBasePath"+File.separator; //Path for test preparation
	private static final Path resourceDirectory = Paths.get("src","test","resources");	                                 
	private static final String absolutePath = resourceDirectory.toFile().getAbsolutePath();                             
	private final static String TEMP_RESOURCE_TEST  =  absolutePath+File.separator+"inputRequestFolder" + File.separator;	
	RequestControler reqControler = new FileRequestControler(TEMP_TEST_BASE_PATH);

	private final static String VALID_PROCESS_ID_1 ="inputRequest1";
	private final static String VALID_PROCESS_ID_2 ="inputRequest2";
	private final static String VALID_PROCESS_ID_3 ="inputRequest3";
	private final static String VALID_PROCESS_ID_4 ="inputRequest4";
	private final static String VALID_PROCESS_ID_5 ="inputRequest5";
	private final static String VALID_PROCESS_ID_6 ="inputRequest6";
	private final static String VALID_PROCESS_ID_7 ="inputRequest7";
	private final static String VALID_PROCESS_ID_8 ="inputRequest8";
	
	private String VALID_PROCESS_ID_1_RESPONSE_1 ="1 3 N";
	private String VALID_PROCESS_ID_1_RESPONSE_2 ="5 1 E";
	
	private String VALID_PROCESS_ID_3_RESPONSE_1 ="1 3 N";
	private String VALID_PROCESS_ID_2_RESPONSE_1 ="5 1 E";
	
	private String VALID_PROCESS_ID_4_RESPONSE_1 ="1 2 E";
	private String VALID_PROCESS_ID_4_RESPONSE_2 ="3 3 E";
	private String VALID_PROCESS_ID_5_RESPONSE_1 ="0 5 N";
	private String VALID_PROCESS_ID_5_RESPONSE_2 ="5 0 E";
	private String VALID_PROCESS_ID_6_RESPONSE_1 ="0 3 N";
	private String VALID_PROCESS_ID_6_RESPONSE_2 ="5 4 E";
	private String VALID_PROCESS_ID_7_RESPONSE_1 ="1 1 E";
	private String VALID_PROCESS_ID_8_RESPONSE_1 ="Error parsing initial data";
	
	
	
	
	@After
	public void cleanUpAfterTest(){
		File folder = new  File(TEMP_TEST_BASE_PATH); 
		try {
			FileUtils.cleanDirectory(folder);
		} catch (IOException e) {
			fail("Error during clean up["+e.getMessage()+"]");
		} 
	}
	
	@Before
	public void beforeAnyTest(){
		File folder = new  File(TEMP_TEST_BASE_PATH); 
		if(!folder.exists()){
			folder.mkdir();
		}
	}
	 
	@Test
	public void baseTestNoExceptionAndNoDataToExecute() {
		//having
		String[] args = new String[1];
		args[0] = TEMP_TEST_BASE_PATH; 
		//when
		ControlStation.main(args);
		//then
		
	}
	
	@Test
	public void baseTestMultipleElements() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_1+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then		
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_1+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;
			result = readRequest(responsePath);
			assertEquals(VALID_PROCESS_ID_1_RESPONSE_1 ,result.get(0).trim());
			assertEquals(VALID_PROCESS_ID_1_RESPONSE_2 , result.get(1).trim());		
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}	
	
	@Test
	public void baseTestSingleElementCaseA() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_2+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then		
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_2+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;		
			result = readRequest(responsePath);
			assertEquals(VALID_PROCESS_ID_2_RESPONSE_1 ,result.get(0).trim());	
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
		
	@Test
	public void baseTestSingleElementCaseB() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_3+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then		
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_3+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;
			result = readRequest(responsePath);
			assertEquals(VALID_PROCESS_ID_3_RESPONSE_1 ,result.get(0).trim());		
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
	
	@Test
	public void baseTestMultipleElementsDontMoveJustTurn() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_4+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then		
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_4+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;		
			result = readRequest(responsePath);
			assertEquals(VALID_PROCESS_ID_4_RESPONSE_1 ,result.get(0).trim());		
			assertEquals(VALID_PROCESS_ID_4_RESPONSE_2 ,result.get(1).trim());		
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
	
	@Test
	public void baseTestMultipleElementsOneExitsFromPlateau() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_5+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then				
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_5+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;		
			result = readRequest(responsePath);
			assertEquals(VALID_PROCESS_ID_5_RESPONSE_1, result.get(0).trim());
			assertEquals(VALID_PROCESS_ID_5_RESPONSE_2, result.get(1).trim());
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
	
	@Test
	public void baseTestMultipleElementsOneExitsFromPlateauTheOtherCrashesAgainsTheFirst() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_6+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then				
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_6+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;		
			result = readRequest(responsePath);
			assertEquals(VALID_PROCESS_ID_6_RESPONSE_1, result.get(0).trim());	
			assertEquals(VALID_PROCESS_ID_6_RESPONSE_2, result.get(1).trim());						
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
		
	@Test
	public void baseTestOneElementDontCrashesAgainstHimself() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_7+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then				
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_7+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;		
			result = readRequest(responsePath);
			assertTrue(result.get(0).trim().indexOf(VALID_PROCESS_ID_7_RESPONSE_1)!=-1);			
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
	
	@Test
	public void baseTestOneElementStartsOutsideThePlateau() {
		//having
		copyResourceToTempFolder(VALID_PROCESS_ID_8+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		//when
		ControlStation.execute();
		//then			
		List<String> result;
		try {
			String responsePath = TEMP_TEST_BASE_PATH + VALID_PROCESS_ID_8+"."+ GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;		
			result = readRequest(responsePath);
			assertTrue(result.get(0).trim().indexOf(VALID_PROCESS_ID_8_RESPONSE_1)!=-1);			
		} catch (ControlStationInputDataException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
	
	
	private void copyResourceToTempFolder(String id){
		String path =TEMP_RESOURCE_TEST+id;
		 try {
			FileUtils.copyFileToDirectory(new File(path), new File(TEMP_TEST_BASE_PATH));
		} catch (IOException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
	}
	
	public List<String> readRequest(String path) throws ControlStationInputDataException{
		//1.0 Init data
		List<String> response = new LinkedList<String>();		
		//2.0 Read fuke
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	response.add(line);
		    }
		} catch (IOException e) {
			fail("Error during initialization up["+e.getMessage()+"]");
		}
		return response;	
		
	}		
}
