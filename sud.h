#ifndef SUD
#define SUD

#include <stdio.h>
#include <stdlib.h>

#define st structure

typedef struct Element Element;
struct Element
{
    int count;
    char box[10];
};

typedef struct myStruct myStruct;
struct myStruct
{
//    int id;
    char *game;                 // game
    int value;
    Element *possibilities;     // create a structure table
    myStruct *father;
};

void getBoxAndCount(myStruct *structure);
void gamePrint(myStruct *structure);
void countPrint(Element *possibilities);
void boxPrint(Element *possibilities);
int emptyCount(char *game);
int countCount(Element *possibilities);

#endif
