#!/bin/bash
java -jar /usr/local/lib/antlr-4.5-complete.jar -no-visitor -listener -o compiler/ Ry.g4
cd compiler

javac -d dependency Ry*.java
cd dependency
jar uf0 compiler_dependency.jar *.class

cd ..

sed -i.bu '1i\
package compiler;' RyLexer.java

sed -i.bu '1i\
package compiler;' RyParser.java

sed -i.bu '1i\
package compiler;' RyBaseListener.java

sed -i.bu '1i\
package compiler;' RyListener.java

rm *.bu

