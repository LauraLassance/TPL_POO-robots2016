package exception;

import donnee.NatureTerrain;

@SuppressWarnings("serial")
public class TerrainInterditException extends Exception {

	public TerrainInterditException(NatureTerrain nature) {
		super("Le robot ne peut pas se déplacer dans"
				+ " le terrain de nature "
				+ nature.name().toLowerCase()+".");
	}
}
