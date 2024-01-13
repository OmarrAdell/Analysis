import java.util.*;

public class graph {
    private List<Integer>[] adjacencyList;

    public graph(int vertices, int[][] edges) {
        adjacencyList = new List[vertices + 1];
        for (int i = 1; i <= vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            adjacencyList[from].add(to);
        }
    }

    public void depthFirstSearch(int startNode) {
        boolean[] visited = new boolean[adjacencyList.length];
        System.out.println("Depth-First Search:");
        dfs(startNode, visited);
    }

    private void dfs(int currentNode, boolean[] visited) {
        if (!visited[currentNode]) {
            System.out.print(currentNode + " ");
            visited[currentNode] = true;
            for (int neighbor : adjacencyList[currentNode]) {
                dfs(neighbor, visited);
            }
        }
    }

    public void breadthFirstSearch(int startNode) {
        boolean[] visited = new boolean[adjacencyList.length];
        System.out.println("\nBreadth-First Search:");
        bfs(startNode, visited);
    }

    private void bfs(int startNode, boolean[] visited) {
        int[] queue = new int[adjacencyList.length];
        int front = 0, rear = 0;

        queue[rear++] = startNode;
        visited[startNode] = true;

        while (front < rear) {
            int currentNode = queue[front++];
            System.out.print(currentNode + " ");
            for (int neighbor : adjacencyList[currentNode]) {
                if (!visited[neighbor]) {
                    queue[rear++] = neighbor;
                    visited[neighbor] = true;
                }
            }
        }
    }

    public void printTreeLeftToRight() {
        boolean[] visited = new boolean[adjacencyList.length];
        System.out.println("\nTree (Left to Right):");
        dfsLeftToRight(1, visited);
    }

    private void dfsLeftToRight(int currentNode, boolean[] visited) {
        if (!visited[currentNode]) {
            System.out.print(currentNode + " ");
            visited[currentNode] = true;
            List<Integer> neighbors = adjacencyList[currentNode];
            Collections.sort(neighbors); // Sort neighbors from left to right
            for (int neighbor : neighbors) {
                dfsLeftToRight(neighbor, visited);
            }
        }
    }

    public void breadthFirstSearchLeftToRight() {
        boolean[] visited = new boolean[adjacencyList.length];
        System.out.println("\nBreadth-First Search (Left to Right):");
        bfsLeftToRight(1, visited);
    }

    private void bfsLeftToRight(int startNode, boolean[] visited) {
        int[] queue = new int[adjacencyList.length];
        int front = 0, rear = 0;

        queue[rear++] = startNode;
        visited[startNode] = true;

        while (front < rear) {
            int currentNode = queue[front++];
            System.out.print(currentNode + " ");
            List<Integer> neighbors = adjacencyList[currentNode];
            Collections.sort(neighbors); // Sort neighbors from left to right
            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    queue[rear++] = neighbor;
                    visited[neighbor] = true;
                }
            }
        }
    }

    public void printCycles() {
        System.out.println("\nCycles in the Graph:");
        boolean[] visited = new boolean[adjacencyList.length];
        for (int node = 1; node < adjacencyList.length; node++) {
            if (!visited[node]) {
                detectCycle(node, new boolean[adjacencyList.length], visited, new int[adjacencyList.length]);
            }
        }
    }

    private void detectCycle(int currentNode, boolean[] onStack, boolean[] visited, int[] path) {
        onStack[currentNode] = true;
        visited[currentNode] = true;
        path[currentNode] = 1;

        for (int neighbor : adjacencyList[currentNode]) {
            if (onStack[neighbor]) {
                printCycle(path, neighbor);
            } else if (!visited[neighbor]) {
                detectCycle(neighbor, onStack, visited, path);
            }
        }

        onStack[currentNode] = false;
        path[currentNode] = 0;
    }

    private void printCycle(int[] path, int startNode) {
        for (int i = startNode; i < path.length; i++) {
            if (path[i] != 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println(startNode);
    }

    public boolean isBipartite() {
        System.out.println("\nChecking Bipartiteness:");
        int[] colors = new int[adjacencyList.length];
        int[] queue = new int[adjacencyList.length];
        int front = 0, rear = 0;

        queue[rear++] = 1; // Starting node
        colors[1] = 1;

        while (front < rear) {
            int currentNode = queue[front++];
            int currentColor = colors[currentNode];

            for (int neighbor : adjacencyList[currentNode]) {
                if (colors[neighbor] == 0) {
                    colors[neighbor] = 3 - currentColor;
                    queue[rear++] = neighbor;
                } else if (colors[neighbor] == currentColor) {
                    System.out.println("Graph is not Bipartite.");
                    return false;
                }
            }
        }

        System.out.println("Graph is Bipartite.");
        return true;
    }

    public static void main(String[] args) {
        // Example graph representation
        int vertices = 4;
        int[][] edges = {{1, 3}, {1, 4}, {2, 1}, {2, 3}, {3, 4}, {4, 1}, {4, 2}};

        graph graph = new graph(vertices, edges);
        graph.depthFirstSearch(1);
        graph.breadthFirstSearch(1);
        graph.printTreeLeftToRight();
        graph.breadthFirstSearchLeftToRight();
        graph.printCycles();
        graph.isBipartite();
    }
}




//To determine whether an undirected graph is a tree or not, you can use depth-first search (DFS) or breadth-first search (BFS) to traverse the graph and check for two conditions:
//Connectivity: Every node in the graph must be reachable from every other node. This means that after performing DFS or BFS starting from any node, all nodes in the graph should be visited.
//Acyclic Structure: The graph must be acyclic, meaning it should not contain any cycles. If there is a cycle in the graph, it cannot be a tree because trees are inherently acyclic structures.

//running time 
//The DFS traversal visits each vertex once, contributing to the O(V) term.
//In the worst case, each edge is examined once during the DFS traversal, contributing to the O(E) term.
//Therefore, the overall time complexity is O(V + E).
