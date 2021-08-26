/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marsrovertkata.controlstation.exceptions;

import com.marsrovertkata.controlstation.constants.enums.ErrorType;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStationInputDataException extends ControlStationException{

	public ControlStationInputDataException(String string, ErrorType err) {
		super(string, err);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7731830879477405759L;
    
}
