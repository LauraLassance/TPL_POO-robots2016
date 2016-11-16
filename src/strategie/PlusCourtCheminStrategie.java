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

public class PlusCourtCheminStrategie implements AffectationStrategie {

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

    @Override
	public double tempsNecessaireUnit(Case dest) {
        if (robot.peutSeDeplacer(dest.getNature()))
            return robot.getVitesse(dest.getNature());
        else
            return infini;
	}

	/**
	 * Calcul le temps minimum pour aller à cette case.
	 * @return La direction du noeud à partir de la case courante avec le cout minimum à traiter
	 */
    private Direction minimum(Case current) {
        Double min = infini;
        //System.out.println("min"+current.getLigne());
        //System.out.println("min"+current.getColonne());
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
     * Met à jour les couts de Djikstra
     */
    private void coutAJour(Case current) {

        for (Direction d : Direction.values()) {
            if (carte.voisinExiste(current, d)) {
                Case v = carte.getVoisin(current, d);
                //System.out.println("cout"+cout.get(v.getLigne()).get(v.getColonne()));
                //System.out.println("cout"+tempsNecessaireUnit(v) +" "+ cout.get(current.getLigne()).get(current.getColonne()));
                if (cout.get(v.getLigne()).get(v.getColonne()) >
                        tempsNecessaireUnit(v) + cout.get(current.getLigne()).get(current.getColonne())) {

                	cout.get(v.getLigne()).set(v.getColonne(), tempsNecessaireUnit(v) + cout.get(current.getLigne()).get(current.getColonne()));
                	predecesseur.get(v.getLigne()).set(v.getColonne(), d);
                }
            }
        }
    }

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

	private void calculChemin() throws PasDeCheminException {
        initialiserDjikstra();
        Case suivant = robot.getPosition();
        Direction dir;
        int nbCase = carte.getNbColonnes() * carte.getNbLignes();
        while (!isEmpty()) {
            //System.out.println(suivant.getColonne());
            //System.out.println(suivant.getLigne());
            dir = minimum(suivant);

            //System.out.println(dir);
            //System.out.println(suivant.getLigne() + " " + suivant.getColonne());
            coutAJour(suivant);
            marque.get(suivant.getLigne()).set(suivant.getColonne(), Boolean.TRUE);
            if (dir != null) {
            	suivant = carte.getVoisin(suivant, dir);
            } else {
                suivant = getCaseNonTraite();
            }
        }

        setChemin();
        /*
        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++)
                System.out.print(predecesseur.get(i).get(j)+ " ");
        }
        System.out.print("\n");
        */
	}

    private void setChemin() throws PasDeCheminException {
        Case suivant = this.dest;
        System.out.println(robot.getType()+":x"+ robot.getPosition().getLigne() + "y"+robot.getPosition().getColonne());
        while (!suivant.equals(robot.getPosition())) {
            Direction d = predecesseur.get(suivant.getLigne()).get(suivant.getColonne());
            /** S'il y a un chemin possible pour cette arête */
            if (d != null) {
            	chemin.add(d);
	            //System.out.println(predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
	            suivant = carte.getVoisin(suivant, predecesseur.get(suivant.getLigne()).get(suivant.getColonne()).inverserDir());
            } else {
            	throw new PasDeCheminException(this.dest);
            }
        }
    }

    private void creerEvenementUnit() {
        for (int i = 0; i<chemin.size(); i++) {
            System.out.println("creerEvenementUnitaire "+ chemin.get(i));
            evenements.add(new DeplacerEvenement(this.date+i, robot, chemin.get(i), carte));
            System.out.println(evenements.size());
        }
    }

    @Override
    public List<Evenement> getChemin() throws PasDeCheminException {
    	calculChemin();
        creerEvenementUnit();
        return this.evenements;
    }

}
