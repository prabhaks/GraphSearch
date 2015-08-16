agent: agent.class
agent.class: Graph.java Neighbour.java Node.java
	javac Graph.java Neighbour.java Node.java
run: agent.class
	java Graph Node Neighbour	
clean:
	rm -rf *.class
