import example.CSCourseDB;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private final Map<Long, Node> Nodes = new LinkedHashMap<>();
    private final Map<Long, Edge> Edges = new LinkedHashMap<>();
    private final HashSet<String> all_names = new HashSet<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        Nodes.entrySet()
                .removeIf(entry -> (entry.getValue().adj.size() == 0));
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return Nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        Node node_v = Nodes.get(v);
        HashSet res = node_v.adj;
        return res;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
//        if (lat > MapServer.ROOT_ULLAT || lat < MapServer.ROOT_LRLAT
//                || lon < MapServer.ROOT_ULLON || lon > MapServer.ROOT_LRLON){
//            throw new IllegalArgumentException("out of the range");
//        }
        //KDtree to find the nearest neighbors
        //Step 1 creat a List to store all the vertices
        List<KDTree.Point> all_the_points = new ArrayList<>();
        Iterable<Long> verticesIterable = vertices();
        for (long v : verticesIterable) {
            all_the_points.add(
                    new KDTree.Point(
                            Nodes.get(v).id,
                            Nodes.get(v).lon,
                            Nodes.get(v).lat,
                            null,
                            null));
        }
        KDTree KDT = new KDTree(all_the_points);
        KDTree.Point p = KDT.nearest(lon, lat);
        return p.id;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return Nodes.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return Nodes.get(v).lat;
    }
    // add a node to the GraphDB
    void addNode(Node n){
        Nodes.put(n.id, n);
        if (n.extraInfo.containsKey("name")){
            String s = cleanString(n.extraInfo.get("name"));
            if (s != null && s.length() != 0) {
                all_names.add(s);
            }
        }
    }
    // add a way to the GraphDB and connect the nodes on the way
    void addEdge(Edge e){
        Edges.put(e.id, e);
        if (e.extraInfo.containsKey("name")){
            String s = cleanString(e.extraInfo.get("name"));
            if (s != null && s.length() != 0) {
                all_names.add(s);
            }
        }
    }
    HashSet<String> getAll_names(){
        return all_names;
    }

    void connect(ArrayList<Long> possible_nodes){
        if (possible_nodes.size() > 1){
            for (int i =1; i < possible_nodes.size(); i++){
                Nodes.get(possible_nodes.get(i)).adj.add(possible_nodes.get(i - 1));
                Nodes.get(possible_nodes.get(i - 1)).adj.add(possible_nodes.get(i));
            }
        }
    }
    // remove a node
    private void removeNode(long v){
        Nodes.remove(v);
    }






    // Node class to store the node information with the neibors in a HashSet
    static class Node{
        long id;
        double lon;
        double lat;
        HashSet<Long> adj;
        Map<String, String> extraInfo;
        public Node(long id, double lon, double lat){
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            adj = new HashSet<>();
            this.extraInfo = new HashMap<>();
        }
    }
    static class Edge{
        long id;
        Map<String, String> extraInfo;
        public Edge(long id){
            this.id = id;
            this.extraInfo = new HashMap<>();
        }
    }
}
