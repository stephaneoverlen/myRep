% Générateur de Stoll-Kirckpatrick
% Etape 1 : Récupération des valeurs du fichier "aleatoire250.txt"
% Etape 2 : Calculer la moyenne des valeurs du fichier
% Etape 3 : Calculer "Y" avec l'équation "Y = Xi-249 xor Xi-102"
% Etape 4 : Afficher les résultats

function stollK(J)
    
    N = 300000;                                                         % nombres aléatoires générés

    Valeurs = importdata('aleatoire250.txt', ' ');                      % récupération des valeurs du fichier "aleatoire250.txt" dans "Valeurs"
    Len = length(Valeurs) + 1;                                          % nombre de valeurs dans "Valeurs"
    
    if Len < 251                                                        % afficher un message si trop peu de valeurs
        disp('Le fichier ne contient pas suffisament de valeurs (>= 250)')
    else                                                                % si suffisament de valeurs
        ValBin = de2bi(Valeurs);                                        % conversion des valeurs en binaire

        for I = 1 : N                                                   % pour chaque itération
            for K = 1 : 16                                              % pour chaque bit
                ValBin(L+I, K) = xor(ValBin(I, K), ValBin(I+147, K));   % application de la formule "Xi+1 = Xi-249 xor Xi-102"
            endfor
        endfor
        
        ValDec = bi2de(ValBin);                                         % conversion des valeurs binaires en décimales
        
        affichage(N, ValDec, J)                                         % affichage en 2D et 3D du résultat
        
        % Calcul des moyennes théoriques et pratiques
        disp(['Moyenne theorique des nombres aleatoires generes = ', num2str(mean(Valeurs))])
        disp(['Moyenne pratique  des nombres aleatoires generes = ', num2str(mean(ValDec))])
    endif
endfunction
