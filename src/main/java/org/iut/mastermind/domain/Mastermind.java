package org.iut.mastermind.domain;

import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.Reponse;
import org.iut.mastermind.domain.tirage.MotsRepository;
import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;

public class Mastermind {
    private final PartieRepository partieRepository;
    private final ServiceTirageMot serviceTirageMot;

    public Mastermind(PartieRepository pr, MotsRepository mr, ServiceNombreAleatoire na) {
        this.partieRepository = pr;
        this.serviceTirageMot = new ServiceTirageMot(mr, na);
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // s'il y a une partie en cours, on renvoie false (pas de nouvelle partie)
    // sinon on utilise le service de tirage aléatoire pour obtenir un mot
    // et on initialise une nouvelle partie et on la stocke
    public boolean nouvellePartie(Joueur joueur) {
        return partieRepository.getPartieEnregistree(joueur)
                .filter(partie -> !partie.isTerminee())
                .isEmpty() && createNewPartie(joueur);
    }

    private boolean createNewPartie(Joueur joueur) {
        String motADeviner = serviceTirageMot.tirageMotAleatoire();
        Partie nouvellePartie = Partie.create(joueur, motADeviner);
        partieRepository.create(nouvellePartie);
        return true;
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si la partie n'est pas une partie en cours, on renvoie une erreur
    // sinon on retourne le resultat du mot proposé
    public ResultatPartie evaluation(Joueur joueur, String motPropose) {
        return partieRepository.getPartieEnregistree(joueur)
                .filter(partie -> !partie.isTerminee())
                .map(partie -> calculeResultat(partie, motPropose))
                .orElse(ResultatPartie.ERROR);
    }

    // on évalue le résultat du mot proposé pour le tour de jeu
    // on, met à jour la bd pour la partie
    // on, retourne le résulat de la partie
    private ResultatPartie calculeResultat(Partie partie, String motPropose) {
        Reponse reponse = partie.tourDeJeu(motPropose);
        partieRepository.update(partie);
        return ResultatPartie.create(reponse, partie.isTerminee());
    }
}
