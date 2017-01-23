OBJ=sym.class parser.class Lexer.class
CUP=sym.java
JAVA=javac
EXE=Main.java
LIB=java-cup-11b.jar


all: Main.class $(OBJ)

Main.class: $(OBJ) Main.java
	$(JAVA) -classpath  $(LIB):. $(EXE)
	

Lexer.java: 
	jflex monflex.flex


%.class: %.java
	$(JAVA) -classpath  $(LIB):. $<
	
$(CUP): moncup.cup
	java -jar $(LIB) moncup.cup

%.java: $(CUP)
	

clean:
	rm -rf $(OBJ) $(CUP) Lexer.java parser.java *.class
