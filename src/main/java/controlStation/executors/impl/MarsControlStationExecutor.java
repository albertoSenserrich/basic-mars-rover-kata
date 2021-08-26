package controlStation.executors.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.marsrovertkata.controlstation.exceptions.ControlStationException;
import com.marsrovertkata.controlstation.exceptions.ControlStationInputDataException;
import com.marsrovertkata.controlstation.requestControlers.RequestControler;
import com.marsrovertkata.controlstation.requestControlers.impl.FileRequestControler;
import com.marsrovertkata.controlstation.utils.CommandsParserUtils;
import com.marsrovertkata.controlstation.vehicles.LandRovert;
import com.marsrovertkata.controlstation.vehicles.MarsVehicle;
import com.marsrovertkata.controlstation.vehicles.components.ActualPosition;
import com.marsrovertkata.controlstation.vehicles.components.Plateau;

import controlStation.executors.ControlStationExecutor;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class MarsControlStationExecutor implements ControlStationExecutor{

	private final RequestControler reqConTroler;
	private final static Logger logger = Logger.getLogger(MarsControlStationExecutor.class);
	
	public MarsControlStationExecutor(String basePath){
		this.reqConTroler = new  FileRequestControler(basePath);
	}
	
	public void performActions() {
		//1.0 REtrieve list of request of process
		List<String>  reqToProcess = reqConTroler.returnListOfRequestToProcess();
		if(reqToProcess != null & reqToProcess.size()>0) {
			for(String requestId : reqToProcess){			
				boolean error = false;
				//2.0 Parse input information
				List<MarsVehicle> vehicles = null;
				try{
					List<String>  orders = reqConTroler.readRequest(requestId);
					vehicles = obtainVehicleData(orders);	
				}catch(ControlStationException e){
					logger.error("Error parsing initial data with detail message ["+e.getMessage()+"]");
				}			
				if(vehicles == null || vehicles.size() == 0)
					error = true;			
				int i=0;
				List<String> dataToReturn  =  new  LinkedList<String>();
				if(!error) {
					dataToReturn = processVehicleCommands(requestId, vehicles, i);			
				}else {
					dataToReturn.add("Error parsing initial data");
				}
				//4.0 Save response into system
				saveResponse(error, requestId, dataToReturn);			
			}
		}else {
			logger.debug("No found any valid request to process");
		}
	}

	private void saveResponse(boolean error, String requestId, List<String> dataToReturn) {
		//1.0 Save answer into a file
		try{
			if(dataToReturn!=null &&dataToReturn.size()>0){
				reqConTroler.returnAnswer(requestId, dataToReturn);
			}
		}catch (ControlStationInputDataException e){
			error = true;
			logger.error("There was an error saving data from  requestId ["+requestId+"], exception detaill ["+e.getMessage()+"]");
		}
		//2.0 Mark request as succefull executed
		try{			
			if(!error){
				reqConTroler.markRequestProcessed(requestId);
				logger.debug("Completed process of requestId ["+requestId+"] with result OK");
			}else {
				generateErrorMessages(requestId, dataToReturn);
			}
		}catch(ControlStationException e){
			logger.error("There was an error marking requestId ["+requestId+"] as resolved, exception detaill ["+e.getMessage()+"]");
		}
	}

	private List<MarsVehicle> obtainVehicleData(List<String> orders) {
		List<MarsVehicle> vehicles = new  LinkedList<MarsVehicle>();	
		MarsVehicle tmpVehicle;
		if(orders!=null){
			Plateau plateau = 	CommandsParserUtils.parsePlateau(orders.get(0));
			for(int i=1;i+1<orders.size();i=i+2){
				ActualPosition actualPoss = CommandsParserUtils.parseActualPosition(orders.get(i));
				tmpVehicle = new LandRovert(actualPoss,plateau);
				tmpVehicle.setOrdersToExecute(orders.get(i+1).trim());
				if(tmpVehicle.isValid()) {
					vehicles.add(tmpVehicle);
					plateau.markOcupiedSpace(tmpVehicle.getPosition().getxPos(), tmpVehicle.getPosition().getyPos());
				}else {
					return new  LinkedList<MarsVehicle>();
				}
			}
		}
		return vehicles;
	}

	/***
	 Check if the movement can be performed, and it that case execute the movement
	 * @param requestId
	 * @param vehicles
	 * @param i
	 * @return
	 */
	private List<String> processVehicleCommands(String requestId, List<MarsVehicle> vehicles, int i) {
		List<String> dataToReturn  =  new  LinkedList<String>();
		for(MarsVehicle vechicle: vehicles){
			try{
				vechicle.performMovements();
			}catch(ControlStationException e ){
				logger.error("Error performing movement on request ["+requestId+"] for vehicle number ["+i+"] with message ["+e.getMessage()+"]");
			}
			i++;				
		}
		for(MarsVehicle vehicle: vehicles){
			dataToReturn.add(vehicle.toString());
//			if(vehicle.isThereIsSomeNotification()){
//			}else{
//				dataToReturn.add(vehicle.toString());
//			}
		}
		return dataToReturn;
	}

	private void generateErrorMessages(String requestId, List<String> dataToReturn) {
		StringBuffer stb = new StringBuffer();
		stb.append("Completed process of requestId [");
		stb.append(requestId);
		stb.append("] with result ERROR ");
		if(dataToReturn!=null &&dataToReturn.size()>0){
			stb.append("and detail messages: ");
			int cont = 0;
			for(String errMsgToReport : dataToReturn) {
				stb.append(errMsgToReport);
				if(dataToReturn.size()>1 && dataToReturn.size()!=cont) {
					stb.append(".    ");
				}
				cont ++ ;
			}
		}	
		logger.debug(stb.toString());
	}
}
