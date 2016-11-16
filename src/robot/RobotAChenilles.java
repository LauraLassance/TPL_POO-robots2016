package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;
import exception.TerrainInterditException;

public class RobotAChenilles extends RobotTerrestre {
	
	private static final double VITESSE_MAX = 80;
	private static final double VITESSE_DEFAULT = 60;
	private static final int VOL_RESERVOIR = 2000;
	private static final int TEMPS_REMPLISSAGE = 5*60;
	private static final int TEMPS_INTERV_UNIT = 8;
	private static final int VOL_INTERV_UNIT = 100;
	
	public RobotAChenilles(Case position) {
		super(position, RobotAChenilles.VOL_RESERVOIR, 
							RobotAChenilles.VITESSE_DEFAULT);
	}
	
	/**
	 * Crée un robot à chenilles dans la position donnée et avec la vitesse 
	 * désirée. Si la vitesse désirée est plus grande que la vitesse maximale,
	 * celle utilisée est la maximale.
	 * @param position Case dans la carte où on crée le drone 
	 * @param vitesse Vitesse de déplacement du drone
	 */
	public RobotAChenilles(Case position, double vitesse) {
		super(position, RobotAChenilles.VOL_RESERVOIR, 
						(vitesse > RobotAChenilles.VITESSE_MAX? 
							RobotAChenilles.VITESSE_MAX : vitesse));
	}
	
	/**
	 * La vitesse est diminuée de 50% en forêt.
	 * @param nature le type du terrain où se trouve le robot 
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
	
	@Override
	public int getTempsRemplissage() {
		return RobotAChenilles.TEMPS_REMPLISSAGE;
	}

	@Override
	public int getVolumeMaxReservoir() {
		return RobotAChenilles.VOL_RESERVOIR;
	}

	@Override
	public int getVolIntervUnit() {
		return RobotAChenilles.VOL_INTERV_UNIT;
	}

	@Override
	public String getRobotImageName() {
		return "chenilles.png";
	}

	/**
	 * Renvoie un booléen pour savoir si le robot peut se deplacer ou pas
	 * @param nature prend en entrée la nature du terrain sur laquelle il veut se deplacer.
	 * @return retourne un boolean, vrai s'il peut se deplacer, faux sinon.
	 */
	@Override
	public boolean peutSeDeplacer(NatureTerrain nature) {
		return (nature != NatureTerrain.EAU || nature != NatureTerrain.ROCHE);
	}

}
