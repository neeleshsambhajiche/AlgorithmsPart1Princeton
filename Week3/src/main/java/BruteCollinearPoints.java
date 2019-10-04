import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * @since 22/09/19
 */
public class BruteCollinearPoints {

    private int numSeg = 0;
    private LineSegment[] LSArray = new LineSegment[0];
    private Point[] input;

    private void resize(LineSegment[] oldLSArray) {
        LineSegment[] newLSArray = new LineSegment[oldLSArray.length + 1];
        for(int i = 0; i <  oldLSArray.length; i++) {
            newLSArray[i] = oldLSArray[i];
        }
        LSArray =  newLSArray;
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if(points == null) throw new IllegalArgumentException("Argument is null");
        input = Arrays.copyOf(points, points.length);

        for(int i = 0; i < input.length; i++) {
            if (input[i] == null) throw new IllegalArgumentException("Point is null");
        }

        Arrays.sort(input);

        for(int i = 0; i < input.length - 1; i++) {
            if (input[i].compareTo(input[ i + 1]) == 0) throw new IllegalArgumentException("Repeated Point");
        }

        for(int i = 0; i < input.length - 3; i++) {
            for(int j = i + 1; j < input.length - 2; j++) {
                for(int k = j + 1; k < input.length - 1; k++) {
                    for(int l = k + 1; l < input.length; l++) {
                        double slope1 = input[i].slopeTo(input[j]);
                        double slope2 = input[i].slopeTo(input[k]);
                        double slope3 = input[i].slopeTo(input[l]);
                        if(slope1 == slope2 && slope2 == slope3) {
                            Point[] collinearPoints = new Point[4];
                            collinearPoints[0] = input[i];
                            collinearPoints[1] = input[j];
                            collinearPoints[2] = input[k];
                            collinearPoints[3] = input[l];
                            Arrays.sort(collinearPoints);
                            LineSegment ls = new LineSegment(collinearPoints[0], collinearPoints[3]);
                            numSeg++;
                            if(numSeg > LSArray.length) {
                                resize(LSArray);
                            }
                            LSArray[numSeg - 1] = ls;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(LSArray, LSArray.length);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
