import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import org.junit.Assert;
import org.junit.Test;

public class KdTreeTest {

  @Test
  public void testIsEmpty() {
    KdTree tree = new KdTree();
    Assert.assertTrue(tree.isEmpty());
    tree.insert(new Point2D(1,1));
    Assert.assertFalse(tree.isEmpty());
  }

  @Test
  public void testSize() {
    KdTree tree = new KdTree();
    Assert.assertEquals(0, tree.size());
    tree.insert(new Point2D(1,1));
    Assert.assertEquals(1, tree.size());
    tree.insert(new Point2D(1,1));
    Assert.assertEquals(1, tree.size());
    tree.insert(new Point2D(1,2));
    Assert.assertEquals(2, tree.size());
  }

  @Test
  public void testContains() {

    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 2);
    Point2D p3 = new Point2D(1, 3);

    assertFalse(tree.contains(p1));

    tree.insert(p1);
    assertTrue(tree.contains(p1));
    assertTrue(tree.contains(p2));

    assertFalse(tree.contains(p3));
    tree.insert(p3);
    assertTrue(tree.contains(p3));
  }

  @Test
  //Sep14 14:30pm
  public void testContainsDegeneratePoints() {

    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(0.5, 0.5);
    Point2D p2 = new Point2D(0.16, 0.5);

    tree.insert(p1);
    assertTrue(tree.contains(p1));
    assertFalse(tree.contains(p2));
  }

  @Test
  public void testRange() {

    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 3);
    tree.insert(p1);
    tree.insert(p2);

    RectHV rect = new RectHV(1, 1, 1, 2);
    assertEquals(1, StreamSupport.stream(tree.range(rect).spliterator(), false).count());
  }

  @Test
  //Sep14 18:48pm
  public void testRange2() {

    KdTree tree = new KdTree();

    List<Point2D> points = new ArrayList<>();
    points.add( new Point2D(0.372, 0.497));
    points.add( new Point2D(0.564, 0.413));
    points.add( new Point2D(0.226, 0.577));
    points.add( new Point2D(0.144, 0.179));
    points.add( new Point2D(0.083, 0.51));
    points.add( new Point2D(0.32, 0.708));
    points.add( new Point2D(0.417, 0.362));
    points.add( new Point2D(0.862, 0.825));
    points.add( new Point2D(0.785, 0.725));
    points.add( new Point2D(0.499, 0.208));

    points.stream().forEach(point -> tree.insert(point));

    RectHV rect = new RectHV(0.745, 0.538, 0.91, 0.948);
    assertEquals(2, StreamSupport.stream(tree.range(rect).spliterator(), false).count());
    StreamSupport.stream(tree.range(rect).spliterator(), false).forEach(point -> System.out.println(point));
  }

  @Test
  //Sep14 18:48pm
  public void testRangeTraversal2() {

    KdTree tree = new KdTree();

    List<Point2D> points = new ArrayList<>();
    points.add( new Point2D(0.375, 0.75));
    points.add( new Point2D(0.1875, 0.25));
    points.add( new Point2D(0.875, 0.5));
    points.add( new Point2D(1.0, 0.9375));
    points.add( new Point2D(0.25, 0.0));
    points.add( new Point2D(0.8125, 0.375));
    points.add( new Point2D(0.0625, 0.4375));
    points.add( new Point2D(0.3125, 0.125));
    points.add( new Point2D(0.75, 0.0625));
    points.add( new Point2D(0.125, 0.8125));

    points.stream().forEach(point -> tree.insert(point));

    RectHV rect = new RectHV(0.5625, 0.625, 0.6875,0.875);
    assertEquals(0, StreamSupport.stream(tree.range(rect).spliterator(), false).count());
    StreamSupport.stream(tree.range(rect).spliterator(), false).forEach(point -> System.out.println(point));
  }

  @Test
  public void testRangeDistinctPoints() {

    KdTree st = new KdTree();
    st.insert(new Point2D(0.0, 1.0));
    st.insert(new Point2D(1.0, 1.0));
    RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    assertEquals(2, StreamSupport.stream(st.range(rect).spliterator(), false).count());
    StreamSupport.stream(st.range(rect).spliterator(), false).forEach(point -> System.out.println(point));
  }

  @Test
  public void testNearest() {

    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(1, 2);
    Point2D p2 = new Point2D(1, 3);
    Point2D p3 = new Point2D(1, 4);
    Point2D p4 = new Point2D(1, 1.9);

    tree.insert(p1);
    tree.insert(p2);
    tree.insert(p3);
    //tree.insert(p4);

    //System.out.println(tree.nearest(p4));
    assertTrue(p1.equals(tree.nearest(p4)));
  }

  @Test
  public void testNearestWithEmptyTree() {
    KdTree tree = new KdTree();
    assertEquals(null, tree.nearest(new Point2D(1, 1)));

  }

  @Test
  public void testNearestWithRandomPoints() {
    KdTree tree = new KdTree();
    List<Point2D> points = new ArrayList<>();
    points.add( new Point2D(0.7, 0.2));
    points.add( new Point2D(0.5, 0.4));
    points.add( new Point2D(0.2, 0.3));
    points.add( new Point2D(0.4, 0.7));
    points.add( new Point2D(0.9, 0.6));
    points.stream().forEach(point -> tree.insert(point));

    assertEquals(new Point2D(0.9, 0.6), tree.nearest(new Point2D(0.984, 0.39)));

  }

  @Test
  public void testRangeTraversal() {
    //TODO: integrate mockito
    KdTree st = new KdTree();
    List<Point2D> points = new ArrayList<>();
    points.add( new Point2D(0.7, 0.2));
    points.add( new Point2D(0.5, 0.4));
    points.add( new Point2D(0.2, 0.3));
    points.add( new Point2D(0.4, 0.7));
    points.add( new Point2D(0.9, 0.6));
    points.stream().forEach(point -> st.insert(point));
    RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    assertEquals(2, StreamSupport.stream(st.range(rect).spliterator(), false).count());
    StreamSupport.stream(st.range(rect).spliterator(), false).forEach(point -> System.out.println(point));
  }

}
