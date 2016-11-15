package evenement;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import exception.DehorsDeLaFrontiereException;
import exception.TerrainInterditException;
import robot.Robot;

public class DeplacerEvenement extends Evenement {

	/** Le robot qui va se déplacer */
	private Robot robot;
	
	/** La destination du déplacement */
	private Direction dir;
	
	/** La carte où le robot va se déplacer */
	private Carte carte;
	
	public DeplacerEvenement(long date, Robot robot, Direction dir, Carte carte) {
		super(date);
		this.robot = robot;
		this.dir = dir;
		this.carte = carte;
	}
	
	
        @Override
	public void execute() throws DehorsDeLaFrontiereException, TerrainInterditException {
		Case dest = this.carte.getVoisin(this.robot.getPosition(), this.dir);
		if (dest != null)
			this.robot.seDeplacer(dest, this.carte);
		else
			throw new DehorsDeLaFrontiereException("Le robot est sorti de la carte dans la direction "+this.dir);
	}

}
