package test;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnee.Direction;
import gui.GUISimulator;
import io.Simulateur;
import robot.Robot;
import evenement.*;

public class TestScenario1Partie2 {

	private final static int tailleSimulateur = 600;
	
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            GUISimulator gui = new GUISimulator(TestScenario1Partie2.tailleSimulateur, 
            									TestScenario1Partie2.tailleSimulateur, 
            									Color.BLACK);
            
            Simulateur simulation = new Simulateur(gui,
            									   args[0],
            									   Color.BLACK,
            									   TestScenario1Partie2.tailleSimulateur,             									  
            									   TestScenario1Partie2.tailleSimulateur);
            
            createScenarie(simulation);
            
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}
	
	private static void createScenarie(Simulateur simulation) {
		
		Robot robot = simulation.getDonnees().getRobots().get(0);
		for (int i=1; i< 5; i++)
			simulation.ajouteEvenement( new DeplacerEvenement(i, 
															  robot,
															  Direction.NORD, 
															  simulation.getDonnees().getCarte()) );
	}

}
