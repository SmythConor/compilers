javacc LexicalAnalyser.jj
if [ $? -eq 0 ]; then
	mkdir src/
	mkdir bin/
	cp *.java src/
	cp *.java bin/
	cd bin/
	javac *.java
	rm *.java
	cd ../
	rm *.java
fi
