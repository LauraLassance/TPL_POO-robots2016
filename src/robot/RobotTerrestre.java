package robot;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import donnee.NatureTerrain;

public abstract class RobotTerrestre extends Robot {

	public abstract int getVolIntervUnit();
	
	public RobotTerrestre(Case position, int volRes, double vitesse) {
		super(position, volRes, vitesse);
	}

	@Override
	public void remplirReservoir(Carte carte) {
		// TODO What to do with the time to fill it up?
		boolean peutRemplir = false;
		for (Direction dir : Direction.values()) {
			Case voisin = carte.getVoisin(this.getPosition(), dir);
			if (voisin.getNature() == NatureTerrain.EAU)
				peutRemplir = true;
		}
		if (peutRemplir)
			this.setVolumeEauReservoir(this.getVolumeMaxReservoir());

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
