package evenement;

import java.util.List;
import java.util.Vector;
import donnee.Carte;
import donnee.Case;
import robot.Robot;

public abstract class CheminEvenement extends Evenement{

	/** Le robot qui va se déplacer */
	private Robot robot;

	/** La carte où le robot va se déplacer */
	private Carte carte;

	/** la case destination */
	private Case dest;

	public CheminEvenement(long date, Robot robot, Carte carte, Case dest) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.dest = dest;
	}
	
	public abstract Vector<Case> calculPlusCourtChemin(Case depart, Case dest);
	
	public abstract List<Evenement> listeMouvementUnit(long date, Robot robot, Carte carte);
	
	@Override
	public void execute() throws DehorsDeLaFrontiereException {
			
	}
	
	}
