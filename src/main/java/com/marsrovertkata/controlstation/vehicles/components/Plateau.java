package com.marsrovertkata.controlstation.vehicles.components;

/**
 * @author Alberto Senserrich Montals
 *
 */
public class Plateau {

	private int maxXPos;
	private int maxYPos;
	private static int[][] colisions;
	private static int OCUPIED_VALUE = 99;
	private static int FREE_VALUE = 0;
	/**
	 * @param maxXPos
	 * @param maxYPos
	 */
	public Plateau(int maxXPos, int maxYPos) {
		super();
		this.maxXPos = maxXPos;
		this.maxYPos = maxYPos;
		Plateau.colisions = new  int[maxXPos+1][maxYPos+1];
	}
	
	public void markFreeSpace (int x, int y ){
		if(x<=this.maxXPos && x>=0 && y<=this.maxYPos && y>=0){
			Plateau.colisions[x][y]= Plateau.FREE_VALUE;
		}			
	}
	
	public void markOcupiedSpace (int x, int y ){
		if(x<=this.maxXPos && x>=0 && y<=this.maxYPos && y>=0){
			Plateau.colisions[x][y] = Plateau.OCUPIED_VALUE;
		}		
	}
	
	public boolean isOcupedSpace(int x, int y){
		if(x<=this.maxXPos && x>=0 && y<=this.maxYPos && y>=0){
			return Plateau.OCUPIED_VALUE == Plateau.colisions[x][y];
		}	else{
			return false;
		}
	}
	
	public int getMaxXPos() {
		return maxXPos;
	}
	public int getMaxYPos() {
		return maxYPos;
	}
	
}
