#include "sudoku.h"

int main(int argc, char *argv[]){
    if(argc != 2){
        printf("File name missing.\nUsage: %s fileName.\nExiting program.\n", argv[0]);
        exit(0);
    }

    myStruct *structure = malloc(sizeof(structure));
//    structure->id = 0;
    structure->game = malloc(81 * sizeof(char));
    structure->possibilities = malloc(81 * sizeof(Element));
    structure->value = 0;
    
	getFileValues(structure->game, argv[1]);	        // "game" contains the values of the sudoku
    
    for(int i = 0; i < 81; i++){                        // initialize the structure table
        strcpy(structure->possibilities[i].box, "1234567890");             // all values are possible
        structure->possibilities[i].count = 0;                             // count is null
    }

    getBoxesPossibleValues(structure);
}
