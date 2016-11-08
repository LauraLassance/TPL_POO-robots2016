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
public class Drone extends Robot {

	private static final double VITESSE_MAX = 150;
	private static final double VITESSE_DEFAULT = 100;
	private static final int VOL_RESERVOIR = 10000;
	private static final int TEMPS_REMPLISSAGE_MIN = 30;
	private static final int TEMPS_VIDE_SEC = 30;
	
	
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
	
	public static int getTempsRemplissageMin() {
		return Drone.TEMPS_REMPLISSAGE_MIN;
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		return this.getVitesse();
	}

	@Override
	public void remplirReservoir(Carte carte) {
		// TODO What to do with the time to fill it up?
		if (this.getPosition().getNature() == NatureTerrain.EAU)
			this.setVolumeEauReservoir(Drone.VOL_RESERVOIR);
	}

	/**
	 * Déplace le drone pour la caseDesiree. La vérification si la case
	 * courante et la destination sont voisins est faite dans la classe de
	 * simulation. Le drone n'a pas de restriction de nature.
	 * @param caseDesiree Case pour laquelle le drone se déplace
	 */
	@Override
	public void seDeplacer(Case caseDesiree) {
		this.setPosition(caseDesiree);
	}
	
	@Override
	public TypeRobot getType() {
		return TypeRobot.DRONE;
	}

	@Override
	public int getTempsInterventionUnitaire() {
		return Drone.TEMPS_VIDE_SEC;
	}
	
	public int deverserEauIntervUnit() {
		this.setVolumeEauReservoir(0);
		return Drone.VOL_RESERVOIR;
	}
}
