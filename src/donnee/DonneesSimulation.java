package donnee;

import java.util.Vector;

import robot.*;

public class DonneesSimulation {
	private Vector<Incendie> incendies;
	private Carte carte;
	private Vector<Robot> robots;
	
	public DonneesSimulation() {
		this.incendies = null;
		this.carte = null;
		this.robots = null;
	}

	public Vector<Incendie> getIncendies() {
		return incendies;
	}

	public Carte getCarte() {
		return carte;
	}

	public Vector<Robot> getRobots() {
		return robots;
	}
	
	public void creerIncendies(int nbIncendies) {
		this.incendies = new Vector<Incendie>(nbIncendies);
	}
	
	public void creerCarte(int tailleCases, int nbLignes, int nbColonnes) {
		this.carte = new Carte(tailleCases, nbLignes, nbColonnes);
	}
	
	public void creerRobots(int nbRobots) {
		this.robots = new Vector<Robot>(nbRobots);
	}
	
	public void addCase(int lig, int col, NatureTerrain nature) {
			this.carte.addCase(lig, col, nature);
	}
	
	public void addIncendie(int lig, int col, int intensite) {
		Incendie nouveauIncendie = new Incendie(this.getCarte().getCase(lig, col), intensite);
		this.incendies.addElement(nouveauIncendie);
	}
	
	public void addRobot(int lig, int col, TypeRobot type, double vitesse) {
		Robot robot = null;
		switch (type) {
			case DRONE:
				robot = new Drone(this.getCarte().getCase(lig, col));
				break;
			case ROUES:
				robot = new RobotARoues(this.getCarte().getCase(lig, col),
												vitesse);
				break;
			case CHENILLES:
				robot = new RobotAChenilles(this.getCarte().getCase(lig, col), 
												vitesse);
				break;
			case PATTES:
				robot = new RobotAPattes(this.getCarte().getCase(lig, col));
		}
		this.robots.addElement(robot);
	}
	
	public void addRobot(int lig, int col, TypeRobot type) {
		Robot robot = null;
		switch (type) {
			case DRONE:
				robot = new Drone(this.getCarte().getCase(lig, col));
				break;
			case ROUES:
				robot = new RobotARoues(this.getCarte().getCase(lig, col));
				break;
			case CHENILLES:
				robot = new RobotAChenilles(this.getCarte().getCase(lig, col));
				break;
			case PATTES:
				robot = new RobotAPattes(this.getCarte().getCase(lig, col));
		}
		this.robots.addElement(robot);
	}
}
