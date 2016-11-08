package robot;

import donnee.Case;
import donnee.NatureTerrain;

public class RobotARoues extends Robot {

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

	public static int getTempsremplissagemin() {
		return RobotARoues.TEMPS_REMPLISSAGE_MIN;
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		return this.getVitesse();
	}

	@Override
	public void remplirReservoir() {
		// TODO What to do with the time to fill it up? How to have access to the neighbors?
		this.setVolumeEauReservoir(RobotARoues.VOL_RESERVOIR);
	}

	/**
	 * Ne peut se déplacer que sur du terrain libre ou habitat.
	 */
	@Override
	public void seDeplacer(Case caseDesiree) {
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
	public int deverserEauIntervUnit() {
		if (this.getVolumeEauReservoir() < RobotARoues.VOL_INTERV_UNIT) {
			int volDeverse = this.getVolumeEauReservoir();
			this.setVolumeEauReservoir(0);
			return volDeverse;
		}
			
		this.setVolumeEauReservoir(this.getVolumeEauReservoir() - RobotARoues.VOL_INTERV_UNIT);
		return RobotARoues.VOL_INTERV_UNIT;
	}
}
