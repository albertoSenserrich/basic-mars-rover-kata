package com.marsrovertkata.controlstation.vehicles;

import com.marsrovertkata.controlstation.constants.enums.ActionsType;
import com.marsrovertkata.controlstation.constants.enums.ErrorType;
import com.marsrovertkata.controlstation.exceptions.ControlStationException;
import com.marsrovertkata.controlstation.vehicles.components.ActualPosition;
import com.marsrovertkata.controlstation.vehicles.components.Plateau;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class LandRovert extends MarsVehicle{

	
	public LandRovert(ActualPosition positon,Plateau areaToExplore) {
		super(positon,areaToExplore);
	
	}



	@Override
	public void performMovements() throws ControlStationException {
		//1.0 init data
		String actionsToExecute = super.getOrdersToExecute();
		if(actionsToExecute!=null && actionsToExecute.length()>0){
			char[] orders = actionsToExecute.toCharArray();
			//2.0 perform actions
			for(char actualOrder: orders){
				ActionsType action = ActionsType.getById(actualOrder);
				if (action == null)
					continue;
				switch(action){
			    case TURN_LEFT :
			    	turnLeft();
			    	break; 
			    case TURN_RIGHT :
			    	turnRight();			 
			       break;
			    case MOVE_FORWARD :
			    	moveForward();				     
				       break; 
			    default :
			    	// Invalid action code,  we wont do nothing, 
			    	//TODO: add some error message
			    	break; 
				}	
			}			
		}
	}

	@Override
	protected void turnRight() {
		super.getPosition().turnRight();
	}

	@Override
	protected void turnLeft() {
		super.getPosition().turnLeft();
	}

	@Override
	protected void moveForward() throws ControlStationException {
		//check if movement is valid
		checkNextMovement();
		//2.0 Update position and plateau data
		markFreeSpace(getXPos(),getYPos());
		super.getPosition().moveForward();
		markOcupiedSpace(getXPos(),getYPos());
	}

	private void checkNextMovement() throws ControlStationException{
		//1.0 Init base data
		boolean isValid = true;
		int xDestination = super.getPosition().getOrigentation().getXposIncrement()+super.getPosition().getxPos();
		int yDestination = super.getPosition().getOrigentation().getYposIncrement()+super.getPosition().getyPos();

//		super.getXAfterMovement(super.position);
//		int xDestination = super.getPosition().getXAfterMovement();
//		int yDestination = super.getPosition().getOrigentation().getYposIncrement()+super.getPosition().getyPos();
		
		
		//2.0 Check if the vehicle is going to exit the plateau (area to explore)
		if(!super.isCoordinateInsidePlateau(xDestination, yDestination)) {
			super.dataToReport = "Abort movement, rovert is going to exit the plateau  keep actual positon ["+super.getPosition().getxPos()+"]["+super.getPosition().getyPos()+"]";
			throw new ControlStationException (super.dataToReport, ErrorType.ERROR_OUT_OF_BOUNDARIES_ON_ROVERT_MOVEMENT);
		}		
		//3.0 Check collisions before movement
		if(super.isOcupedSpace(xDestination, yDestination)){
			super.dataToReport = "Abort movement. Collision alert to element on coordinates ["+xDestination+"]["+yDestination+"]";		
//			super.markOcupiedSpace(super.getPosition().getxPos(),super.getPosition().getyPos());
			throw new ControlStationException (super.dataToReport, ErrorType.ERROR_COLLISION_ALERT_AVORTING_MOVEMENT);
		}
	}
 
}
