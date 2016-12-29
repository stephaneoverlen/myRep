% Méthode des trapèzes
% >> calculTrapezes(borne inférieure, borne supérieure, taille des subdivisions)
% Etape 1 : appliquer la fonction aux bords gauches et droits de chaque subdivision
% Etape 2 : cumuler les aires des trapèzes pour N puis 2*N subdivisions (nécessaires au calcul de convergence)
% Etape 3 : afficher les aires, erreur et valeur de convergence de la fonction

function var = calculTrapezes(a, b, n)                  % Départ de la fonction
    disp('Methode des trapezes :')
    x = a : n : b;                                      % x = vecteur des valeurs de chaque subdivision
    y = fonction(x);                                    % récupération et application de la fonction y = f(x)

    n2 = 2 * n;                                         % n2 = 2 * taille d'une subdivision
    aire = 0;                                           % initialiser aire (pour N subdivisions)
    aireConv = 0;                                       % initialiser aire (pour 2*N subdivisions)
    i = 1;                                              % variable d'incrémentation

    for j = a : n2 : b-n2                               % pour chaque subdivision
        aire += ((y(i) + y(i+2)) * n2) / 2;             % cumuler les aires calculées (pour N subdivisions)
        
        for k = 0 : 1                                   % pendant 2 itérations
            aireConv += ((y(i) + y(i+1)) * n) / 2;      % cumuler les aires calculées (pour 2*N subdivisions)
            i++;                                        % incrémenter i
        end
    end
    
    aireExacte = quadv("fonction",a,b);                 % intégration exacte (plus précis que la fonction "quad")
    err = abs((aireExacte - aire) / aireExacte) * 100;  % calculer l'erreur de la méthode choisie
    converge = abs((aire - aireConv) / aireConv);       % calculer le résultat du calcul de convergence
    
    affichage(aire, aireExacte, err, converge);         % affichage des résultats
end
