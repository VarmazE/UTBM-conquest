public class BureauAssociation extends Batiment {

	private String couleurJeton;

	private int nombre;

	private Recompenses recompenseCase;

	public BureauAssociation(String couleurJeton, int nombre, Bonhomme recompenseBonhomme, Recompenses recompenseCase) {
		super(recompenseBonhomme);
		this.couleurJeton = couleurJeton;
		this.nombre = nombre;
		this.recompenseCase = recompenseCase;
	}

	public String getCouleurJeton() {
		return couleurJeton;
	}


	public int getNombre() {
		return nombre;
	}

	public Recompenses getRecompenseCase() {
		return this.recompenseCase;
	}

}
