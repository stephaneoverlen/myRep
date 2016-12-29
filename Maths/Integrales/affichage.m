% Fonction dédiée à l'affichage des résultats
% >> affichage(aire calculée par la méthode choisie, aire exacte, erreur, convergence)

function var = affichage(aire, aireExacte, err, converge)   % Départ de la fonction
    disp('Aire calculee par la methode :')
    aire                                                    % afficher l'aire calculée pour N subdivisions
    
    disp('Aire exacte :')
    aireExacte                                              % intégration exacte (plus précis que la fonction "quad")
    
    disp('Erreur (%) :')
    err                                                     % afficher l'erreur de la méthode choisie
    
    disp('Calcul de convergence :')
    converge                                                % afficher le résultat du calcul de convergence
    
    disp('')                                                % retour à la ligne (affichage des résultats plus clair)
end
