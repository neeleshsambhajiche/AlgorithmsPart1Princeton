import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @since 08/10/19
 */
public class KdTree {

    private Node root;
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private class Node {
        private Point2D p;
        private Node left, right;
        private int size = 1;
        private boolean axis;
        private RectHV rect;
        public Node(Point2D p, boolean axis, RectHV rect) {
            this.p = p;
            this.axis = axis;
            this.rect = rect;
        }

        public RectHV rectLeft() {
            if(axis == VERTICAL) {
                return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
            }
            else return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }

        public RectHV rectRight() {
            if(axis == VERTICAL) {
                return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            }
            else return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }


    }

    private void put(Point2D p)
    {
        if(root == null) {
            root = new Node(p, VERTICAL, new RectHV(0,0,1,1));
        }
        else root = put(root, p, null);
    }


    private Node put(Node n, Point2D p, Node prev)
    {
        if(n == null) {
            boolean previousAxis = prev.axis;
            if((prev.axis == VERTICAL && p.x() < prev.p.x()) || (prev.axis == HORIZONTAL && p.y() < prev.p.y()))
                n = new Node(p, !previousAxis, prev.rectLeft());
            else n = new Node(p, !previousAxis, prev.rectRight());
            return n;
        }
        prev = n;

        if (p.compareTo(n.p) == 0) return n;
        else if ((n.axis == VERTICAL && p.x() < n.p.x()) || (n.axis == HORIZONTAL && p.y() < n.p.y()))
            n.left = put(n.left, p, prev);
        else n.right = put(n.right, p, prev);
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public  boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        if(!contains(p)) {
            put(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument is null");
        Node n = root;
        while (n != null)
        {
            if(p.compareTo(n.p) == 0) return true;
            else if ((n.axis == VERTICAL && p.x() < n.p.x()) || (n.axis == HORIZONTAL && p.y() < n.p.y()))
                n = n.left;
            else n = n.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {

    }

    private void checkIfInsideRect(Node n, RectHV rect, ArrayList<Point2D> pAL) {
        if(n == null) return;
        if(rect.contains(n.p))
            pAL.add(n.p);
        if((n.axis == VERTICAL && n.p.x() >= rect.xmin() && n.p.x() <= rect.xmax()) || (n.axis == HORIZONTAL && n.p.y() >= rect.ymin() && n.p.y() <= rect.ymax()))  {
            checkIfInsideRect(n.left, rect, pAL);
            checkIfInsideRect(n.right, rect, pAL);
        }
        else if((n.axis == VERTICAL && n.p.x() >= rect.xmax()) || (n.axis == HORIZONTAL && n.p.y() >= rect.ymax())) {
            checkIfInsideRect(n.left, rect, pAL);
        }
        else {
            checkIfInsideRect(n.right, rect, pAL);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new IllegalArgumentException("Null Argument");
        ArrayList<Point2D> insideBoundaryPoints = new ArrayList<>();
        checkIfInsideRect(root, rect, insideBoundaryPoints);
        return insideBoundaryPoints;
    }

    private Node findNearest(Point2D p, Node n, Node minNode) {

        if (n.rect.distanceTo(p) < minNode.p.distanceTo(p)) {
            if (n.p.distanceTo(p) <= minNode.p.distanceTo(p)) {
                minNode = n;
            }
            if ((n.axis == VERTICAL && p.x() < n.p.x()) || (n.axis == HORIZONTAL && p.y() < n.p.y())) {
                if (n.left != null) minNode = findNearest(p, n.left, minNode);
                if (n.right != null) minNode = findNearest(p, n.right, minNode);
            } else if ((n.axis == VERTICAL && p.x() >= n.p.x()) || (n.axis == HORIZONTAL && p.y() >= n.p.y())) {
                if (n.right != null) minNode = findNearest(p, n.right, minNode);
                if (n.left != null) minNode = findNearest(p, n.left, minNode);
            }
        }
        return minNode;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(isEmpty()) return null;
        if(p == null) throw new IllegalArgumentException("Null Argument");
        Node n = root;
        Node minNode = root;
        minNode = findNearest(p, n, minNode);
        return minNode.p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree ps = new KdTree();
        ps.insert(new Point2D(0.7,0.2));
        ps.insert(new Point2D(0.5,0.4));
        ps.insert(new Point2D(0.2,0.3));
        ps.insert(new Point2D(0.4,0.7));
        ps.insert(new Point2D(0.9,0.6));

        Point2D n = ps.nearest(new Point2D(0.715,0.983));
        System.out.println(n.toString());
    }
}
