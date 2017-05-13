BINPATH=bin
LOGO=logo
DTDIN=lib/villes.xml.dtd
DTDPATH=dtd
DTDOUT=bin/dtd/villes.xml.dtd
PACKAGE=.
DOCPATH=javadoc
MANIFEST=manifest.mf
EXEC=cff_shell.jar
SRC=${PACKAGE}/Main.java
OBJECT=${BINPATH}/,$(SRC)
OBJ=$(subst .java,.class,$(OBJECT))
FALSE=false
TRUE=true
output=$(FALSE)

ifeq ($(verbose), true)
	output=$(TRUE)
endif

define gen_doc
	@mkdir -p $(DOCPATH)
	@if [$(1) == $(FALSE) ]; then javadoc -d $(DOCPATH) -sourcepath . -subpackages ON >> /dev/null ; else javadoc -d $(DOCPATH) -sourcepath . -subpackages ON ; fi
endef

define gen_exe
	@if [$(1) == $(FALSE) ]; then jar cvfm $(EXEC) $(MANIFEST) -C $(BINPATH) . >> /dev/null ; else jar cvfm $(EXEC) $(MANIFEST) -C $(BINPATH) . ; fi
endef

all: doc build $(EXEC)

build: $(EXEC)

run: $(EXEC)
	java -jar $(EXEC) $(param)
	
$(EXEC): $(OBJ) $(DTDOUT) $(BINPATH)/$(LOGO)
	@echo -e "Generating $@..."
	$(call gen_exe, $(output))
	@echo -e "[\033[0;32mDONE\033[0m]"

$(OBJ): $(SRC)
	@echo -e "Compiling java file(s)..."
	@mkdir -p $(BINPATH)
	@javac $< -s ON -d $(BINPATH) -sourcepath .
	@echo -e "[\033[0;32mDONE\033[0m]"


$(DTDOUT):$(DTDIN)
	@echo "Adding dtd refs..."
	@mkdir -p $(BINPATH)/$(DTDPATH)/
	@cp -rf $(DTDIN) $(DTDOUT)
	@echo -e "[\033[0;32mDONE\033[0m]"

$(BINPATH)/$(LOGO) : $(LOGO)
	@echo "Adding logos..."
	@mkdir -p $(BINPATH)/$(LOGO)
	@cp -rf $ $(LOGO)/* $(BINPATH)/$(LOGO)/
	@echo -e "[\033[0;32mDONE\033[0m]"

clean:
	@echo "Removing jar file..."
	@rm -rf $(EXEC)
	@rm -rf villes.xml.dtd
	@echo "Cleaning generated class files..."
	@rm -rf $(BINPATH)
	@echo "Cleaning docs..."
	@rm -rf $(DOCPATH)

doc:
	@echo "Generating docs..."
	$(call gen_doc, $(output))
	@echo -e "[\033[0;32mDONE\033[0m]"

