package com.marsrovertkata.controlstation.factory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.marsrovertkata.controlstation.constants.GlobalConstants;
import com.marsrovertkata.controlstation.constants.enums.BaseType;

import controlStation.executors.ControlStationExecutor;
import controlStation.executors.impl.MarsControlStationExecutor;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class ControlStationFactory {

	
    /**
     * Get a base Instance by type
     * @param input Input string with the coordinates of the right north border
     * of area to explore, and all orders to do by the rovers
     * @param base Type of base to be created
     * @return MarsBase
     */
    public static ControlStationExecutor getBase( BaseType base, String paramPath) {
    	//1.0 init basic values
    	
    	ControlStationExecutor defaultBaseExecutor  = null;
        switch (base) {
            case NASA_MARS_CONTROL_STATION:
            	String path = paramPath!= null? paramPath :GlobalConstants.DEFAULT_REQUEST_INPUT_FILE_PATH;
            	Path pathN = Paths.get(path);
                pathN.normalize();
            	defaultBaseExecutor = new MarsControlStationExecutor(pathN.toString());
                break;
            default:
            	
                break;
        }
        return defaultBaseExecutor;
    }
    
    public static ControlStationExecutor getBase( BaseType base) {
    	String path = GlobalConstants.DEFAULT_REQUEST_INPUT_FILE_PATH;
    	Path pathN = Paths.get(path);
        pathN.normalize();
    	return new MarsControlStationExecutor( pathN.toString());
    }
}
