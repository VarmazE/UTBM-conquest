import java.util.List;

public class Bonus {

	private boolean estUtilise;
	// Recompense associée au Bonus
	private Recompenses recompenses;
	// Liste des batiments à construire pour debloquer
	private List<Integer> batimentsRequis;

	public Bonus(Recompenses recompenses, int indiceBat1, int indiceBat2) {
		this.estUtilise = false;
		this.recompenses = recompenses;
		this.batimentsRequis = List.of(indiceBat1, indiceBat2);
	}

	// Getter
	public boolean isEstUtilise() {
		return estUtilise;
	}

	public Recompenses getRecompenses() {
		return recompenses;
	}

	public List<Integer> getBatimentsRequis() {
		return batimentsRequis;
	}

	// Setter


	public void setEstUtilise(boolean estUtilise) {
		this.estUtilise = estUtilise;
	}


}
