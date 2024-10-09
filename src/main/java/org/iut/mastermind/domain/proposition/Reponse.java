// src/main/java/org/iut/mastermind/domain/proposition/Reponse.java
package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // on récupère la lettre à la position dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // on construit le résultat en analysant chaque lettre
    // du mot proposé
    public void compare(String essai) {
        for (int i = 0; i < essai.length(); i++) {
            resultat.add(evaluationCaractere(essai.charAt(i), i));
        }
    }

    // si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        if (resultat.size() == motSecret.length()) {
            for (Lettre lettre : resultat) {
                if (lettre != Lettre.PLACEE) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    // renvoie le statut du caractère
    private Lettre evaluationCaractere(char carCourant, int index) {
        if (estPlace(carCourant, index)) {
            return Lettre.PLACEE;
        } else if (estPresent(carCourant)) {
            return Lettre.NON_PLACEE;
        } else {
            return Lettre.INCORRECTE;
        }
    }

    // le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        for (int i = 0; i < motSecret.length(); i++) {
            if (i != carCourant && motSecret.charAt(i) == carCourant) {
                return true;
            }
        }
        return false;    }

    // le caractère est placé dans le mot secret
    private boolean estPlace(char carCourant, int index) {
        return motSecret.charAt(index) == carCourant;
    }
}