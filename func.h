#ifndef FUNC
#define FUNC

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "sud.h"

#define st structure

void getBoxesPossibleValues(myStruct *structure);
myStruct *createSon(myStruct *structure, int pos, char fig);
void getFileValues(char *game, char *fileName);

#endif
