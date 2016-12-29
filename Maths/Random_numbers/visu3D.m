% Générateur de nombres aléatoires RANDU développé par IBM
% Etape 1 : Définition des paramètres
% Etape 2 : Application de l'équation
% Etape 3 : Affichage des courbes 2D et 3D

function visu3D(J)

    % Paramètres prédéfinis :
    N  = 12000
    A  = 65539
    C  = 0
    M  = power(2, 31)
    X0 = 123456789

    Y(1) = X0;                              % "Y" est un vecteur dont la première valeur est "X0"
    
    for I = 2 : N                           % de 2 a "N" :
        Y(I) = mod(((A * Y(I-1)) + C), M)  % appliquer la formule la valeur précédente de "Y"
    endfor                                  % et stocker le résultat dans le vecteur "Y"
    
    affichage(N, Y, J)                      % afficher les courbes 2D et 3D
endfunction
