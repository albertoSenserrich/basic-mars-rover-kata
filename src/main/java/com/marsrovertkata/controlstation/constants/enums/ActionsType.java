package com.marsrovertkata.controlstation.constants.enums;

/**
 * @author Alberto Senserrich Montals
 *
 */
public enum ActionsType {
	    /**
	     * First base that was landed in mars
	     */
	 TURN_LEFT('L'), 
	 TURN_RIGHT('R'), 
	 MOVE_FORWARD('F')
	 ;
	 
	 private char code;
	 
	 private ActionsType(char code) {
		 this.code  =code;

	 }

	public char getCode() {
		return code;
	}


	public static ActionsType getById(char id) {
	    for(ActionsType e : values()) {
	        if(e.getCode() ==id) return e;
	    }
	    return null;
	 }
	
 }
	 