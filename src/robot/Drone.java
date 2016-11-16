package robot;

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
	 * Crée un drone dans la position donnée et avec la vitesse par defaut.
	 * @param position Case dans la carte où on crée le drone.
	 */
	public Drone(Case position) {
		super(position, Drone.VOL_RESERVOIR, VITESSE_DEFAULT);
	}

	/**
	 * Crée un drone dans la position donnée et avec la vitesse desirée. Si
	 * la vitesse désirée est plus grande que la vitesse maximale, celle
	 * utilisée est la vitesse maximale.
	 * @param position Case dans la carte où on crée le drone
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
		return "drone.png";
	}

	/**
	 * Renvoie un boolean pour savoir si le robot peut se deplacer ou pas
	 * @param nature prend en entrée la nature du terrain sur laquelle il veut se deplacer.
	 * @return retourne un boolean, vrai s'il peut se deplacer, faux sinon.
	 */
	@Override
	public boolean peutSeDeplacer(NatureTerrain nature) {
		return true;
	}

}
