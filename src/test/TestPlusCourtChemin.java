/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import evenement.DeplacerEvenement;
import evenement.DeversementEvenement;
import evenement.RemplirEvenement;
import evenement.PlusCourtChemin;
import gui.GUISimulator;
import io.Simulateur;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import robot.Robot;

/**
 *
 * @author moi
 */
public class TestPlusCourtChemin {
    	private final static int tailleSimulateur = 600;
	
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            GUISimulator gui = new GUISimulator(TestPlusCourtChemin.tailleSimulateur, 
            									TestPlusCourtChemin.tailleSimulateur, 
            									Color.BLACK);
            
            Simulateur simulation = new Simulateur(gui,
            									   args[0],
            									   Color.BLACK,
            									   TestPlusCourtChemin.tailleSimulateur,             									  
            									   TestPlusCourtChemin.tailleSimulateur);
            
            createScenarie(simulation);
            
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}
        
    	private static void createScenarie(Simulateur simulation) {
		int date = 1;
		Robot robot = simulation.getDonnees().getRobots().get(0);
                Carte carte = simulation.getDonnees().getCarte();
                Case dest = simulation.getDonnees().getIncendies().get(0).getLocalisation();
                PlusCourtChemin CalculChemin = new PlusCourtChemin(date, robot, carte, dest);
		simulation.ajouteEvenements(CalculChemin.getEvenements());
	}
}
