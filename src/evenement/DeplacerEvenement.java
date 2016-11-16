package evenement;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import exception.DehorsDeLaFrontiereException;
import exception.PasDeCheminException;
import exception.TerrainInterditException;
import io.Simulateur;
import robot.Robot;

public class DeplacerEvenement extends Evenement {

	/** Le robot qui va se déplacer */
	private Robot robot;
	
	/** La destination du déplacement */
	private Direction dir;
	
	/** La carte où le robot va se déplacer */
	private Carte carte;
	
	/** Le simulateur des robots pompiers */
	private Simulateur simulateur;
	
	/** La case qui est la destination du déplacement */
	private Case dest;
	
	public DeplacerEvenement(long date, Robot robot, Direction dir, Carte carte) {
		super(date);
		this.robot = robot;
		this.dir = dir;
		this.carte = carte;
		this.simulateur = null;
		this.dest = null;
	}
	
	public DeplacerEvenement(long date, Robot robot, Carte carte, Simulateur sim, Case caseDesiree) {
		this(date, robot, null, carte);
		this.dest = caseDesiree;
		this.simulateur = sim;
	}
	
	
        @Override
	public void execute() throws DehorsDeLaFrontiereException, TerrainInterditException, PasDeCheminException {
		if (this.dir != null) {
	        Case dest = this.carte.getVoisin(this.robot.getPosition(), this.dir);
			if (dest != null)
				this.robot.seDeplacer(dest, this.carte, this.simulateur);
			else
				throw new DehorsDeLaFrontiereException("Le robot est sorti de la carte dans la direction "+this.dir);
		} else
			this.robot.seDeplacer(this.dest, this.carte, this.simulateur);
	}

}
