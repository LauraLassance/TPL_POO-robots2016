package strategie;

import donnee.Case;
import exception.PasDeCheminException;


public interface AffectationStrategie {
	
	public abstract void calculChemin() throws PasDeCheminException;
	public abstract double tempsNecessaireUnit(Case dst);
		
}
