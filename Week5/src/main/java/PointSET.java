import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @since 08/10/19
 */
public class PointSET {

    private SET<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public  boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(!set.contains(p))
            set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        Iterator<Point2D> pointSetIterator = set.iterator();
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        while(pointSetIterator.hasNext()) {
            pointSetIterator.next().draw();
        }
        StdDraw.point(1,1);
        StdDraw.show();

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new IllegalArgumentException("Null Argument");
        ArrayList<Point2D> insideBoundaryPoints = new ArrayList<>();
        Iterator<Point2D> pointSetIterator = set.iterator();
        while(pointSetIterator.hasNext()) {
            Point2D p =pointSetIterator.next();
            if(p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax())
                insideBoundaryPoints.add(p);
        }
        return insideBoundaryPoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(set.isEmpty()) return null;
        if(p == null) throw new IllegalArgumentException("Null Argument");
        Iterator<Point2D> pointSetIterator = set.iterator();
        Point2D minPoint = pointSetIterator.next();
        double minDist = minPoint.distanceTo(p);
        while(pointSetIterator.hasNext()) {
            Point2D curr =pointSetIterator.next();
            if(curr.distanceTo(p) < minDist) {
                minPoint = curr;
                minDist = curr.distanceTo(p);
            }
        }
        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET ps = new PointSET();
        Point2D p = new Point2D(3,3);
        ps.insert(new Point2D(0,0));
        ps.insert(new Point2D(0.75,0.75));
        ps.insert(new Point2D(0.75,0.5));
        ps.insert(new Point2D(0.75,0.25));
        ps.nearest(new Point2D(0.5,0.25));
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        ps.draw();
        p.draw();
        StdDraw.show();

        Point2D n = ps.nearest(new Point2D(3,3));
        System.out.println(n.toString());
        System.out.println(ps.size());

    }
}
