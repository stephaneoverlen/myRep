#ifndef SUD
#define SUD

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define st structure

typedef struct Element Element;
struct Element
{
    char box[10];
};

typedef struct myStruct myStruct;
struct myStruct
{
    char *game;
    int value;
    Element *possibilities;
    myStruct *father;
};

void getFileValues(char *game, char *fileName);
int getValue(myStruct *structure, int num);
void gamePrint(char *game, char *msg);
void boxPrint(Element *possibilities);

#endif
