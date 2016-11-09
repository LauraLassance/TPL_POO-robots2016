package io;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnee.Case;
import donnee.DonneesSimulation;
import donnee.Incendie;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;

import io.LecteurDonnees;
import robot.Robot;

public class Simulateur implements Simulable {
	
	/** Les couleurs des différents types de case */
	private static Color couleurEau = Color.BLUE;
	private static Color couleurForet = Color.GREEN;
	private static Color couleurRoche = Color.GRAY;
	private static Color couleurHabitat = Color.MAGENTA;
	private static Color couleurIncendie = Color.RED;
	
	/** La couleur de fond */
	private Color backgroundColor;
	
	/** L'interface graphique associée */
    private GUISimulator gui;	
    
    /** Les donnees de la simulation */
    private DonneesSimulation donnees;

    /**
     * Crée une simulation a partir des données d'un fichier et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param nomFicher nom du fichier où il y a les données de la simulaion
     * @param color la couleur de fond
     */
    public Simulateur(GUISimulator gui, DonneesSimulation donnees, Color bkgColor) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        
		this.donnees = donnees;
		this.backgroundColor = bkgColor;
		
        draw();
    }

    @Override
    public void next() {

    }

    @Override
    public void restart() {

    }


    /**
     * Dessine la carte avec tous ses éléments (robots et incendies).
     */
    private void draw() {
        gui.reset();	// clear the window

        int tailleCase = this.donnees.getCarte().getTailleCases();
        
        // dessine tous les cases
        for (int i=0; i < this.donnees.getCarte().getNbLignes(); i++)
        	for (int j=0; j < this.donnees.getCarte().getNbColonnes(); j++) {
        		Case caseCourante = this.donnees.getCarte().getCase(i, j);
        		drawCase(caseCourante, j*tailleCase, i*tailleCase, tailleCase);
        	}
        
        // dessine tous les incendies
        for (Incendie incend : this.donnees.getIncendies()) {
        	drawIncendie(incend, tailleCase);
        }
        
        // dessine tous les robots
        for (Robot robot : this.donnees.getRobots()) {
        	drawRobot(robot, tailleCase);
        }
    }
    
    private void drawCase(Case caseCourante, int x, int y, int taille) {
		Color color = null;
    	switch (caseCourante.getNature()) {
			case EAU:
				color = Simulateur.couleurEau;
				break;
			case FORET:
				color = Simulateur.couleurForet;
				break;
			case ROCHE:
				color = Simulateur.couleurRoche;
				break;
			case TERRAIN_LIBRE:
				color = this.backgroundColor;
				break;
			case HABITAT:
				color = Simulateur.couleurHabitat;
		}
    	gui.addGraphicalElement(new Rectangle(x + taille / 2,
    										  y + taille / 2, 
    										  color, 
//    										  Color.WHITE,
    										  color, 
    										  taille));
    }
    
    private void drawIncendie(Incendie incendie, int taille) {
    	gui.addGraphicalElement(
    			new Rectangle(incendie.getLocalisation().getColonne()*taille + taille / 2,
    						  incendie.getLocalisation().getLigne()*taille + taille / 2,
    						  Simulateur.couleurIncendie,
//    						  Color.WHITE,
    						  Simulateur.couleurIncendie,
    						  taille));
    }
    
    private void drawRobot(Robot robot, int taille) {
    	// TODO verify if it is important to have an ImageObserver. If yes, verify
    	//		which one
//    	gui.addGraphicalElement(
//    			new ImageElement(robot.getPosition().getColonne()*taille + taille / 2,
//    							 robot.getPosition().getLigne()*taille + taille / 2,
//    							 robot.getRobotImageName(),
//    							 taille,
//    							 taille,
//    							 null));
    	gui.addGraphicalElement(
    			new Rectangle(robot.getPosition().getColonne()*taille + taille / 2,
    						  robot.getPosition().getLigne()*taille + taille / 2,
//    						  robot.getRobotColor(),
    						  Color.WHITE,
    						  robot.getRobotColor(),
    						  taille));
    }
    
}
