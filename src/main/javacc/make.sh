BIN=../../../bin/
BASICL=BasicLAnalyser.jj
JJTREE=BasicLAnalyser.jjt
DEBUG=false

. clean.sh
if [ ! -f "$BIN" ]; then
	mkdir -p $BIN
fi

cp *.java $BIN
cp $JJTREE $BIN
cd $BIN

if [ $? -eq 0 ]; then
	while getopts ":d" opt; do
		case $opt in
			d)
				DEBUG=true
				;;
			\?)
				echo "Invalid Option -$OPTARG" >&2
				echo "To use debug use option -d"
				echo "Exiting"
				exit 1
				;;
		esac
	done

	jjtree -DEBUG_PARSER=$DEBUG $JJTREE
	
	if [ $? -eq 0 ]; then
		javacc -DEBUG_PARSER=$DEBUG $BASICL
	fi
	
	if [ $? -eq 0 ]; then
		javac *.java
	fi
fi
