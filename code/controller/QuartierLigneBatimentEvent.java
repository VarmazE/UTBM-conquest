package controller;

import javafx.event.Event;
import model.Batiment;
import model.Ligne;
import model.Quartier;

public class QuartierLigneBatimentEvent extends Event {
    private final Quartier quartier;
    private final Ligne ligne;
    private final Batiment batiment;

    public QuartierLigneBatimentEvent(Quartier quartier, Ligne ligne, Batiment batiment) {
        super(Event.ANY);
        this.quartier = quartier;
        this.ligne = ligne;
        this.batiment = batiment;
    }

    public Quartier getQuartier() {
        return quartier;
    }

    public Ligne getLigne() {
        return ligne;
    }

    public Batiment getBatiment() {
        return batiment;
    }
}
