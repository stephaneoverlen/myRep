#ifndef FUNC
#define FUNC

#include <string.h>
#include "sud.h"

#define st structure

int getBoxesPossibleValues(myStruct *structure);               // get the values to put in every boxes
myStruct *createSon(myStruct *structure, int pos, char fig);    // create a son with a new value to test if it can lead to the solution
void getFileValues(char *game, char *fileName);                 // get the values put in a file

#endif
