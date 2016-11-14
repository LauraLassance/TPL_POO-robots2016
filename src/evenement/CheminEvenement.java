package evenement;

import java.util.List;
import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import robot.Robot;


public interface CheminEvenement {
	
	public abstract void calculPlusCourtChemin();
	
	public abstract double evaluerDistance(Case src, Case dst);
		
	}
