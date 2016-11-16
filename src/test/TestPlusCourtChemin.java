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
import exception.PasDeCheminException;
import gui.GUISimulator;
import io.Simulateur;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import javax.swing.JOptionPane;

import robot.Robot;
import strategie.PlusCourtCheminStrategie;

/**
 *
 * @author moi
 */
public class TestPlusCourtChemin {
    	private final static int tailleSimulateur = 700;
	
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
    		Robot robot = simulation.getDonnees().getRobots().get(1);
            Carte carte = simulation.getDonnees().getCarte();
            Case dest = simulation.getDonnees().getIncendies().get(0).getLocalisation();
            PlusCourtCheminStrategie calculChemin;
            calculChemin = new PlusCourtCheminStrategie(date, robot, carte, dest);
			simulation.ajouteEvenement(calculChemin);
//			simulation.ajouteEvenements(CalculChemin.getEvenements());
            
	}
}
