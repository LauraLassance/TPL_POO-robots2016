package strategie;

import java.util.List;

import donnee.Case;
import evenement.Evenement;
import exception.PasDeCheminException;
import io.Simulateur;


public interface AffectationStrategie {
	
	public abstract List<Evenement> getChemin() throws PasDeCheminException;
	public abstract double tempsNecessaireUnit(Case dst);
		
}
