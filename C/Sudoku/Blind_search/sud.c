/**
 * @ProgramEntryPoint sudoku.c
 * @file    sud.c
 * @author  Stephane Overlen
 * 
 * @date    decembre 3rd, 2016
 **/

#include "sud.h"

/**
 * @function boxPrint
 * @brief   Enable developpers to watch the evolutions of the possible values in every boxes
 * 
 * @param   possibilities: elements containing every "box"
 **/
void boxPrint(Element *possibilities){

    for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
            for(int k = 0; k < 9; k++){
                printf("%2c", possibilities[i*9+j].box[k]);
            }
            printf(" ");
        }
        printf("\n");
    }
}


/**
 * @function gamePrint
 * @brief   Enable developpers to print the game
 * 
 * @param1  game: game's values
 * @param2  msg: message to be printed above the game
 **/
void gamePrint(char *game, char *msg){
    printf("%s\n", msg);
    printf("-------------------------\n");
    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            printf("| ");
            for(int k = 0; k < 3; k++){
                for(int l = 0; l < 3; l++){
                    printf("%c ", game[i*27+j*9+3*k+l]);
                }
                printf("| ");
            }
            printf("\n");
        }
        printf("-------------------------\n");
    }
    printf("\n");
}


/**
 * @function getValue
 * @brief   Get the value to put in the box
 * 
 * @param1  structure: current structure
 * @param2  num: number of current box
 * 
 * @return  value: value to put in the box
 * @return  -1: invalid value
 **/
int getValue(myStruct *structure, int num){
    
    int value = 0,
        pos = 0;
        
    for(int i = 0; i < 9; i++){                                     // for 9 iterations
        pos = (num/9)*9+i;                                          // calculate position of each box in the raw
        if(st->game[pos] > 48 && st->game[pos] < 58){               // if there is a figure in the box
            st->possibilities[num].box[st->game[pos]-49] = 48;      // discard the figure from possibilities
        }
        
        pos = (num%9)+(9*i);                                        // calculate position of each box in the column
        if(st->game[pos] > 48 && st->game[pos] < 58){               // if there is a figure in the box
            st->possibilities[num].box[st->game[pos]-49] = 48;      // discard the figure from possibilities
        }
    
        pos = ((num/27)*27)+(((num%9)/3)*3)+((i/3)*9)+(i%3);        // calculate position of each box in the square
        if(st->game[pos] > 48 && st->game[pos] < 58){               // if there is a figure in the box
            st->possibilities[num].box[st->game[pos]-49] = 48;      // discard the figure from possibilities
        }
    }

    value = st->value + 1;
    
    while(value < 9 && st->possibilities[num].box[value] == 48) value++;    // look for next value to test
    if(value < 9) return value;                                             // if found, return the value
    else return -1;                                                         // else, return -1
}


/**
 * @function getFileValues
 * @brief   get the values put in a file
 * 
 * @param1  game: game's grid
 * @param2  fileName: name of the file
 **/
void getFileValues(char *game, char *fileName){

    int i = 0;
    FILE* file = NULL;
    
    file = fopen(fileName, "r");                        // open the file
    
    if (file != NULL){
        for(i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                fscanf(file, "%c ", &game[(i*9)+j]);    // put the values in "game"
            }
        }
        
        fclose(file);                                   // close the file
    }
    
    else{
        printf("File Error.\nExiting Program.\n");
        exit(0);
    }
}
