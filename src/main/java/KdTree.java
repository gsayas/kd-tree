
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
    private int id;

    public Node(Point2D point, Axis axis, Side side, int id) {
      this.point = point;
      this.axis = axis;
      this.side = side;
      this.id = id;
    }

    public int getId() { return this.id; }
  }

  private Node root;
  private int size;

  public KdTree() {
    size = 0;
  }

  public boolean isEmpty() { return root == null; }

  public int size()  { return size; }

  public void insert(Point2D p) {
    if(p == null) throw new java.lang.IllegalArgumentException();
    root = insert(root, p, Axis.X, null);
  }

  private Node insert(Node x, Point2D p, Axis axis, Side side) {
    if(x == null) {
      size++;
      return new Node(p, axis, side, size);
    }

    int compare = compareByAxis(x, p, switchAxis(axis));
    if(compare <= 0){
      if(x.right == null) {
        debug(String.format("Inserting point %d to the RIGHT of %d on axis %s ", size + 1, x.getId(), switchAxis(axis)));
      }
      if(!p.equals(x.point)) {
        x.right = insert(x.right, p, switchAxis(axis), Side.RIGHT);
      }else{
        x.point = p;
      }
    }else if(compare > 0) {
      if(x.left == null){
        debug(String.format("Inserting point %d to the LEFT of %d on axis %s ", size + 1, x.getId(), switchAxis(axis)));
      }
      x.left = insert(x.left, p, switchAxis(axis), Side.LEFT);
    }
    debug(String.format("there are %d nodes after this action", size()));
    return x;
  }

  private void debug(String msg) {
    if(enableDebug) System.out.println(msg);
  }

  private int compareByAxis(Node x, Point2D p, Axis axis) {
    int res;
    boolean isXAxis = (axis == Axis.X);
    if(isXAxis) {
      res = Double.compare(x.point.x(), p.x());
    }else {
      res = Double.compare(x.point.y(), p.y());
    }
    return res;
  }

  private Axis switchAxis(Axis axis) {
    return axis == Axis.X ? Axis.Y : Axis.X;
  }

  public boolean contains(Point2D p) {
    if(p == null) throw new java.lang.IllegalArgumentException();
    if(isEmpty()){
      return false;
    }
    return contains(root, p, Axis.X);
  }

  private boolean contains(Node node, Point2D point, Axis axis) {
    if(node == null) {
      return false;
    }

    int compare = compareByAxis(node, point, switchAxis(axis));
    if(compare <= 0){
      if(node.point.equals(point)){
        return true;
      }else{
        return contains(node.right, point, switchAxis(axis));
      }
    }else {
      return contains(node.left, point, switchAxis(axis));
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
    //debug("Drawing Vertical Line:");
    double limit = 1;
    double origin = 0;

    if(node.side == Side.LEFT) {
      StdDraw.line(node.point.x(), origin, node.point.x(), limit);
      //debug(String.format("From: (%f, %f) to: (%f, %f)", node.point.x(), origin, node.point.x(), limit));
    } else if(node.side == Side.RIGHT) {
      StdDraw.line(node.point.x(), origin, node.point.x(), limit);
      //debug(String.format("From: (%f, %f) to: (%f, %f)", node.point.x(), origin, node.point.x(), limit));
    } else {
      StdDraw.line(node.point.x(), 0, node.point.x(), 1);
      //debug(String.format("From: (%f, %f) to: (%f, %f)", node.point.x(), 0.0, node.point.x(), 1.0));
    }
  }

  private void drawHorizontalLine(Node node, Axis axis, Node parent) {
    //debug("Drawing Horizontal Line:");
    double limit = 1;
    double origin = 0;

    if(node.side == Side.LEFT) {
      StdDraw.line(origin, node.point.y(), limit, node.point.y());
      //debug(String.format("From: (%f, %f) to: (%f, %f)", origin, node.point.y(), limit, node.point.y()));
    } else if(node.side == Side.RIGHT) {
      StdDraw.line(origin, node.point.y(), limit, node.point.y());
      //debug(String.format("From: (%f, %f) to: (%f, %f)", origin, node.point.y(), limit, node.point.y()));
    } else {
      StdDraw.line(0.0, node.point.y(), 1.0, node.point.y());
      //debug(String.format("From: (%f, %f) to: (%f, %f)", 0.0, node.point.y(), 1.0, node.point.y()));
    }
  }

  public Iterable<Point2D> range(RectHV rect)  {
    if(rect == null) throw new java.lang.IllegalArgumentException();
    return range(rect, root, Axis.X);
  }

  private List<Point2D> range(RectHV rect, Node node, Axis axis) {
    List<Point2D> range = new ArrayList<>();

    if(node == null){
      return range;
    }

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
    if(axis == Axis.X && rect.xmin() < node.point.x()){
      return true;
    } else if(axis == Axis.Y && rect.ymin() < node.point.y()){
      return true;
    } else {
      return false;
    }
  }

  private boolean rectGoesRight(RectHV rect, Node node, Axis axis) {
    if(axis == Axis.X && rect.xmax() > node.point.x()){
      return true;
    } else if(axis == Axis.Y && rect.ymax() > node.point.y()){
      return true;
    } else {
      return false;
    }
  }

  public Point2D nearest(Point2D p) {
    if(p == null) throw new java.lang.IllegalArgumentException();
    champion = null;
    if(isEmpty()){
      return null;
    }
    findAndSetChampion(root, p);
    return champion;
  }

  private void findAndSetChampion(Node subtree, Point2D p) {
    //System.out.println(String.format("Examining point: %s",  subtree.point.toString()));
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
    return cmp > 0;
  }

  private Node getSubtreeTowardsPoint(Node subtree, Side sideTowardsPoint) {
    return sideTowardsPoint == Side.LEFT ? subtree.left : subtree.right;
  }

  private Side switchSide(Side side) {
    return side == Side.LEFT ? Side.RIGHT : Side.LEFT;
  }

}
