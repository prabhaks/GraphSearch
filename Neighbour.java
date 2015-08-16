public class Neighbour {
	int nodeIndex;
	String name;
	int weight;
	Neighbour next;

	public Neighbour(int nodeIndex, String name, int weight, Neighbour next) {
		this.nodeIndex = nodeIndex;
		this.name = name;
		this.weight = weight;
		this.next = next;
	}

	public Neighbour(int nodeIndex, String name, int weight) {
		this(nodeIndex, name, weight, null);
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Neighbour getNext() {
		return next;
	}

	public void setNext(Neighbour next) {
		this.next = next;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
