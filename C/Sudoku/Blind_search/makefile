GCC=gcc -Wall -std=c99 -lm -lpthread

sudoku: sudoku.o func.o sud.o
	$(GCC) $^ -o $@

sudoku.o: sudoku.c
	$(GCC) $< -c

func.o: func.c func.h
	$(GCC) $< -c

sud.o: sud.c sud.h
	$(GCC) $< -c

clean:
	rm -f *.o sudoku
	rm -f *.o func
	rm -f *.o sud
