package evenement;

import donnee.Carte;
import exception.ReservoirPleinException;

import robot.Robot;

public class RemplirEvenement extends Evenement {

	/** Le robot dont on va remplir le reservoir */
	private Robot robot;

	/** La carte où se trouve le robot */
	private Carte carte;
	/**
     * Constructeur de la classe RemplirEvenement
     * @param date la date de l'evenement est positionée à la date courante
     * + le temps de remplissage du robot
     * @param robot le robot qui effectue le remplissage
     * @param carte la carte où se situe le robot
     */
	public RemplirEvenement(long date, Robot robot, Carte carte) {
		super(date+robot.getTempsRemplissage());
		this.robot = robot;
		this.carte = carte;
	}

    /**
     * Classe polymorphe de la classe evenement,
     * simule un évenement de remplissage
     * @throws ReservoirPleinException renvoie une exception si le
     * réservoir est rempli.
     */
	@Override
	public void execute() throws ReservoirPleinException {
		this.robot.remplirReservoir(carte);
	}
}
