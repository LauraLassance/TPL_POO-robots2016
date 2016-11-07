package donnee;

public class Incendie {
	
	private Case localisation;
	
	private int litresEauNecessaire;
	
	public Incendie(Case localisation, int litresEauNecessaire) {
		this.localisation = localisation;
		this.litresEauNecessaire = litresEauNecessaire;
	}

	public Case getLocalisation() {
		return localisation;
	}

	public int getLitresEauNecessaire() {
		return litresEauNecessaire;
	}
	
	public void eteindreIncendie(int vol) {
		if (vol > this.litresEauNecessaire)
			this.litresEauNecessaire = 0;
		else
			this.litresEauNecessaire -= vol;
	}
	
}
