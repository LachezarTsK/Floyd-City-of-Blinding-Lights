import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {

	/**
	 * The grid is initialized with MAX_DISTANCE_PLUS_ONE. It is the result of:
	 * adding 1 to the multiplication of the maximum number of edges
	 * (maxNodes*(maxNodes-1)/2 = 399*200) with the maximum length of an edge(350).
	 * 
	 * Initialization with Integer.MAX_VALUE would necessitate a grid of type long
	 * in order to avoid negative values for the distances when applying
	 * Floyd-Warshall Algorithm.
	 */
	private static final int MAX_DISTANCE_PLUS_ONE = 399 * 200 * 350 + 1;
	private static int numberOfNodes;
	private static int numberOfEdges;
	private static int[][] grid;

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);

		numberOfNodes = Integer.parseInt(stringTokenizer.nextToken());
		numberOfEdges = Integer.parseInt(stringTokenizer.nextToken());
		initiaizeGrid();

		for (int j = 0; j < numberOfEdges; j++) {
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			int vertexOne = Integer.parseInt(stringTokenizer.nextToken());
			int vertexTwo = Integer.parseInt(stringTokenizer.nextToken());
			int edgeLength = Integer.parseInt(stringTokenizer.nextToken());
			grid[vertexOne][vertexTwo] = edgeLength;
		}

		getMinDistances_FloydWarshallAlgorithm();

		stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		int numberOfQueries = Integer.parseInt(stringTokenizer.nextToken());

		for (int i = 0; i < numberOfQueries; i++) {
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			int startVertex = Integer.parseInt(stringTokenizer.nextToken());
			int endVertex = Integer.parseInt(stringTokenizer.nextToken());
			int result;
			if (grid[startVertex][endVertex] != MAX_DISTANCE_PLUS_ONE) {
				result = grid[startVertex][endVertex];
			} else {
				result = -1;
			}
			bufferedWriter.write(result + "\n");
		}

		bufferedReader.close();
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	public static void initiaizeGrid() {
		grid = new int[numberOfNodes + 1][numberOfNodes + 1];

		for (int nodeOne = 1; nodeOne <= numberOfNodes; nodeOne++) {
			for (int nodeTwo = 1; nodeTwo <= numberOfNodes; nodeTwo++) {
				if (nodeOne != nodeTwo) {
					grid[nodeOne][nodeTwo] = MAX_DISTANCE_PLUS_ONE;
				}
			}
		}
	}

	/**
	 * Calculating the distances between any two connected nodes on the grid by
	 * applying Floyd-Warshall Algorithm. Modifying the corresponding grid values.
	 */
	public static void getMinDistances_FloydWarshallAlgorithm() {
		for (int middle = 1; middle <= numberOfNodes; middle++) {
			for (int start = 1; start <= numberOfNodes; start++) {
				for (int end = 1; end <= numberOfNodes; end++) {

					int currentDistance = grid[start][end];
					int alternativeDistance = grid[start][middle] + grid[middle][end];
					grid[start][end] = Math.min(currentDistance, alternativeDistance);
				}
			}
		}
	}
}
