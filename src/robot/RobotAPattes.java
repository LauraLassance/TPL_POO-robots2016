package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;

public class RobotAPattes extends RobotTerrestre {
	
	private static final double VITESSE_DEFAULT = 30;
	private static final int VOL_RESERVOIR = Integer.MAX_VALUE; // infini
	private static final int VOL_INTERV_UNIT = 10;
	private static final int TEMPS_INTERV_UNIT = 1;
	private static final int TEMPS_REMPLISSAGE = 1;

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
	public void remplirReservoir(Carte carte) {
		// Il fait rien
	}

	/**
	 * Ne peut pas se rendre sur de l’eau.
	 */
	@Override
	public void seDeplacer(Case caseDesiree, Carte carte) {
		if (carte.sontVoisins(this.getPosition(), caseDesiree))
			if (caseDesiree.getNature() != NatureTerrain.EAU)
				this.setPosition(caseDesiree);
	}
	
	@Override
	public TypeRobot getType() {
		return TypeRobot.PATTES;
	}

	@Override
	public int getTempsInterventionUnitaire() {
		return RobotAPattes.TEMPS_INTERV_UNIT;
	}

	/**
	 * Le reservoir de ce robot est infini. Donc, même quand il fait une
	 * intervention unitaire, le volume de son reservoire ne change pas.
	 */
	@Override
	public int deverserEauIntervUnit() {
		return RobotAPattes.VOL_INTERV_UNIT;
	}
	
	@Override
	public int getVolumeMaxReservoir() {
		return RobotAPattes.VOL_RESERVOIR;
	}

	@Override
	public int getVolIntervUnit() {
		return RobotAPattes.VOL_INTERV_UNIT;
	}

	@Override
	public String getRobotImageName() {	
		return "pattes.png";
	}

	@Override
	public int getTempsRemplissage() {
		return RobotAPattes.TEMPS_REMPLISSAGE;
	}

	/**
	 * Renvoie un boolean pour savoir si le robot peut se deplacer ou pas
	 * @param nature prend en entrée la nature du terrain sur laquelle il veut se deplacer.
	 * @return retourne un boolean, vrai s'il peut se deplacer, faux sinon.
	 */
	
	@Override
	public boolean peutSeDeplacer(NatureTerrain nature) {
		return (nature != NatureTerrain.EAU);
	}
	
}
