package io;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.swing.JOptionPane;

import donnee.Case;
import donnee.DonneesSimulation;
import donnee.Incendie;
import evenement.Evenement;
import exception.*;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;

import robot.Robot;

public class Simulateur implements Simulable {
	/** Le taille d'une case pour chaque carte 
	 * tailleSimulateur / nbCases */
	private int tailleCase;
	
	private int largeur;
	private int hauteur;
	
	/** Les noms de fichier des différents types de case */
	private static String fichierTerrainLibre = "terrain_libre.jpg";
	private static String fichierEau = "eau.jpg";
	private static String fichierForet = "foret.jpg";
	private static String fichierRoche = "roche.jpg";
	private static String fichierHabitat = "habitat.jpg";
	private static String fichierIncendie = "feu.jpg";
	
	/** L'interface graphique associée */
    private GUISimulator gui;	
    
    /** Les donnees de la simulation */
    private DonneesSimulation donnees;

	/** Le nom du fichier où il y a les données de la simulaion */
    private String nomFichier;
    
    /** La date courante de simulation */
    private long dateSimulation;

	/** La liste d'évènements de la simulation */
    private List<Evenement> evenements;
    
    /** L'index qui garde quel est le prochain évènement */
    private int indexEvenement;

    /**
     * Crée une simulation a partir des données d'un fichier et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param nomFichier nom du fichier où il y a les données de la simulaion
     * @param largeur Largeur de la fenêtre
     * @param hauteur Hauteur de la fenêtre
     * @throws DataFormatException  Si le format du fichier ou de ses donnees est invalide 
     * @throws FileNotFoundException Si le nom du fichier est inconnu ou illesible
     */
    public Simulateur(GUISimulator gui, String nomFichier, int largeur, int hauteur) 
    											throws FileNotFoundException, DataFormatException {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        
		this.donnees = LecteurDonnees.lire(nomFichier);
		this.nomFichier = nomFichier;
		
		this.largeur = largeur;
		this.hauteur = hauteur;
		
		this.evenements = new ArrayList<Evenement>();
		this.indexEvenement = 0;
		
        this.draw();
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

    /**
     * To restart the simulation, the dateSimulation and the indexEvenement
     * have their values reset to 0 (their initial ones). To reset the robots
     * and the fires, the initial file is read again and its information is 
     * used to reset the important values of the robots (position and amount of
     * water in the tank) and fires (intensity). Yet, the references remain the
     * same, because the robots and fires pointers are also registered inside 
     * the events instances, so it is necessary to use the same instances that
     * were already in use.
     */
    @Override
    public void restart() {
    	try {
    		this.dateSimulation = 0;
    		this.indexEvenement = 0;
    		
    		DonneesSimulation donneesAux = LecteurDonnees.lire(nomFichier);
    		List<Robot> robotsAux = donneesAux.getRobots();
			
    		for (int i=0; i<this.donnees.getRobots().size();i++) {
    			this.donnees.getRobots().get(i).setPosition(
    					robotsAux.get(i).getPosition());
    			this.donnees.getRobots().get(i).setVolumeEauReservoir(
    					robotsAux.get(i).getVolumeEauReservoir());	
    		}
    		
    		List<Incendie> incendiesAux = donneesAux.getIncendies();
    		
    		for (int i=0; i<this.donnees.getIncendies().size();i++) {
    			this.donnees.getIncendies().get(i).setIntensite(
    					incendiesAux.get(i).getIntensite());
    		}
			
			this.draw();
		} catch (Exception e) {
			/* Do nothing. As the file was already read and everything worked, 
			 * we know that nothing will go wrong.
			 */
		}
    }

    /**
     * Increase the date in one unit. Evaluates which events need to be
     * executed because their date is smaller or the same as the current 
     * one. When one event is executed, the variable that keeps track of
     * the next event to be executed (indexEvenement) is increased.
     * Treats the possible exceptions thrown by the events.
     */
    private void incrementeDate() {
    	this.dateSimulation++;
    	
    	boolean doitExecuter = true;
    	
    	while(!this.simulationTerminee() && doitExecuter) {
    		Evenement evenement = this.evenements.get(this.indexEvenement);
    		
    		if (evenement.getDate() <= this.dateSimulation) {
    			try {
    				this.indexEvenement++;
    				evenement.execute();
    			} catch (DehorsDeLaFrontiereException e) {
					System.out.println(e.getMessage()+"\n");
					JOptionPane.showMessageDialog(this.gui.getContentPane(), 
							  e.getMessage());
				} catch (ReservoirPleinException e) {
					System.out.println(e.getMessage()+"\n");
					JOptionPane.showMessageDialog(this.gui.getContentPane(), 
							  e.getMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage()+"\n");
					JOptionPane.showMessageDialog(this.gui.getContentPane(), 
												  e.getMessage());
				}
    			
    		} else {
    			doitExecuter = false;
    		}
    	}
    }
    
    /**
     * Verifies if all the events were already executed.
     * @return Whether there are still events to be executed (true) or not (false).
     */
    private boolean simulationTerminee() {
    	return (this.evenements.size() == this.indexEvenement);
    }
    
    /**
     * Dessine la carte avec tous ses éléments (robots et incendies).
     * Si un incendie a une intensité égal a zero, il n'est pas dessiné.
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
        	if (incend.getIntensite() > 0)
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
		String nomFichier = System.getProperty("user.dir")+"/images/";
    	switch (caseCourante.getNature()) {
			case EAU:
				nomFichier += Simulateur.fichierEau;
				break;
			case FORET:
				nomFichier += Simulateur.fichierForet;
				break;
			case ROCHE:
				nomFichier += Simulateur.fichierRoche;
				break;
			case TERRAIN_LIBRE:
				nomFichier += Simulateur.fichierTerrainLibre;
				break;
			case HABITAT:
				nomFichier += Simulateur.fichierHabitat;
		}

    	gui.addGraphicalElement(
    			new ImageElement(x,
    							 y,
    							 nomFichier,
    							 this.tailleCase,
    							 this.tailleCase,
    							 null));
    }
    
    /**
     * Dessine un incendie dans une position de la carte.
     * @param incendie Objet qui répresent l'incendie dessiné
     * @param taille Taille de la case de la carte
     */
    private void drawIncendie(Incendie incendie) {
    	gui.addGraphicalElement(
    			new ImageElement(
    					incendie.getLocalisation().getColonne()*this.tailleCase,
					    incendie.getLocalisation().getLigne()*this.tailleCase,
					    System.getProperty("user.dir")+"/images/"+Simulateur.fichierIncendie,
					    this.tailleCase,
					    this.tailleCase,
					    null));
    }
    
    /**
     * Dessine un robot dans une position de la carte.
     * @param robot Objet qui répresent le robot dessiné
     * @param taille Taille de la case de la carte
     */
    private void drawRobot(Robot robot) {
    	gui.addGraphicalElement(
    			new ImageElement(robot.getPosition().getColonne()*this.tailleCase,
    							 robot.getPosition().getLigne()*this.tailleCase,
    							 System.getProperty("user.dir")+"/images/"+robot.getRobotImageName(),
    							 this.tailleCase,
    							 this.tailleCase,
    							 null));
    }
    
    public DonneesSimulation getDonnees() {
    	return this.donnees;
    }
    
    public long getDateSimulation() {
		return dateSimulation;
	}
}
