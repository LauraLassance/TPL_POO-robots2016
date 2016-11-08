package robot;

import donnee.Case;
import donnee.NatureTerrain;

public class RobotAPattes extends Robot {
	
	private static final double VITESSE_DEFAULT = 30;
	private static final int VOL_RESERVOIR = -1; // infinit
	private static final int VOL_INTERV_UNIT = 10;
	private static final int TEMPS_INTERV_UNIT = 1;

	public RobotAPattes(Case position) {
		super(position, RobotAPattes.VOL_RESERVOIR, 
								RobotAPattes.VITESSE_DEFAULT);
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		if (this.getPosition().getNature() == NatureTerrain.ROCHE)
			return 10;
		return this.getVitesse();
	}

	/**
	 * Ce robot utilise le poudre, donc sont réservoire est considéré infinit,
	 * donc il n'a jamais rempli.
	 */
	@Override
	public void remplirReservoir() {
		// Il fait rien
	}

	/**
	 * Ne peut pas se rendre sur de l’eau.
	 */
	@Override
	public void seDeplacer(Case caseDesire) {
		if (caseDesire.getNature() != NatureTerrain.EAU)
			this.setPosition(caseDesire);
	}
	
	@Override
	public TypeRobot getType() {
		return TypeRobot.PATTES;
	}

	@Override
	public int getTempsInterventionUnitaire() {
		return RobotAPattes.TEMPS_INTERV_UNIT;
	}

	@Override
	public int deverserEauIntervUnit() {
		return RobotAPattes.VOL_INTERV_UNIT;
	}
	
}
