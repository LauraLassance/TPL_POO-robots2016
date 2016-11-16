package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;
import exception.TerrainInterditException;

public class RobotARoues extends RobotTerrestre {

	private static final double VITESSE_DEFAULT = 80;
	private static final int VOL_RESERVOIR = 5000;
	private static final int TEMPS_REMPLISSAGE = 10*60;
	private static final int TEMPS_INTERV_UNIT = 5;
	private static final int VOL_INTERV_UNIT = 100;
	
	public RobotARoues(Case position) {
		super(position, RobotARoues.VOL_RESERVOIR, 
							RobotARoues.VITESSE_DEFAULT);
	}
	
	/**
	 * Crée un robot à roues dans la position donnée et avec la vitesse 
	 * désirée.
	 * @param position Case dans la carte on crée le drone 
	 * @param vitesse Vitesse de déplacement du drone
	 */
	public RobotARoues(Case position, double vitesse) {
		super(position, RobotARoues.VOL_RESERVOIR, vitesse);
	}

	@Override
	public int getTempsRemplissage() {
		return RobotARoues.TEMPS_REMPLISSAGE;
	}
	
	@Override
	public double getVitesse(NatureTerrain nature) {
		return this.getVitesse();
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
		return "roues.png";
	}

	/**
	 * Renvoie un booleen pour savoir si le robot peut se deplacer ou pas
	 * @param nature prend en entrée la nature du terrain sur laquelle il veut se deplacer.
	 * @return retourne un boolean, vrai s'il peut se deplacer, faux sinon.
	 */
	
	@Override
	public boolean peutSeDeplacer(NatureTerrain nature) {
		return ((nature == NatureTerrain.TERRAIN_LIBRE) || (nature == NatureTerrain.HABITAT));
	}
	
	
}
