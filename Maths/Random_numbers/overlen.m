% Gérération de nombres aléatoires
% >> overlen
% Etape 1 : choix de la méthode
% Etape 2 : appel de la fonction correspondante

function overlen()                                          % début du programme
    
    methode = 0;                                            % initialisation de la méthode choisie par l'utilisateur
    J = 0;                                                  % variable permettant l'affichage de plusieurs figures
    
    while 7 != methode                                      % tant que l'utilsateur ne choisit pas "7" :
        
        disp('----------------------------------------')    % l'utilisateur choisit la méthode de calcul
        disp('1)Algorithme K')
        disp('2)Nombres pseudo-aleatoires par congruence lineaire')
        disp('3)Nombres pseudo-aleatoires par congruence lineaire avec calcul de la periode')
        disp('4)Visualisation de 12000 nombres aleatoires en 2D et 3D')
        disp('5)Generateur de Stoll-Kirckpatrick')
        disp('6)Calcul de pi')
        disp('7)Quitter')
        disp('----------------------------------------')

        % Choix de la méthode par l'utilisateur :
        methode = input('Entrez le numero de la methode que vous souhaitez utiliser : ');
        
        switch methode                                      % selon la méthode choisie :
            case 1
                algorithmeK()                               % exécuter la méthode 1 : l'algorithme K
            case 2
                congruenceLineaire()                        % exécuter la méthode 2 : générateur à congruence linéaire
            case 3
                congruenceLineaireAvecPeriode()             % exécuter la méthode 3 : générateur à congruence linéaire avec période
            case 4
                J++;                                        % incrémenter le numéro de figure
                visu3D(J)                                   % exécuter la méthode 4 : visualisation 2D et 3D de 12'000 nombres
            case 5
                J++;                                        % incrémenter le numéro de figure
                stollK(J)                                   % exécuter la métode 5 : générateur de Stoll-Kirckpatrick
            case 6
                calcPi()                                    % exécuter la méthode 6 : calcul de pi
            case 7
                return                                      % quitter le programme
            otherwise
                disp('Choisissez une methode existante.')
        endswitch
    endwhile
endfunction
