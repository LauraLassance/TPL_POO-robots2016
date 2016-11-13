package robot;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;
import exception.ReservoirPleinException;

public abstract class RobotAerien extends Robot {

	public RobotAerien(Case position, int volRes, double vitesse) {
		super(position, volRes, vitesse);
	}

	@Override
	public void remplirReservoir(Carte carte) throws ReservoirPleinException {
		if (this.getPosition().getNature() == NatureTerrain.EAU)
			this.setVolumeEauReservoir(this.getVolumeMaxReservoir());
		else
			throw new ReservoirPleinException("[Remplissage] Le robot aerien a déjà le reservoir plein.");
	}
}
