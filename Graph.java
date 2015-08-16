import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	private static Node[] adjacencyList;
	private Boolean[] isNodeVisited;
	private Integer[] parent;
	private static Integer[] pathCost;
	private static String searchType;
	private String startState;
	private String goalState;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	private int no_of_states;
	private String[] log;

	private static class PQueue<N extends Node> implements Comparator<N> {
		public int compare(N o1, N o2) {
			if ("1".equals(searchType)) {
				return o1.getName().compareTo(o2.getName());
			}
			int nodeIndex1 = getNodeIndex(o1.getName());
			int nodeIndex2 = getNodeIndex(o2.getName());
			if (pathCost[nodeIndex1] == pathCost[nodeIndex2]) {
				return o1.getName().compareTo(o2.getName());
			}
			return pathCost[nodeIndex1] - pathCost[nodeIndex2];
		}
	}

	private static int getNodeIndex(String name) {
		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i].getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private void initialize() {
		log = new String[adjacencyList.length*2];
		parent = new Integer[adjacencyList.length];
		for (int j = 0; j < parent.length; j++) {
			parent[j] = -1;
		}
		pathCost = new Integer[adjacencyList.length];
		for (int j = 0; j < pathCost.length; j++) {
			pathCost[j] = 0;
		}
		isNodeVisited = new Boolean[adjacencyList.length];
		for (int j = 0; j < isNodeVisited.length; j++) {
			isNodeVisited[j] = false;
		}
	}

	private int runUCS() {
		initialize();
		Queue<Node> queue = new PriorityQueue<Node>(adjacencyList.length,
				new PQueue<Node>());
		int startIndex = getNodeIndex(startState);
		if (startIndex == -1) {
			System.err.println("Invalid input start state: " + startState);
			System.exit(1);
		}
		queue.offer(adjacencyList[startIndex]);
		isNodeVisited[startIndex] = true;
		int j = 0;
		while (!queue.isEmpty()) {
			Node n = queue.poll();
			log[j++] = n.getName();
			if (n.getName().equals(goalState)) {
				return getNodeIndex(n.getName());
			} else {
				for (Neighbour nbr = n.getAdjacent(); nbr != null; nbr = nbr
						.getNext()) {
					int nodeIndex = getNodeIndex(n.getName());
					if (!isNodeVisited[nbr.getNodeIndex()]) {
						isNodeVisited[nbr.getNodeIndex()] = true;
						parent[nbr.getNodeIndex()] = nodeIndex;
						pathCost[nbr.getNodeIndex()] = pathCost[nodeIndex]
								+ nbr.getWeight();
						queue.offer(adjacencyList[nbr.getNodeIndex()]);
					} else if (isNodeVisited[nbr.getNodeIndex()]) {
						if (pathCost[nodeIndex] + nbr.getWeight() < pathCost[nbr
								.getNodeIndex()]) {
							if (queue
									.contains(adjacencyList[nbr.getNodeIndex()]))
								queue.remove(adjacencyList[nbr.getNodeIndex()]);
							parent[nbr.getNodeIndex()] = nodeIndex;
							pathCost[nbr.getNodeIndex()] = pathCost[nodeIndex]
									+ nbr.getWeight();
							queue.offer(adjacencyList[nbr.getNodeIndex()]);
						}
					}
				}
			}
		}
		return -1;
	}

	private int runBFS() {
		initialize();
		Queue<Node> queue = new PriorityQueue<Node>(adjacencyList.length,
				new PQueue<Node>());
		int startIndex = getNodeIndex(startState);
		if (startIndex == -1) {
			System.err.println("Invalid input start state: " + startState);
			System.exit(1);
		}
		queue.offer(adjacencyList[startIndex]);
		isNodeVisited[startIndex] = true;
		int j = 0;
		while (!queue.isEmpty()) {
			Node n = queue.poll();
			log[j++] = n.getName();
			if (n.getName().equals(goalState)) {
				return getNodeIndex(n.getName());
			} else {
				for (Neighbour nbr = n.getAdjacent(); nbr != null; nbr = nbr
						.getNext()) {
					int nodeIndex = getNodeIndex(n.getName());
					if (!isNodeVisited[nbr.getNodeIndex()]) {
						isNodeVisited[nbr.getNodeIndex()] = true;
						parent[nbr.getNodeIndex()] = nodeIndex;
						pathCost[nbr.getNodeIndex()] = pathCost[nodeIndex]
								+ nbr.getWeight();
						queue.offer(adjacencyList[nbr.getNodeIndex()]);
					}
				}
			}
		}
		return -1;
	}

	private int runDFS() {
		initialize();
		Integer unitPathCost[] = new Integer[adjacencyList.length];
		for (int j = 0; j < unitPathCost.length; j++) {
			unitPathCost[j] = 0;
		}
		Stack<Node> stack = new Stack<Node>();
		int startIndex = getNodeIndex(startState);
		if (startIndex == -1) {
			System.err.println("Invalid input start state: " + startState);
			System.exit(1);
		}
		stack.push(adjacencyList[startIndex]);
		isNodeVisited[startIndex] = true;
		int j = 0;
		while (!stack.empty()) {
			Node n = stack.pop();
			log[j++] = n.getName();
			if (n.getName().equals(goalState)) {
				return getNodeIndex(n.getName());
			} else {
				for (Neighbour nbr = n.getAdjacent(); nbr != null; nbr = nbr
						.getNext()) {
					int nodeIndex = getNodeIndex(n.getName());
					if (!isNodeVisited[nbr.getNodeIndex()]) {
						isNodeVisited[nbr.getNodeIndex()] = true;
						parent[nbr.getNodeIndex()] = nodeIndex;
						pathCost[nbr.getNodeIndex()] = pathCost[nodeIndex]
								+ nbr.getWeight();
						unitPathCost[nbr.getNodeIndex()] = unitPathCost[nodeIndex] + 1;
						stack.push(adjacencyList[nbr.getNodeIndex()]);
					} else if (isNodeVisited[nbr.getNodeIndex()]) {
						if (unitPathCost[nodeIndex] + 1 < unitPathCost[nbr
								.getNodeIndex()]) {
							parent[nbr.getNodeIndex()] = nodeIndex;
							pathCost[nbr.getNodeIndex()] = pathCost[nodeIndex]
									+ nbr.getWeight();
							unitPathCost[nbr.getNodeIndex()] = unitPathCost[nodeIndex] + 1;
							stack.push(adjacencyList[nbr.getNodeIndex()]);
						}
					}
				}
			}
		}
		return -1;
	}

	private void printOutput(int i) throws IOException {
		out = new BufferedWriter(new FileWriter("output.txt"));
		// if (i <= -1) {
		// out.write("NoPathAvailable");
		// return;
		// }
		int m = 0;
		while (true) {
			out.write(log[m++]);
			if (m < log.length && log[m] != null) {
				out.write("-");
			} else
				break;
		}
		out.newLine();
		if (i > -1) {
			String path[] = new String[adjacencyList.length];

			int k = 0;
			path[k++] = adjacencyList[i].getName();
			for (int j = parent[i]; j > -1; j = parent[j]) {
				path[k++] = adjacencyList[j].getName();
			}
			for (int j = k - 1; j > 0; j--) {
				out.write(path[j] + "-");
			}
			out.write(path[0]);
			out.newLine();
			out.write(pathCost[i].toString());
		} else {
			out.write("NoPathAvailable");
		}
	}

	public Graph() {
		String line;
		try {
			in = new BufferedReader(new FileReader("input.txt"));
			searchType = in.readLine();
			if (!"1".equals(searchType) && !"2".equals(searchType)
					&& !"3".equals(searchType)) {
				System.err
						.println("Invalid search type : "
								+ searchType
								+ ". Valid inputs for search type are either 1, 2 or 3");
				System.exit(1);
			}
			startState = in.readLine();
			goalState = in.readLine();
			no_of_states = Integer.parseInt(in.readLine());
			adjacencyList = new Node[no_of_states];
			for (int i = 0; i < no_of_states; i++) {
				line = in.readLine();
				adjacencyList[i] = new Node(line, null);
			}
			for (int i = 0; i < no_of_states; i++) {
				line = in.readLine();
				String[] neighbours = line.split(" ");
				if (neighbours.length != adjacencyList.length) {
					System.err.println("Incorrect adjacency matrix input : "
							+ line);
					System.exit(1);
				}
				for (int j = 0; j < neighbours.length; j++) {
					int weight = Integer.parseInt(neighbours[j]);
					if (weight > 0) {
						Neighbour adjacent = adjacencyList[i].getAdjacent();
						String newName = adjacencyList[j].getName();
						Neighbour newElem = new Neighbour(
								getNodeIndex(newName), newName, weight);
						if (adjacent == null
								|| newName.compareTo(adjacent.getName()) >= 0) {
							newElem.setNext(adjacent);
							adjacencyList[i].setAdjacent(newElem);
						} else {
							while (adjacent.getNext() != null) {
								if (newName.compareTo(adjacent.getName()) >= 0) {
									newElem.setNext(adjacent.getNext());
									adjacent.setNext(newElem);
									break;
								} else
									adjacent = adjacent.getNext();
							}
							if (adjacent.getNext() == null) {
								newElem.setNext(null);
								adjacent.setNext(newElem);
							}
						}
					}
				}
			}
			if ("1".equals(searchType)) {
				int i = runBFS();
				printOutput(i);
			} else if ("2".equals(searchType)) {
				int i = runDFS();
				printOutput(i);
			} else {
				int i = runUCS();
				printOutput(i);
			}
		} catch (IOException ioe) {
			System.err
					.println("Error occurred while performing file I/O operation: "
							+ ioe.getLocalizedMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException io) {
					System.err
							.println("Error occurred while closing input file: "
									+ io.getLocalizedMessage());
				}

			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException io) {
					System.err
							.println("Error occurred while closing output file: "
									+ io.getLocalizedMessage());
				}
			}
		}
	}

	public static void main(String args[]) {
		new Graph();
	}
}
