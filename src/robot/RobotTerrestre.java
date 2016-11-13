package robot;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import donnee.NatureTerrain;
import exception.ReservoirPleinException;

public abstract class RobotTerrestre extends Robot {

	public abstract int getVolIntervUnit();
	
	public RobotTerrestre(Case position, int volRes, double vitesse) {
		super(position, volRes, vitesse);
	}

	@Override
	public void remplirReservoir(Carte carte) throws ReservoirPleinException {
		boolean peutRemplir = false;
		for (Direction dir : Direction.values()) {
			Case voisin = carte.getVoisin(this.getPosition(), dir);
			if (voisin.getNature() == NatureTerrain.EAU)
				peutRemplir = true;
		}
		
		if (peutRemplir)
			this.setVolumeEauReservoir(this.getVolumeMaxReservoir());
		else
			throw new ReservoirPleinException("[Remplissage] Le robot terrestre a déjà le reservoir plein.");

	}

	@Override
	public int deverserEauIntervUnit() {
		if (this.getVolumeEauReservoir() < this.getVolIntervUnit()) {
			int volDeverse = this.getVolumeEauReservoir();
			this.setVolumeEauReservoir(0);
			return volDeverse;
		}
			
		this.setVolumeEauReservoir(this.getVolumeEauReservoir() - this.getVolIntervUnit());
		return this.getVolIntervUnit();
	}
}
