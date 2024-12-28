package model;

import java.util.ArrayList;

public class Cout {

	private ArrayList<Ressources> listRessources;

	/**
	 * Constructeur par défaut.
	 * Initialise une liste vide de ressources.
	 */
	public Cout() {
		this.listRessources = new ArrayList<>();
	}

	/**
	 * Constructeur avec une liste de ressources.
	 *
	 * @param lr Une liste initiale de ressources.
	 */
	public Cout(ArrayList<Ressources> lr) {
		if (lr == null) {
			throw new IllegalArgumentException("La liste de ressources ne peut pas être nulle.");
		}
		this.listRessources = new ArrayList<>(lr);
	}

	/**
	 * Getter pour la liste des ressources.
	 *
	 * @return La liste des ressources.
	 */
	public ArrayList<Ressources> getListRessources() {
		return new ArrayList<>(listRessources);
	}

	/**
	 * Ajoute une ressource à la liste.
	 *
	 * @param ressource La ressource à ajouter.
	 */
	public void addRessource(Ressources ressource) {
		if (ressource == null) {
			throw new IllegalArgumentException("La ressource ne peut pas être nulle.");
		}
		listRessources.add(ressource);
	}

	public boolean canPayWithRessource(Ressources ressource) {
		for (Ressources ress : listRessources) {
			if (ress.getType().equals(ressource.getType()))
				return true;
		}
		return false;
	}
}
