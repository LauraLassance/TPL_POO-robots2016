package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;

public class RobotARoues extends RobotTerrestre {

	private static final double VITESSE_DEFAULT = 80;
	private static final int VOL_RESERVOIR = 5000;
	private static final int TEMPS_REMPLISSAGE_MIN = 10;
	private static final int TEMPS_INTERV_UNIT = 5;
	private static final int VOL_INTERV_UNIT = 100;
	
	public RobotARoues(Case position) {
		super(position, RobotARoues.VOL_RESERVOIR, 
							RobotARoues.VITESSE_DEFAULT);
	}
	
	/**
	 * Crée un robot à roues dans la position donnée et avec la vitesse 
	 * désirée.
	 * @param position Case dans la carte où est le drone 
	 * @param vitesse Vitesse de déplacement du drone
	 */
	public RobotARoues(Case position, double vitesse) {
		super(position, RobotARoues.VOL_RESERVOIR, vitesse);
	}

	public static int getTempsRemplissageMin() {
		return RobotARoues.TEMPS_REMPLISSAGE_MIN;
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		return this.getVitesse();
	}

	/**
	 * Ne peut se déplacer que sur du terrain libre ou habitat.
	 */
	@Override
	public void seDeplacer(Case caseDesiree, Carte carte) {
		if (carte.sontVoisins(this.getPosition(), caseDesiree))
			if ((caseDesiree.getNature() == NatureTerrain.TERRAIN_LIBRE) || 
				(caseDesiree.getNature() == NatureTerrain.HABITAT))
				this.setPosition(caseDesiree);
	}

	@Override
	public TypeRobot getType() {
		return TypeRobot.ROUES;
	}

	@Override
	public int getTempsInterventionUnitaire() {
		return RobotARoues.TEMPS_INTERV_UNIT;
	}
	
	@Override
	public int getVolumeMaxReservoir() {
		return RobotARoues.VOL_RESERVOIR;
	}

	@Override
	public int getVolIntervUnit() {
		return RobotARoues.VOL_INTERV_UNIT;
	}

	@Override
	public String getRobotImageName() {
		return "../images/roues.gif";
	}
}
