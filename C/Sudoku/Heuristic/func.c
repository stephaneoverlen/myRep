/**
 * @ProgramEntryPoint sudoku.c
 * @file    func.c
 * @author  Stephane Overlen
 * @date    novembre 30th, 2016
 **/

#include "func.h"

/**
 * @function createSon
 * @brief   create a son with a new value to test if it can lead to the solution
 * 
 * @param1  structure: father of the created son
 * @param2  pos: position of the box
 * @param3  fig: value of the tested figure
 * 
 * @return  structSon: son created
 **/
myStruct *createSon(myStruct *structure, int pos, char fig){

    st->value = fig;                                        // save the tested value so we won't test it again later

    myStruct *structSon = malloc(sizeof(myStruct));         // create a son
    structSon->value = 0;
    structSon->father = structure;

    structSon->game = malloc(81 * sizeof(char));
    strcpy(structSon->game, st->game);
    structSon->game[pos] = fig;                             // add the value in the son's game

    structSon->possibilities = malloc(81 * sizeof(Element));
        
    for(int i = 0; i < 81; i++){
        strcpy(structSon->possibilities[i].box, st->possibilities[i].box);
        structSon->possibilities[i].count = st->possibilities[i].count;
    }

    return structSon;                                       // return the son
}


/**
 * @function getBoxesPossibleValues
 * @brief   get the values to put in every boxes
 * 
 * @param   structure: father of the created son
 **/
int getBoxesPossibleValues(myStruct *structure){

    int pos = 0,
        fig = 0,
        countMax = 8,
        number = 0;

    while(1){
        pos = getBoxAndCount(structure, countMax);                      // get the position of the box with the highest "count"
        
        if(pos == 81){                                                  // if position out of bounds, then every "count" is different than "countMax""
            countMax--;                                                 // decrease "countMax"
            if(countCount(st->possibilities) == 0){                     // if game stuck because no more "count" can be calculated
                structure = st->father;                                 // get back to the father
                countMax = 8;                                           // reset countMax
            }
        }
        
        else if(st->possibilities[pos].count == 8){                     // if "count = 8" found, only one possibility left:
            fig = 0;
            while(st->possibilities[pos].box[fig] == 48)   fig++;       // look for the figure to put in the box
            st->game[pos] = fig + 49;                                   // fill the box
        }
        
        else{                                                               // if "count = countMax" but "countMax < 8" :
            fig = 0;
            while(1){
                while(st->possibilities[pos].box[fig] == 48 && fig < 9){    // find a figure to add in the game
                    fig++;
                }
                
                if(fig >= 9){                                               // if all possible figures have been tested for this box
//gamePrint(st->game, "debug:");     // print the game
//sleep(1);
                    structure = st->father;                                 // game stuck, get back to the father

//gamePrint(st->game, "debug:");     // print the game
                    break;
                }
                
                else if(st->value < fig+49){                                                // if the figure to be tested is higher than last tested figure
                    structure = createSon(structure, pos, st->possibilities[pos].box[fig]); // create a son with "game[pos] = st->possibilities[pos].box[fig]"
                    number++;
                    countMax = 8;                                                           // initialize "countMax"
                    break;
                }
                else{           // if there are still figures to be tested and the figure is equal to last tested figure
                    fig++;      // try next figure
                }
            }
        }

        if(emptyCount(st->game) == 0){          // if no box is empty
            gamePrint(st->game, "Ending:");     // print the game
            break;                              // game is over.
        }
    }
    return number;
}

