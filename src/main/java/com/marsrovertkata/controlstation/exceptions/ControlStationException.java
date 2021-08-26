/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marsrovertkata.controlstation.exceptions;

import org.apache.log4j.Logger;

import com.marsrovertkata.controlstation.ControlStation;
import com.marsrovertkata.controlstation.constants.enums.ErrorType;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStationException extends Exception{
  /**
	 * 
	 */
	private static final long serialVersionUID = -4586758929813238910L;
	private String msg;
	private final static Logger logger = Logger.getLogger(ControlStation.class);
  
  public ControlStationException(String msg, ErrorType err) {	
		StringBuffer sBuffer = new StringBuffer();
		this.msg = msg;
	    sBuffer.append(err.getCode());
	    sBuffer.append(" ");
	    sBuffer.append(msg);
		logger.error(sBuffer.toString());
  }
  
  @Override
  public String getMessage() {
	  return msg;
  }
  
}
