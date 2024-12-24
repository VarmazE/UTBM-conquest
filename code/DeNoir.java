public class DeNoir extends De {

	private boolean etat;
	
	public DeNoir(){
		super();
		this.etat=false;
	}
	public DeNoir(int x) {
		super(x);
		this.etat=false;
	}
	
	public void isActif() {
		if (this.etat==true) {
			this.etat=false;
		}else {
			this.etat=true;
		}
	}
	public boolean getEtat() {
		return this.etat;
	}
	
	public void setEtat(boolean x) {
		this.etat=x;
	}
	

}
