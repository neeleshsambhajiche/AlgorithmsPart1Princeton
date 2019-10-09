import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @since 23/09/19
 */
public class FastCollinearPoints {

    private int numSeg = 0;
    private ArrayList<LineSegment> LSList = new ArrayList<>();
    private ArrayList<PointsOfLineSegment> plsList = new ArrayList<>();
    private final LineSegment[] lineSegments;

    private class SlopeToPoint implements Comparable<SlopeToPoint>{
        Point a;
        double slope;

        public int compareTo(SlopeToPoint that) {
            if(slope < that.slope) return -1;
            if(slope > that.slope) return 1;
            return 0;
        }
    }

    private class PointsOfLineSegment {
        Point a;
        Point b;
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if(points == null) throw new IllegalArgumentException("Argument is null");
        int N = points.length;
        Point[] sortedPoints = Arrays.copyOf(points, N);

        for(int i = 0; i < N; i++) {
            if (sortedPoints[i] == null) throw new IllegalArgumentException("Point is null");
        }

        Arrays.sort(sortedPoints);

        for(int i = 0; i < N - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) throw new IllegalArgumentException("Repeated Point");
        }

        for(int i = 0; i < N; i++) {
            Point currPoint = sortedPoints[i];
            Point[] sortedBySlope= Arrays.copyOf(sortedPoints, N);
            Arrays.sort(sortedBySlope, currPoint.slopeOrder());

            int k = 1;
            while (k < N) {
                LinkedList<Point> currentPossibleSeg = new LinkedList<Point>();
                double refSlope = currPoint.slopeTo(sortedBySlope[k]);
                do {
                    currentPossibleSeg.add(sortedBySlope[k++]);
                } while (k < N && currPoint.slopeTo(sortedBySlope[k]) == refSlope);

                if(currentPossibleSeg.size() >= 3) {
                    //Check if currPoint is lesser than all the points in currentPossibleSeg
                    boolean currPointIsLeast = true;
                    Iterator<Point> itr = currentPossibleSeg.iterator();
                    while(itr.hasNext()) {
                        if(currPoint.compareTo(itr.next()) > 0)
                            currPointIsLeast = false;
                    }
                    if(currPointIsLeast) {
                        Point max = currentPossibleSeg.removeLast();
                        numSeg++;
                        LSList.add(new LineSegment(currPoint, max));
                    }
                }
            }
        }
        lineSegments = LSList.toArray(new LineSegment[LSList.size()]);
    }

    // the number of line segments
    public  int numberOfSegments() {
        return numSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
