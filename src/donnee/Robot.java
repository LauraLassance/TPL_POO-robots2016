package donnee;

public abstract class Robot {
	private Case position;
	private NatureTerrain type;
	private int volumeEauReservoir;

	public Case getPosition() {
		return this.position;
	}
	
	public void setPosition(Case posic) {
		if (posic != null)
			this.position = posic;
	}
	
	public abstract double getVitesse(NatureTerrain nature);
	
	public void deverserEau(int vol) {
		this.volumeEauReservoir -= vol; 
	}
	
	public abstract void remplirReservoir();
}
