% Méthode de Simpson
% >> calculSimpson(borne inférieure, borne supérieure, taille des subdivisions)
% Etape 1 : appliquer la fonction aux bords et point milieu de chaque subdivision
% Etape 2 : cumuler les aires pour N puis 2*N subdivisions (nécessaires au calcul de convergence)
% Etape 3 : afficher les aires, erreur et valeur de convergence de la fonction

function var = calculSimpson(a, b, n)                               % Départ de la fonction
    disp('Méthode de Simpson :')
    x = a : n : b;                                                  % x = vecteur des valeurs de chaque subdivision
    y = fonction(x);                                                % récupération et application de la fonction y = f(x)

    n2 = 2 * n;                                                     % n2 = 2 * taille d'une subdivision
    n4 = 4 * n;                                                     % n4 = 4 * taille d'une subdivision
    aire = 0;                                                       % initialiser aire (pour N subdivisions)
    aireConv = 0;                                                   % initialiser aire (pour 2*N subdivisions)
    i = 1;                                                          % variable d'incrémentation

    for j = a : n4 : b-n4                                           % pour chaque subdivision
        aire += ((y(i) + (4 * y(i+2)) + y(i+4)) * n4) / 6;          % cumuler les aires calculées (pour N subdivisions)
        
        for k = 0 : 1                                               % pendant 2 itérations
            aireConv += ((y(i) + (4 * y(i+1)) + y(i+2)) * n2) / 6;  % cumuler les aires calculées (pour 2*N subdivisions)
            i += 2;                                                 % incrémenter i
        end
    end
    
    aireExacte = quadv("fonction",a,b);                             % intégration exacte (plus précis que la fonction "quad")
    err = abs((aireExacte - aire) / aireExacte) * 100;              % calculer l'erreur de la méthode choisie
    converge = abs((aire - aireConv) / aireConv);                   % calculer le résultat du calcul de convergence
    
    affichage(aire, aireExacte, err, converge);                     % affichage des résultats
end
