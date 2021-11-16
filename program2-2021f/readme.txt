# To run all the test cases in batch, enter the following command at the prompt.
#	% sh readme.txt

JAVA_OPTS="-Xms1024m -Xmx4096m"

java MainBst 10words.txt 10words.txt > 10words.out

java MainBst 1000words.txt 2000words.txt > 1000words.out **

java MainBst 1000words.txt 2000words2.txt > 1000words2.out

java ${JAVA_OPTS} MainBst sawyer.txt sawyer.txt > sawyer.out

java ${JAVA_OPTS} MainBst sawyer.txt mohicans.txt > sawyer-mohicans.out

java ${JAVA_OPTS} MainBst mohicans.txt mohicans.txt > mohicans.out

java ${JAVA_OPTS} MainBst mohicans.txt sawyer.txt > mohicans-sawyer.out

