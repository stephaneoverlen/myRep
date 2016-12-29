% Calculs d'intégrales
% >> overlen(borne inférieure, borne supérieure, nombre de subdivisions)
% Etape 1 : déterminer la taille des subdivisions en fonction des bornes d'intégration et du nombre de subdivisions
% Etape 2 : l'utilisateur choisit la méthode qu'il désire utiliser
% Etape 3 : lancement de la méthode choisie
% Etape 4 : calcul et affichage du temps de traitement

function overlen(a, b, N)                               % début du programme
    n = (b - a) / (4 * N);                              % taille des subdivisions = (xmax - xmin) / (4 * nombre de subdivisions)
    n2 = 2 * n;                                         % récupération de n2 = 2 * taille des subdivisions

    disp('----------------------------------------')    % l'utilisateur choisit la méthode de calcul
    disp('(1)Methode des rectangles (bord gauche)')
    disp('(2)Methode des rectangles (bord droit)')
    disp('(3)Methode des rectangles (point milieu)')
    disp('(4)Methode des trapezes')
    disp('(5)Methode de Simpson')
    disp('(6)Toutes les méthodes')
    disp('----------------------------------------')

    methode = input('Entrez le numero de la methode que vous voulez utiliser : ');
    tic                                                 % début de mesure du temps de traitement
    switch methode                                      % selon le choix de l'utilisateur
        case 1                                          % exécuter la méthode choisie
            calculGauche(a, b, n2)                      % Important : taille des subdivisions = (b - a) / (2 * N)
        case 2
            calculDroit(a, b, n2)                       % Important : taille des subdivisions = (b - a) / (2 * N)
        case 3
            calculMilieu(a, b, n)                       % Important : taille des subdivisions = (b - a) / (4 * N)
        case 4
            calculTrapezes(a, b, n2)                    % Important : taille des subdivisions = (b - a) / (2 * N)
        case 5
            calculSimpson(a, b, n)                      % Important : taille des subdivisions = (b - a) / (4 * N)
        case 6
            calculGauche(a, b, n2)                      % Important : taille des subdivisions = (b - a) / (2 * N)
            calculDroit(a, b, n2)                       % Important : taille des subdivisions = (b - a) / (2 * N)
            calculMilieu(a, b, n)                       % Important : taille des subdivisions = (b - a) / (4 * N)
            calculTrapezes(a, b, n2)                    % Important : taille des subdivisions = (b - a) / (2 * N)
            calculSimpson(a, b, n)                      % Important : taille des subdivisions = (b - a) / (4 * N)
        otherwise                                       % si son choix est incorrect
            disp('Methode inexistante')                 % afficher un message
            return                                      % terminer le programme
    end
    
    toc                                                 % fin de mesure et affichage du temps de traitement
end
