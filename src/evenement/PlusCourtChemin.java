package evenement;

import java.util.ArrayList;
import java.util.List;
import donnee.Direction;

import donnee.Carte;
import donnee.Case;
import exception.DehorsDeLaFrontiereException;
import robot.Robot;

public class PlusCourtChemin extends Evenement implements CheminEvenement {
	
	private Robot robot;

	/** La carte où le robot va se déplacer */
	private Carte carte;

	/** la case destination */
	private Case dest;
	
	private List<Evenement> evenements;
	
	private List<Direction> chemin;
	
	private List<Direction> directionMouvement;
	
	public PlusCourtChemin(long date, Robot robot, Carte carte, Case dest) {
		super(date);
		this.robot = robot;
		this.carte = carte;
		this.dest = dest;
		
		this.evenements = new ArrayList<Evenement>();
		this.chemin = new ArrayList<Direction>();
		
		this.directionMouvement = new ArrayList<Direction>();
		
		if (robot.getPosition().getLigne() - dest.getLigne() > 0) {
			directionMouvement.add(0, Direction.EST);
			directionMouvement.add(2, Direction.OUEST);
		} else { 
			directionMouvement.add(0, Direction.OUEST);
			directionMouvement.add(2, Direction.EST);
		}
		
		if (robot.getPosition().getColonne() - dest.getColonne() > 0) {
			directionMouvement.add(1, Direction.NORD);
			directionMouvement.add(3, Direction.SUD);
		} else {
			directionMouvement.add(1, Direction.SUD);
			directionMouvement.add(3, Direction.NORD);
		}
	
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
