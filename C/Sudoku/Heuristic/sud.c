/**
 * @ProgramEntryPoint sudoku.c
 * @file    sud.c
 * @author  Stephane Overlen
 * @date    novembre 30th, 2016
 **/

#include "sud.h"

/**
 * @function countCount
 * @brief   calculates the quantity of boxes where "count" can be calculated
 * 
 * @param   possibilities: elements containing every "count"
 * 
 * @return  counts: quantity of "count" found
 **/
int countCount(Element *possibilities){

    int counts = 0;

    for(int i = 0; i < 81; i++){                    // for each box
        if(possibilities[i].count < 9) counts++;    // if "count" can be calculated, increase "counts" 
    }
    
    return counts;
}


/**
 * @function emptyCount
 * @brief   calculates the quantity of empty boxes
 * 
 * @param   game: game's values
 * 
 * @return  counts: quantity of "count" found
 **/
int emptyCount(char *game){
    int emptyBoxes = 0;

    for(int i = 0; i < 81; i++){            // for each box
        if(game[i] < 49 || game[i] > 57){   // if it contains a figure
            emptyBoxes++;                   // increase "emptyBoxes"
        }
    }
    
    return emptyBoxes;
}


/**
 * @function countPrint
 * @brief   enables developpers to watch the evolutions of each "count"
 * 
 * @param   possibilities: elements containing every "count"
 **/
void countPrint(Element *possibilities){

    for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
            if(possibilities[i*9+j].count < 9) printf("%2d ", possibilities[i*9+j].count);
            else printf(" - ");
        }
        printf("\n");
    }
    printf("\n");
}


/**
 * @function boxPrint
 * @brief   enable developpers to watch the evolutions of the possible values in every boxes
 * 
 * @param   possibilities: elements containing every "box"
 **/
void boxPrint(Element *possibilities){

    for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
            for(int k = 0; k < 9; k++){
                printf("%d", possibilities[i*9+j].box[k]);
            }
            printf(" ");
        }
        printf("\n");
    }
}


/**
 * @function gamePrint
 * @brief   enable developpers to print the game
 * 
 * @param1   game: game's values
 * @param2   msg: message to be printed above the game
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
 * @function getBoxAndCount
 * @brief   calculates the possibilities and "count"
 * 
 * @param   structure: structure containing the game and its possibilities
 * @param   countMax: maximum value of "count"
 * 
 * @return  pos: position of the box to be filled
 * @return  81: position out of bounds because no box has been found
 **/
int getBoxAndCount(myStruct *structure, int countMax){

    int i = 0,
        j = 0,
        k = 0,
        l = 0,
        m = 0,
        n = 0,
        pos = 0,
        pos2 = 0;

    for(i = 0; i < 9; i++){                                                     // for each line
        for(j = 0; j < 9; j++){                                                 // for each column
            pos = i*9+j;                                                        // calculate box's position
            if(st->game[pos] > 48 && st->game[pos] < 58){                       // if there is a figure in the box
                st->possibilities[pos].count = 9;                               // count will always be > 9 for boxes contaning a figure
                for(k = 0; k < 9; k++){                                         // for the whole line
                    pos2 = i*9+k;                                               // calculate box's position
                    if(st->possibilities[pos2].box[st->game[pos]-49] != 48){    // if the number isn't already discarded from the possibilities
                        st->possibilities[pos2].box[st->game[pos]-49] = 48;     // discard the number from the possibilities by setting corresponding "box[number]" to 0
                        st->possibilities[pos2].count++;                        // increase count
                    }
                }
            }
            if(st->possibilities[pos].count == countMax) return pos;            // if "count = countMax", return the position of the box
            
            pos = i+j*9;                                                        // calculate box's position
            if(st->game[pos] > 48 && st->game[pos] < 58){                       // if there is a figure
                for(k = 0; k < 9; k++){                                         // for the whole column
                    pos2 = i+k*9;                                               // calculate box's position
                    if(st->possibilities[pos2].box[st->game[pos]-49] != 48){    // if the number is not already discarded from the possibilities
                        st->possibilities[pos2].box[st->game[pos]-49] = 48;     // set the corresponding state to 0
                        st->possibilities[pos2].count++;                        // increase count
                    }
                }
            }
            if(st->possibilities[pos].count == countMax) return pos;            // if "count = countMax", return the position of the box
        }
    }

    for(m = 0; m < 9; m += 3){
        for(n = 0; n < 9; n += 3){
            for(i = 0; i < 3; i++){
                for(j = 0; j < 3; j++){
                    pos = m*9+n+i*9+j;                                                      // calculate box's position
                    if(st->game[pos] > 48 && st->game[pos] < 58){                           // if there is a figure
                        for(k = 0; k < 3; k++){                                             // for the whole square
                            for(l = 0; l < 3; l++){
                                pos2 = m*9+n+k*9+l;                                         // calculate box's position
                                if(st->possibilities[pos2].box[st->game[pos]-49] != 48){    // if the number is not already discarded from the possibilities
                                    st->possibilities[pos2].box[st->game[pos]-49] = 48;     // set the corresponding state to 0
                                    st->possibilities[pos2].count++;                        // increase count
                                }
                            }
                        }
                    }
                    if(st->possibilities[pos].count == countMax) return pos;                // if "count = countMax", return the position of the box
                }
            }
        }
    }
    return 81;      // return "out of bounds" position
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
}

