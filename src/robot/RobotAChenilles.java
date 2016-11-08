package robot;

import donnee.Case;
import donnee.NatureTerrain;

public class RobotAChenilles extends RobotTerrestre {
	
	private static final double VITESSE_MAX = 80;
	private static final double VITESSE_DEFAULT = 60;
	private static final int VOL_RESERVOIR = 2000;
	private static final int TEMPS_REMPLISSAGE_MIN = 5;
	private static final int TEMPS_INTERV_UNIT = 8;
	private static final int VOL_INTERV_UNIT = 100;
	
	public RobotAChenilles(Case position) {
		super(position, RobotAChenilles.VOL_RESERVOIR, 
							RobotAChenilles.VITESSE_DEFAULT);
	}
	
	/**
	 * Crée un robot à chenilles dans la position donnée et avec la vitesse 
	 * désirée. Si la vitesse désirée est plus grande que la vitesse maximale,
	 * ce qui est utilisée est la maximale.
	 * @param position Case dans la carte où est le drone 
	 * @param vitesse Vitesse de déplacement du drone
	 */
	public RobotAChenilles(Case position, double vitesse) {
		super(position, RobotAChenilles.VOL_RESERVOIR, 
						(vitesse > RobotAChenilles.VITESSE_MAX? 
							RobotAChenilles.VITESSE_MAX : vitesse));
	}
	
	/**
	 * La vitesse est diminuée de 50% en forêt.
	 * @param nature Quel est le type du terrain où ce robot est
	 */
	@Override
	public double getVitesse(NatureTerrain nature) {
		if (nature == NatureTerrain.FORET)
			return 0.5 * this.getVitesse();
		return this.getVitesse();
	}
	
	@Override
	public TypeRobot getType() {
		return TypeRobot.CHENILLES;
	}

	@Override
	public int getTempsInterventionUnitaire() {
		return RobotAChenilles.TEMPS_INTERV_UNIT;
	}
	
	public static int getTempsRemplissageMin() {
		return RobotAChenilles.TEMPS_REMPLISSAGE_MIN;
	}
	
	public int deverserEauIntervUnit() {
		if (this.getVolumeEauReservoir() < RobotAChenilles.VOL_INTERV_UNIT) {
			int volDeverse = this.getVolumeEauReservoir();
			this.setVolumeEauReservoir(0);
			return volDeverse;
		}
			
		this.setVolumeEauReservoir(this.getVolumeEauReservoir() - RobotAChenilles.VOL_INTERV_UNIT);
		return RobotAChenilles.VOL_INTERV_UNIT;
	}

	/**
	 * Déplace le robot pour la caseDesiree. La vérification si la case
	 * courante et la destination sont voisins est faite dans la classe de
	 * simulation. Le robot a de restrictions de nature (Forêt et Eau). Quand
	 * il y a de restrictions, le robot ne se déplace pas.
	 * @param caseDesiree Case pour laquelle le drone se déplace
	 */
	@Override
	public void seDeplacer(Case caseDesire) {
		if ((caseDesire.getNature() != NatureTerrain.EAU) &&
			(caseDesire.getNature() != NatureTerrain.ROCHE))
			this.setPosition(caseDesire);
	}
	
	@Override
	public int getVolumeMaxReservoir() {
		return RobotAChenilles.VOL_RESERVOIR;
	}
	
}
