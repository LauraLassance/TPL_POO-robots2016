package test;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnee.Direction;
import gui.GUISimulator;
import io.Simulateur;
import robot.Robot;
import evenement.*;

/**
 * Test de la seconde partie de l'algorithme
 */
public class TestRouesTerrainInterdit {

	private final static int tailleSimulateur = 700;
	
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            GUISimulator gui = new GUISimulator(TestRouesTerrainInterdit.tailleSimulateur, 
            								    TestRouesTerrainInterdit.tailleSimulateur, 
            									Color.BLACK);
            
            Simulateur simulation = new Simulateur(gui,
            									   args[0],
            									   TestRouesTerrainInterdit.tailleSimulateur,             									  
            									   TestRouesTerrainInterdit.tailleSimulateur);
            
            createScenarie(simulation);
            
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}
        
        /**
          * Creer le sénario pour le test
          * @param simulation la simulation utilisée pour la création du sénario
         */
        
	private static void createScenarie(Simulateur simulation) {
		
		Robot robot = simulation.getDonnees().getRobots().get(0);
		int i=1;
		simulation.ajouteEvenement( new DeplacerEvenement(i++, 
				  robot,
				  Direction.EST, 
				  simulation.getDonnees().getCarte()) );
		simulation.ajouteEvenement( new DeplacerEvenement(i++, 
				  robot,
				  Direction.SUD, 
				  simulation.getDonnees().getCarte()) );
		for (int j=0; j< 6; j++)
			simulation.ajouteEvenement( new DeplacerEvenement(i+j, 
															  robot,
															  Direction.NORD, 
															  simulation.getDonnees().getCarte()) );
	}

}
