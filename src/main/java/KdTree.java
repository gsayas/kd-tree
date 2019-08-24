
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

  private boolean enableDebug = false;
  private Point2D champion;

  private enum Axis {
    Y, X
  }

  private enum Side {
    LEFT, RIGHT
  }

  private class Node {
    private Node left;
    private Node right;
    private Point2D point;
    private Axis axis;
    private Side side;

    public Node(Point2D point, Axis axis, Side side) {
      this.point = point;
      this.axis = axis;
      this.side = side;
    }
  }

  private Node root;
  private int size;

  public KdTree() {
    size = 0;
  }

  public boolean isEmpty() { return root == null; }

  public int size()  { return size; }

  public void insert(Point2D p) {
    root = insert(root, p, Axis.X, null);
  }

  private Node insert(Node x, Point2D p, Axis axis, Side side) {
    if(x == null) {
      size++;
      return new Node(p, axis, side);
    }

    int compare = compareByAxis(x, p, switchAxis(axis));
    if(compare < 0){
      if(x.right == null) {
        debug(String.format("Inserting to the %s on axis %s ", Side.RIGHT, switchAxis(axis)));
      }
      x.right = insert(x.right, p, switchAxis(axis), Side.RIGHT);
    }else if(compare > 0) {
      if(x.left == null){
        debug(String.format("Inserting to the %s on axis %s ", Side.LEFT, switchAxis(axis)));
      }
      x.left = insert(x.left, p, switchAxis(axis), Side.LEFT);
    }else {
      x.point = p;
    }
    return x;
  }

  private void debug(String msg) {
    if(enableDebug) System.out.println(msg);
  }

  private int compareByAxis(Node x, Point2D p, Axis axis) {
    int res;
    boolean isXAxis = (axis == Axis.X);
    if(isXAxis) {
      res = Double.compare(p.x(), x.point.x());
    }else {
      res = Double.compare(x.point.y(), p.y());
    }
    return res;
  }

  private Axis switchAxis(Axis axis) {
    return axis == Axis.X ? Axis.Y : Axis.X;
  }

  public boolean contains(Point2D p) {
    if(isEmpty()){
      return false;
    }
    return contains(root, p, Axis.X);
  }

  public boolean contains(Node x, Point2D p, Axis axis) {
    if(x == null) {
      return false;
    }

    int compare = compareByAxis(x, p, switchAxis(axis));
    if(compare < 0){
      return contains(x.right, p, switchAxis(axis));
    }else if(compare > 0) {
      return contains(x.left, p, switchAxis(axis));
    }else {
      return true;
    }
  }

  public void draw() {
    draw(root, Axis.X, null);
  }

  private void draw(Node node, Axis axis, Node parent) {
      if(node != null) {
        switchColor(axis);
        StdDraw.point(node.point.x(), node.point.y());
        drawLine(node, axis, parent);
        draw(node.left, switchAxis(axis), node);
        draw(node.right, switchAxis(axis), node);
      }
  }

  private void switchColor(Axis axis) {
    StdDraw.setPenColor(axis == Axis.X ? StdDraw.RED : StdDraw.BLUE);
  }

  private void drawLine(Node node, Axis axis, Node parent) {
    if(axis == Axis.X){
      drawHorizontalLine(node, axis, parent);
    }else {
      drawVerticalLine(node, axis, parent);
    }
  }

  private void drawVerticalLine(Node node, Axis axis, Node parent) {
    debug("Drawing Vertical Line:");
    double limit = 1;
    double origin = 0;

    if(node.side == Side.LEFT) {
      StdDraw.line(node.point.x(), origin, node.point.x(), limit);
      debug(String.format("From: (%f, %f) to: (%f, %f)", node.point.x(), origin, node.point.x(), limit));
    } else if(node.side == Side.RIGHT) {
      StdDraw.line(node.point.x(), origin, node.point.x(), limit);
      debug(String.format("From: (%f, %f) to: (%f, %f)", node.point.x(), origin, node.point.x(), limit));
    } else {
      StdDraw.line(node.point.x(), 0, node.point.x(), 1);
      debug(String.format("From: (%f, %f) to: (%f, %f)", node.point.x(), 0.0, node.point.x(), 1.0));
    }
  }

  private void drawHorizontalLine(Node node, Axis axis, Node parent) {
    debug("Drawing Horizontal Line:");
    double limit = 1;
    double origin = 0;

    if(node.side == Side.LEFT) {
      StdDraw.line(origin, node.point.y(), limit, node.point.y());
      debug(String.format("From: (%f, %f) to: (%f, %f)", origin, node.point.y(), limit, node.point.y()));
    } else if(node.side == Side.RIGHT) {
      StdDraw.line(origin, node.point.y(), limit, node.point.y());
      debug(String.format("From: (%f, %f) to: (%f, %f)", origin, node.point.y(), limit, node.point.y()));
    } else {
      StdDraw.line(0.0, node.point.y(), 1.0, node.point.y());
      debug(String.format("From: (%f, %f) to: (%f, %f)", 0.0, node.point.y(), 1.0, node.point.y()));
    }
  }

  public Iterable<Point2D> range(RectHV rect)  {
    return range(rect, root, Axis.X);
  }

  private List<Point2D> range(RectHV rect, Node node, Axis axis) {
    List<Point2D> range = new ArrayList<>();

    if(rect.contains(node.point)){
      range.add(node.point);
    }

    if(rectGoesLeft(rect, node, switchAxis(axis))){
      range.addAll(range(rect, node.left, switchAxis(axis)));
    }

    if(rectGoesRight(rect, node, switchAxis(axis))){
      range.addAll(range(rect, node.right, switchAxis(axis)));
    }

    return range;
  }

  private boolean rectGoesLeft(RectHV rect, Node node, Axis axis) {
    if(axis == Axis.X && node.point.x() < rect.xmax()){
      return true;
    } else if(axis == Axis.Y && node.point.y() < rect.ymax()){
      return true;
    } else {
      return false;
    }
  }

  private boolean rectGoesRight(RectHV rect, Node node, Axis axis) {
    if(axis == Axis.X && node.point.x() > rect.xmin()){
      return true;
    } else if(axis == Axis.Y && node.point.y() > rect.ymin()){
      return true;
    } else {
      return false;
    }
  }

  public Point2D nearest(Point2D p) {
    champion = null;
    findAndSetChampion(root, p);
    return champion;
  }

  private void findAndSetChampion(Node subtree, Point2D p) {
    if(isBetterChampion(subtree.point, p)){
      champion = subtree.point;
    }

    Side sideTowardsPoint = findSideTowardsPoint(subtree, p);
    Node subtreeTowardsPoint = getSubtreeTowardsPoint(subtree, sideTowardsPoint);

    if(subtreeTowardsPoint != null) {
      findAndSetChampion(subtreeTowardsPoint, p);
    }

    Node subtreeAwayFromPoint = getSubtreeTowardsPoint(subtree, switchSide(sideTowardsPoint));
    if(subtreeAwayFromPoint != null) {
      if (couldOtherSideContainChampion(switchSide(sideTowardsPoint), subtreeAwayFromPoint, p)) {
        findAndSetChampion(subtreeAwayFromPoint, p);
      }
    }

  }

  private boolean couldOtherSideContainChampion(Side switchSide, Node subtreeAwayFromPoint,
      Point2D p) {
    return true;
  }

  private Side findSideTowardsPoint(Node subtree, Point2D p) {
    int cmp = compareByAxis(subtree, p, switchAxis(subtree.axis));
    return cmp < 1 ? Side.RIGHT : Side.LEFT;
  }

  private boolean isBetterChampion(Point2D candidate, Point2D query) {
    if(champion == null) return true;
    Comparator<Point2D> comparator = query.distanceToOrder();
    int cmp = comparator.compare(champion, candidate);
    return cmp < 0;
  }

  private Node getSubtreeTowardsPoint(Node subtree, Side sideTowardsPoint) {
    return sideTowardsPoint == Side.LEFT ? subtree.left : subtree.right;
  }

  private Side switchSide(Side side) {
    return side == Side.LEFT ? Side.RIGHT : Side.LEFT;
  }

}
