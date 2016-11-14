package evenement;

import java.util.ArrayList;
import java.util.List;
import donnee.Direction;

import donnee.Carte;
import donnee.Case;
import exception.DehorsDeLaFrontiereException;
import robot.Robot;

public class PlusCourtChemin extends Evenement implements CheminEvenement {
	
	/** Le robot qui va se déplacer */
	private Robot robot;

	/** La carte où le robot va se déplacer */
	private Carte carte;

	/** la case destination */
	private Case dest;
	
	/** la liste des evenements de déplacement unitaire pour faire le déplacement de la position du robot à la case dest*/
	private List<Evenement> evenements;
	
	/** la liste des direction à prendre pour le plus court chemin de la case où se trouve le robot à la case dest */
	private List<Direction> chemin;
	
	/** contient les cout de l'algorithme de djikstra */
	private List<List<Case>> cout;
	
	

	public PlusCourtChemin(long date, Robot robot, Carte carte, Case dest) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.dest = dest;
		
		this.evenements = new ArrayList<Evenement>();
		this.chemin = new ArrayList<Direction>();
		this.cout = new ArrayList<List<double>>();
	}
	
	public void initialiserDjikstra() {

	}
	
	public double tempsNecessaireUnit(Case dest) {
		return robot.getVitesse(dest.getNature());
	}

	public boolean compareTempsNecessaire(Case current, Direction dir1, Direction dir2) {
		return (tempsNecessaireUnit(carte.getVoisin(current, dir1)) >= tempsNecessaireUnit(carte.getVoisin(current, dir2)));
	}
	/**
	 * 
	 * @return
	 */
	
	@Override
	public void calculPlusCourtChemin() {
		Case predecesseur;
		Case current = robot.getPosition(); 
		
		if (robot.peutSeDeplacer(carte.getVoisin(current, directionMouvement.get(0)).getNature()) || 
				robot.peutSeDeplacer(carte.getVoisin(current, directionMouvement.get(1)).getNature())) {
			if (compareTempsNecessaire(current, directionMouvement.get(0), directionMouvement.get(1)) ) {
				chemin.add(directionMouvement.get(0));
			} else {
				chemin.add(directionMouvement.get(1));
			}
		} else if (robot.peutSeDeplacer(carte.getVoisin(current, dir)))
		
		
		do {
			if (robot.peutSeDeplacer(carte.getVoisin(current, directionMouvement.get(0)).getNature()) || 
					robot.peutSeDeplacer(carte.getVoisin(current, directionMouvement.get(1)).getNature())) {
				
			} else if ( robot.peutSeDeplacer(nature))
			if (compareTempsNecessaire(current, directionMouvement.get(0), directionMouvement.get(1))) {
				current = carte.getVoisin(current, directionMouvement.get(0))
						
				chemin.add()
			}
		} while (current != this.dest && current != robot.getPosition());
	}
	
	@Override
	public void execute() throws DehorsDeLaFrontiereException {
		throw new DehorsDeLaFrontiereException("Le robot est sorti de la carte dans la direction ");
	}

}
