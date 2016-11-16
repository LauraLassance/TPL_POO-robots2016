package donnee;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import robot.*;

public class DonneesSimulation {
	private List<Incendie> incendies;
	private Carte carte;
	private List<Robot> robots;
	
	public DonneesSimulation() {
		this.incendies = null;
		this.carte = null;
		this.robots = null;
	}

	public List<Incendie> getIncendies() {
		return incendies;
	}

	public Carte getCarte() {
		return carte;
	}

	public List<Robot> getRobots() {
		return robots;
	}
	
	public void creerIncendies(int nbIncendies) {
		this.incendies = new ArrayList<Incendie>(nbIncendies);
	}
	
	public void creerCarte(int tailleCases, int nbLignes, int nbColonnes) {
		this.carte = new Carte(tailleCases, nbLignes, nbColonnes);
	}
	
	public void creerRobots(int nbRobots) {
		this.robots = new ArrayList<Robot>(nbRobots);
	}
	
	public void addCase(int lig, int col, NatureTerrain nature) {
			this.carte.addCase(lig, col, nature);
	}
	
	public void addIncendie(int lig, int col, int intensite) {
		Incendie nouveauIncendie = new Incendie(this.getCarte().getCase(lig, col), intensite);
		this.incendies.add(nouveauIncendie);
	}
	
        /**
         * Ajoute un robot à la carte.
         * @param lig la ligne où le robot doit être créer
         * @param col la colonne où le robot doit être créer
         * @param type le type de robot
         * @param vitesse la vitesse du robot
         */
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
		this.robots.add(robot);
	}
	
        
        /**
         * Ajoute un robot à la carte.
         * @param lig la ligne où le robot doit être créer
         * @param col la colonne où le robot doit être créer
         * @param type le type de robot
         */
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
		this.robots.add(robot);
	}
}
