
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

  private boolean enableDebug = false;

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

    int cmp = compareByAxis(x, p, switchAxis(axis));
    if(cmp < 0){
      if(x.right == null) {
        debug(String.format("Inserting to the %s on axis %s ", Side.RIGHT, switchAxis(axis)));
      }
      x.right = insert(x.right, p, switchAxis(axis), Side.RIGHT);
    }else if(cmp > 0) {
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
    if(enableDebug) debug(msg);
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

  public boolean contains(Point2D p) { return false; }

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
    double limit = parent != null ? parent.point.y() : 1.0;
    double origin = parent != null ? parent.point.y() : 0.0;

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
    double limit = parent != null ? parent.point.x() : 1.0;
    double origin = parent != null ? parent.point.y() : 0.0;

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

  public Iterable<Point2D> range(RectHV rect)  { return null; }
  public Point2D nearest(Point2D p) { return null; }

}
