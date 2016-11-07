package donnee;

public class Case {
	private int ligne, colonne;
	private NatureTerrain nature;
	
	public Case(int ligne, int colonne, NatureTerrain nature) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = nature;
	}
	
	public int getLigne() {
		return ligne;
	}
	public int getColonne() {
		return colonne;
	}
	public NatureTerrain getNature() {
		return nature;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Case) {
			if (	(this.ligne == ((Case) other).getLigne()) && 
					(this.colonne == ((Case) other).getColonne()) &&
					(this.nature == ((Case) other).getNature()) )
				return true;
			return false;
		}
		return false;
	}
}
