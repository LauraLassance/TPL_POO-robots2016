package evenement;

import donnee.Carte;
import donnee.Case;
import donnee.Direction;
import exception.DehorsDeLaFrontiereException;
import exception.PasDeCheminException;
import exception.TerrainInterditException;
import io.Simulateur;
import robot.Robot;

public class DeplacerEvenement extends Evenement {

	/** Le robot qui va se déplacer */
	private Robot robot;

	/** La destination du déplacement */
	private Direction dir;

	/** La carte où le robot va se déplacer */
	private Carte carte;

	/** Le simulateur des robots pompiers */
	private Simulateur simulateur;

	/** La case qui est la destination du déplacement */
	private Case dest;

    /**
     * Constructeur de la classe DeplacerEvenement
     * @param date la date à laquelle on place l'évenement
     * @param robot le robot utilisé pour effectuer le déplacement
     * @param dir la direction du déplacement
     * @param carte la carte sur laquelle le robot se déplace
     */

	public DeplacerEvenement(long date, Robot robot, Direction dir, Carte carte) {
		super(date);
		this.robot = robot;
		this.dir = dir;
		this.carte = carte;
		this.simulateur = null;
		this.dest = null;
	}

    /**
     *
     * @param date la date à laquelle on place l'évenement
     * @param robot le robot utilisé pour effectuer le déplacement
     * @param carte la carte sur laquelle le robot se déplace
     * @param sim le simulateur qui effectue la lecture des evenements
     * @param caseDesiree la case à laquelle on veut se déplacer
     */

	public DeplacerEvenement(long date, Robot robot, Carte carte, Simulateur sim, Case caseDesiree) {
		this(date, robot, null, carte);
		this.dest = caseDesiree;
		this.simulateur = sim;
	}

	/**
     * Méthode polymorphe des classe Evenement, elle exécute un déplacement.
     * Vers une case donnée
     * @throws DehorsDeLaFrontiereException exception levée si on sort de la carte
     * @throws TerrainInterditException exception levée si on souhaite se déplacer vers une case
     * où le robot n'a pas accés
     * @throws PasDeCheminException exception levée s'il n'existe pas de chemin
     * entre la position du robot et la case où l'on souhaite se déplacer.
     */
     @Override
	public void execute() throws DehorsDeLaFrontiereException, TerrainInterditException, PasDeCheminException {
		if (this.dir != null) {
	        Case dest = this.carte.getVoisin(this.robot.getPosition(), this.dir);
			if (dest != null)
				this.robot.seDeplacer(dest, this.carte, this.simulateur);
			else
				throw new DehorsDeLaFrontiereException("Le robot est sorti de la carte dans la direction "+this.dir);
		} else
			this.robot.seDeplacer(this.dest, this.carte, this.simulateur);
	}

}
