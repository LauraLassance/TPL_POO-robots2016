package robot;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import donnee.NatureTerrain;

public abstract class RobotTerrestre extends Robot {

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
	
	public abstract int getVolumeMaxReservoir();

	@Override
	public void seDeplacer(Case caseDesire) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTempsInterventionUnitaire() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deverserEauIntervUnit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TypeRobot getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
