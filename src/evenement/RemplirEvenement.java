package evenement;

import donnee.Carte;
import exception.ReservoirPleinException;

import robot.Robot;

public class RemplirEvenement extends Evenement {

	/** Le robot qui va avoir le reservoir rempli */
	private Robot robot;
	
	/** La carte où se trouve le robot */
	private Carte carte;
	
	public RemplirEvenement(long date, Robot robot, Carte carte) {
		super(date+robot.getTempsRemplissage());
		this.robot = robot;
		this.carte = carte;
	}
	
	@Override
	public void execute() throws ReservoirPleinException {
		this.robot.remplirReservoir(carte);
	}
}
