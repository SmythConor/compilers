cp LexicalAnalyser.jj ../../../bin/
cd ../../../bin/
javacc LexicalAnalyser.jj

if [ $? -eq 0 ]; then
	javac *java
fi
