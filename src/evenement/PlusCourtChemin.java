package evenement;

import donnee.Case;
import robot.Robot;

public class PlusCourtChemin extends CheminEvenement {
	
	int xDirection;
	int yDirection;
	
	public PlusCourtChemin(long date, Robot robot, Carte carte, Case dest) {
		super(date, robot, carte, dest);
		this.xDirection = robot.getPosition().getLigne() - dest.getLigne();
		this.yDirection = robot.getPosition().getColonne() - dest.getColonne();
	}
	
	@Override
	public Vector<Case> calculPlusCourtChemin(Case depart, Case dest) {
		
	}

}
