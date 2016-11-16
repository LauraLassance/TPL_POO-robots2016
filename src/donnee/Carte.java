package donnee;

import java.util.Vector;

public class Carte {
	private int tailleCases,
				nbLignes, // n lignes of cases
				nbColonnes; // m colonnes of cases

	/**
	 * Represente la matrice n x m des cases qui forment la carte
	 */
	private Vector<Vector<Case>> cases;
        
        
	public Carte(int tailleCases, int nbLignes, int nbColonnes) {
		this.tailleCases = tailleCases;
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;

		this.cases = new Vector<Vector<Case>>(this.nbLignes);
		for (int i = 0; i < this.nbColonnes; i++)
			this.cases.addElement(new Vector<Case>(this.nbColonnes));
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
	 * Getter de case dans la carte
	 * @param lig la ligne souhaitée
	 * @param col la colonne souhaitée
	 * @return retourne la case dans la position (lig, col) souhaitée si elle existe null, sinon
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
	 * Getter de case voisine, Vérifie si le voisin existe.
	 * @param src La case dont on souhaite le voisin
	 * @param dir Direction du voisin souhaité
	 * @return le voisin de la case src, dans la direction dir, s'il existe
         * sinon renvoie un null
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

	/**
	 * Vérifie si deux cases sont voisines.
	 * @param src Case source
	 * @param dst Case destination
	 * @return true si source et destination sont voisines, false sinon
	 */
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
