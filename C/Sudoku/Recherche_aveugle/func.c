/**
 * @ProgramEntryPoint sudoku.c
 * @file    func.c
 * @author  Stephane Overlen
 * 
 * @date    decembre 3rd, 2016
 **/

#include "func.h"

/**
 * @function createSon
 * @brief   create a son with a new value to test if it can lead to the solution
 * 
 * @param1  structure: father of the created son
 * @param2  value: last value tested in the box
 * 
 * @return  structSon: son created
 **/
myStruct *createSon(myStruct *structure, int value){

    st->value = value;                                                      // record last tested value
    myStruct *structSon = malloc(sizeof(myStruct));                         // create a son
    structSon->game = malloc(81 * sizeof(char));
    structSon->possibilities = malloc(81 * sizeof(Element));
    strcpy(structSon->game, st->game);                                      // copy the father's game in the son's game
    structSon->value = -1;                                                  // initialize the son's last tested value
    structSon->father = structure;                                          // son gets his father's adress
    
    for(int i = 0; i < 81; i++){                                            // initialize the structure table
        strcpy(structSon->possibilities[i].box, st->possibilities[i].box);
    }
    
    return structSon;
}


/**
 * @function getBoxesPossibleValues
 * @brief   get the values to put in every boxes
 * 
 * @param   structure: father of the created son
 * 
 * @return  structure: ending structure
 **/
myStruct *getBoxesPossibleValues(myStruct *structure, int *number){
    
    int value = 0,
        num = 0;
    
    while(num < 81){                                        // for each box
        if(st->game[num] <= 48 || st->game[num] >= 58){     // if there is no figure in the box
            value = getValue(structure, num);               // get the value to put in "game[num]"
            
            if(value != -1){                                // if "value" is valid
                structure = createSon(structure, value);    // create a son
                st->game[num] = value+49;                   // put the value in the game
                *number += 1;
            }
            
            else{                                           // if invalid "value"
                structure = st->father;                     // get back to the father
                value = 0;                                  // initialize "value"
                num = -1;                                   // and "num"
            }
        }
        num++;                                              // try next "num"
    }
    
    return structure;
}
