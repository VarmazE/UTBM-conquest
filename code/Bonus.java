import java.util.ArrayList;
import java.util.List;

/**
 * La classe Bonus représente un bonus déblocable en construisant certains bâtiments adjacents.
 */
public class Bonus {

	private boolean estUtilise; // Indique si le bonus a été utilisé
	private Recompenses recompenses;
	private List<Batiment> batimentsRequis;

	/**
	 * Constructeur de la classe Bonus.
	 *
	 * @param recompenses La récompense associée au bonus.
	 * @param b1 Le premier bâtiment requis.
	 * @param b2 Le second bâtiment requis.
	 * @throws IllegalArgumentException Si l'une des récompenses ou des bâtiments est null.
	 */
	public Bonus(Recompenses recompenses, Batiment b1, Batiment b2) {
		/*if (recompenses == null || b1 == null || b2 == null) {
			throw new IllegalArgumentException("Les récompenses et les bâtiments requis ne peuvent pas être null.");
		}*/
		this.estUtilise = false;
		this.recompenses = recompenses;
		this.batimentsRequis = new ArrayList<>();
		batimentsRequis.add(b1);
		batimentsRequis.add(b2);
	}

	/**
	 * Vérifie si le bonus est disponible.
	 * Un bonus est disponible si les bâtiments requis sont construits et le bonus n'a pas encore été utilisé.
	 *
	 * @return true si le bonus est disponible, false sinon.
	 */
	public boolean isBonusAvailable() {
		if (!estUtilise && batimentsRequis.get(0).isConstruit() && batimentsRequis.get(1).isConstruit()) {
			estUtilise = true;
			return true;
		}
		return false;
	}

	/**
	 * Indique si le bonus a été utilisé.
	 *
	 * @return true si le bonus a été utilisé, false sinon.
	 */
	public boolean isEstUtilise() {
		return estUtilise;
	}

	/**
	 * Obtient la récompense associée au bonus.
	 *
	 * @return La récompense.
	 */
	public Recompenses getRecompenses() {
		return recompenses;
	}

	/**
	 * Définit une nouvelle récompense pour le bonus.
	 *
	 * @param recompenses La nouvelle récompense à associer.
	 * @throws IllegalArgumentException Si la récompense est null.
	 */
	public void setRecompenses(Recompenses recompenses) {
		/*if (recompenses == null) {
			throw new IllegalArgumentException("La récompense ne peut pas être null.");
		}*/
		this.recompenses = recompenses;
	}

	/**
	 * Obtient la liste des bâtiments requis pour débloquer le bonus.
	 *
	 * @return La liste des bâtiments requis.
	 */
	public List<Batiment> getBatimentsRequis() {
		return new ArrayList<>(batimentsRequis); // Retourne une copie pour préserver l'encapsulation
	}
}
