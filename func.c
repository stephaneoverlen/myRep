#include "func.h"

myStruct *createSon(myStruct *structure, int pos, char fig){

    st->value = fig;                                        // save the value put in "game[pos] = fig"

    char *game = malloc(81 * sizeof(char));              // define a new game
    Element *possibilities = malloc(81 * sizeof(Element));
    
    strcpy(game, st->game);
    game[pos] = fig;

    myStruct *structSon = malloc(sizeof(structure));        // create a son
//    structSon->id = st->id+1;
    structSon->game = game;
    structSon->value = 0;
    structSon->father = structure;
    
    for(int i = 0; i < 81; i++){
        strcpy(possibilities[i].box, st->possibilities[i].box);
        possibilities[i].count = st->possibilities[i].count;
    }

    structSon->possibilities = possibilities;

printf("On remplit la case %d avec %c\n", pos, structSon->game[pos]);
    return structSon;
}

void getBoxesPossibleValues(myStruct *structure){

    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");
    printf("********************************************************************************\n");

    int i = 0,
        j = 0,
        figure = 0,
        countMax = 8;

    while(1){
        getBoxAndCount(structure);
        
        i = 0;
        while(i < 81 && st->possibilities[i].count != countMax)    i++; // look for count = 8
        
        if(i == 81){                                                    // if no count = 8 found
            countMax--;                                                 // decrease count
printf("Pas de count = 8, countMax = %d\n", countMax);
            if(countCount(st->possibilities) == 0){                     // if countMax negative
printf("@structure=%p, @pere=%p, @papy=%p\n", st, st->father, st->father->father);
printf("Aucune possibilite, retour au pere\n");
                structure = st->father;                                 // get back to the father
printf("@structure=%p, @father=%p\n", st, st->father);
                countMax = 8;                                           // reset countMax
            }
        }
        
        else if(st->possibilities[i].count == 8){                       // if count = 8 found :
printf("count = 8 dans la case %d\n", i);                               // i = number of the box found :
            figure = 0;                                                 // initialize "figure"
            while(st->possibilities[i].box[figure] == 48)   figure++;   // look for the figure to put in the box
            st->game[i] = figure + 49;                                  // fill the box
printf("on remplit la case %d avec %d\n", i, figure+1);
        }
        
        else{                                                                               // if count < 8 :
printf("countMax = %d < 8\n", st->possibilities[i].count);
            j = 0;
            while(1){                                                                       // tant qu'un fils n'a pas été créé
sleep(1);
printf("case %d contient countMax = %d\n", i, countMax);                                    // i = numéro de la case trouvée :
                while(st->possibilities[i].box[j] == 48 && j < 9){
                    j++;
                }                                                   // j = chiffre à ajouter 
                
                if(j >= 9){                                         // si tous les chiffres ont été testés
printf("@father=%p\n", st->father);
printf("Tous les chiffres ont ete testes, retour au pere\n");
                    structure = st->father;                         // get back to the father
printf("@structure=%p, @father=%p\n", st, st->father);
                    break;
                }
                
//printf("si j=%c > st->value=%c, on crée un fils: \n", j+49, st->value);
                else if(st->value < j+49){                                              // s'il reste des chiffres à tester et si j est supérieur à la dernière valeur testée
printf("@structure=%p\n", st);
                    structure = createSon(structure, i, st->possibilities[i].box[j]);   // creation d'un fils avec game[i] = st->possibilities[i].box[j]
printf("@structure=%p, @pere=%p\n", st, st->father);
if(NULL != st->father->father)  printf("@papy=%p\n", st->father->father);
printf("Creation du fils avec game[%d] = %c\n", i, st->possibilities[i].box[j]);
                    countMax = 8;                                                       // initialize countMax
                    break;                                                              // leave the loop
                }
                else{
                    j++;
                }
            }
        }

        gamePrint(structure);                       // print the game
//        countPrint(st->possibilities);
        if(emptyCount(st->game) == 0)   break;      // if game is over, quit the while loop
        
        printf("\n*******************************************************************************\n");
    }
}

void getFileValues(char *game, char *fileName){

    int i = 0;
    FILE* file = NULL;
    
    file = fopen("sudoku.txt", "r");
    
    if (file != NULL){
        for(i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                fscanf(file, "%c ", &game[(i*9)+j]);
            }
        }
        
        fclose(file);
    }
}
