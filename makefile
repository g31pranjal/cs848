JC = javac
JFLAGS = -d build/ -classpath build/

all:
	rm -rf build/exec/
	rm -rf build/store/
	$(JC) $(JFLAGS) src/store/KeyStore.java
	$(JC) $(JFLAGS) src/store/ValueStore.java
	$(JC) $(JFLAGS) src/store/GTopology.java
	$(JC) $(JFLAGS) src/store/Graph.java
	$(JC) $(JFLAGS) src/exec/Query.java
	$(JC) $(JFLAGS) src/exec/Parser.java
	$(JC) $(JFLAGS) src/exec/Execute.java
	$(JC) $(JFLAGS) src/DatasetParser.java
	$(JC) $(JFLAGS) src/Console.java


clean :
	rm -rf build/exec/
	rm -rf build/store/