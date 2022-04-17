import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

//    public LinkedList<Edge>[] reverse(LinkedList<Edge>[] adjLists){
//        LinkedList<Edge>[] reverseLists = (LinkedList<Edge>[]) new LinkedList[vertexCount];
//        for (int k = 0; k < vertexCount; k++) {
//            reverseLists[k] = new LinkedList<Edge>();
//        }
//        for (int k = 0; k < vertexCount; k++) {
//            for (Edge eachEdge : adjLists[k]){
//                reverseLists[eachEdge.to].add(new Edge(eachEdge.to, k, eachEdge.weight));
//            }
//        }
//        return reverseLists;
//    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        boolean isChanged = false;
        Edge newEdge = new Edge(v1, v2, weight);
        for (Edge eachEdge : adjLists[v1]){
            if (eachEdge.to == newEdge.to){
                eachEdge.weight = weight;
                isChanged = true;
            }
        }
        if (!isChanged){
            adjLists[v1].add(newEdge);
        }
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
        // TODO: YOUR CODE HERE
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
        for (Edge eachEdge : adjLists[from]){
            if (eachEdge.to == to){
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // TODO: YOUR CODE HERE
        List<Integer> neightbourList = new ArrayList<Integer>();
        for (Edge eachEdge : adjLists[v]){
            neightbourList.add(eachEdge.to);
        }
        return neightbourList;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // TODO: YOUR CODE HERE
        int count = 0;
        for (int i = 0; i<vertexCount; i++){
            for (Edge eachEdge : adjLists[i]){
                if (eachEdge.to == v){
                    count++;
                }
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }
    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        // TODO: YOUR CODE HERE
        List<Integer> DFS = dfs(start);
        if(DFS.contains(stop)){
            return true;
        }
        return false;
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        // TODO: YOUR CODE HERE
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(start);

        while (iter.hasNext()) {
            int next = iter.next();
            result.add(next);
            if (next == stop) {
                break;
            }
        }
        List<Integer> toReturn = new ArrayList<>();
        if(pathExists(start, stop)) {
            toReturn.add(0, stop);
            int curr = result.size() - 1;
            int prev = result.size() - 2;
            while (result.get(prev) != start) {
                if (isAdjacent(result.get(prev), result.get(curr))) {
                    toReturn.add(0, result.get(prev));
                    curr = prev;
                }
                prev--;
            }
            if (!(start == stop)) {
                toReturn.add(0, start);
            }
        }
        return toReturn;
    }
//        List<Integer> toReturn = new ArrayList<>();
//        List<Integer> vistied = new ArrayList<>();
//        for (int i = 0; i < vertexCount; i++) {
//            for (int possibleStop : neighbors(i)) {
//                if (possibleStop == stop) {
//                    toReturn.add(possibleStop);//stop=3
//                    vistied.add(possibleStop);
//                    toReturn = pathHelper(start, i, toReturn, vistied); //start=1, i/newStop = 2;
//                    toReturn.add(start);
//                    List<Integer> correctOrderToReturn = new ArrayList<>();
//                    for (int j=toReturn.size()-1; j>-1; j++){
//                        correctOrderToReturn.add(j);
//                    }
//                    return correctOrderToReturn;
//                }
//            }
//        }
//        return new ArrayList<Integer>();
//    }
//
//    public List<Integer> pathHelper(int start, int newStop, List toReturn, List visited){//newStop = 2;
//            if (start == newStop) {
//                return toReturn;
//            }
//            for (int i = 0; i < vertexCount; i++) {
//                for (int possibleStop : neighbors(i)) {
//                    if (possibleStop == newStop) {
//                        if (!visited.contains(i)){
//                            toReturn.add(possibleStop);
//                            visited.add(possibleStop);
//                            toReturn = pathHelper(start, i, toReturn, visited);
//                            return toReturn;
//                        }
//                    }
//                }
//            }
//        return new ArrayList<Integer>();
//        }


    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private int[] currentInDegree;
        private HashSet<Integer> visited;

        private Stack<Integer> tempStack;

        // TODO: Instance variables here!

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            tempStack = new Stack<Integer>();
            visited = new HashSet<>();
            // TODO: YOUR CODE HERE
            currentInDegree = new int[vertexCount];
            for (int i = 0; i<vertexCount; i++){
                currentInDegree[i] = inDegree(i);
                if(inDegree(i) == 0){
                    fringe.push(i);
                }
            }

        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE
            if (!fringe.isEmpty()){
                int i = fringe.pop();
                while (visited.contains(i)){
                    if (fringe.isEmpty()){
                        return false;
                    }
                    i = fringe.pop();
                }//already checked repeated

                //below check currentIndegree is 0
                while (currentInDegree[i] != 0){
                    if (fringe.isEmpty()){
                        return false;
                    }
                    tempStack.push(i);
                    i = fringe.pop();
                }// i is the indegree = 0 one

                //below restore the tempStack
                while (!tempStack.isEmpty()){
                    int k = tempStack.pop();
                    fringe.push(k);
                }
                //below push back the next i(target)
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            // TODO: YOUR CODE HERE
            int curr = fringe.pop();
            visited.add(curr);
            for (int nextNode : neighbors(curr)){
                currentInDegree[nextNode]--;
            }
            for (int l = 0; l<vertexCount; l++){
                if (currentInDegree[l] == 0){
                    fringe.push(l);
                }
            }
            return curr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public void addHelper(Vertex currVertex, ArrayList<Integer> shortestPath){
        if (currVertex.predecessor == null){
            shortestPath.add(currVertex.nodeNumber);
            return;
        }
        shortestPath.add(currVertex.nodeNumber);
        currVertex = currVertex.predecessor;
        addHelper(currVertex, shortestPath);
    }

    public ArrayList<Integer> reverseList(ArrayList<Integer> shortestPath){
        ArrayList<Integer> correctOrderList = new ArrayList<>();
        int originalSize = shortestPath.size();
        for (int i = 0; i<originalSize; i++){
            correctOrderList.add(0, shortestPath.remove(0));
        }
        return correctOrderList;
    }

        public ArrayList<Integer> shortestPath(int start, int stop) {
        double inf = Double.POSITIVE_INFINITY;
        PriorityQueue<Vertex> unvisited = new PriorityQueue<>();
        ArrayList<Vertex> visited = new ArrayList<>();
        ArrayList<Vertex> waitingList = new ArrayList<>();
        ArrayList <Integer> shortestPath = new ArrayList<>();
        for (int i = 0; i < adjLists.length; i++){ // add all the vertex execpt start to the waitingList
            if (i != start){
                waitingList.add(new Vertex(i, (int)inf, null));
            }
        }

        unvisited.add(new Vertex(start, 0, null));//add start itself
        Vertex currVertex = unvisited.poll(); // set currVertex to the start

        while (true){

            if (currVertex.nodeNumber == stop){ // return case when we reach the stop
                addHelper(currVertex, shortestPath); //adds the vertex to shortest from the stop to start
                return reverseList(shortestPath); // reorder to go from start to stop
            }else {
                visited.add(currVertex);
                unvisited.remove(currVertex); // add currVertex to visited and remove from unvisited
                boolean alreadyVisited = false;
                boolean alreadybeingUnVisited = false;
                Vertex currentbeingUnVisited = null;
                List neighbourList = neighbors(currVertex.nodeNumber); //  get all of currVertex neighbors

                while (!neighbourList.isEmpty()) { // 3 cases possible for a neighbour
                    int currNeighboutNumber = (int) neighbourList.remove(0); // get the number of neighbor
                    for (Vertex everyVertex : visited) { // check if neighbor is already visited, if yes then continue
                        if (everyVertex.nodeNumber == currNeighboutNumber){
                            alreadyVisited = true;
                        }
                    }
                    if (alreadyVisited){
                        alreadyVisited = false;
                        continue; //if already in visited, then should not do the operations below
                    }

                    for (Vertex everyVertex : unvisited) { //get the vertex of the neighbor (before was only the number)
                        if (everyVertex.nodeNumber == currNeighboutNumber){
                            alreadybeingUnVisited = true;
                            currentbeingUnVisited = everyVertex; //copy them to a new Vertex, in order to compare shorter path
                        }
                    }
                    if (alreadybeingUnVisited){ // check if distance is less from currVertex, if yes change predecessor and distanceFromStart
                        alreadybeingUnVisited = false;
                            if (currentbeingUnVisited.distanceFromStart > (currVertex.distanceFromStart + getEdge(currVertex.nodeNumber, currentbeingUnVisited.nodeNumber).weight)) {// now eachVertex is currNeighbour
                                currentbeingUnVisited.predecessor = currVertex;
                                currentbeingUnVisited.distanceFromStart = currVertex.distanceFromStart + getEdge(currVertex.nodeNumber, currentbeingUnVisited.nodeNumber).weight;
                                //remain unvisited
                            }
                    }

                    ArrayList<Vertex> copy = new ArrayList<>();
                    for (Vertex eachVertex : waitingList) { // add neighbor to the PQ if it is still waiting for it to be checked(check its neighbors) and remove from waiting
                        if (eachVertex.nodeNumber == currNeighboutNumber) {// now eachVertex is currNeighbour
                            eachVertex.predecessor = currVertex;
                            eachVertex.distanceFromStart = currVertex.distanceFromStart + getEdge(currVertex.nodeNumber, currNeighboutNumber).weight;
                            unvisited.add(eachVertex);
                            copy.add(eachVertex);
                        }
                    }
                    for (int i=0; i<copy.size(); i++){
                        waitingList.remove(copy.remove(0));
                    }
                }
                currVertex = unvisited.poll();
            }
        }
    }

    public class Vertex implements Comparable<Vertex>{
        private int nodeNumber;
        private int distanceFromStart;
        private Vertex predecessor;

        Vertex(int nodeNumber, int distanceFromStart, Vertex predecessor){
            this.nodeNumber = nodeNumber;
            this.distanceFromStart = distanceFromStart;
            this.predecessor = predecessor;
        }

        @Override
        public int compareTo(Vertex o) {
            return this.distanceFromStart - o.distanceFromStart;
        }
    }

    public Edge getEdge(int u, int v) {
        // TODO: YOUR CODE HERE
        for (Edge eachEach: adjLists[u]){
            if (eachEach.to == v){
                return eachEach;
            }
        }
        return null;
    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();

        Graph g3 = new Graph(7);
        g3.generateG3();
        g3.printDFS(0);
        g3.printDFS(2);
        g3.printDFS(3);
        g3.printDFS(4);

        g3.printPath(0, 3);
        g3.printPath(0, 4);
        g3.printPath(1, 3);
        g3.printPath(1, 4);
        g3.printPath(4, 0);

//        g1.printDFS(2);
//        g1.printDFS(3);
//        g1.printDFS(4);
//
//        g1.printPath(0, 3);
//        g1.printPath(0, 4);
//        g1.printPath(1, 3);
//        g1.printPath(1, 4);
//        g1.printPath(4, 0);
    }
}