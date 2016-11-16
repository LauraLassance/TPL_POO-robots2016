package evenement;

import donnee.Incendie;
import robot.Robot;

public class DeversementEvenement extends Evenement {

	/** Le robot qui va deverser d'eau */
	private Robot robot;
	
	/** Le incendie qui va être éteint */
	private Incendie incendie;
        
        public Robot getRobot() {
            return this.robot;
        }
	
	public DeversementEvenement(long date, Robot robot, Incendie incendie) {
		super(date+robot.getTempsInterventionUnitaire()-1);
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
}
