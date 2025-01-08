package model;

/**
 * La classe model.TourPenchee représente un type spécifique de bâtiment
 * avec des caractéristiques permettant la protection d'une colonne en cas de construction
 */
public class TourPenchee extends Batiment {

    /**
     * Constructeur de la classe model.TourPenchee.
     *
     * @param recompense La récompense associée à la construction de la model.TourPenchee.
     */
    public TourPenchee(Bonhomme recompense) {
        super(recompense);
    }
}
