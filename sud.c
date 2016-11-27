#include "sud.h"

int countCount(Element *possibilities){
    
    int count = 0;

    for(int i = 0; i < 81; i++){
        if(possibilities[i].count < 9) count++;
    }
    
printf("count = %d\n", count);
    return count;
}

int emptyCount(char *game){
    int emptyBoxes = 0;

    for(int i = 0; i < 81; i++){
        if(game[i] < 49 || game[i] > 57){
            emptyBoxes++;
        }
    }
    
    return emptyBoxes;
}

void countPrint(Element *possibilities){

//Afficher les count
for(int i = 0; i < 9; i++){
    for(int j = 0; j < 9; j++){
        if(possibilities[i*9+j].count < 9)
            printf("%2d ", possibilities[i*9+j].count);
        
        else
            printf(" - ");
        }
        
        printf("\n");
        }
        
    printf("\n");
}

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

void gamePrint(myStruct *structure){
    printf("\n-------------------------\n");
    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            printf("| ");
            for(int k = 0; k < 3; k++){
                for(int l = 0; l < 3; l++){
                    printf("%c ", st->game[i*27+j*9+3*k+l]);
                }
                printf("| ");
            }
            printf("\n");
        }
            printf("-------------------------\n");
    }
    printf("\n");
}

void getBoxAndCount(myStruct *structure){

    int i, j, k, l, m, n;

    for(i = 0; i < 9; i++){                                                     // for each line
        for(j = 0; j < 9; j++){                                                 // for each column
            if(st->game[i*9+j] > 48 && st->game[i*9+j] < 58){                   // if there is a figure
                st->possibilities[i*9+j].count = 9;                             // count will never be < 9 for boxes contaning a figure
                for(k = 0; k < 9; k++){                                         // for the whole line
                    if(st->possibilities[i*9+k].box[st->game[i*9+j]-49] != 48){ // if the number is already discarded from the possibilities
                        st->possibilities[i*9+k].box[st->game[i*9+j]-49] = 48;  // set the corresponding state to 0
                        st->possibilities[i*9+k].count++;                       // increase count
                    }
                }
            }
        }
    }
    
    for(i = 0; i < 9; i++){                                                     // for each column
        for(j = 0; j < 9; j++){                                                 // for each line
            if(st->game[i+j*9] > 48 && st->game[i+j*9] < 58){                   // if there is a figure
                for(k = 0; k < 9; k++){                                         // for the whole column
                    if(st->possibilities[i+k*9].box[st->game[i+j*9]-49] != 48){ // if the number is not already discarded from the possibilities
                        st->possibilities[i+k*9].box[st->game[i+j*9]-49] = 48;  // set the corresponding state to 0
                        st->possibilities[i+k*9].count++;                       // increase count
                    }
                }
            }
        }
    }

    for(m = 0; m < 9; m += 3){
        for(n = 0; n < 9; n += 3){
            for(i = 0; i < 3; i++){
                for(j = 0; j < 3; j++){
                    if(st->game[m*9+n+i*9+j] > 48 && st->game[m*9+n+i*9+j] < 58){                       // if there is a figure
                        for(k = 0; k < 3; k++){                                                         // for the whole square
                            for(l = 0; l < 3; l++){
                                if(st->possibilities[m*9+n+k*9+l].box[st->game[m*9+n+i*9+j]-49] != 48){ // if the number is not already discarded from the possibilities
                                    st->possibilities[m*9+n+k*9+l].box[st->game[m*9+n+i*9+j]-49] = 48;  // set the corresponding state to 0
                                    st->possibilities[m*9+n+k*9+l].count++;                             // increase count
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
