public class SalleProfesseurs extends Batiment {

	private Personnage Personnage;

	public SalleProfesseurs(Bonhomme recompense, Personnage personnage) {
		super(recompense);
		Personnage = personnage;
	}

	public Personnage getPersonnage() {
		return Personnage;
	}
}
