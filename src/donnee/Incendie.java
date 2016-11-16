package donnee;

public class Incendie {
	
	private Case localisation;
	
	/**
	 * intensite = nombre de litres d'eau necessaire pour eteindre l'incendie
	 */
	private int intensite;
	
	public Incendie(Case localisation, int intensite) {
		this.localisation = localisation;
		this.intensite = intensite;
	}

	public Case getLocalisation() {
		return localisation;
	}

	public int getIntensite() {
		return intensite;
	}
	
	public void setIntensite(int intensite) {
		if (intensite > 0)
			this.intensite = intensite;
	}
	
        /**
         * Simule le versement d'une certaine quantité d'eau
         * @param vol le volume d'eau à déverser
         */
	public void eteindreIncendie(int vol) {
		if (vol > this.intensite)
			this.intensite = 0;
		else
			this.intensite -= vol;
	}
        
        public boolean getEtatIncendie() {
            if (intensite <= 0) {
                return false; 
            } else {
                return true;
            }
        }
	
}
