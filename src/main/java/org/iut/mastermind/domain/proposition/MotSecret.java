package org.iut.mastermind.domain.proposition;

public class MotSecret {
    private final String mot;

    public MotSecret(String mot) {
        this.mot = mot;
    }

    // on retourne le résultat de la comparaison
    // du mot essayé avec le mot secret
    public Reponse compareProposition(String essai) {
        Reponse reponse = new Reponse(mot);
        reponse.compare(essai);
        return reponse;
    }
}
