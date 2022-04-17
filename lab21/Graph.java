import jdk.jshell.execution.Util;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.IOException;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v)); //could use a Set/TreeSet as parameter
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
        // TODO: YOUR CODE HERE
//        HashMap<Integer, Edge> distFromTree = new HashMap<Integer, Edge>(); //
        ArrayList<Edge> distFromTree = new ArrayList<>();
        PriorityQueue<Edge> everyOutsideEdge = new PriorityQueue<Edge>();

        ArrayList<Integer> visited = new ArrayList<>();
        int currVertex = start;
        visited.add(start);
        while (visited.size() != getAllVertices().size()){
            if (!getEdges(currVertex).isEmpty()) {// store every (Edge) from start to neighbour, in whole a treeSet
                Iterator<Edge> iter = getEdges(currVertex).iterator();
                while (iter.hasNext()) {
                    Edge next = iter.next();
                    if (!visited.contains(next.getDest())) {
                        everyOutsideEdge.add(next);
                    } else {
                        everyOutsideEdge.remove(next);//don't have to do the back check, check Edge equals
                    }
                }

                //deal with someDisjoint case!
                if (everyOutsideEdge.isEmpty()){
                    ArrayList<Integer> unvisited = new ArrayList<>();
                    for (int i = 0; i< neighbors.size(); i++){
                        if (!visited.contains(i)){
                            unvisited.add(i);
                        }
                    }
                    if (!unvisited.isEmpty()){
                        currVertex = unvisited.get(0);
                    }
                    visited.add(currVertex);
                    continue;
                }

                Edge currShortestEdge = everyOutsideEdge.poll(); //remove in the meanwhile, which is good
//                if (distFromTree.containsKey(currShortestEdge.getSource())) {
//                    distFromTree.put(currShortestEdge.getDest(), currShortestEdge);
//                    currVertex = currShortestEdge.getDest();
//                } else {
//                    distFromTree.put(currShortestEdge.getSource(), currShortestEdge);
//                    currVertex = currShortestEdge.getDest();
//                }
                distFromTree.add(currShortestEdge);
                currVertex = currShortestEdge.getDest();
            } else{//disjoint node
                ArrayList<Integer> unvisited = new ArrayList<>();
                for (int i = 0; i< neighbors.size(); i++){
                    if (!visited.contains(i)){
                        unvisited.add(i);
                    }
                }
                if (!unvisited.isEmpty()){
                    currVertex = unvisited.get(0);
                }else{
                    break;
                }
            }
            visited.add(currVertex);
        }
        Graph graph = new Graph();
        for (int i = 0; i<getAllVertices().size(); i++){
            graph.addVertex(i);
        }
        for (int i = 0; i<distFromTree.size(); i++){
//            if (distFromTree.containsKey(i)){
            graph.addEdge(distFromTree.get(i));
//            }
        }
        return graph;
    }

//    public Edge reverseEdge(Edge prevEdge){
//        return new Edge(prevEdge.getDest(), prevEdge.getSource(), prevEdge.getWeight());
//    }

    public Graph kruskals() {
        // TODO: YOUR CODE HERE
        ArrayList<Edge> distFromTree = new ArrayList<>();
        //HashMap<Integer, Edge> distFromTree = new HashMap<Integer, Edge>();
        UnionFind disjointSet = new UnionFind(neighbors.size());
        Iterator<Edge> iterator = allEdges.iterator();
        while (iterator.hasNext() && (distFromTree.size()-1) != neighbors.size()){
            Edge currSmallest = iterator.next();
            if(!disjointSet.connected(currSmallest.getSource(), currSmallest.getDest())){
                disjointSet.union(currSmallest.getDest(), currSmallest.getSource());
//                if (distFromTree.containsKey(currSmallest.getSource())) {
//                    distFromTree.put(currSmallest.getDest(), currSmallest);
//                } else {
//                    distFromTree.put(currSmallest.getSource(), currSmallest);
//                }

                distFromTree.add(currSmallest);
            }

        }

        Graph graph = new Graph();
        for (int i = 0; i<getAllVertices().size(); i++){
            graph.addVertex(i);
        }
        for (int i = 0; i<distFromTree.size(); i++){
//            if (distFromTree.containsKey(i)){
                graph.addEdge(distFromTree.get(i));
//            }
        }
        return graph;
    }

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public static void main(String[] args) {
        Graph g1p = loadFromText("graphTestNormal.in");
        Graph primG1 = g1p.prims(0);// passed!
        Graph g1k = loadFromText("graphTestNormal.in");
        Graph krG1 = g1k.kruskals();

        Graph g2p = loadFromText("graphTestAllDisjoint.in");
        Graph primG2 = g2p.prims(0);// passed!
        Graph g2k = loadFromText("graphTestAllDisjoint.in");
        Graph krG2 = g2k.kruskals();

        Graph g3p = loadFromText("graphTestMultiEdge.in");
        Graph primg3 = g3p.prims(0);// passed!
        Graph g3k = loadFromText("graphTestMultiEdge.in");
        Graph krG3 = g3k.kruskals();

        Graph g4p = loadFromText("graphTestSomeDisjoint.in");
        Graph primG4 =  g4p.prims(0);//
        Graph g4k = loadFromText("graphTestSomeDisjoint.in");
        Graph krG4 = g4k.kruskals();
    }
}