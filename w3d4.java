import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class Scratch {

    public static void main(String[] args) {

        char[] labels = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
        int[][] graphs = new int[][]{
                {0, 1, 1, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
        };
        List<List<Integer>> components = findConnectedComponentsDFS(graphs);
        List<List<Integer>> components2 = findConnectedComponentsBFS(graphs);
        StringBuilder sb = new StringBuilder();
        String arrow = " -> ";
        System.out.println("DFS: ");
        for (List<Integer> component : components) {
            for (Integer i : component) {
                sb.append(labels[i]);
                sb.append(arrow);
            }
            sb.delete(sb.length() - arrow.length(), sb.length());
            System.out.println(sb);
            sb.delete(0, sb.length());
        }
        System.out.println("BFS: ");
        for (List<Integer> component : components2) {
            for (Integer i : component) {
                sb.append(labels[i]);
                sb.append(arrow);
            }
            sb.delete(sb.length() - arrow.length(), sb.length());
            System.out.println(sb);
            sb.delete(0, sb.length());
        }
    }

    public static List<List<Integer>> findConnectedComponentsDFS(int[][] adjacencyMatrix) {
        int numVertices = adjacencyMatrix.length;
        int[] visited = new int[numVertices];
        Stack<Integer> stack = new Stack<>();
        List<List<Integer>> connectedComponents = new ArrayList<>();

        for (int startVertex = 0; startVertex < visited.length; startVertex++) {
            if (visited[startVertex] != 1) {
                visited[startVertex] = 1;
                stack.push(startVertex);
                List<Integer> currentComponent = new ArrayList<>();
                currentComponent.add(startVertex);

                while (!stack.isEmpty()) {
                    int currentVertex = stack.peek();
                    int adjacentVertex = 0;
                    for (; adjacentVertex < numVertices; adjacentVertex++) {
                        if (adjacencyMatrix[currentVertex][adjacentVertex] == 1 && visited[adjacentVertex] != 1) {
                            visited[adjacentVertex] = 1;
                            currentComponent.add(adjacentVertex);
                            stack.push(adjacentVertex);
                            break;
                        }
                    }
                    if (adjacentVertex == numVertices) {
                        stack.pop();
                    }
                }

                connectedComponents.add(currentComponent);
            }
        }

        return connectedComponents;
    }

    public static List<List<Integer>> findConnectedComponentsBFS(int[][] adjacencyMatrix) {
        int numVertices = adjacencyMatrix.length;
        int[] visited = new int[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        List<List<Integer>> connectedComponents = new ArrayList<>();

        for (int startVertex = 0; startVertex < numVertices; startVertex++) {
            if (visited[startVertex] == 0) { // If the vertex is unvisited
                List<Integer> currentComponent = new ArrayList<>();
                queue.add(startVertex);
                visited[startVertex] = 1; // Mark as visited

                while (!queue.isEmpty()) {
                    int currentVertex = queue.poll();
                    currentComponent.add(currentVertex);

                    for (int adjacentVertex = 0; adjacentVertex < numVertices; adjacentVertex++) {
                        if (adjacencyMatrix[currentVertex][adjacentVertex] == 1 && visited[adjacentVertex] == 0) {
                            queue.add(adjacentVertex);
                            visited[adjacentVertex] = 1; // Mark as visited
                        }
                    }
                }

                connectedComponents.add(currentComponent); // Add the component to the list
            }
        }

        return connectedComponents;
    }
}
