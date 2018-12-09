JC = javac
JFLAGS = -d build/ -classpath build/

all:
	# rm -rf build/exec/
	# rm -rf build/store/
	# $(JC) $(JFLAGS) src/store/btree/LElement.java
	# $(JC) $(JFLAGS) src/store/btree/Node.java
	# $(JC) $(JFLAGS) src/store/btree/INode.java
	# $(JC) $(JFLAGS) src/store/btree/LNode.java
	# $(JC) $(JFLAGS) src/store/btree/BTree.java
	# $(JC) $(JFLAGS) src/store/btree/test.java
	
	# $(JC) $(JFLAGS) src/store/topology/Result.java
	# $(JC) $(JFLAGS) src/store/topology/Neighbour.java
	# $(JC) $(JFLAGS) src/store/topology/Vertex.java
	# $(JC) $(JFLAGS) src/store/topology/GTopology.java

	# $(JC) $(JFLAGS) src/store/KeyStore.java
	# $(JC) $(JFLAGS) src/store/ValueStore.java
	# $(JC) $(JFLAGS) src/store/PropertyStore.java
	$(JC) $(JFLAGS) src/store/Graph.java
	$(JC) $(JFLAGS) src/store/GraphVanilla.java
	$(JC) $(JFLAGS) src/store/GraphBTree.java
	
	# $(JC) $(JFLAGS) src/exec/Query.java
	# $(JC) $(JFLAGS) src/exec/Parser.java
	$(JC) $(JFLAGS) src/exec/Execute.java
	
	# $(JC) $(JFLAGS) src/DatasetParser.java
	$(JC) $(JFLAGS) src/Console.java


clean :
	rm -rf build/exec/
	rm -rf build/store/