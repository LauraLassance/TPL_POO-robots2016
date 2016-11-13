import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnee.Direction;
import evenement.DeplacerEvenement;
import evenement.DeversementEvenement;
import evenement.RemplirEvenement;
import gui.GUISimulator;
import io.Simulateur;
import robot.Robot;

public class TestScenario2Partie2 {
	private final static int tailleSimulateur = 600;
	
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            GUISimulator gui = new GUISimulator(TestScenario2Partie2.tailleSimulateur, 
            									TestScenario2Partie2.tailleSimulateur, 
            									Color.BLACK);
            
            Simulateur simulation = new Simulateur(gui,
            									   args[0],
            									   Color.BLACK,
            									   TestScenario2Partie2.tailleSimulateur,             									  
            									   TestScenario2Partie2.tailleSimulateur);
            
            createScenarie(simulation);
            
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}
	
	private static void createScenarie(Simulateur simulation) {
		int date = 1;
		Robot robot = simulation.getDonnees().getRobots().get(1);
		simulation.ajouteEvenement( 
					new DeplacerEvenement(date++, 
									      robot,
									      Direction.NORD, 
									      simulation.getDonnees().getCarte()));
		
		simulation.ajouteEvenements(
				DeversementEvenement.creerDeversermentUnit(
							date++, 
							robot, 
							simulation.getDonnees().getIncendies().get(3)));
		
		for (int i=0; i< 2; i++)
			simulation.ajouteEvenement( 
					new DeplacerEvenement(date++, 
										  robot,
										  Direction.OUEST, 
										  simulation.getDonnees().getCarte()));
		
		simulation.ajouteEvenements(
				RemplirEvenement.creerRemplissageUnit(date++,
													  robot, 
													  simulation.getDonnees().getCarte()));
		
		for (int i=0; i< 2; i++)
			simulation.ajouteEvenement( 
					new DeplacerEvenement(date++, 
										  robot,
										  Direction.EST, 
										  simulation.getDonnees().getCarte()));
		
		simulation.ajouteEvenements(
				DeversementEvenement.creerDeversermentUnit(
							date++, 
							robot, 
							simulation.getDonnees().getIncendies().get(3)));
		
	}
}