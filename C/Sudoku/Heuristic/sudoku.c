/**
 * @file    sudoku.c
 * @author  Stephane Overlen
 * 
 * @brief   Sudoku resolving
 * @date    novembre 30th, 2016
 * 
 * @usage   ./sudoku fileName.txt
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
int main(int argc, char *argv[]){

    float time;
    clock_t t1, t2;
    t1 = clock();

    if(argc != 2){
        printf("Usage: %s fileName.\nExiting program.\n", argv[0]);
        exit(0);
    }

    myStruct *structure = malloc(sizeof(structure));            // define new structure
    structure->game = malloc(81 * sizeof(char));
    structure->possibilities = malloc(81 * sizeof(Element));
    structure->value = 0;
    structure->father = NULL;
    
	getFileValues(structure->game, argv[1]);	                // "game" contains the sudoku
    
    for(int i = 0; i < 81; i++){                                // initialize the structure table
        strcpy(structure->possibilities[i].box, "1234567890");  // all values are possible
        structure->possibilities[i].count = 0;                  // count is null
    }

    gamePrint(structure->game, "Beginning:");                   // print the game
    number = getBoxesPossibleValues(structure);                 // get boxes possible values
    
    t2 = clock();
    time = (float)(t2-t1)/CLOCKS_PER_SEC;
    printf("time = %f ms\n%d states created\n", time*1000, number);
    
    return 0;
} 
