package com.marsrovertkata.controlstation.constants.enums;

/**
 * @author Alberto Senserrich Montals
 *
 */
public enum OrientationType {
	    /**
	     * First base that was landed in mars
	     */
	 NORTH("N",1,0), 
	 WEST("W",0,-1), 
	 SOUTH("S",-1,0), 
	 EAST("E",0,1) ;
	 
	 private String code;
	 private int xposIncrement ;
	 private int yposIncrement ;
	 
	 private OrientationType(String code, int yposIncrement,int xposIncrement) {
		 this.code  =code;
		 this.yposIncrement = yposIncrement;
		 this.xposIncrement = xposIncrement;
	 }

	public String getCode() {
		return code;
	}

	public int getXposIncrement() {
		return xposIncrement;
	}

	public int getYposIncrement() {
		return yposIncrement;
	}

	public static OrientationType getById(String id) {
	    for(OrientationType e : values()) {
	        if(e.code.equals(id)) return e;
	    }
	    return null;
	 }
		 
 }
	 