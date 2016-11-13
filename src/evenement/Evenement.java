package evenement;

public abstract class Evenement implements Comparable<Evenement>{
	private long date;
	
	public Evenement(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public abstract void execute() throws Exception;
	
	@Override
	public int compareTo(Evenement o) {
		if (this.getDate() > o.getDate())
			return 1;
		
		if (this.getDate() == o.getDate())
			return 0;
		
		return -1;
	}
}
