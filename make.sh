javacc LexicalAnalyser.jj
if [ $? -eq 0 ]; then
	if [ -d "src" ]; then
		rm -rf src/
	fi
	if [ -d "bin" ]; then
		rm -rf bin/
	fi
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
