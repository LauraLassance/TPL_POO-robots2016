package strategie;

import donnee.Case;


public interface MouvementStrategie {
	
	public abstract void calculChemin();
	public abstract double tempsNecessaireUnit(Case dst);
		
}
