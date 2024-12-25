public class Batiment {

	public boolean construit;

	private boolean protege;

	// ajout statut detruit
	private boolean detruit;

	private Bonhomme recompense;

	public Batiment(Bonhomme recompense) {
		this.construit = false;
		this.protege = false;
		this.detruit = false;
		this.recompense = recompense;
	}

	public Batiment construire() {
		this.construit = true;
		return this;
	}

	public void detruire() {
		this.detruit = true;
	}

	public boolean getConstruit() {
		return construit;
	}

	public void setConstruit(boolean construit) {
		this.construit = construit;
	}

	public boolean getProtege() {
		return protege;
	}

	public void setProtege() {
		this.protege = true;
	}

	public boolean getDetruit() {
		return detruit;
	}

	public void setDetruit(boolean detruit) {
		this.detruit = detruit;
	}

	public Bonhomme getRecompense() {
		return recompense;
	}

	public void setRecompense(Bonhomme recompense) {
		this.recompense = recompense;
	}
}
