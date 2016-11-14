package robot;

import java.awt.Color;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;

/**
 * Type de robot aérien. Il se déplace à vitesse constante dans n'importe quel
 * type de terrain.
 * @author lauralassance
 *
 */
public class Drone extends RobotAerien {

	private static final double VITESSE_MAX = 150;
	private static final double VITESSE_DEFAULT = 100;
	private static final int VOL_RESERVOIR = 10000;
	private static final int TEMPS_REMPLISSAGE = 30*60;
	private static final int TEMPS_INTERV_UNIT = 30;
	
	
	/**
	 * Crée un drone dans la position donnée et avec la vitesse default.
	 * @param position Case dans la carte où est le drone 
	 */
	public Drone(Case position) {
		super(position, Drone.VOL_RESERVOIR, VITESSE_DEFAULT);
	}
	
	/**
	 * Crée un drone dans la position donnée et avec la vitesse desirée. Si
	 * la vitesse désirée est plus grande que la vitesse maximale, ce qui
	 * est utilisée est la maximale.
	 * @param position Case dans la carte où est le drone 
	 * @param vitesse Vitesse de déplacement du drone
	 */
	public Drone(Case position, double vitesse) {
		super(position, Drone.VOL_RESERVOIR, (vitesse > VITESSE_MAX? VITESSE_MAX : vitesse));
	}
	
	@Override
	public int getTempsRemplissage() {
		return Drone.TEMPS_REMPLISSAGE;
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		return this.getVitesse();
	}

	/**
	 * Déplace le drone pour la caseDesiree. La vérification si la case
	 * courante et la destination sont voisins est faite dans la classe de
	 * simulation. Le drone n'a pas de restriction de nature.
	 * @param caseDesiree Case pour laquelle le drone se déplace
	 */
	@Override
	public void seDeplacer(Case caseDesiree, Carte carte) {
		if (carte.sontVoisins(this.getPosition(), caseDesiree))
			this.setPosition(caseDesiree);
	}
	
	@Override
	public TypeRobot getType() {
		return TypeRobot.DRONE;
	}

	@Override
	public int getTempsInterventionUnitaire() {
		return Drone.TEMPS_INTERV_UNIT;
	}
	
	/**
	 * Pour out an amount of water from the robot reservatory that corresponds
	 * to one unit of time (1 second). 
	 * @return Volume of water that was really poured out
	 */
	@Override
	public int deverserEauIntervUnit() {
		this.setVolumeEauReservoir(
					this.getVolumeEauReservoir()-Drone.VOL_RESERVOIR);
		return Drone.VOL_RESERVOIR;
	}
	
	@Override
	public int getVolumeMaxReservoir() {
		return Drone.VOL_RESERVOIR;
	}

	@Override
	public String getRobotImageName() {
		return "../images/drone.gif";
	}

	@Override
	public Color getRobotColor() {
		return Color.ORANGE;
	}
	
}
