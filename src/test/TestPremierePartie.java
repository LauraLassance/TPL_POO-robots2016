package test;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import io.Simulateur;

public class TestPremierePartie {
	
	private final static int tailleSimulateur = 600;

	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            GUISimulator gui = new GUISimulator(TestPremierePartie.tailleSimulateur, 
            									TestPremierePartie.tailleSimulateur, 
            									Color.BLACK);
            
            Simulateur simulation = new Simulateur(gui,
            									   args[0],
            									   TestPremierePartie.tailleSimulateur,             									  
            									   TestPremierePartie.tailleSimulateur);
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
	}

}
