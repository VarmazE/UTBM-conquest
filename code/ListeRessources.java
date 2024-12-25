public class ListeRessources {

	private static final char AVAILABLE = 'a';
	private static final char OWNED = 'o';
	private static final char USED = 'u';

	private final int RESSOURCES_SIZE = 18;

	private char[] ressources;

	private String type;

	public ListeRessources(String c) {
		this.type = c;
		ressources = new char[RESSOURCES_SIZE];

		// Initialize all resources to AVAILABLE
		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			ressources[i] = AVAILABLE;
		}

		// Assign initial resources to OWNED
		for (int i = 0; i < 3; i++) {
			ressources[i] = OWNED;
		}
	}

	public String getType() {
		return this.type;
	}

	public boolean isBonus() {
		return false;
	}

	public int calculerRessource() {
		int count = 0;
		for (char c : ressources) {
			if (c == OWNED) {
				count++;
			}
		}
		return count;
	}

	public void addRessource(Ressources r) {
		// don't forget to put the logic linked to bonhomme bonus
		int toAdd = r.getNombre();

		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			if (ressources[i] == AVAILABLE) {
				ressources[i] = OWNED;
				toAdd--;

				if (toAdd == 0) {
					break;
				}
			}
		}

	}

	public boolean RemoveRessource(Ressources r) {

		int toRemove = r.getNombre();
		int currentOwned = calculerRessource();

		if (toRemove > currentOwned) {
			return false;
		}

		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			if (ressources[i] == OWNED) {
				ressources[i] = USED;
				toRemove--;

				if (toRemove == 0) {
					break;
				}
			}
		}

		return true;
	}

}
