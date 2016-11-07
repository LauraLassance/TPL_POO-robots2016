package robot;

import donnee.Case;
import donnee.NatureTerrain;

public abstract class Robot {
	private Case position;
	private int volumeEauReservoir;
	private double vitesse;

	public Case getPosition() {
		return this.position;
	}
	
	public void setPosition(Case posic) {
		if (posic != null)
			this.position = posic;
	}
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void remplirReservoir();
	
	public abstract void seDeplacer(Case caseDesire);
	
	public void deverserEau(int vol) {
		this.setVolumeEauReservoir(this.getVolumeEauReservoir() - vol);
	}

	public abstract TypeRobot getType();

	public int getVolumeEauReservoir() {
		return volumeEauReservoir;
	}

	public void setVolumeEauReservoir(int volumeEauReservoir) {
		this.volumeEauReservoir = volumeEauReservoir;
	}

	public double getVitesse() {
		return vitesse;
	}
	
}
