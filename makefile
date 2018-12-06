JFLAGS = 
JC = javac
.SUFFIXES: .java .class

.store:
	javac keystore.java
	javac valuestore.java