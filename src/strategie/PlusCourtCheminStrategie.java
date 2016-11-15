package strategie;

import java.util.ArrayList;
import java.util.List;
import donnee.Direction;

import donnee.Carte;
import donnee.Case;
import donnee.NatureTerrain;
import evenement.DeplacerEvenement;
import evenement.Evenement;
import exception.DehorsDeLaFrontiereException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robot.Robot;

public class PlusCourtCheminStrategie extends Evenement implements MouvementStrategie {
	
	/** Le robot qui va se déplacer */
	private Robot robot;

	/** La carte où le robot va se déplacer */
	private Carte carte;

	/** la case destiation */
	private Case dest;

	/** la liste des evenements de déplacement unitaire pour faire le déplacement de la position du robot à la case dest*/
	private List<Evenement> evenements;
	
	/** la liste des directions à prendre pour le plus court chemin de la case où se trouve le robot à la case dest */
	private List<Direction> chemin;
        
    /**Représente l'infini */
    private Double infini = 1000000.0;
    
    private List<List<Boolean>> marque;
        
	/** contient le cout de déplacement de déplacement vers la case cout[i][j]
         * Ce déplacement ne dépend que de la case, car la vitesse ne dépend que de la
         * case à laquelle on veut se déplacer.
         */
	private List<List<Double>> cout;
                
    /** Contient la liste de predecesseur */
    private List<List<Direction>> predecesseur;
        
        
	public PlusCourtCheminStrategie(long date, Robot robot, Carte carte, Case dest) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.dest = dest;
		
		this.evenements = new ArrayList<Evenement>();
		this.chemin = new ArrayList<Direction>();
		this.cout = new ArrayList<>();
        this.predecesseur = new ArrayList <>();
        this.marque = new ArrayList<>();
        calculChemin();
        creerEvenementUnit();
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
	 * calcul le temps minimum pour aller à cette case.
	 * @return la direction du noeud à partir de la case courante avec le cout minimum à traiter
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
     * met à jour les couts de Djikstra
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
                	if (current.getLigne() == 7 && current.getColonne() == 0)
                		System.out.println(predecesseur.get(v.getLigne()).get(v.getColonne()));
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
                 if (marque.get(i).get(j) == false) {
                     return carte.getCase(i,j);
                 }
             }
         }
         return null;
    }
        
	@Override
	public void calculChemin() {
        initialiserDjikstra();
        Case suivant = robot.getPosition();
        Direction dir;
        int iteration = 0;
        int nbCase = carte.getNbColonnes() * carte.getNbLignes();
        while (!isEmpty()) {
            //System.out.println(suivant.getColonne());
            //System.out.println(suivant.getLigne());
            iteration++;
            dir = minimum(suivant);
            //System.out.println(dir);
            marque.get(suivant.getLigne()).set(suivant.getColonne(), Boolean.TRUE);
            if (dir != null) {
            	coutAJour(suivant);
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
        
    private void setChemin() {
        Case suivant = this.dest;
        System.out.println("robot:x"+ robot.getPosition().getLigne() + "y"+robot.getPosition().getColonne());
        while (!suivant.equals(robot.getPosition())) {
            chemin.add(predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
            //System.out.println(predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
            suivant = carte.getVoisin(suivant, predecesseur.get(suivant.getLigne()).get(suivant.getColonne()).inverserDir());
        }
    }
	
    private void creerEvenementUnit() {
        for (int i = 0; i<chemin.size(); i++) {
            System.out.println("creerEvenementUnitaire "+ chemin.get(i));
            evenements.add(new DeplacerEvenement(this.getDate()+i, robot, chemin.get(i), carte));
            System.out.println(evenements.size());
        }
    } 
        
    public List<Evenement> getEvenements() {
        //System.out.println(evenements.toString());
        return this.evenements;
    }

    @Override
    public void execute() throws DehorsDeLaFrontiereException {
        for (int i = 0; i < evenements.size(); i++) {
            try {
                evenements.get(i).execute();
            } catch (Exception ex) {
                throw new DehorsDeLaFrontiereException("Le robot est sorti de la carte dans la direction ");
            }
        }
    }

}
