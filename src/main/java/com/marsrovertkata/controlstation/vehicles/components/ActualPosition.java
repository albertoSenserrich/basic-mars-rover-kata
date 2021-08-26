package com.marsrovertkata.controlstation.vehicles.components;

import com.marsrovertkata.controlstation.constants.enums.OrientationType;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class ActualPosition {

	private int posX;
	private int posY;
	OrientationType origentation;
	
	public ActualPosition(int xPos, int yPos,OrientationType origentation) {
		this.origentation = origentation;
		this.posX = xPos;
		this.posY = yPos;
	}
	
	public int getxPos() {
		return posX;
	}
	
	public int getyPos() {
		return posY;
	}
	
	public void addPosY(int yPos) {
		this.posY = this.posY+yPos;
	}
	
	public void addPosX(int xPos) {
		this.posX = this.posX+xPos;
	}
	
	public OrientationType getOrigentation() {
		return origentation;
	}
	
	public void  turnRight() {
		//1.0 init data
		OrientationType orientation = this.origentation;		
		 //2.0 check transition
		switch(orientation){
	    case NORTH :
	    	this.origentation = OrientationType.EAST;
	    	break; 
	    case WEST :
	    	this.origentation = OrientationType.NORTH;	 
	       break;
	    case SOUTH :
	    	this.origentation = OrientationType.WEST;		     
		       break; 
	    case EAST :
	    	this.origentation = OrientationType.SOUTH;     
		       break; 
	    default :
	    	// Invalid action code,  we wont do nothing, 
	    	//TODO: add some error message
	    	break; 	    	
		}
	}

	public void turnLeft() {
		//1.0 init data
		OrientationType orientation = this.origentation;		
		 //2.0 check transition
		switch(orientation){
	    case NORTH :
	    	this.origentation = OrientationType.WEST;
	    	break; 
	    case WEST :
	    	this.origentation = OrientationType.SOUTH; 
	       break;
	    case SOUTH :
	    	this.origentation = OrientationType.EAST;		     
		       break; 
	    case EAST :
	    	this.origentation = OrientationType.NORTH;     
		       break; 
	    default :
	    	// Invalid action code,  we wont do nothing, 
	    	//TODO: add some error message
	    	break; 	    	
		}
	}

	public void moveForward() {
		this.addPosX(this.origentation.getXposIncrement());
		this.addPosY(this.origentation.getYposIncrement());
	}
	
	
}
