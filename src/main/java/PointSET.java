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
    if(p == null) throw new java.lang.IllegalArgumentException();
    set.add(p);
  }

  public boolean contains(Point2D p) {
    if(p == null) throw new java.lang.IllegalArgumentException();
    return set.contains(p);
  }

  public void draw() {
    set.stream().forEach(p -> p.draw());
  }

  public Iterable<Point2D> range(RectHV rect) {
    if(rect == null) throw new java.lang.IllegalArgumentException();
    List<Point2D> range = new ArrayList<>();

    set.stream().forEach(p -> {
      if(rect.contains(p)){
        range.add(p);
      }
    });

    return range;
  }

  public Point2D nearest(Point2D query) {
    if(query == null) throw new java.lang.IllegalArgumentException();
    Point2D closestPoint = null;
    double nearestDistance = -1;
    double candidateDistance;

    for (Point2D p : set) {
      candidateDistance = getDistance(p, query);
      if( isCloser(candidateDistance, nearestDistance) ){
        closestPoint = p;
        nearestDistance = candidateDistance;
      }
    }

    return closestPoint;
  } // a nearest neighbor in the set to point p; null if the set is empty

  private double getDistance(Point2D p, Point2D origin) {
    return p.distanceSquaredTo(origin);
  }

  private boolean isCloser(double candidate, double current) {
    return current == -1 || candidate < current;
  }

  public static void main(String[] args) {}                 // unit testing of the methods (optional)
}
