% Générateur RANDU développé par IBM
% Etape 1 : Définition des paramètres
% Etape 2 : Affichage des courbes

function affichage(N, Y, J)
    
    A = Y(1 : N-2);                         % A est un vecteur qui récupère Y(1) a Y(N-2)
    Z = Y(2 : N-1);                         % Z est un vecteur qui récupère Y(2) a Y(N-1)
    Y = Y(3 : N);                           % Y est réduit de 2 valeurs pour avoir la meme taille que A et Z

    figure(J);                              % numéro de la figure
    
    subplot(2,1,1);                         % affichage en position 1
    plot(Z, Y)                              % affichage en 2D de Xi = f(Xi-1)
    
    subplot(2,1,2);                         % affichage en position 2
    plot3(A, Z, Y)                          % affichage en 3D de Xi = f(Xi-1, Xi-2)
end
