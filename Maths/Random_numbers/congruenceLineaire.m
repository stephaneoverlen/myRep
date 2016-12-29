% Générateur de nombres aléatoires
% Etape 1 : Entrée des paramètres par l'utilisateur
% Etape 2 : Application de l'équation pour générer les nombres aléatoires

function congruenceLineaire()

    % Affichage de l'équation :
    disp('Equation : Xi+1 = ((A * Xi) + C) % M')

    % Entrée des paramètres par l'utilisateur :
    N  = input('Combien de nombres aleatoires voulez-vous générer : N = ');
    A  = input('Déterminez A = ');
    C  = input('Déterminez C = ');
    M  = input('Déterminez M = ');
    X0 = input('Déterminez le germe : X0 = ');
    
    Y = X0;                         % "Y" prend la valeur du germe X0
    
    for I = 1 : N                   % pour chaque itération
        Y = mod(((A * Y) + C), M)   % affichage du résultat de l'équation
    endfor
endfunction
