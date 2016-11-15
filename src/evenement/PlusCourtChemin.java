package evenement;

import java.util.ArrayList;
import java.util.List;
import donnee.Direction;

import donnee.Carte;
import donnee.Case;
import exception.DehorsDeLaFrontiereException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robot.Robot;

public class PlusCourtChemin extends Evenement implements CheminEvenement {
	
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
        private Double infini = 1000.0;
        
        private List<List<Boolean>> marque;
	
	/** contient le cout de déplacement de déplacement vers la case cout[i][j]
         * Ce déplacement ne dépend que de la case, car la vitesse ne dépend que de la
         * case à laquelle on veut se déplacer.
         */
	private List<List<Double>> cout;
                
        /** Contient la liste de predecesseur */
        private List<List<Direction>> predecesseur;
        
        
	public PlusCourtChemin(long date, Robot robot, Carte carte, Case dest) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.dest = dest;
		
		this.evenements = new ArrayList<>();
		this.chemin = new ArrayList<>();
		this.cout = new ArrayList<>();
                this.predecesseur = new ArrayList <>();
                this.marque = new ArrayList<>();
                calculPlusCourtChemin();
                creerEvenementUnit();
	}
	
	private void initialiserDjikstra() {
            List<Double> colonneCout = new ArrayList<>(); 
            List<Direction> colonneDirection = new ArrayList<>();
            List<Case> colonneCase = new ArrayList<>();
            List<Boolean> colonneMarque = new ArrayList<>();
            Double max = infini;
            for (int i = 0; i  < carte.getNbLignes(); i++) {
                for (int j = 0; j < carte.getNbColonnes(); j++) {
                    colonneCout.add(j, max);
                    colonneDirection.add(j,null);
                    colonneCase.add(j, null);
                    colonneMarque.add(j, false);
                }
                cout.add(i, colonneCout);
                predecesseur.add(i, colonneDirection);
                marque.add(i, colonneMarque);
            }
            colonneCout.set(robot.getPosition().getColonne(), 0.0);
            cout.set(robot.getPosition().getLigne(), colonneCout);
            
        }
	
        @Override
	public double tempsNecessaireUnit(Case dest) {
                if (robot.peutSeDeplacer(dest.getNature())) 
                    return robot.getVitesse(dest.getNature());
                else 
                    return 1000;
	}
        
        /**
         * calcul le temps minimum pour aller à cette case.
         * @return la direction du noeud à partir de la case courante avec le cout minimum à traiter
         */
        private Direction minimum(Case current) {
            Direction dir = null;
            Double min = infini;
            for (Direction d : Direction.values()) {
                if (tempsNecessaireUnit(carte.getVoisin(current,d)) + cout.get(current.getLigne()).get(current.getColonne()) < min &&
                        marque.get(carte.getVoisin(current, d).getLigne()).get(carte.getVoisin(current, d).getColonne()) == Boolean.FALSE) {
                    min = tempsNecessaireUnit(carte.getVoisin(current, d))+ cout.get(current.getLigne()).get(current.getColonne());
                    dir = d;
                }
            }
            return dir; 
        }
        
        /**
         * met à jour les couts de Djikstra
         */
        private void coutAJour(Case current) {
            
            List<Double> colonneCout = cout.get(current.getLigne());
            List<Direction> colonneDirection = predecesseur.get(current.getColonne());
            
            for (Direction d : Direction.values()) {
                Case v = carte.getVoisin(current, d);
                if (cout.get(v.getLigne()).get(v.getColonne()) > 
                        tempsNecessaireUnit(v) + cout.get(current.getLigne()).get(current.getColonne())) {
                
                colonneCout.set(current.getColonne(), tempsNecessaireUnit(v) + cout.get(current.getLigne()).get(current.getColonne()));
                colonneDirection.set(current.getColonne(), d);
                cout.set(current.getLigne(), colonneCout);
                predecesseur.add(current.getLigne(), colonneDirection);
             
            
            }
            }
        }
	
	@Override
	public void calculPlusCourtChemin() {
            initialiserDjikstra();
            List<Boolean> marqueColonne = marque.get(robot.getPosition().getLigne());
            Case suivant = robot.getPosition();
            Direction dir;
            int iteration = 0;
            int nbCase = carte.getNbColonnes() * carte.getNbLignes();
            while (!suivant.equals(dest) || (suivant != null) || iteration < nbCase) {
                System.out.println(suivant.getColonne());
                System.out.println(suivant.getLigne());
                marqueColonne = marque.get(suivant.getLigne());
                iteration++;
                dir = minimum(suivant);
                marqueColonne.set(suivant.getColonne(), Boolean.TRUE);
                marque.set(suivant.getLigne(), marqueColonne);
                
                System.out.println(dir);
                if (dir != null) {
                chemin.add(dir);
                coutAJour(suivant); 
                suivant = carte.getVoisin(suivant, dir);
                } else {
                    
                }
            }
        } 
        
        private void setChemin() {
            Case suivant = this.robot.getPosition();
            System.out.println("robot:x"+ robot.getPosition().getLigne() + "y"+robot.getPosition().getColonne());
            while (!suivant.equals(this.dest)) {
                /*
                chemin.add(predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
                suivant = carte.getVoisin(carte.getCase(suivant.getLigne(), suivant.getColonne()), predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
                */
                System.out.println(predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
                suivant = carte.getVoisin(suivant, predecesseur.get(suivant.getLigne()).get(suivant.getColonne()));
            }
        }
        
        public List<Evenement> getEvenements() {
            System.out.println(evenements.toString());
            return this.evenements;
        }
	
        private void creerEvenementUnit() {
            setChemin();
                for (int i = 0; i<chemin.size(); i++) {
                    evenements.add(new DeplacerEvenement(i, robot, chemin.get(i), carte));
                }
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
