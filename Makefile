.DEFAULT_GOAL = all

JAVACC_GENERATED_FILES = src/ParseException.java     \
                         src/Parser.java             \
                         src/ParserConstants.java    \
                         src/ParserTokenManager.java \
                         src/SimpleCharStream.java   \
                         src/Token.java              \
                         src/TokenMgrError.java

.SUFFIXES:
.SECONDARY:
.PHONY: all build package clean

all: build

build:
	mkdir -p out
	javacc -OUTPUT_DIRECTORY=src src/Parser.jj
	javac -d out src/*.java

package: build
	jar cmf META-INF/MANIFEST.MF parser.jar -C out/ .

clean:
	rm -f $(JAVACC_GENERATED_FILES) parser.jar
	rm -rf out
