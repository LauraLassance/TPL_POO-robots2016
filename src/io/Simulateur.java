package io;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import java.util.List;
import java.util.zip.DataFormatException;

import donnee.Case;
import donnee.DonneesSimulation;
import donnee.Incendie;
import evenement.Evenement;
import exception.*;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import robot.Robot;

public class Simulateur implements Simulable {
	/** Le taille d'une case pour chaque carte 
	 * tailleSimulateur / nbCases */
	private int tailleCase;
	
	private int largeur;
	private int hauteur;
	
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

	/** Le nom du fichier où il y a les données de la simulaion */
    private String nomFichier;
    
    /** La date courante de simulation */
    private long dateSimlation;
    
    /** La liste d'évènements de la simulation */
    private List<Evenement> evenements;

    /**
     * Crée une simulation a partir des données d'un fichier et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param nomFicher nom du fichier où il y a les données de la simulaion
     * @param bkgColor la couleur de fond
     * @param largeur Largeur de la fenêtre
     * @param hauteur Hauteur de la fenêtre
     * @throws DataFormatException 
     * @throws FileNotFoundException 
     */
    public Simulateur(GUISimulator gui, String nomFichier, Color bkgColor, int largeur, int hauteur) throws FileNotFoundException, DataFormatException {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        
		this.donnees = LecteurDonnees.lire(nomFichier);
		this.nomFichier = nomFichier;
		
		this.backgroundColor = bkgColor;
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		this.evenements = new ArrayList<Evenement>();
		
        draw();
    }

    public void ajouteEvenement(Evenement e) {
    	this.evenements.add(e);
    	Collections.sort(this.evenements);
    }
    
    public void ajouteEvenements(List<Evenement> list) {
    	this.evenements.addAll(list);
    	Collections.sort(this.evenements);
    }
    
    @Override
    public void next() {
    	this.incrementeDate();
    	this.draw();
    }

    @Override
    public void restart() {
    	try {
			this.donnees = LecteurDonnees.lire(nomFichier);
			
			this.draw();
		} catch (Exception e) {
			/* Do nothing. As the file was already read and everything worked, 
			 * we know that nothing will go wrong.
			 */
		}
    }

    private void incrementeDate() {
    	this.dateSimlation++;
    	if	(!this.simulationTerminee()) {
    		List<Evenement> evenementsRealises = new ArrayList<Evenement>();
    		
    		Iterator<Evenement> it = this.evenements.iterator();
    		boolean memeDate = true;
    		
    		while (it.hasNext() && memeDate)
    		{
    			Evenement evenement = it.next();
    			if (evenement.getDate() == this.dateSimlation) {
					try {
						evenementsRealises.add(evenement);
						evenement.execute();
						
					} catch (DehorsDeLaFrontiereException e) {
						// TODO Open dialog to say show message
						System.out.println(e.getMessage());
						e.printStackTrace();
					} catch (ReservoirPleinException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
    				} catch (Exception e) {
    					System.out.println(e.getMessage());
    					e.printStackTrace();
					}
				} else
					memeDate = false;
			}
    		
    		if (!evenementsRealises.isEmpty())
    			this.evenements.removeAll(evenementsRealises);
    	}
    }
    
    private boolean simulationTerminee() {
    	return this.evenements.isEmpty();
    }
    
    /**
     * Dessine la carte avec tous ses éléments (robots et incendies).
     */
    private void draw() {
        gui.reset();	// clear the window

        this.tailleCase = Math.min(
        					this.largeur / this.donnees.getCarte().getNbColonnes(),
        					this.hauteur / this.donnees.getCarte().getNbLignes());
        
        // dessine tous les cases
        for (int i=0; i < this.donnees.getCarte().getNbLignes(); i++)
        	for (int j=0; j < this.donnees.getCarte().getNbColonnes(); j++) {
        		Case caseCourante = this.donnees.getCarte().getCase(i, j);
        		drawCase(caseCourante, j*this.tailleCase, i*this.tailleCase);
        	}
        
        // dessine tous les incendies
        for (Incendie incend : this.donnees.getIncendies()) {
        	drawIncendie(incend);
        }
        
        // dessine tous les robots
        for (Robot robot : this.donnees.getRobots()) {
        	drawRobot(robot);
        }
    }
    
    /**
     * Dessine une case dans une position de la carte.
     * @param caseCourante Case dessinée
     * @param x Position horizontal de la case
     * @param y Position vertical de la case
     * @param taille Taille de la case
     */
    private void drawCase(Case caseCourante, int x, int y) {
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
    	gui.addGraphicalElement(new Rectangle(x + this.tailleCase / 2,
    										  y + this.tailleCase / 2, 
    										  color, 
//    										  Color.WHITE,
    										  color, 
    										  this.tailleCase));
    }
    
    /**
     * Dessine un incendie dans une position de la carte.
     * @param incendie Objet qui répresent l'incendie dessiné
     * @param taille Taille de la case de la carte
     */
    private void drawIncendie(Incendie incendie) {
    	gui.addGraphicalElement(
    			new Rectangle(incendie.getLocalisation().getColonne()*this.tailleCase + this.tailleCase / 2,
    						  incendie.getLocalisation().getLigne()*this.tailleCase + this.tailleCase / 2,
    						  Simulateur.couleurIncendie,
//    						  Color.WHITE,
    						  Simulateur.couleurIncendie,
    						  this.tailleCase));
    }
    
    /**
     * Dessine un robot dans une position de la carte.
     * @param robot Objet qui répresent le robot dessiné
     * @param taille Taille de la case de la carte
     */
    private void drawRobot(Robot robot) {
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
    			new Rectangle(robot.getPosition().getColonne()*this.tailleCase + this.tailleCase / 2,
    						  robot.getPosition().getLigne()*this.tailleCase + this.tailleCase / 2,
//    						  robot.getRobotColor(),
    						  Color.WHITE,
    						  robot.getRobotColor(),
    						  this.tailleCase));
    }
    
    public DonneesSimulation getDonnees() {
		return donnees;
	}
    
}
