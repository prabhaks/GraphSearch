public class Node {
	String name;
	Neighbour adjacent;

	public Node(String name, Neighbour adjacent) {
		this.name = name;
		this.adjacent = adjacent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Neighbour getAdjacent() {
		return adjacent;
	}

	public void setAdjacent(Neighbour adjacent) {
		this.adjacent = adjacent;
	}
}
