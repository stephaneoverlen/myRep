% Calcul de Pi
% Etape 1 : Choix de la méthode à utiliser
% Etape 2 : Application de la méthode choisie
% Etape 3 : Réduire les valeurs à [0, 1]
% Etape 4 : Calculer le rapport "valeurs <= 1 / valeurs totales"
% Etape 5 : Calculer pi

function calcPi()
    
    format long;                                        % afficher les décimales
    
    Inferieures = 0;                                    % variable incrémentée lorsque x²+y² <= 1
    
    % Affichage des choix :
    disp('1- Générateur congruentiel linéaire');
    disp('2- Stoll-Kirckpatrick');
    disp('3- rand() de Matlab');
    
    Methode = input('Choisir une méthode : ');          % l'utilisateur choisit la méthode
    N = input('Nombre de valeurs desirees : N = ');     % puis le nombre d'itérations
    
    if Methode == 1                                     % méthode 1 : générateur congruentiel linéaire
        % Affichage de l'équation :
        disp('Equation : Xi+1 = ((A * Xi) + C) % M');

        % Entrée des paramètres par l'utilisateur :
        A  = input('Déterminez A = ');
        C  = input('Déterminez C = ');
        M  = input('Déterminez M = ');
        X0 = input('Déterminez le germe : X0 = ');
        
        N *= 2;                                         % valeurs pour x et y

        Y(1) = mod(((A * X0) + C), M);                  % calcul de la première valeur de Y
        
        for I = 2 : N                                   % de 2 a N :
            Y(I) = mod(((A * Y(I-1)) + C), M);          % appliquer la formule à la valeur précédente de Y
        endfor                                          % et stocker le résultat dans le vecteur Y

    elseif Methode == 2
        Valeurs = importdata('aleatoire250.txt', ' ');  % récupération des valeurs du fichier "aleatoire250.txt" dans "Valeurs"
        ValBin = de2bi(Valeurs);                        % conversion des valeurs en binaire
        for I = 1 : N                                                   % pour chaque itération
            for K = 1 : 16                                              % pour chaque bit
                ValBin(250+I, K) = xor(ValBin(I, K), ValBin(I+147, K)); % application de la formule "Xi+1 = Xi-249 xor Xi-102"
            endfor
        endfor
        Y = bi2de(ValBin);                                              % conversion binaire -> décimale
        
    elseif Methode == 3                         % méthode 3 : rand()
        N *= 2;                                 % valeurs pour x et y
        for I = 1 : N                           % de 1 à N :
            Y(I) = rand();                      % Y est rempli de valeurs aléatoires
        endfor
        
    else                                        % méthode inexistante
        disp('Choix incorrect.')
        return
    endif
    
    for I = 1 : N/2                             % pour la moitié des itérations

        while Y(I) > 1                          % tant que Y(I) > 1
            Y(I) /= 10;                         % division par 10
        endwhile
        Z(1, I) = Y(I);                         % la première colonne de Z récupère Y(1 : N/2)
                                                % (correspond à x)
        while Y(I + N/2) > 1                    % tant que Y(I + N/2) > 1
            Y(I + N/2) /= 10;                   % division par 10
        endwhile
        Z(2, I) = Y(I + N/2);                   % la deuxième colonne de Z récupère Y((N/2 + 1) : N)
                                                % (correspond à y)
        Carre = power(Z(1, I), 2) + power(Z(2, I), 2);  % Carre = x²+y²
        
        if Carre <= 1                           % si Carre <= 1
            Inferieures++;                      % on incrémente la variable Inferieures
        endif
    endfor
    
    Pi = 4 * Inferieures / (N/2)                % Pi = 4 * valeurs inférieures à 1 / nombre d'itérations choisi par l'utilisateur
    
endfunction
