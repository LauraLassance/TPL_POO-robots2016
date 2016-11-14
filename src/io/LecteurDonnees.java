package io;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import donnee.DonneesSimulation;
import donnee.NatureTerrain;
import robot.TypeRobot;



/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {

    /**
     * Lit et crée un objet DonneesSimulation avec le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     * @return donnees objet qui contient les cases, robots et incendies lus du fichier
     */
    public static DonneesSimulation lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        
    	
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte();
        lecteur.lireIncendies();
        lecteur.lireRobots();
        scanner.close();
        
        return donnees;
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;
    private static DonneesSimulation donnees;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
        
        donnees = new DonneesSimulation();
    }

    /**
     * Lit et remplit l'objet donnees avec les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m

            donnees.creerCarte(tailleCases, nbLignes, nbColonnes);
            
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }


    /**
     * Lit et remplit l'objet donnees avec les donnees d'une case.
     * @throws ExceptionFormatDonnees
     */
    private void lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();

        try {
            chaineNature = scanner.next();
            verifieLigneTerminee();

            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            
            donnees.addCase(lig, col, nature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        } catch (IllegalArgumentException e) {
        	throw new DataFormatException("format de case invalide. "
                    + "Nature de terrain specifiée,"+chaineNature+" , n'existe pas");
        }

    }


    /**
     * Lit et remplit l'objet donnees avec les donnees des incendies.
     * @throws ExceptionFormatDonnees 
     */
    private void lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            donnees.creerIncendies(nbIncendies);
            
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et remplit l'objet donnees avec les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            donnees.addIncendie(lig, col, intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et remplit l'objet donnees avec les donnees des robots.
     */
    private void lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            donnees.creerRobots(nbRobots);
            
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et remplit l'objet donnees avec les donnees du i-eme robot.
     * @param i
     */
    private void lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();
        String chaineType = new String();
        
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            chaineType = scanner.next();

            TypeRobot type = TypeRobot.valueOf(chaineType);

            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                donnees.addRobot(lig, col, type);
            } else {
                double vitesse = Double.parseDouble(s);
                donnees.addRobot(lig, col, type, vitesse);
            }
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        } catch (IllegalArgumentException e) {
        	throw new DataFormatException("format de robot invalide. "
                    + "Type de robot specifiée,"+chaineType+" , n'existe pas");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
