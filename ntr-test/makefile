# Make file for the NTR project

build:
	javac -cp bin/refactor_v2 -d bin src/refactor_v2/*.java

run:
	touch tmp.csv
	mv -b --suffix=`date +%j%H%I%S` *.csv backup/.
	java -cp bin refactor_v2/Test

clean: mrproper

mrproper:
	touch tmp.csv
	mv -b --suffix=`date +%j%H%I%S` *.csv backup/.
	rm bin/refactor_v2/*.class
