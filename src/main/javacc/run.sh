set -e
BIN=../../../bin/
BASICL=../src/main/basicL/
ANALYSER=BasicLAnalyser

cd $BIN
while getopts ":a" opt; do
	case $opt in
		a)
			java $ANALYSER $BASICL/sum_odd.bl
			if [ $? -eq 0 ]; then
				java $ANALYSER $BASICL/simple_largest.bl
			fi
			;;
		\?)
			echo "Invalid Option -$OPTARG" >&2
			echo "To run all files use option -a"
			echo "Exiting."
			exit 1
			;;
	esac
done
java $ANALYSER $BASICL/sum_primes.bl

{
cd -
} &> /dev/null
