package test;
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

/**
 * Test le second senario du sujet
 */
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
            									   TestScenario2Partie2.tailleSimulateur,
            									   TestScenario2Partie2.tailleSimulateur);

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
		int date = 1;
		Robot robot = simulation.getDonnees().getRobots().get(1);
		simulation.ajouteEvenement(
					new DeplacerEvenement(date++,
									      robot,
									      Direction.NORD,
									      simulation.getDonnees().getCarte()));

		simulation.ajouteEvenement(
				 new DeversementEvenement(
							date,
							robot,
							simulation.getDonnees().getIncendies().get(3)));

		// corrige la date pour après le deversement d'eau (qui prend un peu
		// de temps défini dans chaque robot)
		date += robot.getTempsInterventionUnitaire();

		for (int i=0; i< 2; i++)
			simulation.ajouteEvenement(
					new DeplacerEvenement(date++,
										  robot,
										  Direction.OUEST,
										  simulation.getDonnees().getCarte()));

		simulation.ajouteEvenement(
					new RemplirEvenement(date,
										 robot,
										 simulation.getDonnees().getCarte()));
		// corrige la date pour après la remplissage du reservoir (qui prend
		// un peu de temps défini dans chaque robot)
		date += robot.getTempsRemplissage();

		for (int i=0; i< 2; i++)
			simulation.ajouteEvenement(
					new DeplacerEvenement(date++,
										  robot,
										  Direction.EST,
										  simulation.getDonnees().getCarte()));

		simulation.ajouteEvenement(
				 new DeversementEvenement(
						 	  date,
							  robot,
							  simulation.getDonnees().getIncendies().get(3)));

	}
}
