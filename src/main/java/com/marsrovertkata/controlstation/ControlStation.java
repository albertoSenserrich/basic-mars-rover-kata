package com.marsrovertkata.controlstation;

import org.apache.log4j.Logger;

import com.marsrovertkata.controlstation.constants.enums.BaseType;
import com.marsrovertkata.controlstation.factory.ControlStationFactory;

import controlStation.executors.ControlStationExecutor;


/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStation {
	
	private final static Logger logger = Logger.getLogger(ControlStation.class);

	public static void main(String[] args) {
       //1.0 Instanciate type of base   
       ControlStationExecutor mrbase = ControlStationFactory.getBase(BaseType.NASA_MARS_CONTROL_STATION);      
       if(args!= null && args.length>0) {
    	   logger.debug( "Initiating space station with files on path:"+args[0]);
    	   mrbase = ControlStationFactory.getBase(BaseType.NASA_MARS_CONTROL_STATION, args[0]);
       }else {
    	   mrbase = ControlStationFactory.getBase(BaseType.NASA_MARS_CONTROL_STATION);
       }
       logger.debug("Space Station is on-line");
       //2.0 Perform base action of base (process orders)
       mrbase.performActions();      
       logger.debug("Space Station is shuting down, end execution of request");
	}

	public static void execute() {
	       logger.debug("Space Station is on-line");
	       //1.0 Instanciate type of base   
	       ControlStationExecutor mrbase = ControlStationFactory.getBase(BaseType.NASA_MARS_CONTROL_STATION);      
	       //2.0 Perform base action of base (process orders)
	       mrbase.performActions();      
	       logger.debug("Space Station is shuting down, end execution of request");		
	}
}
