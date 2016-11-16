package exception;

import donnee.Case;

@SuppressWarnings("serial")
public class PasDeCheminException extends Exception {
	
	public PasDeCheminException(Case dest) {
		super("Il n'y a pas de chemin possible jusqu'à la position ("
					+ dest.getLigne()
					+ ","
					+ dest.getColonne()
					+ ")");
	}
}
