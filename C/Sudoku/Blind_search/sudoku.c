/**
 * @file    sudoku.c
 * @author  Stephane Overlen
 * 
 * @brief   Sudoku resolving
 * @date    decembre 3rd, 2016
 * 
 * @usage   ./sudoku fileName
 * @info    As defined in func.h, "structure" will sometimes be called "st"
 **/

#include "sudoku.h"

/**
 * @function main
 * @brief   Entry Point, define first structure, get values from a file, print the game and look for values to add.
 * 
 * @param1  argc: count of total command line arguments 
 * @param2  argv: array of character string of each command line argument
 * 
 * @return  0
 **/
int main(int argc, char **argv){

    float time;
    clock_t t1, t2;
    t1 = clock();

    if(argc != 2){
        printf("File name missing.\nUsage: %s fileName.\nExiting program.\n", argv[0]);
        exit(0);
    }

    myStruct *structure = malloc(sizeof(myStruct));
    structure->game = malloc(81 * sizeof(char));
    structure->possibilities = malloc(81 * sizeof(Element));
    structure->value = -1;
    structure->father = NULL;
    
    for(int i = 0; i < 81; i++){                                // initialize the structure table
        strcpy(structure->possibilities[i].box, "1234567890");  // all values are possible
    }
    
	getFileValues(structure->game, argv[1]);	                    // "game" contains the grid to be filled

    gamePrint(structure->game, "Beginning:");
    structure = getBoxesPossibleValues(structure, &number);     // "structure" gets the grid filled
    gamePrint(structure->game, "Ending:");
    
    t2 = clock();                                               // ending time
    time = (float)(t2-t1)/CLOCKS_PER_SEC;                       // calculate time
    printf("time = %f ms\n%d states created\n", time*1000, number);
    
    return 0;
}
