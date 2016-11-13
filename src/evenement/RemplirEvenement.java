package evenement;

import java.util.List;

import donnee.Carte;
import exception.ReservoirPleinException;

import java.util.ArrayList;

import robot.Robot;

public class RemplirEvenement extends Evenement {

	/** Le robot qui va avoir le reservoir rempli */
	private Robot robot;
	
	/** La carte où se trouve le robot */
	private Carte carte;
	
	public RemplirEvenement(long date, Robot robot, Carte carte) {
		super(date);
		this.robot = robot;
	}
	
	@Override
	public void execute() throws ReservoirPleinException {
		this.robot.remplirReservoir(carte);
	}
	
	public static List<Evenement> creerRemplissageUnit(long date, Robot robot, Carte carte) {
		ArrayList<Evenement> list = new ArrayList<Evenement>();
		
		for (int i=0; i<robot.getTempsRemplissage(); i++) {
			list.add(new RemplirEvenement(date+i, robot, carte));
		}
		
		return list;
	}

}
