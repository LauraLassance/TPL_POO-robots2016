import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnee.DonneesSimulation;
import gui.GUISimulator;
import io.LecteurDonnees;
import io.Simulateur;

public class TestPremierePartie {

	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
            
            GUISimulator gui = new GUISimulator(
            		donnees.getCarte().getTailleCases() * donnees.getCarte().getNbColonnes(), 
            		donnees.getCarte().getTailleCases() * donnees.getCarte().getNbLignes(), 
            		Color.BLACK);
            
            Simulateur simulation = new Simulateur(gui, donnees, Color.BLACK);
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
	}

}
