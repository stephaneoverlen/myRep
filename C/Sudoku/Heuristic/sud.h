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
    int count;
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

int getBoxAndCount(myStruct *structure, int countMax);  // calculates the possibilities and "count"
void gamePrint(char *game, char *msg);                  // enable developpers to print the game
void boxPrint(Element *possibilities);                  // enable developpers to watch the evolutions of the possible values in every boxes
void countPrint(Element *possibilities);                // enables developpers to watch the evolutions of each "count"
int emptyCount(char *game);                             // calculates the quantity of empty boxes
int countCount(Element *possibilities);                 // calculates the quantity of boxes where "count" can be calculated

#endif
