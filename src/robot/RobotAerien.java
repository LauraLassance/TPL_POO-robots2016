package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;

public abstract class RobotAerien extends Robot {

	public RobotAerien(Case position, int volRes, double vitesse) {
		super(position, volRes, vitesse);
	}

	@Override
	public void remplirReservoir(Carte carte) {
		// TODO What to do with the time to fill it up?
		if (this.getPosition().getNature() == NatureTerrain.EAU)
			this.setVolumeEauReservoir(this.getVolumeMaxReservoir());
	}
}
