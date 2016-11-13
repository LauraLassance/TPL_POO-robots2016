package evenement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import donnee.Incendie;
import robot.Robot;

public class DeversementEvenement extends Evenement {

	/** Le robot qui va deverser d'eau */
	private Robot robot;
	
	/** Le incendie qui va être éteint */
	private Incendie incendie;
	
	public DeversementEvenement(long date, Robot robot, Incendie incendie) {
		super(date);
		this.robot = robot;
		this.incendie = incendie;
	}

	@Override
	public void execute() throws Exception {
		if (this.incendie.getIntensite() <= 0) {
			throw new Exception();
		}
		
		int volumeEau = this.robot.deverserEauIntervUnit();
		this.incendie.eteindreIncendie(volumeEau);
	}
	
	public static List<Evenement> creerDeversermentUnit(long date, Robot robot, Incendie incendie) {
		ArrayList<Evenement> list = new ArrayList<Evenement>();
		int temps = robot.getTempsInterventionUnitaire();
		
		for(int i=0; i< temps; i++) {
			list.add(new DeversementEvenement(date+i, robot, incendie));
		}
		
		Collections.sort(list);
		
		return list;
	}

}
