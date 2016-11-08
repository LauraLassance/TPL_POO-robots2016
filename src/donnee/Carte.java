package donnee;

import java.util.Vector;

public class Carte {
	private int tailleCases,
				nbLignes, // n lignes of cases
				nbColonnes; // m colonnes of cases
	
	/**
	 * Represents the matrix n x m of cases that forms the carte
	 */
	private Vector<Vector<Case>> cases;
	
	public Carte(int tailleCases, int nbLignes, int nbColonnes) {
		this.tailleCases = tailleCases;
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		
		this.cases = new Vector<Vector<Case>>(this.nbLignes);
		for (int i = 0; i < this.nbColonnes; i++)
			this.cases.set(i, new Vector<Case>(this.nbColonnes));
	}
	
	public void Evenement(long date) {
		// TODO
	}
	
	public int getNbLignes() {
		return this.nbLignes;
	}
	
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	
	public int getTailleCases() {
		return this.tailleCases;
	}
	
	/**
	 * Returns the case in the position (lig, col) or null if this position doesn't exist
	 * @param lig line of the case wanted
	 * @param col column of the case wanted
	 * @return the case located in the position (lig, col) or null if it doesn't exists
	 */
	public Case getCase(int lig, int col) {
		if ( (lig < 0) || (lig >= this.nbLignes) || (col < 0) || (col >= this.nbColonnes) )
			return null;
		
		if (this.cases.get(lig) != null)
			return this.cases.get(lig).get(col);
		
		return null;
	}

	public void addCase(int lig, int col, NatureTerrain nature) {
		this.cases.get(lig).add(col, new Case(lig, col, nature));
	}
	
	public boolean voisinExiste(Case src, Direction dir) {
		if (this.getVoisin(src, dir) != null)
			return true;
		return false;
	}
	
	/**
	 * Returns the neighbor of case src in the direction dir.
	 * @param src The case of which neighbor is wanted
	 * @param dir Direction of the neighbor wanted
	 * @return The neighbor of src in the direction dir or null if it doesn't 
	 * exists (src located in the borders)
	 */
	public Case getVoisin(Case src, Direction dir) {
		switch (dir) {
			case NORD:
				if (src.getLigne() > 0)
					return this.cases.get(src.getLigne()-1).get(src.getColonne());
				break;
				
			case SUD:
				if (src.getLigne() < this.nbLignes-1)
					return this.cases.get(src.getLigne()+1).get(src.getColonne());
				break;
				
			case OUEST:
				if (src.getColonne() > 0)
					return this.cases.get(src.getLigne()).get(src.getColonne()-1);
				break;
				
			case EST:
				if (src.getColonne() < this.getNbColonnes()-1)
					return this.cases.get(src.getLigne()).get(src.getColonne()+1);
		}
		
		return null;
	}
	
	public boolean sontVoisins(Case src, Case dst) {
		for(Direction dir : Direction.values()) {
			Case voisin = getVoisin(src, dir);
			if (voisin != null)
				if (voisin.equals(dst))
					return true;
		}
		
		return false;
	}
	
}
