% Générateur de nombres aléatoires : l'Algorithme K
% Etape 1 : choix de la valeur d'entrée
% Etape 2 : déterminer un nombre d'itérations aléatoire
% Etape 3 : génération d'une valeur aléatoire
% Etape 4 : affichage de la valeur

function algorithmeK()
    
    X = -1;                                                         % initialisation de X
    Xmax = power(10, 6) - 1;                                        % valeur max de X

    while X < 0 || X > Xmax                                         % tant que X n'a pas la valeur désirée
        X = input('Entrez une valeur de X (0 < X < 1000000) : ');   % choix de X
        if X < 0 && X > Xmax                                        % si X n'a pas la valeur désirée
            disp('Valeur incorrecte. Recommencez :')                % afficher un message
        endif
    end
    
    Y = floor(X / 10^5);                                            % arrondir Y
    for i = 1 : Y+1                                                 % choix d’operation aléatoire
        Z = mod(floor(X / 10^4), 10);
        if Z == 1 || Z == 9
            X = mod(X^2 / 10^3, 10^6);
        elseif Z == 2 || Z == 5
            X = mod(1001001 * X, 10^6);
        elseif Z == 3                                               % inverser les chiffres
            X = (10^3 * mod(X, 10^3)) + floor(X / 10^3);
        elseif Z == 4 || Z == 8
            if X < 10^4
                X = X + 20469;
            else
                X = 10^6 - X;
            endif
        elseif (Z == 6 || Z == 7)
            if X < 5 * 10^3
                X = X + 5 * 10^3;
            endif
        endif
        X                                                           % afficher X
    endfor
end
