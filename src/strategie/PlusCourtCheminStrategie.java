package strategie;

import java.util.ArrayList;
import java.util.List;
import donnee.Direction;

import donnee.Carte;
import donnee.Case;
import evenement.DeplacerEvenement;
import evenement.Evenement;
import exception.PasDeCheminException;
import io.Simulateur;
import robot.Robot;

public class PlusCourtCheminStrategie implements MouvementStrategie {

	/** Le robot qui va se déplacer */
	private Robot robot;

	/** La carte où le robot va se déplacer */
	private Carte carte;

	/** La case destiation */
	private Case dest;

	/** La liste des evenements de déplacement unitaire pour faire le déplacement de la position du robot à la case dest*/
	private List<Evenement> evenements;

	/** La liste des directions à prendre pour le plus court chemin de la case où se trouve le robot à la case dest */
	private List<Direction> chemin;

	/** La date de début des évènements */
	private long date;

    /**Représente l'infini */
    private Double infini = Double.MAX_VALUE;

    private List<List<Boolean>> marque;

	/** Contient le cout de déplacement de déplacement vers la case cout[i][j]
     * Ce déplacement ne dépend que de la case, car la vitesse ne dépend que de la
     * case à laquelle on veut se déplacer.
     */
	private List<List<Double>> cout;

    /** Contient la liste de predecesseur */
    private List<List<Direction>> predecesseur;

    /**
     * instancie la classe PlusCourtCheminStrategie
     * @param date la date de debut des évenements unitaires de déplacement
     * @param robot le robot qui sera déplacer
     * @param carte la carte où se déplace le robot
     * @param dest la destination à laquelle veut se déplacer le robot
     */
	public PlusCourtCheminStrategie(long date, Robot robot, Carte carte, Case dest) {
		this.date = date;
		this.robot = robot;
		this.carte = carte;
		this.dest = dest;

		this.evenements = new ArrayList<Evenement>();
		this.chemin = new ArrayList<Direction>();
		this.cout = new ArrayList<>();
        this.predecesseur = new ArrayList <>();
        this.marque = new ArrayList<>();
   	}

   /**
    * Initialisation des attributs de la classe pour effectuer
    * l'algorithme de dijkstra, je l'ai toujours appelé Djikstra
    * il me semble que Djikstra est plus cool que Dijkstra
    */
	private void initialiserDjikstra() {
		Double max = infini;
        for (int i = 0; i  < carte.getNbLignes(); i++) {
            cout.add(new ArrayList<>());
            predecesseur.add(new ArrayList<>());
            marque.add(new ArrayList<>());
        }

        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                cout.get(i).add(j, max);
                marque.get(i).add(j, false);
                predecesseur.get(i).add(j, null);
            }
        }

        cout.get(robot.getPosition().getLigne()).set(robot.getPosition().getColonne(), 0.0);
    }

    /**
     * renvoie le temps nécessaire pour un déplacement sur une case destination.
     * @param dest la case à laquelle le robot a envie de se déplacer
     * @return le temps nécessaire pour le déplacement.
     */
    @Override
	public double tempsNecessaireUnit(Case dest) {
        if (robot.peutSeDeplacer(dest.getNature()))
            return robot.getVitesse(dest.getNature());
        else
            return infini;
	}

	/**
	 * Calcul le temps minimum pour aller à cette case.
	 * @return La direction du noeud à partir de la case courante avec le cout minimum suivante
	 */
    private Direction minimum(Case current) {
        Double min = infini;
        for (Direction d : Direction.values()) {
            if (carte.voisinExiste(current, d) &&
                (tempsNecessaireUnit(carte.getVoisin(current,d)) +
                cout.get(current.getLigne()).get(current.getColonne()) < min) &&
	            marque.get(carte.getVoisin(current, d).getLigne()).get(carte.getVoisin(current, d).getColonne()) == Boolean.FALSE) {
	                min = tempsNecessaireUnit(carte.getVoisin(current, d))+ cout.get(current.getLigne()).get(current.getColonne());

	                return d;
            }
        }
        return null;
    }

    /**
     * Met à jour la matrice de couts de Djikstra
     */
    private void coutAJour(Case current) {

        for (Direction d : Direction.values()) {
            if (carte.voisinExiste(current, d)) {
                Case v = carte.getVoisin(current, d);

                if (cout.get(v.getLigne()).get(v.getColonne()) >
                        tempsNecessaireUnit(v) + cout.get(current.getLigne()).get(current.getColonne())) {

                	cout.get(v.getLigne()).set(v.getColonne(), tempsNecessaireUnit(v) + cout.get(current.getLigne()).get(current.getColonne()));
                	predecesseur.get(v.getLigne()).set(v.getColonne(), d);
                }
            }
        }
    }

    /**
     * Méthode pour vérifier si tous les noeuds ont été visité
     * @return false s'il ne reste pas de noeud à visiter, true sinon.
     */
    public boolean isEmpty() {
    	for (int i = 0; i < marque.size(); i++) {
            for (int j = 0; j < marque.get(i).size(); j++) {
                if (marque.get(i).get(j) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Getter de case non marque par l'algorithme.
     * @return une case de la carte non traitée
     */

    public Case getCaseNonTraite() {
         for (int i = 0; i < marque.size(); i++) {
             for (int j = 0; j < marque.get(i).size(); j++) {
                 if (marque.get(i).get(j) == false && cout.get(i).get(j) < infini) {
                     return carte.getCase(i,j);
                 }
             }
         }
         return null;
    }

    /**
     * algorithme explicite du calcul de chemin, trouve le chemin le plus court
     * s'il existe, sinon renvoie une exception.
     * @throws PasDeCheminException exception levée s'il n'existe pas de chemin
     * entre la case de départ du robot, et la case souhaitée
     */

	private void calculChemin() throws PasDeCheminException {
        initialiserDjikstra();
        Case suivant = robot.getPosition();
        Direction dir;
        int nbCase = carte.getNbColonnes() * carte.getNbLignes();
        while (!isEmpty()) {
            dir = minimum(suivant);
            coutAJour(suivant);
            marque.get(suivant.getLigne()).set(suivant.getColonne(), Boolean.TRUE);
            if (dir != null) {
            	suivant = carte.getVoisin(suivant, dir);
            } else {
                suivant = getCaseNonTraite();
            }
        }

        setChemin();
	}

    /**
     * reparcourt la matrice des prédecesseur pour reconstruire
     * le plus court chemin.
     * @throws PasDeCheminException exception levée s'il n'y a pas de
     * chemin.
     */
    private void setChemin() throws PasDeCheminException {
        Case suivant = this.dest;
        System.out.println(robot.getType()+":x"+ robot.getPosition().getLigne() + "y"+robot.getPosition().getColonne());
        while (!suivant.equals(robot.getPosition())) {
            Direction d = predecesseur.get(suivant.getLigne()).get(suivant.getColonne());
            /** S'il y a un chemin possible pour cette arête */
            if (d != null) {
            	chemin.add(d);
	            suivant = carte.getVoisin(suivant, predecesseur.get(suivant.getLigne()).get(suivant.getColonne()).inverserDir());
            } else {
            	throw new PasDeCheminException(this.dest);
            }
        }
    }

    /**
     * créer dans la liste d'evenement tous les évenements de déplacement
     * unitaire pour effectuer le plus court chemin.
     */

    private void creerEvenementUnit() {
        for (int i = 0; i<chemin.size(); i++) {
            System.out.println("creerEvenementUnitaire "+ chemin.get(i));
            evenements.add(new DeplacerEvenement(this.date+i, robot, chemin.get(i), carte));
            System.out.println(evenements.size());
        }
    }

    /**
     * Getter pour la liste d'évenement contenant le chemin.
     * @return la suite d'évement pour arriver jusqu'à la case
     * @throws PasDeCheminException lorsque le chemin n'existe pas.
     */
    @Override
    public List<Evenement> getChemin() throws PasDeCheminException {
    	calculChemin();
        creerEvenementUnit();
        return this.evenements;
    }

}
