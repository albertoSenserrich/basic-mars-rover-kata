package com.marsrovertkata.controlstation.utils;

import com.marsrovertkata.controlstation.constants.enums.OrientationType;
import com.marsrovertkata.controlstation.vehicles.components.ActualPosition;
import com.marsrovertkata.controlstation.vehicles.components.Plateau;


/**
 * @author Alberto Senserrich Montals
 *
 */
public final class   CommandsParserUtils {


	
	public static Plateau parsePlateau (String line){
        Plateau plateau;
        int pos = line.lastIndexOf(" ");
        int maxX, maxY;
        try {
            maxX = Integer.parseInt(line.substring(0,pos).trim());
            maxY = Integer.parseInt(line.substring(pos+1,line.length()).trim());
        } catch (NumberFormatException e) {
            maxX = -1;
            maxY = -1;
        }
        plateau = new Plateau(maxX, maxY);
        
		return plateau;
	}
	
	public static ActualPosition parseActualPosition (String line){
		ActualPosition actualPoss;
        line = line.trim();
        int pos = line.indexOf(" ");
        int last = line.lastIndexOf(" ");
        int maxX, maxY;
        OrientationType origentation;
    	
        try {
            maxX = Integer.parseInt(line.substring(0,pos));
            maxY = Integer.parseInt(line.substring(pos+1,last));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            maxX = 10;
            maxY = 10;
        }
        origentation = OrientationType.getById(line.substring(last+1,line.length()));
        actualPoss = new ActualPosition(maxX, maxY,origentation);
        
		return actualPoss;
	}	
}
