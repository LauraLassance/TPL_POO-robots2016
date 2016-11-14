package evenement;

import java.util.List;

import donnee.Carte;
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
	public List<Case> calculPlusCourtChemin(Case depart, Case dest) {
		return null;
		
	}

	@Override
	public List<Evenement> listeMouvementUnit(long date, Robot robot, Carte carte) {
		// TODO Auto-generated method stub
		return null;
	}

}
