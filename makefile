JC = javac
JFLAGS = -d build/ -classpath build/

all:
	rm -rf build/exec/
	rm -rf build/store/
	$(JC) $(JFLAGS) keystore.java
	$(JC) $(JFLAGS) valuestore.java
	$(JC) $(JFLAGS) query.java
	$(JC) $(JFLAGS) parser.java


clean :
	rm -rf build/exec/
	rm -rf build/store/