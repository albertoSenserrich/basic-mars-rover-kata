package com.marsrovertkata.controlstation.vehicles;

import com.marsrovertkata.controlstation.exceptions.ControlStationException;
import com.marsrovertkata.controlstation.vehicles.components.ActualPosition;
import com.marsrovertkata.controlstation.vehicles.components.Plateau;


/**
 * @author Alberto Senserrich Montals
 *
 */
public abstract class MarsVehicle {
	
	protected ActualPosition position;
	protected String ordersToExecute;
//	protected boolean thereIsSomeNotification = false;
	protected String dataToReport = null;
	protected static Plateau areaToExplore;
	
	public boolean isThereIsSomeNotification() {
		return dataToReport != null;
	}

	public MarsVehicle(ActualPosition positon, Plateau areaToExplore2) {
		this.position = positon;
		MarsVehicle.areaToExplore = areaToExplore2;
	}
	
	public void setOrdersToExecute(String orderToExecuteI) {
		this.ordersToExecute = orderToExecuteI;
	}
	
	public ActualPosition getPosition() {
		return position;
	}

	public String getOrdersToExecute() {
		return ordersToExecute;
	}

	public abstract void performMovements() throws ControlStationException;
	
	protected abstract void turnRight();
	
	protected abstract void turnLeft();
	
	protected abstract void moveForward() throws ControlStationException;
	
	@Override
	public String toString(){
		return position.getxPos()+" "+position.getyPos()+" " +position.getOrigentation().getCode();
	}

	
	public boolean isValid() {
		return position.getOrigentation()!= null && isCoordinateInsidePlateau(position.getxPos(),position.getyPos() );
	}
	
	public int getYPos() {
		return position.getyPos();
	}

	public int getXPos() {
		return position.getxPos();
	}

	public void markOcupiedSpace(int x, int y) {
		areaToExplore.markOcupiedSpace(x, y);
	}
	
	public void markFreeSpace(int x, int y) {
		areaToExplore.markFreeSpace(x, y);
	}

	public boolean isOcupedSpace(int x, int y) {
		return areaToExplore.isOcupedSpace(x, y);
	}
	
	public boolean isCoordinateInsidePlateau(int xDestination, int yDestination) {
		boolean isValid = true;
		if(getMaxXPos()<xDestination || 0> xDestination){
			isValid = false;
		}else if(getMaxYPos()<yDestination || 0> yDestination){
			isValid = false;
		}
		return isValid;
	}
	
	public  int getMaxXPos() {
		return areaToExplore.getMaxXPos();
	}
	
	public  int getMaxYPos() {
		return areaToExplore.getMaxYPos();
	}

	public String getDataToReport() {
		return dataToReport;
	}
}
