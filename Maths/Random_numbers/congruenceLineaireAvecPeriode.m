% Générateur de nombres aléatoires
% Etape 1 : Entrée des paramètres par l'utilisateur
% Etape 2 : Application de l'équation pour générer les nombres aléatoires
% Etape 3 : La variable "Restart" prend la valeur de la période

function congruenceLineaireAvecPeriode()

    Periode = 0;                                        % initialisation de "Periode" (début de la période)
    Fin = 0;                                            % initialisation de "Fin" (fin de la période)

    % Affichage de l'équation :
    disp('Equation : Xi+1 = ((A * Xi) + C) % M')

    % Entrée des paramètres par l'utilisateur :
    N  = input('Combien de nombres aleatoires voulez-vous générer : N = ');
    A  = input('Determinez A = ');
    C  = input('Determinez C = ');
    M  = input('Determinez M = ');
    X0 = input('Determinez le germe : X0 = ');
    
    Y(1) = X0;                                          % "Y" prend la valeur du germe (X0)
    
    for I = 1 : N                                       % pour chaque itération
        Y(I+1) = mod(((A * Y(I)) + C), M);              % application de l'équation, puis stockage dans le vecteur "Y"
        Y(I+1)                                          % affichage du "Y+1" précédemment calculé (enlever le ";" à la ligne précédente affiche le vecteur entier)
    endfor
    
    for K = 0 : N-1                                     % pour toutes les valeurs
        if Y(N+1) == Y(N-K)                             % si la dernière valeur du vecteur "Y" est égale au "Y" courant
            Fin = Periode + 1;                          % "Fin" prend la valeur de la période
            break                                       % on sort de la boucle "for" pour économiser les ressources de l'ordinateur
        else                                            % si la dernière valeur du vecteur "Y" est différente du "Y" courant
            Periode += 1;                               % on incrémente la période
        endif
    endfor
    
    if Fin == 0                                         % si "Fin" n'a toujours pas de valeur
        disp('*')
        disp(['* Periode > N=', num2str(N)])            % il n'y a pas assez de nombres pour obtenir une période complète
        disp('*')
    else                                                % si "Fin" a une valeur
        disp('*')
        disp(['* Periode du generateur = ', num2str(Fin)])    % afficher la période
        disp('*')
    endif
end
