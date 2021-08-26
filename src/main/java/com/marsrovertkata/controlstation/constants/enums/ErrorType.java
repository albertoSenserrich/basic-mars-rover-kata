package com.marsrovertkata.controlstation.constants.enums;


	/**
	 * @author Alberto Senserrich Montals
	 *
	 */
	public enum ErrorType {
		 ERROR_INVALID_INPUT_OUTPUT_PARAMETERS("001","Invalid/Output data"), 
		 ERROR_COLISION_ON_ROVERT_MOVEMENT("002","Error during Rovert movement"), 
		 ERROR_FATAL_ERROR_CREATING_CONTROLER("003","Error creating controler"), 
		 ERROR_OUT_OF_BOUNDARIES_ON_ROVERT_MOVEMENT("004","Rovert is going to exit the plateau, aborting movements"),
		 ERROR_COLLISION_ALERT_AVORTING_MOVEMENT("005","Red alert!!! Rovert is going to crash against something. Aborting action!!!"), 
		 UNEXPECTED_SYSTEM_ERROR("999","Unexpected system error") ;
		 
		 private String code;
		 private String description;
		 
		 private ErrorType(String code, String description) {
			 this.code  =code;
			 this.description = description;
		 }
		 
		 public String getCode() {
		   return code;
		 }
		 public String getDescription(){
			 return this.description;
		 }
		 
	}
	
		 
