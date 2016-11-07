package robot;

import donnee.Case;
import donnee.NatureTerrain;

public class Drone extends Robot {

	private final double vitesseMax = 150;
	
	public Drone(Case position) {
		// TODO
	}
	
	public Drone(Case position, double vitesse) {
		this(position);
		
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub

	}

	@Override
	public void seDeplacer(Case caseDesire) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public TypeRobot getType() {
		return TypeRobot.DRONE;
	}

}
