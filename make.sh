javacc LexicalAnalyser.jj
if [ $? -eq 0 ]; then
	mkdir src/
	cp *.java src/
fi
if [ $? -eq 0 ]; then
 mkdir bin/
 cp *.java bin/
 rm *.java
 cd bin/
 javac *.java
 rm *.java
 cd ../
fi
