import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KDTree{
    private Point root;
    private int size;
    private double minDist;
    private Point minPoint;

    public KDTree(List<Point> points){
        root = null;
        size = 0;
        for (int i = 0; i < points.size(); i++){
            insert(points.get(i));
            size++;
        }
        minDist = Integer.MAX_VALUE;
        minPoint = root;
    }
    public Point nearest(double x, double y){
        Point target = new Point(0, x, y, null, null);
        nearestHelper(target, root, 0);
        return minPoint;
    }
        private void nearestHelper(Point target, Point curr, int level){
        if (curr == null){
            return;
        }
        double bestDist = customDist(target, minPoint);
        double currDist = customDist(target, curr);
        if (currDist < bestDist){
            minPoint = curr;
        }
        Point goodside;
        Point badside;
        boolean cmp = comparePoints(target, curr, level);
        if (cmp){
            goodside = curr.left;
            badside = curr.right;
        }else{
            goodside = curr.right;
            badside = curr.left;
        }
        nearestHelper(target, goodside, 1-level);
        if (isWorth(target, curr, level)){
            nearestHelper(target, badside, 1-level);
        }
    }
//     Check whether the badSide intersects with the circle that,
//     centred at target point with radius of square distance between
//     target point and best point. If intersects, then the badSide is
//     worth looking.
    private boolean isWorth(Point target, Point curr, int level){
        double bestDist = customDist(target, minPoint);
        double badDist = 0;
        if (level == 0){
            badDist = customDist(target, new Point(0, curr.data[0], target.data[1], null, null));
        }else{
            badDist = customDist(target, new Point(0, target.data[0], curr.data[1], null, null));
        }
        return badDist < bestDist;

    }
    private double customDist(Point A, Point B){
        return GraphDB.distance(A.data[0], A.data[1], B.data[0], B.data[1]);
    }



//    private void nearestHelper(Point target, Point curr, int level){
//        if (curr == null){
//            return;
//        }
//        double bestDist = euclidean_distance(target, minPoint);
//        double currDist = euclidean_distance(target, curr);
//        if (currDist < bestDist){
//            minPoint = curr;
//        }
//        Point goodside;
//        Point badside;
//        boolean cmp = comparePoints(target, curr, level);
//        if (cmp){
//            goodside = curr.left;
//            badside = curr.right;
//        }else{
//            goodside = curr.right;
//            badside = curr.left;
//        }
//        nearestHelper(target, goodside, 1-level);
//        if (isWorth(target, curr, level)){
//            nearestHelper(target, badside, 1-level);
//        }
//    }
    // Check whether the badSide intersects with the circle that,
    // centred at target point with radius of square distance between
    // target point and best point. If intersects, then the badSide is
    // worth looking.
//    private boolean isWorth(Point target, Point curr, int level){
//        double bestDist = euclidean_distance(target, minPoint);
//        double badDist = 0;
//        if (level == 0){
//            badDist = euclidean_distance(target, new Point(0, curr.data[0], target.data[1], null, null));
//        }else{
//            badDist = euclidean_distance(target, new Point(0, target.data[0], curr.data[1], null, null));
//        }
//        return badDist < bestDist;
//
//    }


    private boolean comparePoints(Point A, Point B, int level){
        return A.data[level] < B.data[level];
    }
    private boolean isEqual(Point A, Point B){
        if (A.data[0] == B.data[0] && A.data[1] == B.data[1]){
            return true;
        }
        return false;
    }

    public int size(){
        return size;
    }
    // return the square of the euclidean_distance between two points
    private double euclidean_distance (Point A, Point B){
        return (A.data[0] - B.data[0]) * (A.data[0] - B.data[0])
                + (A.data[1] - B.data[1]) * (A.data[1] - B.data[1]);
    }
    private boolean isLeaf(Point p){
        return p.left == null && p.right == null;
    }
    private void insert(Point p){
        root = insertHelper(root, p, 0);
    }

    private Point insertHelper(Point t, Point p, int level){
        if (t == null){
            return new Point(p.id, p.data[0], p.data[1], null, null);
        }
        else if (p.data[level] < t.data[level]){
            t.left = insertHelper(t.left, p, 1 - level);
        }
        else{
            t.right = insertHelper(t.right, p, 1 - level);
        }
        return t;
    }


    static class Point{
        long id;
        double[] data;
        Point left, right;
        public Point(long id, double x, double y, Point left, Point right){
            data = new double[]{x, y};
            this.left = left;
            this.right = right;
            this.id = id;
        }

    }

    public static void main(String[] args){

        List<Point> pointSets = new ArrayList<>();
        Point a1 = new Point(1,3, 6, null, null);
        Point a2 = new Point(2,2, 7, null, null);
        Point a3 = new Point(3,17, 15, null, null);
        Point a4 = new Point(4,6, 12, null, null);
        Point a5 = new Point(5,13, 15, null, null);
        Point a6 = new Point(6,9, 1, null, null);
        Point a7 = new Point(7,10, 19, null, null);
        pointSets.add(a1);
        pointSets.add(a2);
        pointSets.add(a3);
        pointSets.add(a4);
        pointSets.add(a5);
        pointSets.add(a6);
        pointSets.add(a7);
        KDTree KDT = new KDTree(pointSets);
        Point min = KDT.nearest(8,2);
        System.out.println(Arrays.toString(min.data));
    }
}