import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

  private Set<Point2D> set;

  public PointSET() {
    set = new TreeSet<>();
  }

  public boolean isEmpty() {
    return set.isEmpty();
  }

  public int size() { return set.size(); }

  public void insert(Point2D p) {
    set.add(p);
  }

  public boolean contains(Point2D p) {
    return set.contains(p);
  }

  public void draw() {
    set.stream().forEach(p -> p.draw());
  }

  public Iterable<Point2D> range(RectHV rect) {
    List<Point2D> range = new ArrayList<>();

    set.stream().forEach(p -> {
      if(isInsideRect(p, rect)){
        range.add(p);
      }
    });

    return range;
  }

  private boolean isInsideRect(Point2D p, RectHV rect) {
    return p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax();
  }

  public Point2D nearest(Point2D origin) {
    Point2D closest = null;
    double nearest = -1;
    double candidateDistance;

    for (Point2D p : set) {
      if (p.equals(origin)) continue;

      candidateDistance = getDistance(p, origin);
      if( isCloser(nearest, candidateDistance) ){
        closest = p;
        nearest = candidateDistance;
      }
    }

    return closest;
  } // a nearest neighbor in the set to point p; null if the set is empty

  private double getDistance(Point2D p, Point2D origin) {
    double diffX = Math.abs(Math.abs(origin.x()) - Math.abs(p.x()));
    double diffY = Math.abs(Math.abs(origin.y()) - Math.abs(p.y()));

    return diffX + diffY;
  }

  private boolean isCloser(double current, double candidate) {
    return current == -1 || candidate < current;
  }

  public static void main(String[] args) {}                 // unit testing of the methods (optional)
}
