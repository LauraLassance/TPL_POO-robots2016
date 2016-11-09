package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;

public abstract class Robot {
	private Case position;
	private int volumeEauReservoir;
	private double vitesse;

	public Robot(Case position, int volRes, double vitesse) {
		this.position = position;
		this.volumeEauReservoir = volRes;
		this.vitesse = vitesse;
	}
	
	public Case getPosition() {
		return this.position;
	}
	
	public void setPosition(Case posic) {
		if (posic != null)
			this.position = posic;
	}
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void remplirReservoir(Carte carte);
	
	public abstract void seDeplacer(Case caseDesire, Carte carte);
	
	public abstract int getTempsInterventionUnitaire();
	
	public void deverserEau(int vol) {
		this.setVolumeEauReservoir(this.getVolumeEauReservoir() - vol);
	}

	/**
	 * Deverse le volume d'eau définit pour une intervention unitaire.
	 * @return le volume d'eau vraiment deversé
	 */
	public abstract int deverserEauIntervUnit();
	
	public abstract TypeRobot getType();
	
	public abstract int getVolumeMaxReservoir();
	
	public int getVolumeEauReservoir() {
		return volumeEauReservoir;
	}

	public void setVolumeEauReservoir(int volumeEauReservoir) {
		this.volumeEauReservoir = volumeEauReservoir;
	}

	public double getVitesse() {
		return vitesse;
	}
	
	public abstract String getRobotImageName();
	
}
