package com.marsrovertkata.controlstation.requestControlers.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import com.marsrovertkata.controlstation.constants.GlobalConstants;
import com.marsrovertkata.controlstation.constants.enums.ErrorType;
import com.marsrovertkata.controlstation.exceptions.ControlStationException;
import com.marsrovertkata.controlstation.exceptions.ControlStationInputDataException;
import com.marsrovertkata.controlstation.requestControlers.RequestControler;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class FileRequestControler implements RequestControler{

	private String baseInputRequestPath ="";
	
	public FileRequestControler(String path){
		this.baseInputRequestPath = path;
	}
	
//	public FileRequestControler(String inputPath, String outputPath){
//		this.baseInputRequestPath = inputPath;
//	}
	
	/***
	 * Mark a request as succefully performed, it wont be executed again
	 */
	public void markRequestProcessed(String requestId) throws ControlStationException{
		// File (or directory) with old name
		//1.0 initialize new data
		File file = new File(this.baseInputRequestPath+File.separator+requestId+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST);
		File file2 = new File(this.baseInputRequestPath+File.separator+requestId+"."+GlobalConstants.FILE_EXTENSION_FOR_PROCESSED_REQUEST);
		//2.0 basic validations, destination file cannot exist
		if (file2.exists())
		   throw new ControlStationInputDataException("Proceessed file  ["+requestId+"] already exists", ErrorType.ERROR_INVALID_INPUT_OUTPUT_PARAMETERS);
		//3 .0 Rename file (or directory)
		boolean success = file.renameTo(file2);
		//4.0 validate file
		if (!success) {
			 throw new ControlStationInputDataException("File ["+requestId+"] was not successfully renamed", ErrorType.ERROR_INVALID_INPUT_OUTPUT_PARAMETERS);
		}
	}
	
	/***
	 * Returns a list with all the commands to process associated to a request id
	 */
	public List<String> readRequest(String requestId) throws ControlStationInputDataException{
	//1.0 Init data
	List<String> response = new LinkedList<String>();
	String path = this.baseInputRequestPath+File.separator+requestId+"."+GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST;
	//2.0 Read fuke
	try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	    String line;
	    while ((line = br.readLine()) != null) {
	    	response.add(line);
	    }
	} catch (IOException e) {
		 throw new ControlStationInputDataException("File ["+requestId+"] cannot be readed for reason ["+e.getMessage()+"]", ErrorType.ERROR_INVALID_INPUT_OUTPUT_PARAMETERS);
	}
	return response;	
	}
	
	/***
	 * Return the id Of all the files to be processed
	 */
	public List<String> returnListOfRequestToProcess(){
		List<String> response = new LinkedList<String>();
		File folder = new File(baseInputRequestPath);
		if(folder != null && folder.exists()) {
			for (final File fileEntry : folder.listFiles()) {
				if (!fileEntry.isDirectory()) {
					String completeFileName = fileEntry.getName();
					int posExt = completeFileName.lastIndexOf(".");
					if(posExt != completeFileName.length()){
						String extension = completeFileName.substring(posExt+1, completeFileName.length());
						if(GlobalConstants.FILE_EXTENSION_FOR_PENDING_REQUEST.equals(extension)){
							response.add(completeFileName.substring(0, posExt));
						}	
					}
				} 
			}
		}
		
		return response;
	}

	@Override
	public void returnAnswer(String requestId, List<String> responseData) throws ControlStationInputDataException {
		String path = this.baseInputRequestPath+File.separator+requestId+"."+GlobalConstants.FILE_EXTENSION_FOR_RESPONSE;
		File checkFile = new File (path);
		if (checkFile.exists())
			   throw new ControlStationInputDataException("Proceessed file response ["+requestId+"] already exists", ErrorType.ERROR_INVALID_INPUT_OUTPUT_PARAMETERS);		
		try(  PrintWriter out = new PrintWriter(path )  ){
			for(String data  : responseData){
				out.println( data );
			}
		} catch (FileNotFoundException e) {
			 throw new ControlStationInputDataException("File ["+requestId+"] cannot save response for reason ["+e.getMessage()+"]", ErrorType.ERROR_INVALID_INPUT_OUTPUT_PARAMETERS);
		}
	}
}
